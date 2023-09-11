package scheduler_mgmt.services.cud;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scheduler_mgmt.model.dto.SchedulerMaster_DTO;
import scheduler_mgmt.model.master.SchedulerMaster;
import scheduler_mgmt.model.repo.cud.SchedulerMasterPublicCUD_Repo;
import scheduler_mgmt.model.repo.read.SchedulerMasterPublicRead_Repo;

@Service("schedulerMasterPublicCUDServ")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class SchedulerMasterPublicCUD_Service implements I_SchedulerMasterPublicCUD_Service 
{
	private static final Logger logger = LoggerFactory.getLogger(SchedulerMasterPublicCUD_Service.class);

	@Autowired
	private Executor asyncExecutor;

	@Autowired
	private SchedulerMasterPublicRead_Repo schedulerMasterPublicReadRepo;

	@Autowired
	private SchedulerMasterPublicCUD_Repo schedulerMasterPublicCUDRepo;

	public CompletableFuture<SchedulerMaster_DTO> newSchedulerMaster(SchedulerMaster_DTO lMaster) {
		CompletableFuture<SchedulerMaster_DTO> future = CompletableFuture.supplyAsync(() -> {
			logger.info("creating schedule for");
			logger.info("Comp :" + Long.toString(lMaster.getCompanySeqNo()));
			logger.info("Rule :" + Long.toString(lMaster.getRuleSeqNo()));
			logger.info("Targ :" + Long.toString(lMaster.getTargetSeqNo()));
			logger.info("From :" + lMaster.getFromDttm());
			logger.info("To :" + lMaster.getToDttm());
			SchedulerMaster_DTO lMaster2 = null;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			LocalDateTime frdttm = LocalDateTime.parse(lMaster.getFromDttm(), formatter);
			LocalDateTime todttm = LocalDateTime.parse(lMaster.getToDttm(), formatter);
			Timestamp fdttm = Timestamp.valueOf(frdttm);
			Timestamp tdttm = Timestamp.valueOf(todttm);

			Long countChk = schedulerMasterPublicReadRepo.checkIfSExists(lMaster.getCompanySeqNo(),
					lMaster.getTargetSeqNo(), lMaster.getRuleSeqNo(), fdttm, tdttm);

			if (countChk == 0) {
				SchedulerMaster SchedulerMaster = schedulerMasterPublicReadRepo.save(this.setSchedulerMaster(lMaster));
				lMaster2 = getSchedulerMaster_DTO(SchedulerMaster);
				logger.info("created schedule");
			}
			return lMaster2;
		}, asyncExecutor);
		return future;
	}
	
	public CompletableFuture<Void> updSchedulerMaster(SchedulerMaster_DTO lMaster) 
	{
		CompletableFuture<Void> future = CompletableFuture.runAsync(() ->		{
		SchedulerMaster scheduleMaster = this.setSchedulerMaster(lMaster);
		if (schedulerMasterPublicReadRepo.existsById(lMaster.getRuleLineSeqNo())) 
		{
			scheduleMaster.setRuleSeqNo(lMaster.getRuleSeqNo());
			schedulerMasterPublicCUDRepo.save(scheduleMaster);
		}
		return;
	}, asyncExecutor);
	return future;

	}

	public CompletableFuture<Void> delSchedulerMaster(Long resourceSeqNo) 
	{
		CompletableFuture<Void> future = CompletableFuture.runAsync(() ->		{
		if (schedulerMasterPublicReadRepo.existsById(resourceSeqNo)) {
			schedulerMasterPublicCUDRepo.deleteById(resourceSeqNo);
		}
		return;
	}, asyncExecutor);
	return future;
	}

	public CompletableFuture<Void> delAllSchedulerMasters() 
	{
		CompletableFuture<Void> future = CompletableFuture.runAsync(() ->		{
		schedulerMasterPublicCUDRepo.deleteAll();
		return;
		}, asyncExecutor);
		return future;
	}

	public CompletableFuture<Void> delSelectSchedulers(CopyOnWriteArrayList<Long> ids) {
		
		CompletableFuture<Void> future = CompletableFuture.runAsync(() ->		{
		if (ids != null) 
		{
			schedulerMasterPublicCUDRepo.delSelectSchedules(ids);
		}
		return;
		}, asyncExecutor);
		return future;
	}
	
	private synchronized SchedulerMaster_DTO getSchedulerMaster_DTO(SchedulerMaster lMaster) {
		SchedulerMaster_DTO lDTO = new SchedulerMaster_DTO();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		lDTO.setFromDttm(formatter.format(lMaster.getFrDttm().toLocalDateTime()));
		lDTO.setToDttm(formatter.format(lMaster.getToDttm().toLocalDateTime()));
		lDTO.setRuleSeqNo(lMaster.getRuleSeqNo());
		lDTO.setFrtm(lMaster.getFrtm());
		lDTO.setTotm(lMaster.getTotm());
		lDTO.setCompanySeqNo(lMaster.getCompanySeqNo());
		lDTO.setJobTypeSeqNo(lMaster.getJobTypeSeqNo());
		lDTO.setRuleLineSeqNo(lMaster.getRuleLineSeqNo());
		lDTO.setScheduledFlag(lMaster.getScheduledFlag());
		lDTO.setScheduleData(lMaster.getScheduleData());
		lDTO.setTargetSeqNo(lMaster.getTargetSeqNo());
		return lDTO;
	}

	private synchronized SchedulerMaster setSchedulerMaster(SchedulerMaster_DTO lDTO) {
		SchedulerMaster lMaster = new SchedulerMaster();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime frDateTime = LocalDateTime.parse(lDTO.getFromDttm(), formatter);
		LocalDateTime toDateTime = LocalDateTime.parse(lDTO.getToDttm(), formatter);
		lMaster.setFrDttm(Timestamp.valueOf(frDateTime));
		lMaster.setToDttm(Timestamp.valueOf(toDateTime));
		lMaster.setRuleSeqNo(lDTO.getRuleSeqNo());
		lMaster.setFrtm(lDTO.getFrtm());
		lMaster.setTotm(lDTO.getTotm());
		lMaster.setCompanySeqNo(lDTO.getCompanySeqNo());
		lMaster.setJobTypeSeqNo(lDTO.getJobTypeSeqNo());
		lMaster.setScheduledFlag(lDTO.getScheduledFlag());
		lMaster.setScheduleData(lDTO.getScheduleData());
		lMaster.setTargetSeqNo(lDTO.getTargetSeqNo());		
		return lMaster;
	}

}
