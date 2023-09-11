package scheduler_mgmt.model.dto;

import java.io.Serializable;

public class SchedulerDetail_DTO implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7713966746504538614L;
	private String frDttm;
	private String toDttm;
	private Long ruleLineSeqNo;
	private Long jobSeqNo;
	private String remarks;
	private String status;

	public String getFrDttm() {
		return frDttm;
	}

	public void setFrDttm(String frDttm) {
		this.frDttm = frDttm;
	}

	public String getToDttm() {
		return toDttm;
	}

	public void setToDttm(String toDttm) {
		this.toDttm = toDttm;
	}

	public Long getRuleLineSeqNo() {
		return ruleLineSeqNo;
	}

	public void setRuleLineSeqNo(Long ruleLineSeqNo) {
		this.ruleLineSeqNo = ruleLineSeqNo;
	}

	public Long getJobSeqNo() {
		return jobSeqNo;
	}

	public void setJobSeqNo(Long jobSeqNo) {
		this.jobSeqNo = jobSeqNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SchedulerDetail_DTO(String frDttm, String toDttm, Long ruleLineSeqNo, Long jobSeqNo, String remarks,
			String status) {
		super();
		this.frDttm = frDttm;
		this.toDttm = toDttm;
		this.ruleLineSeqNo = ruleLineSeqNo;
		this.jobSeqNo = jobSeqNo;
		this.remarks = remarks;
		this.status = status;
	}

	public SchedulerDetail_DTO() {
		super();
	}

}