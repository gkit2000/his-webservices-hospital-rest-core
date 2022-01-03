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
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.Money;
import org.openmrs.module.hospitalrestcore.billing.BillableServiceDetails;
import org.openmrs.module.hospitalrestcore.billing.BillingOrderDetails;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.OrderDTO;
import org.openmrs.module.hospitalrestcore.billing.OrderServiceDetails;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;
import org.openmrs.module.hospitalrestcore.billing.TestOrderDetails;
import org.openmrs.module.hospitalrestcore.billing.api.BillingService;
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
			@RequestParam(value = "patient", required = false) String patientUuid,
			@RequestParam(value = "date", required = false) String date)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		PatientService ps = Context.getPatientService();
		Patient patient = null;
		if (patientUuid != null && !patientUuid.isEmpty()) {
			patient = ps.getPatientByUuid(patientUuid);
		}
		EncounterService es = Context.getEncounterService();
		BillingService billingService = Context.getService(BillingService.class);
		Date creationDate = null;
		if (date != null && !date.isEmpty()) {
			creationDate = formatter.parse(date);
		}
		List<OpdTestOrder> opdTestOrders = billingService.getOpdTestOrder(patient, creationDate);
		OrderDTO orderDTO = new OrderDTO();
		List<TestOrderDetails> listOforders = new ArrayList<TestOrderDetails>();
		Map<Integer, List<BillableServiceDetails>> opdTestOrderMap = new LinkedHashMap<Integer, List<BillableServiceDetails>>();

		for (OpdTestOrder opdTestOrder : opdTestOrders) {
			Integer key = opdTestOrder.getEncounter().getId();
			if (opdTestOrderMap.containsKey(key)) {
				List<BillableServiceDetails> list = opdTestOrderMap.get(key);
				BillableServiceDetails billableServiceDetails = new BillableServiceDetails();
				billableServiceDetails.setConUuid(opdTestOrder.getBillableService().getConcept().getUuid());
				billableServiceDetails.setConName(opdTestOrder.getBillableService().getConcept().getName().getName());
				billableServiceDetails.setPrice(opdTestOrder.getBillableService().getPrice());
				billableServiceDetails.setOpdOrderId(opdTestOrder.getOpdOrderId());
				list.add(billableServiceDetails);

			} else {
				List<BillableServiceDetails> list = new ArrayList<BillableServiceDetails>();
				BillableServiceDetails billableServiceDetails = new BillableServiceDetails();
				billableServiceDetails.setConUuid(opdTestOrder.getBillableService().getConcept().getUuid());
				billableServiceDetails.setConName(opdTestOrder.getBillableService().getConcept().getName().getName());
				billableServiceDetails.setPrice(opdTestOrder.getBillableService().getPrice());
				billableServiceDetails.setOpdOrderId(opdTestOrder.getOpdOrderId());
				list.add(billableServiceDetails);
				opdTestOrderMap.put(key, list);
			}
		}

		for (Map.Entry<Integer, List<BillableServiceDetails>> entry : opdTestOrderMap.entrySet()) {
			TestOrderDetails orders = new TestOrderDetails();
			orders.setEncounterId(es.getEncounter(entry.getKey()).getId());
			orders.setEncounterUuid(es.getEncounter(entry.getKey()).getUuid());
			orders.setPatientId(es.getEncounter(entry.getKey()).getPatient().getId());
			orders.setPatientUuid(es.getEncounter(entry.getKey()).getPatient().getUuid());
			orders.setBillableServiceDetails(entry.getValue());
			listOforders.add(orders);

		}
		orderDTO.setTestOrderDetails(listOforders);

		new ObjectMapper().writeValue(out, orderDTO);
	}

	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public ResponseEntity<Void> processOrders(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody BillingOrderDetails billingOrderDetails)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		HttpSession httpSession = request.getSession();

		PatientService ps = Context.getPatientService();
		ConceptService conceptService = Context.getService(ConceptService.class);

		BillingService billingService = Context.getService(BillingService.class);

		Patient patient = null;

		PatientServiceBill bill = new PatientServiceBill();
		bill.setCreatedDate(new Date());
		bill.setCreator(Context.getAuthenticatedUser());

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

			OpdTestOrder opdTestOrder = billingService.getOpdTestOrderById(osd.getOpdOrderId());

			if (osd.getBilled()) {

				patient = opdTestOrder.getPatient();
				Concept service = opdTestOrder.getBillableService().getConcept();
				Integer quantity = osd.getQuantity();
				BigDecimal unitPrice = opdTestOrder.getBillableService().getPrice();

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
				billingService.saveOrUpdateOpdTestOrder(opdTestOrder);

			} else {
				opdTestOrder.setCancelStatus(true);
				billingService.saveOrUpdateOpdTestOrder(opdTestOrder);
			}

		}

		BillingReceipt receipt = new BillingReceipt();
		receipt.setPaidDate(new Date());
		receipt = billingService.createReceipt(receipt);

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
		bill.setBillType("out/paid");
		bill.setReceipt(receipt);
		// bill.setPatientSubcategory(patientSubcategory);

		bill = billingService.saveOrUpdatePatientServiceBill(bill);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
