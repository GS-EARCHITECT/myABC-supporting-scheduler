package scheduler_mgmt.model.dto;

import java.io.Serializable;

public class SchedulerSpecEvMaster_DTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -590349344105748297L;
	private Long specEventSeqNo;
	private String companySeqNo;
	private String fromDttm;

	public Long getSpecEventSeqNo() {
		return specEventSeqNo;
	}

	public void setSpecEventSeqNo(Long specEventSeqNo) {
		this.specEventSeqNo = specEventSeqNo;
	}

	public String getCompanySeqNo() {
		return companySeqNo;
	}

	public void setCompanySeqNo(String companySeqNo) {
		this.companySeqNo = companySeqNo;
	}

	public String getFromDttm() {
		return fromDttm;
	}

	public void setFromDttm(String fromDttm) {
		this.fromDttm = fromDttm;
	}

	public SchedulerSpecEvMaster_DTO(Long specEventSeqNo, String companySeqNo, String fromDttm) {
		super();
		this.specEventSeqNo = specEventSeqNo;
		this.companySeqNo = companySeqNo;
		this.fromDttm = fromDttm;
	}

	public SchedulerSpecEvMaster_DTO() {
		super();
	}
}