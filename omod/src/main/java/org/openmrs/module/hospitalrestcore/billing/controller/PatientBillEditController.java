/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.BillEditPayload;
import org.openmrs.module.hospitalrestcore.billing.BillEditResponse;
import org.openmrs.module.hospitalrestcore.billing.BillVoidedResponse;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Ghanshyam
 *
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/patient-bill-edit")
public class PatientBillEditController extends BaseRestController {

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<BillEditResponse> editBill(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody BillEditPayload billEditPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		PatientServiceBill bill = hospitalRestCoreService.getPatientServiceBillById(billEditPayload.getBillId());

		bill.setAmount(billEditPayload.getAmount());
		bill.setActualAmount(billEditPayload.getAmount());
		bill.setComment(billEditPayload.getComment());
		bill.setAmountReturned(billEditPayload.getAmountToBeReturned());
		bill.setEdited(true);
		bill.setEditedDate(new Date());
		bill.setEditedBy(Context.getAuthenticatedUser());

		bill.setWaiverPercentage(billEditPayload.getWaiverPercentage());
		float waiverAmount = billEditPayload.getAmount().floatValue() * billEditPayload.getWaiverPercentage() / 100;
		bill.setWaiverAmount(waiverAmount);

		Set<PatientServiceBillItem> patientServiceBillItems = new HashSet<PatientServiceBillItem>();

		for (Integer billItemId : billEditPayload.getRemovableBillItemIds()) {
			PatientServiceBillItem billItem = hospitalRestCoreService.getPatientServiceBillItemById(billItemId);
			if (billItem == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_BILL_ITEM_ID, billItemId));
			}
			PatientServiceBillItem bibb = hospitalRestCoreService.getPatientServiceBillItemByIdAndBill(billItemId,
					bill);
			if (bibb == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_BILL_ITEM_ID_AND_BILL_ID,
								billItemId, billEditPayload.getBillId()));
			}
			bibb.setEdited(true);
			bibb.setEditedDate(new Date());
			bibb.setEditedBy(Context.getAuthenticatedUser());
			// patientServiceBillItems.add(billItem);
			bibb = hospitalRestCoreService.saveOrUpdatePatientServiceBillItem(bibb);
		}

		// bill.setBillItems(patientServiceBillItems);

		bill = hospitalRestCoreService.saveOrUpdatePatientServiceBill(bill);

		BillEditResponse ber = new BillEditResponse();
		ber.setBillId(bill.getPatientServiceBillId());
		ber.setEdited(bill.getEdited());

		return new ResponseEntity<BillEditResponse>(ber, HttpStatus.OK);

	}

	@RequestMapping(value = "/void", method = RequestMethod.PUT)
	public ResponseEntity<BillVoidedResponse> voidBill(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody BillEditPayload billEditPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		PatientServiceBill bill = hospitalRestCoreService.getPatientServiceBillById(billEditPayload.getBillId());

		bill.setVoided(true);
		bill.setVoidedDate(new Date());
		bill.setVoidedBy(Context.getAuthenticatedUser());
		bill.setComment(billEditPayload.getComment());
		bill.setAmount(billEditPayload.getAmount());
		bill.setActualAmount(billEditPayload.getAmount());
		bill.setAmountReturned(billEditPayload.getAmountToBeReturned());
		for (PatientServiceBillItem item : bill.getBillItems()) {
			item.setVoided(true);
			item.setVoidedDate(new Date());
			item.setVoidedBy(Context.getAuthenticatedUser());
			Order ord = item.getOrder();
			if (ord != null) {
				ord.setVoided(true);
				ord.setDateVoided(new Date());
				ord.setVoidedBy(Context.getAuthenticatedUser());
			}
			item.setOrder(ord);
		}

		bill = hospitalRestCoreService.saveOrUpdatePatientServiceBill(bill);

		BillVoidedResponse bvr = new BillVoidedResponse();
		bvr.setBillId(bill.getPatientServiceBillId());
		bvr.setVoided(bill.getVoided());

		return new ResponseEntity<BillVoidedResponse>(bvr, HttpStatus.OK);
	}
}
