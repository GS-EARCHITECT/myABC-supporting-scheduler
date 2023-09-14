package scheduler_mgmt.model.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "SCHEDULER_MASTER")
public class SchedulerMaster implements Serializable 
{

	/**
	 * 
	 */
	private static final Long serialVersionUID = 4731012908992860044L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RULE_LINE_SEQ_NO")
	@SequenceGenerator(name = "RULE_LINE_SEQ_NO", sequenceName = "RULE_LINE_SEQ_NO", allocationSize = 1)
	@Column(name = "RULE_LINE_SEQ_NO")
	private Long ruleLineSeqNo;

	@Column(name = "COMPANY_SEQ_NO")
	private Long companySeqNo;

	@Column(name = "FR_TM")
	private String frtm;

	@Column(name = "TO_TM")
	private String totm;

	@Column(name = "FROM_DTTM")
	private Timestamp fromDttm;

	@Column(name = "TO_DTTM")
	private Timestamp toDttm;

	@Column(name = "JOB_TYPE_SEQ_NO")
	private BigDecimal jobTypeSeqNo;

	@Column(name = "RULE_SEQ_NO")
	private Long ruleSeqNo;

	@Column(name = "SCHEDULED_FLAG")
	private Character scheduledFlag;

	@Column(name = "TARGET_SEQ_NO")
	private String targetSeqNo;

	@Column(name = "SCHEDULE_DATA")
	private String scheduleData;

	public Long getRuleLineSeqNo() {
		return ruleLineSeqNo;
	}

	public void setRuleLineSeqNo(Long ruleLineSeqNo) {
		this.ruleLineSeqNo = ruleLineSeqNo;
	}

	public Long getCompanySeqNo() {
		return companySeqNo;
	}

	public void setCompanySeqNo(Long companySeqNo) {
		this.companySeqNo = companySeqNo;
	}

	public String getFrtm() {
		return frtm;
	}

	public void setFrtm(String frtm) {
		this.frtm = frtm;
	}

	public Timestamp getFrDttm() {
		return fromDttm;
	}

	public void setFrDttm(Timestamp frDttm) {
		this.fromDttm = frDttm;
	}

	public Timestamp getToDttm() {
		return toDttm;
	}

	public void setToDttm(Timestamp toDttm) {
		this.toDttm = toDttm;
	}

	public BigDecimal getJobTypeSeqNo() {
		return jobTypeSeqNo;
	}

	public void setJobTypeSeqNo(BigDecimal jobTypeSeqNo) {
		this.jobTypeSeqNo = jobTypeSeqNo;
	}

	public Long getRuleSeqNo() {
		return ruleSeqNo;
	}

	public void setRuleSeqNo(Long ruleSeqNo) {
		this.ruleSeqNo = ruleSeqNo;
	}

	public Character getScheduledFlag() {
		return scheduledFlag;
	}

	public void setScheduledFlag(Character scheduledFlag) {
		this.scheduledFlag = scheduledFlag;
	}

	public String getTargetSeqNo() {
		return targetSeqNo;
	}

	public void setTargetSeqNo(String targetSeqNo) {
		this.targetSeqNo = targetSeqNo;
	}

	public String getTotm() {
		return totm;
	}

	public void setTotm(String totm) {
		this.totm = totm;
	}

	public String getScheduleData() {
		return scheduleData;
	}

	public void setScheduleData(String scheduleData) {
		this.scheduleData = scheduleData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companySeqNo == null) ? 0 : companySeqNo.hashCode());
		result = prime * result + ((fromDttm == null) ? 0 : fromDttm.hashCode());
		result = prime * result + ((frtm == null) ? 0 : frtm.hashCode());
		result = prime * result + ((jobTypeSeqNo == null) ? 0 : jobTypeSeqNo.hashCode());
		result = prime * result + (int) (ruleLineSeqNo ^ (ruleLineSeqNo >>> 32));
		result = prime * result + ((ruleSeqNo == null) ? 0 : ruleSeqNo.hashCode());
		result = prime * result + ((scheduleData == null) ? 0 : scheduleData.hashCode());
		result = prime * result + ((scheduledFlag == null) ? 0 : scheduledFlag.hashCode());
		result = prime * result + ((targetSeqNo == null) ? 0 : targetSeqNo.hashCode());
		result = prime * result + ((toDttm == null) ? 0 : toDttm.hashCode());
		result = prime * result + ((totm == null) ? 0 : totm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulerMaster other = (SchedulerMaster) obj;
		if (companySeqNo == null) {
			if (other.companySeqNo != null)
				return false;
		} else if (!companySeqNo.equals(other.companySeqNo))
			return false;
		if (fromDttm == null) {
			if (other.fromDttm != null)
				return false;
		} else if (!fromDttm.equals(other.fromDttm))
			return false;
		if (frtm == null) {
			if (other.frtm != null)
				return false;
		} else if (!frtm.equals(other.frtm))
			return false;
		if (jobTypeSeqNo == null) {
			if (other.jobTypeSeqNo != null)
				return false;
		} else if (!jobTypeSeqNo.equals(other.jobTypeSeqNo))
			return false;
		if (ruleLineSeqNo != other.ruleLineSeqNo)
			return false;
		if (ruleSeqNo == null) {
			if (other.ruleSeqNo != null)
				return false;
		} else if (!ruleSeqNo.equals(other.ruleSeqNo))
			return false;
		if (scheduleData == null) {
			if (other.scheduleData != null)
				return false;
		} else if (!scheduleData.equals(other.scheduleData))
			return false;
		if (scheduledFlag == null) {
			if (other.scheduledFlag != null)
				return false;
		} else if (!scheduledFlag.equals(other.scheduledFlag))
			return false;
		if (targetSeqNo == null) {
			if (other.targetSeqNo != null)
				return false;
		} else if (!targetSeqNo.equals(other.targetSeqNo))
			return false;
		if (toDttm == null) {
			if (other.toDttm != null)
				return false;
		} else if (!toDttm.equals(other.toDttm))
			return false;
		if (totm == null) {
			if (other.totm != null)
				return false;
		} else if (!totm.equals(other.totm))
			return false;
		return true;
	}

	public SchedulerMaster() {
		super();
	}

	public SchedulerMaster(Long ruleLineSeqNo, Long companySeqNo, String frtm, String totm, Timestamp fromDttm,
			Timestamp toDttm, BigDecimal jobTypeSeqNo, Long ruleSeqNo, Character scheduledFlag, String targetSeqNo,
			String scheduleData) {
		super();
		this.ruleLineSeqNo = ruleLineSeqNo;
		this.companySeqNo = companySeqNo;
		this.frtm = frtm;
		this.totm = totm;
		this.fromDttm = fromDttm;
		this.toDttm = toDttm;
		this.jobTypeSeqNo = jobTypeSeqNo;
		this.ruleSeqNo = ruleSeqNo;
		this.scheduledFlag = scheduledFlag;
		this.targetSeqNo = targetSeqNo;
		this.scheduleData = scheduleData;
	}
	
	

}