package scheduler_mgmt.controller.cud;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import scheduler_mgmt.model.dto.SchedulerMaster_DTO;
import scheduler_mgmt.services.cud.I_SchedulerMasterPublicCUD_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/schedulerPublicCUDManagement")
public class SchedulerMasterPublicCUD_Controller {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerMasterPublicCUD_Controller.class);

	@Autowired
	private Executor asyncExecutor;

	@Autowired
	private I_SchedulerMasterPublicCUD_Service schedulerMasterPublicCUDService;

	@CircuitBreaker(name = "myCB", fallbackMethod = "schedulerOverFlow")
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
	
		return;
	}

	@RateLimiter(name = "myRL")
	public CompletableFuture<SchedulerMaster_DTO> rateLimiter() {
		CompletableFuture<SchedulerMaster_DTO> future = CompletableFuture.supplyAsync(() -> {
			SchedulerMaster_DTO lMaster = new SchedulerMaster_DTO();
			lMaster.setRuleLineSeqNo(-3);
			return lMaster;
		}, asyncExecutor);
		return future;
	}

	@TimeLimiter(name = "myTL")
	public CompletableFuture<SchedulerMaster_DTO> timeLimiter() {
		CompletableFuture<SchedulerMaster_DTO> future = CompletableFuture.supplyAsync(() -> {
			SchedulerMaster_DTO lMaster = new SchedulerMaster_DTO();
			double random = Math.random();
			if (random < 0.5) {
				lMaster.setRuleLineSeqNo(-4);
			} else {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return lMaster;
		}, asyncExecutor);
		return future;
	}

	@GetMapping("/retry")
	@Retry(name = "myRE", fallbackMethod = "fallbackAfterRetry")
	public ResponseEntity<SchedulerMaster_DTO> retryApi(@RequestBody SchedulerMaster_DTO scheduleDTO) {
		CompletableFuture<SchedulerMaster_DTO> completableFuture = schedulerMasterPublicCUDService
				.newSchedulerMaster(scheduleDTO);
		logger.info("new schedule");
		SchedulerMaster_DTO scheduleDTO2 = completableFuture.join();
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(scheduleDTO2, httpHeaders, HttpStatus.CREATED);
	}

	public ResponseEntity<SchedulerMaster_DTO> fallbackAfterRetry(Exception ex) {
		SchedulerMaster_DTO scheduleDTO2 = new SchedulerMaster_DTO();
		scheduleDTO2.setRuleLineSeqNo(-7);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(scheduleDTO2, httpHeaders, HttpStatus.CREATED);
	}

	private ResponseEntity<SchedulerMaster_DTO> schedulerOverFlow(@RequestBody SchedulerMaster_DTO scheduleDTO,
			Exception ex) 
	{
		SchedulerMaster_DTO lMaster = new SchedulerMaster_DTO();
		lMaster.setRuleLineSeqNo(-2);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(lMaster, httpHeaders, HttpStatus.CREATED);
	}

}