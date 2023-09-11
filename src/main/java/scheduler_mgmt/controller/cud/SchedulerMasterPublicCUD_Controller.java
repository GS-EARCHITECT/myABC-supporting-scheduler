package scheduler_mgmt.controller.cud;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scheduler_mgmt.model.dto.SchedulerMaster_DTO;
import scheduler_mgmt.services.cud.I_SchedulerMasterPublicCUD_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/schedulerPublicCUDManagement")
public class SchedulerMasterPublicCUD_Controller {
	// private static final Logger logger =
	// LoggerFactory.getLogger(Scheduler_Master_Controller.class);

	@Autowired
	private I_SchedulerMasterPublicCUD_Service schedulerMasterPublicCUDService;

	@PostMapping("/new")
	public ResponseEntity<SchedulerMaster_DTO> newschedule(@RequestBody SchedulerMaster_DTO scheduleDTO) {
		CompletableFuture<SchedulerMaster_DTO> completableFuture = schedulerMasterPublicCUDService
				.newSchedulerMaster(scheduleDTO);
		SchedulerMaster_DTO scheduleDTO2 = completableFuture.join();
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(scheduleDTO2, httpHeaders, HttpStatus.CREATED);
	}

	@PutMapping("/updschedule")
	public void updateschedule(@RequestBody SchedulerMaster_DTO scheduleDTO) {
		schedulerMasterPublicCUDService.updSchedulerMaster(scheduleDTO);
		return;
	}

	@DeleteMapping("/delschedule/{scheduleSeqNo}")
	public void deleteschedule(@PathVariable Long scheduleSeqNo) {
		schedulerMasterPublicCUDService.delSchedulerMaster(scheduleSeqNo);
	}

	@DeleteMapping("/delSelectschedules")
	public void deleteSelectschedules(@RequestBody CopyOnWriteArrayList<Long> schedulersSeqNoList) {
		schedulerMasterPublicCUDService.delSelectSchedulers(schedulersSeqNoList);
		return;
	}

	@DeleteMapping("/delAllschedules")
	public void deleteAllschedules() {
		schedulerMasterPublicCUDService.delAllSchedulerMasters();
		;
		return;
	}
}