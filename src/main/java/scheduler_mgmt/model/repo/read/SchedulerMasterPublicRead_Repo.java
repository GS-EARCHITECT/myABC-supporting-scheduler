package scheduler_mgmt.model.repo.read;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scheduler_mgmt.model.master.SchedulerMaster;

@Repository("schedulerMasterPublicReadRepo")
public interface SchedulerMasterPublicRead_Repo extends JpaRepository<SchedulerMaster, Long> 
{ 
@Query(value = "SELECT * FROM SCHEDULER_MASTER b where upper(trim(SCHEDULED_FLAG))<>'Y' order by rule_line_seq_no", nativeQuery = true)
CopyOnWriteArrayList<SchedulerMaster> getSchedules();

@Query(value = "SELECT coalesce(count(*),0) FROM SCHEDULER_MASTER b where (b.company_seq_no= :compSeqNo and b.rule_seq_no= :ruleSeqNo and upper(trim(b.target_seq_no)) = upper(trim(:targetSeqNo)))", nativeQuery = true)
Float checkIfSExists(@Param("compSeqNo") Long compSeqNo, @Param("targetSeqNo") String targetSeqNo, @Param("ruleSeqNo") Long ruleSeqNo);

@Query(value = "SELECT * FROM SCHEDULER_MASTER where dow > 0 order by target_type", nativeQuery = true)
CopyOnWriteArrayList<SchedulerMaster> getSchedulesDOW();

@Query(value = "SELECT * FROM SCHEDULER_MASTER where DAY_PLUS_BASIS > 0 order by target_type", nativeQuery = true)
CopyOnWriteArrayList<SchedulerMaster> getSchedulesDaysPlus();

@Query(value = "SELECT * FROM SCHEDULER_MASTER a WHERE a.rule_seq_no in :ids order by rule_seq_no", nativeQuery = true)
SchedulerMaster getSelectSchedules(@Param("id") CopyOnWriteArrayList<Long> ids);

} 

