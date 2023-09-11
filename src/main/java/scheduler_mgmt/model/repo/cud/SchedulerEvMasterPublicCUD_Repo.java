package scheduler_mgmt.model.repo.cud;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scheduler_mgmt.model.master.SchedulerMaster;

@Repository("schedulerEventMasterPublicCUDRepo")
public interface SchedulerEvMasterPublicCUD_Repo extends JpaRepository<SchedulerMaster, Long> 
{ 

@Query(value = "DELETE FROM SCHEDULER_MASTER WHERE a.rule_seq_no in :ids", nativeQuery = true)
void delSelectSchedules(@Param("ids") CopyOnWriteArrayList<Long> ids);

@Modifying
@Query(value="update SCHEDULER_MASTER set scheduled_flag = :st WHERE rule_line_seq_no = :rlSeqNo", nativeQuery = true)
void updateScheduleStatus(@Param("st") Character st, @Param("rlSeqNo") Long rlSeqNo);
} 

