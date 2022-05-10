/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Visit;
import org.openmrs.VisitAttribute;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.Money;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.BillableServiceDetails;
import org.openmrs.module.hospitalrestcore.billing.BillingOrderDetails;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.OrderDTO;
import org.openmrs.module.hospitalrestcore.billing.OrderServiceDetails;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;
import org.openmrs.module.hospitalrestcore.billing.ProcessOrdersResponseDTO;
import org.openmrs.module.hospitalrestcore.billing.ServiceDetailsForTestOrder;
import org.openmrs.module.hospitalrestcore.billing.TestOrderDetails;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ghanshyam
 *
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/procedureinvestigationorder")
public class ProcedureInvestigationOrderController extends BaseRestController {

	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public void getProcedureInvestigationOrders(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "patientUuid", required = false) String patientUuid,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "priceCategoryConceptUuid", required = false) String priceCategoryConceptUuid,
			@RequestParam(value = "serviceConceptUuids", required = false) List<String> serviceConceptUuids)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		PatientService ps = Context.getPatientService();
		LocationService ls = Context.getLocationService();
		Patient patient = null;
		if (patientUuid != null && !patientUuid.isEmpty()) {
			patient = ps.getPatientByUuid(patientUuid);
			if (patient == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_PATIENT_UUID, patientUuid));
			}
		}
		EncounterService es = Context.getEncounterService();
		ConceptService conceptService = Context.getConceptService();
		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		Location location = null;
		Date creationDate = null;
		if (date != null && !date.isEmpty()) {
			creationDate = formatter.parse(date);
		}
		List<OpdTestOrder> opdTestOrders = hospitalRestCoreService.getOpdTestOrder(patient, creationDate);
		Map<Integer, Location> encounterAndLocationMap = new LinkedHashMap<Integer, Location>();
		for (OpdTestOrder opdTestOrder : opdTestOrders) {
			encounterAndLocationMap.put(opdTestOrder.getEncounter().getId(), opdTestOrder.getLocation());
		}

		OrderDTO orderDTO = new OrderDTO();
		List<TestOrderDetails> listOforders = new ArrayList<TestOrderDetails>();
		Map<Integer, List<ServiceDetailsForTestOrder>> opdTestOrderMap = new LinkedHashMap<Integer, List<ServiceDetailsForTestOrder>>();

		for (Map.Entry<Integer, Location> entry : encounterAndLocationMap.entrySet()) {
			Integer encounterId = entry.getKey();
			location = entry.getValue();
			Encounter encounter = es.getEncounter(entry.getKey());
			List<OpdTestOrder> opdTestOrdersByEncounter = hospitalRestCoreService.getOpdTestOrderByEncounter(encounter);
			List<BillableServiceDetails> billableServiceDetails = new LinkedList<BillableServiceDetails>();
			billableServiceDetails.clear();
			for (OpdTestOrder opdTestOrderByEncounter : opdTestOrdersByEncounter) {
				BillableServiceDetails bsd = new BillableServiceDetails();
				bsd.setServiceConUuid(opdTestOrderByEncounter.getBillableService().getServiceConcept().getUuid());
				bsd.setServiceConName(
						opdTestOrderByEncounter.getBillableService().getServiceConcept().getName().getName());
				bsd.setPrice(opdTestOrderByEncounter.getBillableService().getPrice());
				bsd.setOpdOrderId(opdTestOrderByEncounter.getOpdOrderId());
				billableServiceDetails.add(bsd);
			}

			if (patientUuid != null && date != null && priceCategoryConceptUuid != null
					&& serviceConceptUuids != null) {
				Concept priceCategoryConcept = conceptService.getConceptByUuid(priceCategoryConceptUuid);
				if (priceCategoryConcept == null) {
					throw new ResourceNotFoundException(String
							.format(OpenmrsCustomConstants.VALIDATION_ERROR_PRICE_CATEGORY, priceCategoryConceptUuid));
				}
				for (String serviceConceptUuid : serviceConceptUuids) {
					Concept serviceConcept = conceptService.getConceptByUuid(serviceConceptUuid);
					if (serviceConcept == null) {
						throw new ResourceNotFoundException(
								String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_SERVICE_CONCEPT_UUID,
										serviceConceptUuid));
					}
					BillableService billableService = hospitalRestCoreService
							.getServicesByServiceConceptAndPriceCategory(serviceConcept, priceCategoryConcept);
					if (billableService == null) {
						throw new ResourceNotFoundException(
								String.format(OpenmrsCustomConstants.VALIDATION_ERROR_BILLABLE_SERVICE,
										serviceConcept.getName().getName(), priceCategoryConcept.getName().getName()));
					}
					BillableServiceDetails bsd = new BillableServiceDetails();
					bsd.setServiceConUuid(serviceConcept.getUuid());
					bsd.setServiceConName(serviceConcept.getName().getName());
					bsd.setPrice(billableService.getPrice());
					bsd.setOpdOrderId(null);
					billableServiceDetails.add(bsd);
				}
			}

			List<ServiceDetailsForTestOrder> serviceDetailsForTestOrders = new ArrayList<ServiceDetailsForTestOrder>();
			ServiceDetailsForTestOrder serviceDetailsForTestOrder = new ServiceDetailsForTestOrder();
			serviceDetailsForTestOrders.clear();
			serviceDetailsForTestOrder.setEncounterId(encounter.getId());
			serviceDetailsForTestOrder.setEncounterUuid(encounter.getUuid());
			serviceDetailsForTestOrder.setLocationId(location.getLocationId());
			serviceDetailsForTestOrder.setLocationUuid(location.getUuid());
			serviceDetailsForTestOrder.setLocationName(location.getName());
			serviceDetailsForTestOrder.setBillableServiceDetails(billableServiceDetails);
			serviceDetailsForTestOrders.add(serviceDetailsForTestOrder);
			opdTestOrderMap.put(encounterId, serviceDetailsForTestOrders);
		}

		Map<Integer, Encounter> patientEncounterMap = new LinkedHashMap<Integer, Encounter>();
		PatientIdentifierType pit = ps.getPatientIdentifierTypeByUuid("05a29f94-c0ed-11e2-94be-8c13b969e334");
		CategoryLocation categoryLocation = hospitalRestCoreService.getCategoryLocationByLocation(location);

		for (Map.Entry<Integer, List<ServiceDetailsForTestOrder>> entry : opdTestOrderMap.entrySet()) {
			Encounter encounter = es.getEncounter(entry.getKey());
			patientEncounterMap.put(encounter.getPatient().getId(), encounter);
		}

		for (Map.Entry<Integer, Encounter> entry : patientEncounterMap.entrySet()) {
			TestOrderDetails orders = new TestOrderDetails();
			Patient pat = ps.getPatient(entry.getKey());
			Encounter enc = entry.getValue();
			orders.setPatientId(pat.getPatientIdentifier(pit).getIdentifier());
			orders.setPatientUuid(pat.getUuid());
			orders.setPatientName(getName(pat));
			orders.setGender(pat.getGender());
			orders.setAge(pat.getAge());
			if (pat.getBirthdate() != null) {
				orders.setBirthDate(formatter.format(pat.getBirthdate()));
			}
			Visit visit = enc.getVisit();
			for (VisitAttribute va : visit.getActiveAttributes()) {
				if (va.getAttributeType().getUuid().equals("80c68ebe-d696-4b8e-8aa0-53018f8e5d7b")) {
					orders.setPatientCategory(va.getValueReference());
				}
			}
			orders.setPriceCategoryConceptUuid(categoryLocation.getPriceCategoryConcept().getUuid());
			orders.setServiceDetailsForTestOrder(opdTestOrderMap.get(enc.getId()));
			listOforders.add(orders);
		}
		orderDTO.setTestOrderDetails(listOforders);

		new ObjectMapper().writeValue(out, orderDTO);
	}

	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public ResponseEntity<ProcessOrdersResponseDTO> processOrders(HttpServletResponse response,
			HttpServletRequest request, @Valid @RequestBody BillingOrderDetails billingOrderDetails)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		HttpSession httpSession = request.getSession();

		ConceptService conceptService = Context.getService(ConceptService.class);
		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		PatientService ps = Context.getPatientService();

		Patient patient = null;

		PatientServiceBill bill = new PatientServiceBill();
		bill.setCreatedDate(new Date());
		bill.setCreator(Context.getAuthenticatedUser());

		Concept priceCategoryConcept = null;

		float waiverPercentage = billingOrderDetails.getWaiverPercentage();
		BigDecimal totalAmountPayable = billingOrderDetails.getTotalAmountPayable();
		Integer amountGiven = billingOrderDetails.getAmountGiven();
		Integer amountReturned = billingOrderDetails.getAmountReturned();
		float total = billingOrderDetails.getTotal();

		PatientServiceBillItem item;
		Money mUnitPrice;
		Money itemAmount;
		Money totalAmount = new Money(BigDecimal.ZERO);
		BigDecimal rate;
		BigDecimal totalActualAmount = new BigDecimal(0);

		for (OrderServiceDetails osd : billingOrderDetails.getOrderServiceDetails()) {

			if (osd.getOpdOrderId() != null) {
				OpdTestOrder opdTestOrder = hospitalRestCoreService.getOpdTestOrderById(osd.getOpdOrderId());
				if (opdTestOrder == null) {
					throw new ResourceNotFoundException(String.format(
							OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_OPD_ORDER_ID, osd.getOpdOrderId()));
				}
				patient = opdTestOrder.getPatient();
				Integer quantity = osd.getQuantity();
				if (quantity < 0) {
					throw new ResourceNotFoundException(
							String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_QUANTITY,
									opdTestOrder.getBillableService().getName()));
				}
			} else {
				Concept serviceConcept = conceptService.getConceptByUuid(osd.getServiceConceptUUid());
				if (serviceConcept == null) {
					throw new ResourceNotFoundException(
							String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_SERVICE_CONCEPT_UUID,
									osd.getServiceConceptUUid()));
				}
				if ("walkin".equals(billingOrderDetails.getBillType())) {
					patient = ps.getPatientByUuid(billingOrderDetails.getPatientUuid());
					if (patient == null) {
						throw new ResourceNotFoundException(
								String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_PATIENT_UUID,
										billingOrderDetails.getPatientUuid()));
					}
				}
				Integer quantity = osd.getQuantity();
				if (quantity < 0) {
					throw new ResourceNotFoundException(
							String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_QUANTITY,
									serviceConcept.getName().getName()));
				}
			}
		}

		for (OrderServiceDetails osd : billingOrderDetails.getOrderServiceDetails()) {

			if (osd.getOpdOrderId() != null) {
				OpdTestOrder opdTestOrder = hospitalRestCoreService.getOpdTestOrderById(osd.getOpdOrderId());
				if (opdTestOrder != null) {
					if (osd.getBilled()) {

						Concept service = opdTestOrder.getBillableService().getServiceConcept();
						Integer quantity = osd.getQuantity();
						BigDecimal unitPrice = opdTestOrder.getBillableService().getPrice();
						priceCategoryConcept = opdTestOrder.getBillableService().getPriceCategoryConcept();

						mUnitPrice = new Money(unitPrice);
						itemAmount = mUnitPrice.times(quantity);
						totalAmount = totalAmount.plus(itemAmount);

						item = new PatientServiceBillItem();
						item.setCreatedDate(new Date());
						item.setName(service.getName().getName());
						item.setPatientServiceBill(bill);
						item.setQuantity(quantity);
						item.setBillableService(opdTestOrder.getBillableService());
						item.setUnitPrice(unitPrice);
						item.setAmount(itemAmount.getAmount());

						rate = new BigDecimal(1);
						item.setActualAmount(item.getAmount().multiply(rate));
						totalActualAmount = totalActualAmount.add(item.getActualAmount());
						bill.addBillItem(item);

						opdTestOrder.setBillingStatus(true);
						hospitalRestCoreService.saveOrUpdateOpdTestOrder(opdTestOrder);

					} else {
						opdTestOrder.setCancelStatus(true);
						hospitalRestCoreService.saveOrUpdateOpdTestOrder(opdTestOrder);
					}
				}
			} else {
				Concept serviceConcept = conceptService.getConceptByUuid(osd.getServiceConceptUUid());
				if ("walkin".equals(billingOrderDetails.getBillType())) {
					priceCategoryConcept = conceptService.getConceptByUuid(OpenmrsCustomConstants.GENERAL_WARD_PRICES);
				}
				BillableService service = hospitalRestCoreService
						.getServicesByServiceConceptAndPriceCategory(serviceConcept, priceCategoryConcept);
				Integer quantity = osd.getQuantity();
				BigDecimal unitPrice = service.getPrice();

				mUnitPrice = new Money(unitPrice);
				itemAmount = mUnitPrice.times(quantity);
				totalAmount = totalAmount.plus(itemAmount);

				item = new PatientServiceBillItem();
				item.setCreatedDate(new Date());
				item.setName(serviceConcept.getName().getName());
				item.setPatientServiceBill(bill);
				item.setQuantity(quantity);
				item.setBillableService(service);
				item.setUnitPrice(unitPrice);
				item.setAmount(itemAmount.getAmount());

				rate = new BigDecimal(1);
				item.setActualAmount(item.getAmount().multiply(rate));
				totalActualAmount = totalActualAmount.add(item.getActualAmount());
				bill.addBillItem(item);

			}

		}

		BillingReceipt receipt = new BillingReceipt();
		receipt.setPaidDate(new Date());
		receipt = hospitalRestCoreService.createReceipt(receipt);

		bill.setPatient(patient);
		bill.setAmount(totalAmount.getAmount());
		bill.setActualAmount(totalActualAmount);
		// bill.setPatientCategory(patientCategory);
		bill.setWaiverPercentage(waiverPercentage);
		float waiverAmount = total * waiverPercentage / 100;
		bill.setWaiverAmount(waiverAmount);
		bill.setAmountPayable(totalAmountPayable);
		bill.setAmountGiven(amountGiven);
		bill.setAmountReturned(amountReturned);
		bill.setComment(billingOrderDetails.getComment());
		bill.setBillType(billingOrderDetails.getBillType());
		bill.setReceipt(receipt);
		// bill.setPatientSubcategory(patientSubcategory);

		bill = hospitalRestCoreService.saveOrUpdatePatientServiceBill(bill);

		ProcessOrdersResponseDTO processOrdersResponseDTO = new ProcessOrdersResponseDTO();

		processOrdersResponseDTO.setBillId(bill.getPatientServiceBillId());

		return new ResponseEntity<ProcessOrdersResponseDTO>(processOrdersResponseDTO, HttpStatus.CREATED);
	}

	public String getName(Patient patient) {
		String name = "";
		if (patient.getGivenName() != null) {
			name = patient.getGivenName();
		}
		if (patient.getMiddleName() != null) {
			name = name + " " + patient.getMiddleName();
		}
		if (patient.getFamilyName() != null) {
			name = name + " " + patient.getFamilyName();
		}
		return name;
	}

}
