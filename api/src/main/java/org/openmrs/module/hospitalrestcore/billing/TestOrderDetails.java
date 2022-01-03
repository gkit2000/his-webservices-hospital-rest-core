/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class TestOrderDetails {

	private Integer encounterId;
	private String encounterUuid;
	private Integer patientId;
	private String patientUuid;
	private List<BillableServiceDetails> billableServiceDetails;

	/**
	 * @return the encounterId
	 */
	public Integer getEncounterId() {
		return encounterId;
	}

	/**
	 * @param encounterId the encounterId to set
	 */
	public void setEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
	}

	/**
	 * @return the encounterUuid
	 */
	public String getEncounterUuid() {
		return encounterUuid;
	}

	/**
	 * @param encounterUuid the encounterUuid to set
	 */
	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}

	/**
	 * @return the patientId
	 */
	public Integer getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the patientUuid
	 */
	public String getPatientUuid() {
		return patientUuid;
	}

	/**
	 * @param patientUuid the patientUuid to set
	 */
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}

	/**
	 * @return the billableServiceDetails
	 */
	public List<BillableServiceDetails> getBillableServiceDetails() {
		return billableServiceDetails;
	}

	/**
	 * @param billableServiceDetails the billableServiceDetails to set
	 */
	public void setBillableServiceDetails(List<BillableServiceDetails> billableServiceDetails) {
		this.billableServiceDetails = billableServiceDetails;
	}

}
