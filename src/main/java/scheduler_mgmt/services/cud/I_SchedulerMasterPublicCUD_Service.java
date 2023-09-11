package scheduler_mgmt.services.cud;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import scheduler_mgmt.model.dto.SchedulerMaster_DTO;

public interface I_SchedulerMasterPublicCUD_Service 
{
public CompletableFuture<SchedulerMaster_DTO> newSchedulerMaster(SchedulerMaster_DTO promoMasterDTO);
public CompletableFuture<Void> updSchedulerMaster(SchedulerMaster_DTO SchedulerMaster_DTO);
public CompletableFuture<Void> delSchedulerMaster(Long DocumentSeqNo);
public CompletableFuture<Void> delAllSchedulerMasters();    
public CompletableFuture<Void> delSelectSchedulers(CopyOnWriteArrayList<Long> ids);
}