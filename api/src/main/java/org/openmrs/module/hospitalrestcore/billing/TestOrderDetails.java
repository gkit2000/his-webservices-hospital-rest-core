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
	private String patientId;
	private String patientUuid;
	private String patientName;
	private String gender;
	private Integer age;
	private String patientCategory;
	private Integer locationId;
	private String locationUuid;
	private String locationName;
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
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
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
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the patientCategory
	 */
	public String getPatientCategory() {
		return patientCategory;
	}

	/**
	 * @param patientCategory the patientCategory to set
	 */
	public void setPatientCategory(String patientCategory) {
		this.patientCategory = patientCategory;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the locationUuid
	 */
	public String getLocationUuid() {
		return locationUuid;
	}

	/**
	 * @param locationUuid the locationUuid to set
	 */
	public void setLocationUuid(String locationUuid) {
		this.locationUuid = locationUuid;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
