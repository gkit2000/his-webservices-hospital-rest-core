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
package org.openmrs.module.hospitalrestcore.billing.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplate;

/**
 * This service exposes module's core functionality. It is a Spring managed bean
 * which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(AppointmentService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
public interface BillingService extends OpenmrsService {

	List<BillableService> getAllServices() throws APIException;

	List<BillableService> getServicesByPriceCategory(Concept priceCategory) throws APIException;

	BillableService getServicesByServiceConceptAndPriceCategory(Concept serviceConcept, Concept priceCategory)
			throws APIException;

	BillableService saveBillableService(BillableService billableService) throws APIException;

	List<BillableService> saveBillableService(Collection<BillableService> billableServices) throws APIException;

	OpdTestOrder saveOrUpdateOpdTestOrder(OpdTestOrder OpdTestOrder) throws APIException;

	OpdTestOrder getOpdTestOrderById(Integer opdOrderId) throws APIException;

	List<OpdTestOrder> getOpdTestOrder(Patient patient, Date creationDate) throws APIException;

	List<OpdTestOrder> getOpdTestOrderByEncounter(Encounter encounter) throws APIException;

	PatientServiceBill saveOrUpdatePatientServiceBill(PatientServiceBill patientServiceBill) throws APIException;

	BillingReceipt createReceipt(BillingReceipt receipt) throws APIException;

	List<CategoryLocation> getAllCategoryLocation() throws APIException;

	List<CategoryLocation> getCategoryLocationByPriceCategory(Concept priceCategoryConcept) throws APIException;

	CategoryLocation getCategoryLocationByLocation(Location location) throws APIException;

	CategoryLocation getCategoryLocationByPriceCategoryAndLocation(Concept priceCategoryConcept, Location location)
			throws APIException;

	CategoryLocation saveOrUpdateCategoryLocation(CategoryLocation categoryLocation) throws APIException;

	List<ConsentTemplate> getAllConsentTemplate() throws APIException;

	ConsentTemplate getConsentTemplateByUuid(String uuid) throws APIException;

	List<ConsentTemplate> getConsentTemplateByType(Concept type) throws APIException;

	ConsentTemplate saveOrUpdateConsentTemplate(ConsentTemplate consentTemplate) throws APIException;

}
