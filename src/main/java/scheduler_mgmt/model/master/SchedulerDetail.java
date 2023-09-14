package scheduler_mgmt.model.master;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SCHEDULER_DETAILS database table.
 * 
 */
@Entity
@Table(name = "SCHEDULER_DETAILS")
public class SchedulerDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SchedulerDetailPK id;

	@Column(name = "JOB_SEQ_NO")
	private Long jobSeqNo;

	@Column(name = "REMARK")
	private String remarks;

	@Column(name = "STATUS")
	private String status;

	public SchedulerDetail() {
	}

	public SchedulerDetailPK getId() {
		return this.id;
	}

	public void setId(SchedulerDetailPK id) {
		this.id = id;
	}

	public Long getJobSeqNo() {
		return this.jobSeqNo;
	}

	public void setJobSeqNo(Long jobSeqNo) {
		this.jobSeqNo = jobSeqNo;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SchedulerDetail(SchedulerDetailPK id, Long jobSeqNo, String remarks, String status) {
		super();
		this.id = id;
		this.jobSeqNo = jobSeqNo;
		this.remarks = remarks;
		this.status = status;
	}

}