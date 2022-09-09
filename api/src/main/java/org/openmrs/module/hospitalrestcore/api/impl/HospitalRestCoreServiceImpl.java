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
package org.openmrs.module.hospitalrestcore.api.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.Ambulance;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.Driver;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;
import org.openmrs.module.hospitalrestcore.billing.api.db.AmbulanceDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillableServiceDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillingReceiptDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.CategoryLocationDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.ConceptAnswerDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.DriverDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.OpdTestOrderDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.PatientServiceBillDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.PatientServiceBillItemDAO;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplate;
import org.openmrs.module.hospitalrestcore.consent.api.db.ConsentTemplateDAO;
import org.openmrs.module.hospitalrestcore.inventory.*;
import org.openmrs.module.hospitalrestcore.inventory.api.db.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * It is a default implementation of {@link HospitalRestCoreService}.
 */

public class HospitalRestCoreServiceImpl extends BaseOpenmrsService implements HospitalRestCoreService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private BillableServiceDAO billableServiceDAO;

	private OpdTestOrderDAO opdTestOrderDAO;

	private BillingReceiptDAO billingReceiptDAO;

	private PatientServiceBillDAO patientServiceBillDAO;

	private PatientServiceBillItemDAO patientServiceBillItemDAO;

	private CategoryLocationDAO categoryLocationDAO;

	private ConsentTemplateDAO consentTemplateDAO;

	private ConceptAnswerDAO conceptAnswerDAO;

	private DriverDAO driverDAO;

	private AmbulanceDAO ambulanceDAO;

	private InventoryDrugDAO inventoryDrugDAO;

	private InventoryStoreDAO inventoryStoreDAO;

	private InventoryDrugCategoryDAO inventoryDrugCategoryDAO;

	private InventoryReceiptFormDAO inventoryReceiptFormDAO;

	private InventoryStoreDrugDAO inventoryStoreDrugDAO;

	private InventoryStoreDrugTransactionDetailDAO inventoryStoreDrugTransactionDetailDAO;

	private InventoryStoreDrugTransactionDAO inventoryStoreDrugTransactionDAO;

	private InventoryDrugUnitDAO inventoryDrugUnitDAO;

	private InventoryDrugFormulationDAO inventoryDrugFormulationDAO;

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
	 * @return the patientServiceBillItemDAO
	 */
	public PatientServiceBillItemDAO getPatientServiceBillItemDAO() {
		return patientServiceBillItemDAO;
	}

	/**
	 * @param patientServiceBillItemDAO the patientServiceBillItemDAO to set
	 */
	public void setPatientServiceBillItemDAO(PatientServiceBillItemDAO patientServiceBillItemDAO) {
		this.patientServiceBillItemDAO = patientServiceBillItemDAO;
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

	/**
	 * @return the conceptAnswerDAO
	 */
	public ConceptAnswerDAO getConceptAnswerDAO() {
		return conceptAnswerDAO;
	}

	/**
	 * @param conceptAnswerDAO the conceptAnswerDAO to set
	 */
	public void setConceptAnswerDAO(ConceptAnswerDAO conceptAnswerDAO) {
		this.conceptAnswerDAO = conceptAnswerDAO;
	}

	/**
	 * @return the driverDAO
	 */
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	/**
	 * @param driverDAO the driverDAO to set
	 */
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	/**
	 * @return the ambulanceDAO
	 */
	public AmbulanceDAO getAmbulanceDAO() {
		return ambulanceDAO;
	}

	/**
	 * @param ambulanceDAO the ambulanceDAO to set
	 */
	public void setAmbulanceDAO(AmbulanceDAO ambulanceDAO) {
		this.ambulanceDAO = ambulanceDAO;
	}

	/**
	 * @return the inventoryStoreDAO
	 */
	public InventoryStoreDAO getInventoryStoreDAO() {
		return inventoryStoreDAO;
	}

	/**
	 * @param inventoryStoreDAO the inventoryStoreDAO to set
	 */
	public void setInventoryStoreDAO(InventoryStoreDAO inventoryStoreDAO) {
		this.inventoryStoreDAO = inventoryStoreDAO;
	}

	/**
	 * @return the inventoryDrugDAO
	 */
	public InventoryDrugDAO getInventoryDrugDAO() {
		return inventoryDrugDAO;
	}

	/**
	 * @param inventoryDrugDAO the inventoryDrugDAO to set
	 */
	public void setInventoryDrugDAO(InventoryDrugDAO inventoryDrugDAO) {
		this.inventoryDrugDAO = inventoryDrugDAO;
	}

	/**
	 * @return the inventoryDrugCategoryDAO
	 */
	public InventoryDrugCategoryDAO getInventoryDrugCategoryDAO() {
		return inventoryDrugCategoryDAO;
	}

	/**
	 * @param inventoryDrugCategoryDAO the inventoryDrugCategoryDAO to set
	 */
	public void setInventoryDrugCategoryDAO(InventoryDrugCategoryDAO inventoryDrugCategoryDAO) {
		this.inventoryDrugCategoryDAO = inventoryDrugCategoryDAO;
	}

	/**
	 * @return the inventoryReceiptFormDAO
	 */
	public InventoryReceiptFormDAO getInventoryReceiptFormDAO() {
		return inventoryReceiptFormDAO;
	}

	/**
	 * @param inventoryReceiptFormDAO the inventoryReceiptFormDAO to set
	 */
	public void setInventoryReceiptFormDAO(InventoryReceiptFormDAO inventoryReceiptFormDAO) {
		this.inventoryReceiptFormDAO = inventoryReceiptFormDAO;
	}

	/**
	 * @return the inventoryStoreDrugTransactionDetailDAO
	 */
	public InventoryStoreDrugTransactionDetailDAO getInventoryStoreDrugTransactionDetailDAO() {
		return inventoryStoreDrugTransactionDetailDAO;
	}

	/**
	 * @param inventoryStoreDrugTransactionDetailDAO the inventoryStoreDrugTransactionDetailDAO to set
	 */
	public void setInventoryStoreDrugTransactionDetailDAO(InventoryStoreDrugTransactionDetailDAO inventoryStoreDrugTransactionDetailDAO) {
		this.inventoryStoreDrugTransactionDetailDAO = inventoryStoreDrugTransactionDetailDAO;
	}

	/**
	 * @return the inventoryStoreDrugTransactionDAO
	 */
	public InventoryStoreDrugTransactionDAO getInventoryStoreDrugTransactionDAO() {
		return inventoryStoreDrugTransactionDAO;
	}

	/**
	 * @param inventoryStoreDrugTransactionDAO the inventoryStoreDrugTransactionDAO to set
	 */
	public void setInventoryStoreDrugTransactionDAO(InventoryStoreDrugTransactionDAO inventoryStoreDrugTransactionDAO) {
		this.inventoryStoreDrugTransactionDAO = inventoryStoreDrugTransactionDAO;
	}

	/**
	 * @return the inventoryStoreDrugIndentDAO
	 */
	public InventoryStoreDrugDAO getInventoryStoreDrugDAO() {
		return inventoryStoreDrugDAO;
	}

	/**
	 * @param inventoryStoreDrugDAO the inventoryStoreDrugIndent to set
	 */
	public void setInventoryStoreDrugDAO(InventoryStoreDrugDAO inventoryStoreDrugDAO) {
		this.inventoryStoreDrugDAO = inventoryStoreDrugDAO;
	}


	/**
	 * @return the inventoryDrugUnitDAO
	 */
	public InventoryDrugUnitDAO getInventoryDrugUnitDAO() {
		return inventoryDrugUnitDAO;
	}

	/**
	 * @param inventoryDrugUnitDAO the inventoryDrugUnitDAO to set
	 */
	public void setInventoryDrugUnitDAO(InventoryDrugUnitDAO inventoryDrugUnitDAO) {
		this.inventoryDrugUnitDAO = inventoryDrugUnitDAO;
	}

	/**
	 * @return the inventoryDrugFormulationDAO
	 */
	public InventoryDrugFormulationDAO getInventoryDrugFormulationDAO() {
		return inventoryDrugFormulationDAO;
	}

	/**
	 * @param inventoryDrugFormulationDAO the inventoryDrugFormulationDAO to set
	 */
	public void setInventoryDrugFormulationDAO(InventoryDrugFormulationDAO inventoryDrugFormulationDAO) {
		this.inventoryDrugFormulationDAO = inventoryDrugFormulationDAO;
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
	public PatientServiceBillItem saveOrUpdatePatientServiceBillItem(PatientServiceBillItem patientServiceBillItem)
			throws APIException {
		return (PatientServiceBillItem) getPatientServiceBillItemDAO().saveOrUpdate(patientServiceBillItem);
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

	@Override
	@Transactional
	public PatientServiceBill getPatientServiceBillById(Integer billId) throws APIException {
		return getPatientServiceBillDAO().getPatientServiceBillById(billId);
	}

	@Override
	@Transactional
	public PatientServiceBill getPatientServiceBillByIdAndPatient(Integer billId, Patient patient) throws APIException {
		return getPatientServiceBillDAO().getPatientServiceBillByIdAndPatient(billId, patient);
	}

	@Override
	@Transactional
	public List<PatientServiceBill> getPatientServiceBill(Patient patient) throws APIException {
		return getPatientServiceBillDAO().getPatientServiceBill(patient);
	}

	@Override
	@Transactional
	public PatientServiceBillItem getPatientServiceBillItemById(Integer patientServiceBillItemId) throws APIException {
		return getPatientServiceBillItemDAO().getPatientServiceBillItemById(patientServiceBillItemId);
	}

	@Override
	@Transactional
	public PatientServiceBillItem getPatientServiceBillItemByIdAndBill(Integer patientServiceBillItemId,
			PatientServiceBill patientServiceBill) throws APIException {
		return getPatientServiceBillItemDAO().getPatientServiceBillItemByIdAndBill(patientServiceBillItemId,
				patientServiceBill);
	}

	@Override
	@Transactional
	public List<PatientServiceBillItem> getPatientServiceBillItem(PatientServiceBill patientServiceBill)
			throws APIException {
		return getPatientServiceBillItemDAO().getPatientServiceBillItem(patientServiceBill);
	}

	@Override
	@Transactional
	public List<ConceptAnswer> getConceptAnswerByAnswerConcept(Concept answerConcept) throws APIException {
		return getConceptAnswerDAO().getConceptAnswerByAnswerConcept(answerConcept);
	}

	@Override
	@Transactional
	public List<Driver> searchDriver(String searchText) throws APIException {
		return getDriverDAO().searchDriver(searchText);
	}

	@Override
	@Transactional
	public List<Driver> getAllDriver() throws APIException {
		return getDriverDAO().getAllDriver();
	}

	@Override
	@Transactional
	public Driver getDriverByUuid(String uuid) throws APIException {
		return getDriverDAO().getDriverByUuid(uuid);
	}

	@Override
	@Transactional
	public Driver saveOrUpdateDriver(Driver driver) throws APIException {
		return (Driver) getDriverDAO().saveOrUpdate(driver);
	}

	@Override
	@Transactional
	public List<Ambulance> searchAmbulance(String searchText) throws APIException {
		return getAmbulanceDAO().searchAmbulance(searchText);
	}

	@Override
	@Transactional
	public List<Ambulance> getAllAmbulance() throws APIException {
		return getAmbulanceDAO().getAllAmbulance();
	}

	@Override
	@Transactional
	public Ambulance getAmbulanceByUuid(String uuid) throws APIException {
		return getAmbulanceDAO().getAmbulanceByUuid(uuid);
	}

	@Override
	@Transactional
	public Ambulance saveOrUpdateAmbulance(Ambulance ambulance) throws APIException {
		return (Ambulance) getAmbulanceDAO().saveOrUpdate(ambulance);
	}

	@Override
	@Transactional
	public List<Role> getAllRoles() throws APIException {
		return getInventoryStoreDAO().getAllRoles();
	}

	@Override
	public Role getRoleByUuid(String uuid) throws APIException {
		return getInventoryStoreDAO().getRoleByUuid(uuid);
	}

	@Override
	@Transactional
	public InventoryStore getStoreByCollectionRole(List<Role> roles) throws APIException {
		return (InventoryStore) getInventoryStoreDAO().getStoreByCollectionRole(roles);
	}

	@Override
	public InventoryStore saveOrUpdateInventoryStore(InventoryStore inventoryStore) throws APIException {
		return (InventoryStore) getInventoryStoreDAO().saveOrUpdate(inventoryStore);
	}

	@Override
	public InventoryStore getInventoryStoreByUuid(String uuid) throws APIException {
		return getInventoryStoreDAO().getInventoryStoreByUuid(uuid);
	}

	@Override
	public List<InventoryStore> listAllInventoryStore() throws APIException {
		return getInventoryStoreDAO().listAllInventoryStore();
	}

	@Override
	@Transactional
	public InventoryDrugCategory saveOrUpdateInventoryDrugCategory(InventoryDrugCategory inventoryDrugCategory)
			throws APIException {
		return (InventoryDrugCategory) getInventoryDrugCategoryDAO().saveOrUpdate(inventoryDrugCategory);
	}

	@Override
	@Transactional
	public InventoryDrugCategory getInventoryDrugCategoryByUuidString(String uuid) throws APIException {
		return getInventoryDrugCategoryDAO().getInventoryDrugCategoryByUuidString(uuid);
	}

	@Override
	public List<InventoryDrugCategory> listAllInventoryDrugCategory() throws APIException {
		return getInventoryDrugCategoryDAO().listAllInventoryDrugCategory();
	}

	@Override
	@Transactional
	public InventoryDrugUnit saveOrUpdateInventoryDrugUnit(InventoryDrugUnit inventoryDrugUnit) throws APIException {
		return (InventoryDrugUnit) getInventoryDrugUnitDAO().saveOrUpdate(inventoryDrugUnit);
	}

	@Override
	@Transactional
	public InventoryDrugUnit getInventoryDrugUnitByUuidString(String uuid) throws APIException {
		return getInventoryDrugUnitDAO().getInventoryDrugUnitByUuidString(uuid);
	}

	@Override
	public List<InventoryDrugUnit> listAllInventoryDrugUnit() throws APIException {
		return getInventoryDrugUnitDAO().listAllInventoryDrugUnit();
	}

	@Override
	@Transactional
	public InventoryDrugFormulation saveOrUpdateInventoryDrugFormulation(
			InventoryDrugFormulation inventoryDrugFormulation) throws APIException {
		return (InventoryDrugFormulation) getInventoryDrugFormulationDAO().saveOrUpdate(inventoryDrugFormulation);
	}

	@Override
	public InventoryDrugFormulation getInventoryDrugFormulationByUuidString(String uuid) throws APIException {
		return getInventoryDrugFormulationDAO().getInventoryDrugFormulationByUuidString(uuid);
	}

	@Override
	public List<InventoryDrugFormulation> listAllInventoryDrugFormulation() throws APIException {
		return getInventoryDrugFormulationDAO().listAllInventoryDrugFormulation();
	}

	@Override
	@Transactional
	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate,
			String toDate, boolean isExpiry) throws APIException {
		return getInventoryStoreDAO().countViewStockBalance(storeId, categoryId, drugName, fromDate, toDate, isExpiry);
	}

	@Override
	@Transactional
	public List<InventoryStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId,
			String drugName, String fromDate, String toDate, boolean isExpiry, int min, int max) throws APIException {
		return getInventoryStoreDAO().listViewStockBalance(storeId, categoryId, drugName, fromDate, toDate, isExpiry,
				min, max);
	}

	@Override
	@Transactional
	public List<InventoryDrugCategory> listDrugCategory(String name, int min, int max) throws APIException {
		return getInventoryStoreDAO().listDrugCategory(name, min, max);
	}

	@Override
	@Transactional
	public Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName, String fromDate,
			String toDate) throws APIException {
		return getInventoryStoreDAO().countStoreItemViewStockBalance(storeId, categoryId, itemName, fromDate, toDate);
	}

	@Override
	@Transactional
	public List<InventoryStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
			String itemName, String fromDate, String toDate, int min, int max) throws APIException {
		return getInventoryStoreDAO().listStoreItemViewStockBalance(storeId, categoryId, itemName, fromDate, toDate,
				min, max);
	}

	@Override
	@Transactional
	public List<InventoryItemSubCategory> listItemSubCategory(String name, int min, int max) throws APIException {
		return getInventoryStoreDAO().listItemSubCategory(name, min, max);
	}

	@Override
	@Transactional
	public InventoryReceiptForm saveOrUpdateInventoryReceiptForm(
			InventoryReceiptForm inventoryReceiptForm) throws APIException {
		return (InventoryReceiptForm) getInventoryReceiptFormDAO().saveOrUpdate(inventoryReceiptForm);
	}

	@Override
	@Transactional
	public InventoryReceiptForm getInventoryReceiptFormByUuidString(String uuid) throws APIException {
		return getInventoryReceiptFormDAO().getInventoryReceiptFormByUuidString(uuid);
	}

	@Override
	@Transactional
	public List<InventoryReceiptForm> listAllInventoryReceiptForm() throws APIException {
		return getInventoryReceiptFormDAO().listAllInventoryReceiptForm();
	}

	@Override
	@Transactional
	public Integer countStoreDrugTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
											 String toDate) throws APIException {
		return getInventoryStoreDAO().countStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate);
	}

	@Override
	@Transactional
	public List<InventoryStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType, Integer storeId,
																		String description, String fromDate, String toDate, int min, int max) throws APIException {
		return getInventoryStoreDAO().listStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate, min, max);
	}

	@Override
	@Transactional
	public InventoryStoreDrugTransaction saveOrUpdateStoreDrugTransaction(InventoryStoreDrugTransaction inventoryStoreDrugTransaction)
			throws APIException {
		return (InventoryStoreDrugTransaction) getInventoryStoreDrugTransactionDAO().saveOrUpdate(inventoryStoreDrugTransaction);
	}

	@Override
	@Transactional
	public InventoryStoreDrugTransaction getInventoryStoreDrugTransactionByUuidString(String uuid) throws APIException {
		return getInventoryStoreDAO().getInventoryStoreDrugTransactionByUuidString(uuid);
	}

	@Override
	@Transactional
	public Integer countReceiptsToGeneralStore(String companyName, String fromDate, String toDate) throws APIException {
		return getInventoryReceiptFormDAO().countReceiptsToGeneralStore(companyName, fromDate, toDate);
	}

	@Override
	@Transactional
	public List<InventoryReceiptForm> listReceiptsToGeneralStore(String companyName,
																 String fromDate, String toDate, int min, int max) throws APIException {
		return getInventoryReceiptFormDAO().listReceiptsToGeneralStore(companyName, fromDate, toDate, min, max);
	}

	@Override
	@Transactional
	public List<InventoryStoreDrugIndent> listAllInventoryStoreDrug() throws APIException {
		return getInventoryStoreDrugDAO().listAllInventoryStoreDrug();
	}

	@Override
	@Transactional
	public InventoryStoreDrugIndent saveOrUpdateInventoryDrugIndent(InventoryStoreDrugIndent inventoryStoreDrugIndent) throws APIException {
		return (InventoryStoreDrugIndent) getInventoryStoreDrugDAO().saveOrUpdate(inventoryStoreDrugIndent);
	}

	@Override
	@Transactional
	public InventoryStoreDrugIndent getInventoryStoreDrugByUuidString(String uuid) throws APIException {
		return getInventoryStoreDrugDAO().getInventoryStoreDrugByUuidString(uuid);
	}

	@Override
	@Transactional
	public Integer countStoreDrugFromGeneralStore(String storeName, String indentStatus, String indentName, String fromDate,
												  String toDate) throws APIException {
		return getInventoryStoreDrugDAO().countStoreDrugFromGeneralStore(storeName, indentStatus, indentName, fromDate, toDate);
	}

	@Override
	@Transactional
	public List<InventoryStoreDrugIndent> listStoreDrugFromGeneralStore(String storeName, String indentStatus,
																		String indentName, String fromDate, String toDate, int min, int max) throws APIException {
		return getInventoryStoreDrugDAO().listStoreDrugFromGeneralStore(storeName, indentStatus, indentName, fromDate, toDate, min, max);
	}

	@Override
	@Transactional
	public InventoryStoreDrugTransactionDetail saveOrUpdateDrugTransactionDetail(
			InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) throws APIException {
		return (InventoryStoreDrugTransactionDetail) getInventoryStoreDrugTransactionDetailDAO().saveOrUpdate(inventoryStoreDrugTransactionDetail);
	}

	@Override
	@Transactional
	public List<InventoryStoreDrugTransactionDetail> listAllStoreDrugTransactionDetail() throws APIException {
		return getInventoryStoreDrugTransactionDetailDAO().listAllStoreDrugTransactionDetail();
	}

	@Override
	@Transactional
	public List<InventoryStoreDrugTransactionDetail> listStoreDrugTransactionDetail(String category, String drugName,
			String fromDate, String toDate, int min, int max) throws APIException {
		return getInventoryStoreDrugTransactionDetailDAO().listStoreDrugTransactionDetail(category, drugName, fromDate,
				toDate, min, max);
	}

	@Override
	@Transactional
	public InventoryStoreDrugTransactionDetail getDrugTransactionDetailByUuidString(String uuid) throws APIException {
		return getInventoryStoreDrugTransactionDetailDAO().getDrugTransactionDetailByUuidString(uuid);
	}

	@Override
	@Transactional
	public Integer countViewStockBalanceExpiry(String category, String drugName, String fromDate, String toDate) throws APIException {
		return getInventoryStoreDrugTransactionDetailDAO().countViewStockBalanceExpiry(category, drugName, fromDate, toDate);
	}

	@Override
	@Transactional
	public List<InventoryDrug> listAllInventoryDrug() throws APIException {
		return getInventoryDrugDAO().listAllInventoryDrug();
	}
}
