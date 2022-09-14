/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class WalkinPatientServicesDetails {

	private String patientId;
	private String patientUuid;
	private String patientName;
	private String gender;
	private Integer age;
	private String birthDate;
	private String patientCategory;
	private String patientSubCategory1;
	private String patientSubCategory2;
	private String priceCategoryConceptUuid;
	private List<BillableServiceDetails> billableServiceDetails;

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
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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
	 * @return the patientSubCategory1
	 */
	public String getPatientSubCategory1() {
		return patientSubCategory1;
	}

	/**
	 * @param patientSubCategory1 the patientSubCategory1 to set
	 */
	public void setPatientSubCategory1(String patientSubCategory1) {
		this.patientSubCategory1 = patientSubCategory1;
	}

	/**
	 * @return the patientSubCategory2
	 */
	public String getPatientSubCategory2() {
		return patientSubCategory2;
	}

	/**
	 * @param patientSubCategory2 the patientSubCategory2 to set
	 */
	public void setPatientSubCategory2(String patientSubCategory2) {
		this.patientSubCategory2 = patientSubCategory2;
	}

	/**
	 * @return the priceCategoryConceptUuid
	 */
	public String getPriceCategoryConceptUuid() {
		return priceCategoryConceptUuid;
	}

	/**
	 * @param priceCategoryConceptUuid the priceCategoryConceptUuid to set
	 */
	public void setPriceCategoryConceptUuid(String priceCategoryConceptUuid) {
		this.priceCategoryConceptUuid = priceCategoryConceptUuid;
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
