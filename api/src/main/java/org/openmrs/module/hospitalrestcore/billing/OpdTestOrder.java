/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.User;

/**
 * @author Ghanshyam
 *
 */
public class OpdTestOrder extends BaseOpenmrsData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer opdOrderId;
	private Patient patient;
	private Encounter encounter;
	private Concept serviceConcept;
	private Integer typeConcept;
	private Concept valueCoded;
	private User creator;
	private Date createdOn;
	private Boolean billingStatus = false; // false=0=not yet billed,true=1=billed
	private Boolean cancelStatus = false; // false=0=not yet canceled,true=1=canceled
	private String comment;
	private BillableService billableService;
	private Date scheduleDate;
	private Boolean indoorStatus = false; // false=0=not indoor,true=1=indoor
	private Location location;

	public Integer getOpdOrderId() {
		return opdOrderId;
	}

	public void setOpdOrderId(Integer opdOrderId) {
		this.opdOrderId = opdOrderId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Encounter getEncounter() {
		return encounter;
	}

	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}

	public Concept getServiceConcept() {
		return serviceConcept;
	}

	public void setServiceConcept(Concept serviceConcept) {
		this.serviceConcept = serviceConcept;
	}

	public Integer getTypeConcept() {
		return typeConcept;
	}

	public void setTypeConcept(Integer typeConcept) {
		this.typeConcept = typeConcept;
	}

	public Concept getValueCoded() {
		return valueCoded;
	}

	public void setValueCoded(Concept valueCoded) {
		this.valueCoded = valueCoded;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(Boolean billingStatus) {
		this.billingStatus = billingStatus;
	}

	public Boolean getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(Boolean cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BillableService getBillableService() {
		return billableService;
	}

	public void setBillableService(BillableService billableService) {
		this.billableService = billableService;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Boolean getIndoorStatus() {
		return indoorStatus;
	}

	public void setIndoorStatus(Boolean indoorStatus) {
		this.indoorStatus = indoorStatus;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

}
