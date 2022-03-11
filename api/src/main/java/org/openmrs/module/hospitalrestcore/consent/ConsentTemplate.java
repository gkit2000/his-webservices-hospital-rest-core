/**
 * 
 */
package org.openmrs.module.hospitalrestcore.consent;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;
import org.openmrs.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ghanshyam
 *
 */
public class ConsentTemplate extends BaseOpenmrsObject implements Serializable {

	public static final long serialVersionUID = 57333L;

	private static final Logger log = LoggerFactory.getLogger(Concept.class);

	private Integer consentTemplateId;

	private String name;

	private String mailSubject;

	private String description;

	private Concept type;

	private String templateContent;

	private Boolean deleted = false;

	private User creator;

	private Date dateCreated;

	private User changedBy;

	private Date dateChanged;

	/**
	 * @return the consentTemplateId
	 */
	public Integer getConsentTemplateId() {
		return consentTemplateId;
	}

	/**
	 * @param consentTemplateId the consentTemplateId to set
	 */
	public void setConsentTemplateId(Integer consentTemplateId) {
		this.consentTemplateId = consentTemplateId;
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
	 * @return the type
	 */
	public Concept getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Concept type) {
		this.type = type;
	}

	/**
	 * @return the templateContent
	 */
	public String getTemplateContent() {
		return templateContent;
	}

	/**
	 * @param templateContent the templateContent to set
	 */
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
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

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the changedBy
	 */
	public User getChangedBy() {
		return changedBy;
	}

	/**
	 * @param changedBy the changedBy to set
	 */
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	/**
	 * @return the dateChanged
	 */
	public Date getDateChanged() {
		return dateChanged;
	}

	/**
	 * @param dateChanged the dateChanged to set
	 */
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer arg0) {
		// TODO Auto-generated method stub

	}

}
