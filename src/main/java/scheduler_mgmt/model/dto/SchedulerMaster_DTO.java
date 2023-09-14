package scheduler_mgmt.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SchedulerMaster_DTO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7315857685192294458L;
	private long ruleLineSeqNo;
	private Long companySeqNo;
	private BigDecimal jobTypeSeqNo;
	private Long ruleSeqNo;
	private Character scheduledFlag;
	private String targetSeqNo;
	private String scheduleData;
	private String frtm;
	private String totm;
	private String fromDttm;
	private String toDttm;

	public long getRuleLineSeqNo() {
		return ruleLineSeqNo;
	}

	public void setRuleLineSeqNo(long ruleLineSeqNo) {
		this.ruleLineSeqNo = ruleLineSeqNo;
	}

	public Long getCompanySeqNo() {
		return companySeqNo;
	}

	public void setCompanySeqNo(Long companySeqNo) {
		this.companySeqNo = companySeqNo;
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

	public String getScheduleData() {
		return scheduleData;
	}

	public void setScheduleData(String scheduleData) {
		this.scheduleData = scheduleData;
	}

	public String getFrtm() {
		return frtm;
	}

	public void setFrtm(String frtm) {
		this.frtm = frtm;
	}

	public String getTotm() {
		return totm;
	}

	public void setTotm(String totm) {
		this.totm = totm;
	}

	public String getFromDttm() {
		return fromDttm;
	}

	public void setFromDttm(String fromDttm) {
		this.fromDttm = fromDttm;
	}

	public String getToDttm() {
		return toDttm;
	}

	public void setToDttm(String toDttm) {
		this.toDttm = toDttm;
	}

	public SchedulerMaster_DTO(long ruleLineSeqNo, Long companySeqNo, BigDecimal jobTypeSeqNo, Long ruleSeqNo,
			Character scheduledFlag, String targetSeqNo, String scheduleData, String frtm, String totm, String fromDttm,
			String toDttm) {
		super();
		this.ruleLineSeqNo = ruleLineSeqNo;
		this.companySeqNo = companySeqNo;
		this.jobTypeSeqNo = jobTypeSeqNo;
		this.ruleSeqNo = ruleSeqNo;
		this.scheduledFlag = scheduledFlag;
		this.targetSeqNo = targetSeqNo;
		this.scheduleData = scheduleData;
		this.frtm = frtm;
		this.totm = totm;
		this.fromDttm = fromDttm;
		this.toDttm = toDttm;
	}

	public SchedulerMaster_DTO() {
		super();
	}

}