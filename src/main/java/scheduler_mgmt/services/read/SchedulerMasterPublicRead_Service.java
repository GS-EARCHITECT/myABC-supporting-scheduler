package scheduler_mgmt.services.read;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scheduler_mgmt.model.dto.SchedulerDetail_DTO;
import scheduler_mgmt.model.dto.SchedulerMaster_DTO;
import scheduler_mgmt.model.master.SchedulerDetail;
import scheduler_mgmt.model.master.SchedulerDetailPK;
import scheduler_mgmt.model.master.SchedulerMaster;
import scheduler_mgmt.model.repo.cud.SchedulerDetailPublicCUD_Repo;
import scheduler_mgmt.model.repo.cud.SchedulerMasterPublicCUD_Repo;
import scheduler_mgmt.model.repo.read.SchedulerDetailPublicRead_Repo;
import scheduler_mgmt.model.repo.read.SchedulerMasterPublicRead_Repo;
import scheduler_rules_mgmt.model.master.RuleMaster;
import scheduler_rules_mgmt.model.repo.RuleMasterRepo;

@Service("schedulerMasterPublicReadServ")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class SchedulerMasterPublicRead_Service implements I_SchedulerMasterPublicRead_Service {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerMasterPublicRead_Service.class);

	@Autowired
	private Executor asyncExecutor;

	@Autowired
	private SchedulerMasterPublicRead_Repo schedulerMasterPublicReadRepo;

	@Autowired
	private SchedulerMasterPublicCUD_Repo schedulerMasterPublicCUDRepo;

	@Autowired
	private RuleMasterRepo ruleMasterRepo;

	@Autowired
	private SchedulerDetailPublicRead_Repo schedulerDetailPublicReadRepo;

	@Autowired
	private SchedulerDetailPublicCUD_Repo schedulerDetailPublicCUDRepo;

	@Scheduled(fixedRate = 10000)
	//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void createSchedules() 
	{
	//	CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			CopyOnWriteArrayList<SchedulerMaster> schedulerMasters = schedulerMasterPublicReadRepo.getSchedules();

			if (schedulerMasters != null) {
				SchedulerMaster schedulerMaster = null;
				CopyOnWriteArrayList<SchedulerDetail_DTO> schedulerDetailDTOs = null;
				Class<?> classRef = null;
				Method method = null;
				String methodName = null;
				String className = null;
				String packageName = null;
				Optional<RuleMaster> ruleMaster = null;
				CopyOnWriteArrayList<SchedulerDetail_DTO> schedulerDetailDTOs2 = null;
				Float cntRecs = (float) 0;

				for (int ctr = 0; ctr < schedulerMasters.size(); ctr++) {
					schedulerMaster = schedulerMasters.get(ctr);
					ruleMaster = ruleMasterRepo.findById(schedulerMasters.get(ctr).getRuleSeqNo());

					if (ruleMaster.isPresent() && ruleMaster != null) {
						methodName = ruleMaster.get().getFunctionName().trim();
						className = ruleMaster.get().getClassName().trim();
						packageName = ruleMaster.get().getClassPackage().trim();
						className = packageName + '.' + className;
						Object instance = null;

						try {
							classRef = Class.forName(className.trim());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							instance = classRef.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							method = classRef.getDeclaredMethod(methodName, SchedulerMaster.class);
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							schedulerDetailDTOs2 = (CopyOnWriteArrayList<SchedulerDetail_DTO>) method.invoke(instance,schedulerMaster);

							if (schedulerDetailDTOs2 != null) {
								cntRecs = schedulerDetailPublicReadRepo
										.getCountOfSchedules(schedulerMaster.getRuleLineSeqNo());
								if (cntRecs > 0) {
									schedulerDetailPublicCUDRepo
											.delSchedulesForRuleLine(schedulerMaster.getRuleLineSeqNo());
								}
								for (int i = 0; i < schedulerDetailDTOs2.size(); i++) {
									schedulerDetailPublicCUDRepo.save(this.setSchedulerDetail(schedulerDetailDTOs2.get(i)));
								}
							}
							schedulerMasterPublicCUDRepo.updateScheduleStatus('Y', schedulerMaster.getRuleLineSeqNo());

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Throwable actualException = e.getCause();
							actualException.printStackTrace();
						}
					}
				}
			}
	//		return;
		//}, asyncExecutor);
		return;
	}



	public CompletableFuture<CopyOnWriteArrayList<SchedulerMaster_DTO>> getAllSchedulerMasters() {

		CompletableFuture<CopyOnWriteArrayList<SchedulerMaster_DTO>> future = CompletableFuture.supplyAsync(() -> {
			CopyOnWriteArrayList<SchedulerMaster> resourceList = (CopyOnWriteArrayList<SchedulerMaster>) schedulerMasterPublicReadRepo
					.findAll();
			CopyOnWriteArrayList<SchedulerMaster_DTO> lMasterss = new CopyOnWriteArrayList<SchedulerMaster_DTO>();
			lMasterss = resourceList != null ? this.getSchedulerMaster_DTOs(resourceList) : null;
			return lMasterss;
		}, asyncExecutor);
		return future;
	}

	public CompletableFuture<SchedulerMaster_DTO> getSchedulerMasterById(Long scheduleSeqNo) {
		CompletableFuture<SchedulerMaster_DTO> future = CompletableFuture.supplyAsync(() -> {
			Optional<SchedulerMaster> SchedulerMaster = schedulerMasterPublicReadRepo.findById(scheduleSeqNo);
			SchedulerMaster_DTO lMasters = null;
			if (SchedulerMaster.isPresent()) {
				lMasters = SchedulerMaster != null ? this.getSchedulerMaster_DTO(SchedulerMaster.get()) : null;
			}
			return lMasters;
		}, asyncExecutor);
		return future;
	}

	public CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> getSelectSchedulesForCompanyTargetRule(
			Long cSeqNo, Long rSeqNo, Long tSeqNo) {
		CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> future = CompletableFuture.supplyAsync(() -> {
			CopyOnWriteArrayList<SchedulerDetail> schedulerDetails = schedulerDetailPublicReadRepo
					.getSelectSchedulesForCompanyTargetRule(cSeqNo, rSeqNo, tSeqNo);
			CopyOnWriteArrayList<SchedulerDetail_DTO> lDetailDTOs = schedulerDetails != null
					? this.getSchedulerDetailsDTOs(schedulerDetails)
					: null;
			return lDetailDTOs;
		}, asyncExecutor);
		return future;
	}

	public CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> getSelectSchedulesForRuleLine(Long rSeqNo) {
		CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> future = CompletableFuture.supplyAsync(() -> {
			CopyOnWriteArrayList<SchedulerDetail> schedulerDetails = schedulerDetailPublicReadRepo
					.getSelectSchedulesForRuleLine(rSeqNo);
			CopyOnWriteArrayList<SchedulerDetail_DTO> lDetailDTOs = schedulerDetails != null
					? this.getSchedulerDetailsDTOs(schedulerDetails)
					: null;
			return lDetailDTOs;
		}, asyncExecutor);
		return future;
	}

	private synchronized CopyOnWriteArrayList<SchedulerDetail_DTO> getSchedulerDetailsDTOs(
			CopyOnWriteArrayList<SchedulerDetail> lDetails) {
		SchedulerDetail_DTO lDTO = null;
		CopyOnWriteArrayList<SchedulerDetail_DTO> lDetailDTOs2 = new CopyOnWriteArrayList<SchedulerDetail_DTO>();
		for (int i = 0; i < lDetails.size(); i++) {
			lDTO = getSchedulerDetailsDTO(lDetails.get(i));
			lDetailDTOs2.add(lDTO);
		}
		return lDetailDTOs2;
	}

	private synchronized SchedulerDetail_DTO getSchedulerDetailsDTO(SchedulerDetail lDetail) {
		SchedulerDetail_DTO lDTO = new SchedulerDetail_DTO();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		lDTO.setFrDttm(formatter.format(lDetail.getId().getFrDttm().toLocalDateTime()));
		lDTO.setToDttm(formatter.format(lDetail.getId().getToDttm().toLocalDateTime()));
		lDTO.setRuleLineSeqNo(lDetail.getId().getRuleLineSeqNo());
		lDTO.setJobSeqNo(lDetail.getJobSeqNo());
		lDTO.setRemarks(lDetail.getRemarks());
		lDTO.setStatus(lDetail.getStatus());
		return lDTO;
	}

	private synchronized CopyOnWriteArrayList<SchedulerMaster_DTO> getSchedulerMaster_DTOs(
			CopyOnWriteArrayList<SchedulerMaster> lMasters) {
		SchedulerMaster_DTO lDTO = null;
		CopyOnWriteArrayList<SchedulerMaster_DTO> lMasterDTOs = new CopyOnWriteArrayList<SchedulerMaster_DTO>();
		for (int i = 0; i < lMasters.size(); i++) {
			lDTO = getSchedulerMaster_DTO(lMasters.get(i));
			lMasterDTOs.add(lDTO);
		}
		return lMasterDTOs;
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

	private synchronized SchedulerDetail setSchedulerDetail(SchedulerDetail_DTO sDTO) {
		SchedulerDetail sDetail = new SchedulerDetail();
		SchedulerDetailPK schedulerDetailPK = new SchedulerDetailPK();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime frDateTime = LocalDateTime.parse(sDTO.getFrDttm(), formatter);
		LocalDateTime toDateTime = LocalDateTime.parse(sDTO.getToDttm(), formatter);
		schedulerDetailPK.setFrDttm(Timestamp.valueOf(frDateTime));
		schedulerDetailPK.setToDttm(Timestamp.valueOf(toDateTime));
		schedulerDetailPK.setRuleLineSeqNo(sDTO.getRuleLineSeqNo());
		sDetail.setId(schedulerDetailPK);
		sDetail.setJobSeqNo(sDTO.getJobSeqNo());
		sDetail.setRemarks(sDTO.getRemarks());
		sDetail.setStatus(sDTO.getStatus());
		return sDetail;
	}

}
