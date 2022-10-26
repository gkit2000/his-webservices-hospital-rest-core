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
package org.openmrs.module.hospitalrestcore.api;

import org.openmrs.*;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalrestcore.billing.*;
import org.openmrs.module.hospitalrestcore.billing.Ambulance;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.Company;
import org.openmrs.module.hospitalrestcore.billing.Driver;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;
import org.openmrs.module.hospitalrestcore.billing.Tender;
import org.openmrs.module.hospitalrestcore.billing.TenderBill;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplate;
import org.openmrs.module.hospitalrestcore.inventory.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
public interface HospitalRestCoreService extends OpenmrsService {

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

	PatientServiceBillItem saveOrUpdatePatientServiceBillItem(PatientServiceBillItem patientServiceBillItem)
			throws APIException;

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

	PatientServiceBill getPatientServiceBillById(Integer billId) throws APIException;

	PatientServiceBill getPatientServiceBillByIdAndPatient(Integer billId, Patient patient) throws APIException;

	List<PatientServiceBill> getPatientServiceBill(Patient patient) throws APIException;

	PatientServiceBillItem getPatientServiceBillItemById(Integer patientServiceBillItemId) throws APIException;

	PatientServiceBillItem getPatientServiceBillItemByIdAndBill(Integer patientServiceBillItemId,
			PatientServiceBill patientServiceBill) throws APIException;

	List<PatientServiceBillItem> getPatientServiceBillItem(PatientServiceBill patientServiceBill) throws APIException;

	List<ConceptAnswer> getConceptAnswerByAnswerConcept(Concept answerConcept) throws APIException;

	List<Driver> searchDriver(String searchText) throws APIException;

	List<Driver> getAllDriver() throws APIException;

	Driver getDriverByUuid(String uuid) throws APIException;

	Driver saveOrUpdateDriver(Driver driver) throws APIException;

	List<Ambulance> searchAmbulance(String searchText) throws APIException;

	List<Ambulance> getAllAmbulance() throws APIException;

	Ambulance getAmbulanceByUuid(String uuid) throws APIException;

	Ambulance saveOrUpdateAmbulance(Ambulance ambulance) throws APIException;

	List<Role> getAllRoles() throws APIException;

	Role getRoleByUuid(String uuid) throws APIException;

	InventoryStore getStoreByCollectionRole(List<Role> roles) throws APIException;

	InventoryStore saveOrUpdateInventoryStore(InventoryStore inventoryStore) throws APIException;

	InventoryStore getInventoryStoreByUuid(String uuid) throws APIException;

	List<InventoryStore> listAllInventoryStore() throws APIException;

	InventoryStore getMainStore() throws APIException;

	InventoryStore getSubStore() throws APIException;

	InventoryDrugCategory saveOrUpdateInventoryDrugCategory(InventoryDrugCategory inventoryDrugCategory)
			throws APIException;

	InventoryDrugCategory getInventoryDrugCategoryByUuidString(String uuid) throws APIException;

	List<InventoryDrugCategory> listAllInventoryDrugCategory() throws APIException;

	InventoryDrugUnit saveOrUpdateInventoryDrugUnit(InventoryDrugUnit inventoryDrugUnit) throws APIException;

	InventoryDrugUnit getInventoryDrugUnitByUuidString(String uuid) throws APIException;

	List<InventoryDrugUnit> listAllInventoryDrugUnit() throws APIException;

	InventoryDrugFormulation saveOrUpdateInventoryDrugFormulation(InventoryDrugFormulation inventoryDrugFormulation)
			throws APIException;

	InventoryDrugFormulation getInventoryDrugFormulationByUuidString(String uuid) throws APIException;

	List<InventoryDrugFormulation> listAllInventoryDrugFormulation() throws APIException;

	InventoryDrug saveOrUpdateInventoryDrug(InventoryDrug inventoryDrug) throws APIException;

	InventoryDrug getInventoryDrugByUuidString(String uuid) throws APIException;

	Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate, String toDate,
			boolean isExpiry) throws APIException;

	List<InventoryStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId, String drugName,
			String fromDate, String toDate, boolean isExpiry, int min, int max) throws APIException;

	List<InventoryDrugCategory> listDrugCategory(String name, int min, int max) throws APIException;

	Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName, String fromDate,
			String toDate) throws APIException;

	List<InventoryStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
			String itemName, String fromDate, String toDate, int min, int max) throws APIException;

	List<InventoryItemSubCategory> listItemSubCategory(String name, int min, int max) throws APIException;
	
	int countListDrug(Integer categoryId, String name) throws APIException;
	
	List<InventoryDrug> listDrug(Integer categoryId, String name, int min, int max) throws APIException;
	
	Drug getDrugByUuid(String uuid) throws APIException;

	Integer countStoreDrugTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
									  String toDate) throws APIException;

	InventoryStoreDrugTransaction saveOrUpdateStoreDrugTransaction(InventoryStoreDrugTransaction inventoryStoreDrugTransaction) throws APIException;

	List<InventoryStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType, Integer storeId,
																 String description, String fromDate, String toDate, int min, int max) throws APIException;

	InventoryStoreDrugTransaction getInventoryStoreDrugTransactionByUuidString(String uuid) throws APIException;

	InventoryStoreDrugTransactionDetail saveOrUpdateDrugTransactionDetail(
			InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) throws APIException;

	List<InventoryStoreDrugTransactionDetail> listAllStoreDrugTransactionDetail(InventoryStore store) throws APIException;

	List<InventoryStoreDrugTransactionDetail> listAllStoreDrugExpiryTransactionDetail(InventoryStore store) throws APIException;

	InventoryStoreDrugTransactionDetail getDrugTransactionDetailByUuidString(String uuid) throws APIException;

	InventoryStoreDrugReceipt saveOrUpdateDrugReceipt(InventoryStoreDrugReceipt inventoryStoreDrugReceipt) throws APIException;

	List<InventoryStoreDrugReceipt> listAllInventoryStoreDrugReceipt() throws APIException;

	Integer countStoreDrugReceipt(String vendorName, String fromDate, String toDate) throws APIException;

	List<InventoryStoreDrugReceipt> listStoreDrugReceipt(String vendorName, String fromDate, String toDate, int min, int max) throws APIException;

	List<InventoryStoreDrugReceipt> getInventoryStoreDrugReceiptByName(String name) throws APIException;

	InventoryStoreDrugReceiptDetail saveOrUpdateInventoryStoreDrugReceiptDetail(InventoryStoreDrugReceiptDetail inventoryStoreDrugReceiptDetail) throws APIException;

	List<InventoryStoreDrugReceiptDetail> getStoreDrugReceiptDetailByReceiptId(Integer receiptId) throws APIException;

	List<InventoryReceiptFormDetail> listAllInventoryReceiptFormDetail() throws APIException;

	InventoryReceiptFormDetail saveOrUpdateInventoryReceiptFormDetail(InventoryReceiptFormDetail inventoryReceiptFormDetail) throws APIException;

	InventoryStoreDrugIndentDetail saveOrUpdateInventoryStoreDrugIndentDetail(
			InventoryStoreDrugIndentDetail inventoryStoreDrugIndentDetail) throws APIException;

	List<InventoryStoreDrugIndentDetail> listAllInventoryStoreDrugIndentDetail() throws APIException;

	List<InventoryStoreDrugIndent> listAllInventoryStoreDrugIndent() throws APIException;

	InventoryStoreDrugIndent saveOrUpdateInventoryDrugIndent(InventoryStoreDrugIndent inventoryStoreDrugIndent) throws APIException;

	InventoryStoreDrugIndent getInventoryStoreDrugIndentByUuidString(String uuid) throws APIException;

	Integer countStoreDrugIndent(Integer storeId, String storeName, String indentStatus, String indentName, String fromDate,
								 String toDate) throws APIException;

	List<InventoryStoreDrugIndent> listStoreDrugIndent(Integer storeId, String storeName, String indentStatus, String indentName,
													   String fromDate, String toDate, int min, int max) throws APIException;

	Integer countViewStockBalanceExpiry(Integer storeId, String category, String drugName, String fromDate, String toDate) throws APIException;

	List<InventoryDrug> listAllInventoryDrug() throws APIException;

	InventoryStoreDrug saveOrUpdateInventoryStoreDrug(InventoryStoreDrug inventoryStoreDrug) throws APIException;

	InventoryStoreDrugPatient saveOrUpdateInventoryStoreDrugPatient(InventoryStoreDrugPatient inventoryStoreDrugPatient) throws APIException;

	InventoryStoreDrugPatient getInventoryStoreDrugPatientByIdentifier(String identifier) throws APIException;

	InventoryStoreDrugPatient getInventoryStoreDrugPatientById(Integer id) throws APIException;

	List<InventoryStoreDrugPatientDetail> listAllInventoryStoreDrugPatientDetail() throws APIException;

	InventoryStoreIssuePatientDetail getInventoryStoreIssuePatientDetail() throws APIException;

	Integer countStoreDrugPatient(Integer storeId, String identifierOrName, Integer billNo, String fromDate,
										 String toDate) throws APIException;

	List<InventoryStoreDrugPatient> listStoreDrugPatient(Integer storeId, String identifierOrName, Integer billNo,
																String fromDate, String toDate, int min, int max) throws APIException;

	InventoryStoreDrugPatientDetail saveOrUpdateInventoryStoreDrugPatientDetail(
			InventoryStoreDrugPatientDetail inventoryStoreDrugPatientDetail) throws APIException;

	InventoryStoreIssuePatientDetail saveOrUpdateInventoryStoreIssuePatientDetail(
			InventoryStoreIssuePatientDetail inventoryStoreIssuePatientDetail) throws APIException;

	List<InventoryStoreDrug> listAllInventoryStoreDrug(InventoryStore storeId) throws APIException;

	List<InventoryStoreDrugTransactionDetail> listAllStoreDrugTransactionDetail() throws APIException;

	List<InventoryStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, String category, String drugName,
																			 String fromDate, String toDate, int min, int max) throws APIException;

	InventoryStoreDrugIssueDetail saveOrUpdateInventoryStoreDrugIssueDetail(InventoryStoreDrugIssueDetail inventoryStoreDrugIssueDetail) throws APIException;

	List<InventoryStoreDrugIssueDetail> listAllInventoryStoreDrugIssueDetail() throws APIException;

	PatientIdentifier getPatientIdentifierByPatientId(Integer patientId) throws APIException;

	Integer getPatientIdByIdentifierString(String identifier) throws APIException;

	Person getPersonByPersonId(Integer personId) throws APIException;

	Patient getPatientByPatientId(Integer patientId) throws APIException;

	PersonName getPersonNameByPersonId(Integer personId) throws APIException;

	PersonName getPersonNameByNameString(String name) throws APIException;
	
	List<Tender> searchTender(String searchText) throws APIException;

	List<Tender> getAllTender() throws APIException;

	Tender getTenderByUuid(String uuid) throws APIException;

	Tender saveOrUpdateTender(Tender tender) throws APIException;
	
	List<Company> searchCompany(String searchText) throws APIException;

	List<Company> getAllCompanies() throws APIException;
	
	Company getCompanyByUuid(String uuid);
	
	Company saveOrUpdateCompany(Company company) throws APIException;
	
	TenderBill getTenderBillByUuid(String uuid) throws APIException;
	
	List<TenderBill> getTenderBillByCompany(Company company) throws APIException;
	
	TenderBill saveOrUpdateTenderBill(TenderBill tenderBill) throws APIException;

	Integer countDrugOrderPatient(String identifierOrName, String date) throws APIException;

	List<InventoryStoreDrugOrderPatient> listDrugOrderPatient(String identifierOrName, String date, int min, int max) throws APIException;

	InventoryStoreDrugOrderPatient getDrugOrderPatientByIdentifier(String identifier, String date) throws APIException;

	List<InventoryStoreDrugOrderPatientDetail> getDrugOrderPatientDetail(InventoryStoreDrugOrderPatient patient,
																		 String date) throws APIException;

	List<OpdDrugOrder> getDrugOrderByOrderId(Integer orderId) throws APIException;

	List<OpdDrugOrder> listAllDrugOrder() throws APIException;

	OpdDrugOrderDetail saveOrUpdateOpdDrugOrderDetail(OpdDrugOrderDetail orderDetail) throws APIException;

	List<OpdDrugOrderDetail> getDrugOrderDetailByOrderId(Integer orderId) throws APIException;

	List<OpdDrugOrderDetail> listAllDrugOrderDetail() throws APIException;

	List<InventoryStoreDrugOrderIssueDetail> listAllInventoryStoreDrugOrderIssueDetail() throws APIException;

	InventoryStoreDrugOrderIssueDetail saveOrUpdateInventoryStoreDrugOrderIssueDetail(
			InventoryStoreDrugOrderIssueDetail inventoryStoreDrugOrderIssueDetail) throws APIException;

}
