/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.hospitalrestcore.billing.api.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.api.BillingService;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillableServiceDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillingReceiptDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.CategoryLocationDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.OpdTestOrderDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.PatientServiceBillDAO;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplate;
import org.openmrs.module.hospitalrestcore.consent.api.db.ConsentTemplateDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * It is a default implementation of {@link BillingService}.
 */

public class BillingServiceImpl extends BaseOpenmrsService implements BillingService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private BillableServiceDAO billableServiceDAO;

	private OpdTestOrderDAO opdTestOrderDAO;

	private BillingReceiptDAO billingReceiptDAO;

	private PatientServiceBillDAO patientServiceBillDAO;

	private CategoryLocationDAO categoryLocationDAO;

	private ConsentTemplateDAO consentTemplateDAO;

	/**
	 * @return the billableServiceDAO
	 */
	public BillableServiceDAO getBillableServiceDAO() {
		return billableServiceDAO;
	}

	/**
	 * @param billableServiceDAO the billableServiceDAO to set
	 */
	public void setBillableServiceDAO(BillableServiceDAO billableServiceDAO) {
		this.billableServiceDAO = billableServiceDAO;
	}

	/**
	 * @return the opdTestOrderDAO
	 */
	public OpdTestOrderDAO getOpdTestOrderDAO() {
		return opdTestOrderDAO;
	}

	/**
	 * @param opdTestOrderDAO the opdTestOrderDAO to set
	 */
	public void setOpdTestOrderDAO(OpdTestOrderDAO opdTestOrderDAO) {
		this.opdTestOrderDAO = opdTestOrderDAO;
	}

	/**
	 * @return the billingReceiptDAO
	 */
	public BillingReceiptDAO getBillingReceiptDAO() {
		return billingReceiptDAO;
	}

	/**
	 * @param billingReceiptDAO the billingReceiptDAO to set
	 */
	public void setBillingReceiptDAO(BillingReceiptDAO billingReceiptDAO) {
		this.billingReceiptDAO = billingReceiptDAO;
	}

	/**
	 * @return the patientServiceBillDAO
	 */
	public PatientServiceBillDAO getPatientServiceBillDAO() {
		return patientServiceBillDAO;
	}

	/**
	 * @param patientServiceBillDAO the patientServiceBillDAO to set
	 */
	public void setPatientServiceBillDAO(PatientServiceBillDAO patientServiceBillDAO) {
		this.patientServiceBillDAO = patientServiceBillDAO;
	}

	/**
	 * @return the categoryLocationDAO
	 */
	public CategoryLocationDAO getCategoryLocationDAO() {
		return categoryLocationDAO;
	}

	/**
	 * @param categoryLocationDAO the categoryLocationDAO to set
	 */
	public void setCategoryLocationDAO(CategoryLocationDAO categoryLocationDAO) {
		this.categoryLocationDAO = categoryLocationDAO;
	}

	/**
	 * @return the consentTemplateDAO
	 */
	public ConsentTemplateDAO getConsentTemplateDAO() {
		return consentTemplateDAO;
	}

	/**
	 * @param consentTemplateDAO the consentTemplateDAO to set
	 */
	public void setConsentTemplateDAO(ConsentTemplateDAO consentTemplateDAO) {
		this.consentTemplateDAO = consentTemplateDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BillableService> getAllServices() throws APIException {
		return getBillableServiceDAO().getAllServices();
	}

	@Override
	@Transactional(readOnly = true)
	public List<BillableService> getServicesByPriceCategory(Concept priceCategory) throws APIException {
		return getBillableServiceDAO().getServicesByPriceCategory(priceCategory);
	}

	@Override
	@Transactional(readOnly = true)
	public BillableService getServicesByServiceConceptAndPriceCategory(Concept serviceConcept, Concept priceCategory)
			throws APIException {
		return getBillableServiceDAO().getServicesByServiceConceptAndPriceCategory(serviceConcept, priceCategory);
	}

	@Override
	@Transactional
	public BillableService saveBillableService(BillableService billableService) throws APIException {
		// ValidateUtil.validate(appointmentType);
		return (BillableService) getBillableServiceDAO().saveOrUpdate(billableService);
	}

	@Override
	@Transactional
	public List<BillableService> saveBillableService(Collection<BillableService> billableServices) throws APIException {
		return (List<BillableService>) getBillableServiceDAO().saveOrUpdate(billableServices);
	}

	@Override
	@Transactional
	public OpdTestOrder saveOrUpdateOpdTestOrder(OpdTestOrder opdTestOrder) throws APIException {
		return (OpdTestOrder) getOpdTestOrderDAO().saveOrUpdate(opdTestOrder);
	}

	@Override
	@Transactional
	public OpdTestOrder getOpdTestOrderById(Integer opdOrderId) throws APIException {
		return getOpdTestOrderDAO().getOpdTestOrderById(opdOrderId);
	}

	@Override
	@Transactional
	public List<OpdTestOrder> getOpdTestOrder(Patient patient, Date creationDate) throws APIException {
		return getOpdTestOrderDAO().getOpdTestOrder(patient, creationDate);
	}

	@Override
	@Transactional
	public List<OpdTestOrder> getOpdTestOrderByEncounter(Encounter encounter) throws APIException {
		return getOpdTestOrderDAO().getOpdTestOrderByEncounter(encounter);
	}

	@Override
	@Transactional
	public BillingReceipt createReceipt(BillingReceipt receipt) throws APIException {
		return (BillingReceipt) getBillingReceiptDAO().saveOrUpdate(receipt);
	}

	@Override
	@Transactional
	public PatientServiceBill saveOrUpdatePatientServiceBill(PatientServiceBill patientServiceBill)
			throws APIException {
		return (PatientServiceBill) getPatientServiceBillDAO().saveOrUpdate(patientServiceBill);
	}

	@Override
	@Transactional
	public List<CategoryLocation> getAllCategoryLocation() throws APIException {
		return getCategoryLocationDAO().getAllCategoryLocation();
	}

	@Override
	@Transactional
	public List<CategoryLocation> getCategoryLocationByPriceCategory(Concept priceCategoryConcept) throws APIException {
		return getCategoryLocationDAO().getCategoryLocationByPriceCategory(priceCategoryConcept);
	}

	@Override
	@Transactional
	public CategoryLocation getCategoryLocationByLocation(Location location) throws APIException {
		return getCategoryLocationDAO().getCategoryLocationByLocation(location);
	}

	@Override
	@Transactional
	public CategoryLocation getCategoryLocationByPriceCategoryAndLocation(Concept priceCategoryConcept,
			Location location) throws APIException {
		return getCategoryLocationDAO().getCategoryLocationByPriceCategoryAndLocation(priceCategoryConcept, location);
	}

	@Override
	@Transactional
	public CategoryLocation saveOrUpdateCategoryLocation(CategoryLocation categoryLocation) throws APIException {
		return (CategoryLocation) getCategoryLocationDAO().saveOrUpdate(categoryLocation);
	}

	@Override
	@Transactional
	public List<ConsentTemplate> getAllConsentTemplate() throws APIException {
		return getConsentTemplateDAO().getAllConsentTemplate();
	}

	@Override
	@Transactional
	public ConsentTemplate getConsentTemplateByUuid(String uuid) throws APIException {
		return getConsentTemplateDAO().getConsentTemplateByUuid(uuid);
	}

	@Override
	@Transactional
	public List<ConsentTemplate> getConsentTemplateByType(Concept type) throws APIException {
		return getConsentTemplateDAO().getConsentTemplateByType(type);
	}

	@Override
	@Transactional
	public ConsentTemplate saveOrUpdateConsentTemplate(ConsentTemplate consentTemplate) throws APIException {
		return (ConsentTemplate) getConsentTemplateDAO().saveOrUpdate(consentTemplate);
	}

}
