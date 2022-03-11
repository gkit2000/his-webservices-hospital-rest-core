/**
 * 
 */
package org.openmrs.module.hospitalrestcore.consent;

/**
 * @author Ghanshyam
 *
 */
public class ConsentTemplateDetails {

	private String name;

	private String consentTemplateUuid;

	private String mailSubject;

	private String description;

	private String consentType;

	private String consentTypeConUuid;

	private String consentTemplateContent;

	private Boolean deleted;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the consentTemplateUuid
	 */
	public String getConsentTemplateUuid() {
		return consentTemplateUuid;
	}

	/**
	 * @param consentTemplateUuid the consentTemplateUuid to set
	 */
	public void setConsentTemplateUuid(String consentTemplateUuid) {
		this.consentTemplateUuid = consentTemplateUuid;
	}

	/**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the consentType
	 */
	public String getConsentType() {
		return consentType;
	}

	/**
	 * @param consentType the consentType to set
	 */
	public void setConsentType(String consentType) {
		this.consentType = consentType;
	}

	/**
	 * @return the consentTypeConUuid
	 */
	public String getConsentTypeConUuid() {
		return consentTypeConUuid;
	}

	/**
	 * @param consentTypeConUuid the consentTypeConUuid to set
	 */
	public void setConsentTypeConUuid(String consentTypeConUuid) {
		this.consentTypeConUuid = consentTypeConUuid;
	}

	/**
	 * @return the consentTemplateContent
	 */
	public String getConsentTemplateContent() {
		return consentTemplateContent;
	}

	/**
	 * @param consentTemplateContent the consentTemplateContent to set
	 */
	public void setConsentTemplateContent(String consentTemplateContent) {
		this.consentTemplateContent = consentTemplateContent;
	}

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
