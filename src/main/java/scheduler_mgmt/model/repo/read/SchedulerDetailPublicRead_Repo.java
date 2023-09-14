package scheduler_mgmt.model.repo.read;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scheduler_mgmt.model.master.SchedulerDetail;
import scheduler_mgmt.model.master.SchedulerDetailPK;

@Repository("schedulerDetailPublicReadRepo")
public interface SchedulerDetailPublicRead_Repo extends JpaRepository<SchedulerDetail, SchedulerDetailPK> 
{ 
@Query(value = "SELECT * FROM SCHEDULER_DETAILS a WHERE a.rule_line_seq_no in :ids order by rule_seq_no", nativeQuery = true)
CopyOnWriteArrayList<SchedulerDetail> getSelectSchedules(@Param("id") CopyOnWriteArrayList<Long> ids);

@Query(value = "SELECT * FROM SCHEDULER_DETAILS a WHERE (a.company_seq_no = :cSeqNo and a.rule_seq_no = :rSeqNo and a.target_seq_no = :tSeqNo) order by rule_seq_no", nativeQuery = true)
CopyOnWriteArrayList<SchedulerDetail> getSelectSchedulesForCompanyTargetRule(@Param("cSeqNo") Long cSeqNo, @Param("rSeqNo") Long rSeqNo, @Param("tSeqNo") Long tSeqNo);

@Query(value = "SELECT * FROM SCHEDULER_DETAILS a WHERE a.rule_line_seq_no = :rSeqNo order by rule_line_seq_no", nativeQuery = true)
CopyOnWriteArrayList<SchedulerDetail> getSelectSchedulesForRuleLine(@Param("rSeqNo") Long rSeqNo);

@Query(value = "SELECT coalesce(count(*),0) FROM SCHEDULER_DETAILS a WHERE a.rule_line_seq_no = :id", nativeQuery = true)
Float getCountOfSchedules(@Param("id") Long id);

} 

