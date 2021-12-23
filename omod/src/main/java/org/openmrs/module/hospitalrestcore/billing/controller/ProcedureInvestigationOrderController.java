/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.billing.BillableServiceDetails;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.OrderDTO;
import org.openmrs.module.hospitalrestcore.billing.TestOrderDetails;
import org.openmrs.module.hospitalrestcore.billing.api.BillingService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
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
				list.add(billableServiceDetails);

			} else {
				List<BillableServiceDetails> list = new ArrayList<BillableServiceDetails>();
				BillableServiceDetails billableServiceDetails = new BillableServiceDetails();
				billableServiceDetails.setConUuid(opdTestOrder.getBillableService().getConcept().getUuid());
				billableServiceDetails.setConName(opdTestOrder.getBillableService().getConcept().getName().getName());
				billableServiceDetails.setPrice(opdTestOrder.getBillableService().getPrice());
				list.add(billableServiceDetails);
				opdTestOrderMap.put(key, list);
			}
		}

		for (Map.Entry<Integer, List<BillableServiceDetails>> entry : opdTestOrderMap.entrySet()) {
			TestOrderDetails orders = new TestOrderDetails();
			orders.setEncounterUuid(es.getEncounter(entry.getKey()).getUuid());
			orders.setBillableServiceDetails(entry.getValue());
			listOforders.add(orders);
		}
		orderDTO.setTestOrderDetails(listOforders);

		new ObjectMapper().writeValue(out, orderDTO);
	}
}
