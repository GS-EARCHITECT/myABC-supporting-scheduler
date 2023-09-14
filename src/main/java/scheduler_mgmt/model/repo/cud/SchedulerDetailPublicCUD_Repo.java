package scheduler_mgmt.model.repo.cud;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import scheduler_mgmt.model.master.SchedulerDetail;
import scheduler_mgmt.model.master.SchedulerDetailPK;

@Transactional(propagation=Propagation.REQUIRES_NEW)
@Repository("schedulerDetailPublicCUDRepo")
public interface SchedulerDetailPublicCUD_Repo extends JpaRepository<SchedulerDetail, SchedulerDetailPK> 
{ 

@Modifying
@Query(value = "DELETE FROM SCHEDULER_DETAILS WHERE a.rule_line_seq_no in :ids", nativeQuery = true)
void delSelectSchedules(@Param("ids") CopyOnWriteArrayList<Long> ids);

@Modifying
@Query(value = "DELETE FROM SCHEDULER_DETAILS a WHERE a.rule_line_seq_no = :id", nativeQuery = true)
void delSchedulesForRuleLine(@Param("id") Long id);
} 

