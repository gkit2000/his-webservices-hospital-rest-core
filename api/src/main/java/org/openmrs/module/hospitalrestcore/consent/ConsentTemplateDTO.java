/**
 * 
 */
package org.openmrs.module.hospitalrestcore.consent;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Ghanshyam
 *
 */
public class ConsentTemplateDTO {

	private String consentTemplateUuid;

	private String name;

	private String mailSubject;

	private String description;

	@NotNull(message = "Please provide consent template concept uuid")
	@NotBlank(message = "Please provide consent template concept uuid")
	private String consentTypeConUuid;

	@NotNull(message = "Please provide consent template content")
	@NotBlank(message = "Please provide consent template content")
	private String consentTemplateContent;

	private Boolean deleted;

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
