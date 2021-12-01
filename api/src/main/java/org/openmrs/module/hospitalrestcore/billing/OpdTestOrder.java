/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Encounter;
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
	private Concept concept;
	private Integer typeConcept;
	private Concept valueCoded;
	private User creator;
	private Date createdOn;
	private int billingStatus; //0=not yet billed,1=billed
	private int cancelStatus; //0=not yet canceled,1=canceled
	private BillableService billableService;
	private Date scheduleDate;
	private int indoorStatus;
	private String fromDept;

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

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
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

	public int getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(int billingStatus) {
		this.billingStatus = billingStatus;
	}

	public int getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(int cancelStatus) {
		this.cancelStatus = cancelStatus;
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

	public int getIndoorStatus() {
		return indoorStatus;
	}

	public void setIndoorStatus(int indoorStatus) {
		this.indoorStatus = indoorStatus;
	}

	public String getFromDept() {
		return fromDept;
	}

	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
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

