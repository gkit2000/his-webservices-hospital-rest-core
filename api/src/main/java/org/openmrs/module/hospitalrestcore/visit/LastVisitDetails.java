/**
 * 
 */
package org.openmrs.module.hospitalrestcore.visit;

/**
 * @author Ghanshyam
 *
 */
public class LastVisitDetails {

	String registrationDate;
	String departmentName;
	String reportingPlace;
	String doctorName;
	String reportingTime;
	private String patientCategory;
	private String patientSubCategory1;
	private String patientSubCategory2;
	private String mlc;
	private String mlcSubCategory;

	/**
	 * @return the registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the reportingPlace
	 */
	public String getReportingPlace() {
		return reportingPlace;
	}

	/**
	 * @param reportingPlace the reportingPlace to set
	 */
	public void setReportingPlace(String reportingPlace) {
		this.reportingPlace = reportingPlace;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @return the reportingTime
	 */
	public String getReportingTime() {
		return reportingTime;
	}

	/**
	 * @param reportingTime the reportingTime to set
	 */
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
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
	 * @return the mlc
	 */
	public String getMlc() {
		return mlc;
	}

	/**
	 * @param mlc the mlc to set
	 */
	public void setMlc(String mlc) {
		this.mlc = mlc;
	}

	/**
	 * @return the mlcSubCategory
	 */
	public String getMlcSubCategory() {
		return mlcSubCategory;
	}

	/**
	 * @param mlcSubCategory the mlcSubCategory to set
	 */
	public void setMlcSubCategory(String mlcSubCategory) {
		this.mlcSubCategory = mlcSubCategory;
	}

}
