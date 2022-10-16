/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.Money;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.Company;
import org.openmrs.module.hospitalrestcore.billing.Tender;
import org.openmrs.module.hospitalrestcore.billing.TenderBill;
import org.openmrs.module.hospitalrestcore.billing.TenderBillItem;
import org.openmrs.module.hospitalrestcore.billing.TenderBillPayload;
import org.openmrs.module.hospitalrestcore.billing.TenderDetails;
import org.openmrs.module.hospitalrestcore.billing.TenderDetailsResponse;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/tenderBill ")
public class TenderBillController extends BaseRestController {
	@RequestMapping(value = "/add-tender-bill", method = RequestMethod.GET)
	public void addTenderBill(@Valid @RequestBody TenderBillPayload tenderBillPayload, HttpServletResponse response,
			HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		Company company = hospitalRestCoreService.getCompanyByUuid(tenderBillPayload.getCompanyUuid());
		if (company == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_COMPANY_UUID,
							tenderBillPayload.getCompanyUuid()));
		}

		TenderBill tenderBill = new TenderBill();
		tenderBill.setCompany(company);
		tenderBill.setCreatedDate(new Date());
		tenderBill.setCreatedBy(Context.getAuthenticatedUser());

		Tender tender = null;
		int quantity = 0;
		Money itemAmount;
		Money totalAmount = new Money(BigDecimal.ZERO);
		for (TenderDetails td : tenderBillPayload.getTenderDetails()) {

			tender = hospitalRestCoreService.getTenderByUuid(td.getTenderUuid());
			quantity = td.getQuantity();
			itemAmount = new Money(tender.getPrice());
			itemAmount = itemAmount.times(quantity);
			totalAmount = totalAmount.plus(itemAmount);

			TenderBillItem item = new TenderBillItem();
			item.setName(tender.getName() + "_" + tender.getNumber());
			item.setCreatedDate(new Date());
			item.setTender(tender);
			item.setUnitPrice(tender.getPrice());
			item.setQuantity(quantity);
			item.setTenderBill(tenderBill);
			item.setAmount(itemAmount.getAmount());
			tenderBill.addBillItem(item);
		}
		tenderBill.setAmount(totalAmount.getAmount());
		
		BillingReceipt receipt = new BillingReceipt();
		receipt.setPaidDate(new Date());
		receipt = hospitalRestCoreService.createReceipt(receipt);
		
		tenderBill.setBillingReceipt(receipt);
		
		tenderBill = hospitalRestCoreService.saveOrUpdateTenderBill(tenderBill);
	}
	
	@RequestMapping(value = "/all-tender-bills", method = RequestMethod.GET)
	public void getAllTenderBills(HttpServletResponse response, HttpServletRequest request,@RequestParam(value = "companyUuid", required = false) String companyUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		
		Company company = hospitalRestCoreService.getCompanyByUuid(companyUuid);
		if (company == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_COMPANY_UUID,companyUuid));
		}

		List<TenderBill> tenderBills = hospitalRestCoreService.getTenderBillByCompany(company);
		/*List<TenderDetailsResponse> ddrs = tenders.stream().map(tend -> getTenderDetails(tend))
				.collect(Collectors.toList());*/

		new ObjectMapper().writeValue(out, tenderBills);
	}
}
