package scheduler_mgmt.services.read;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import scheduler_mgmt.model.dto.SchedulerDetail_DTO;
import scheduler_mgmt.model.dto.SchedulerMaster_DTO;

public interface I_SchedulerMasterPublicRead_Service 
{
public void createSchedules();	
public CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> getSelectSchedulesForCompanyTargetRule(Long cSeqNo, Long rSeqNo, Long tSeqNo);
public CompletableFuture<CopyOnWriteArrayList<SchedulerDetail_DTO>> getSelectSchedulesForRuleLine(Long rSeqNo);
public CompletableFuture<CopyOnWriteArrayList<SchedulerMaster_DTO>> getAllSchedulerMasters();    
public CompletableFuture<SchedulerMaster_DTO> getSchedulerMasterById(Long promoSeqNo);
}