package scheduler_mgmt.controller.read;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scheduler_mgmt.model.dto.SchedulerDetail_DTO;
import scheduler_mgmt.model.dto.SchedulerMaster_DTO;
import scheduler_mgmt.services.read.I_SchedulerMasterPublicRead_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/schedulerPublicReadManagement")
public class SchedulerMasterPublicRead_Controller {
	// private static final Logger logger =
	// LoggerFactory.getLogger(Scheduler_Master_Controller.class);

	@Autowired
	private I_SchedulerMasterPublicRead_Service schedulerMasterPublicReadService;

	@GetMapping(value = "/getAllSchedules", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<SchedulerMaster_DTO>> getAllSchedulerMasters() {
		CompletableFuture<CopyOnWriteArrayList<SchedulerMaster_DTO>> completableFuture = schedulerMasterPublicReadService
				.getAllSchedulerMasters();
		CopyOnWriteArrayList<SchedulerMaster_DTO> scheduleDTOs = completableFuture.join();
		return new ResponseEntity<>(scheduleDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/getSelectSchedulesForCompanyTargetRule", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<SchedulerDetail_DTO>> getSelectSchedulesForCompanyTargetRule(
			@PathVariable Long cSeqNo, @PathVariable Long rSeqNo, @PathVariable Long tSeqNo) {
		CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> completableFuture = schedulerMasterPublicReadService
				.getSelectSchedulesForCompanyTargetRule(cSeqNo, rSeqNo, tSeqNo);
		;
		CopyOnWriteArrayList<SchedulerDetail_DTO> scheduleDetailDTOs = completableFuture.join();
		return new ResponseEntity<>(scheduleDetailDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/getSelectSchedulesForRuleLine", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<SchedulerDetail_DTO>> getSelectSchedulesForRuleLine(
			@RequestBody Long rLineSeqNo) {
		CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> completableFuture = schedulerMasterPublicReadService
				.getSelectSchedulesForRuleLine(rLineSeqNo);
		CopyOnWriteArrayList<SchedulerDetail_DTO> scheduleDetailDTOs = completableFuture.join();
		return new ResponseEntity<>(scheduleDetailDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/getById/{scheduleSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<SchedulerMaster_DTO> getSchedulerMasterById(@PathVariable Long scheduleSeqNo) {
		CompletableFuture<SchedulerMaster_DTO> completableFuture = schedulerMasterPublicReadService
				.getSchedulerMasterById(scheduleSeqNo);
		SchedulerMaster_DTO scheduleAccNoDTOs = completableFuture.join();
		return new ResponseEntity<>(scheduleAccNoDTOs, HttpStatus.OK);
	}

}