/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class OrderDetails {
	private String patient;
	private List<String> procedures;
	private List<String> investigations;

//private List<String> drugs;

	/**
	 * @return the patient
	 */
	public String getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(String patient) {
		this.patient = patient;
	}

	/**
	 * @return the procedures
	 */
	public List<String> getProcedures() {
		return procedures;
	}

	/**
	 * @param procedures the procedures to set
	 */
	public void setProcedures(List<String> procedures) {
		this.procedures = procedures;
	}

	/**
	 * @return the investigations
	 */
	public List<String> getInvestigations() {
		return investigations;
	}

	/**
	 * @param investigations the investigations to set
	 */
	public void setInvestigations(List<String> investigations) {
		this.investigations = investigations;
	}

}
