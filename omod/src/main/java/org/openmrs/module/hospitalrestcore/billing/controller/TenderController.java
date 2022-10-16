/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.Tender;
import org.openmrs.module.hospitalrestcore.billing.TenderDetailsResponse;
import org.openmrs.module.hospitalrestcore.billing.TenderPayload;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/tender")
public class TenderController extends BaseRestController {

	@RequestMapping(value = "/all-tenders", method = RequestMethod.GET)
	public void getAllTenders(HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		List<Tender> tenders = hospitalRestCoreService.getAllTender();
		List<TenderDetailsResponse> ddrs = tenders.stream().map(tend -> getTenderDetails(tend))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, ddrs);
	}

	@RequestMapping(value = "/add-tender", method = RequestMethod.POST)
	public ResponseEntity<Void> addTender(@Valid @RequestBody TenderPayload tenderPayload, HttpServletResponse response,
			HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		Tender tender = new Tender();
		tender.setNumber(tenderPayload.getNumber());
		tender.setName(tenderPayload.getName());
		tender.setDescription(tenderPayload.getDescription());
		tender.setOpeningDate(tenderPayload.getOpeningDate());
		tender.setClosingDate(tenderPayload.getClosingDate());
		tender.setPrice(tenderPayload.getPrice());
		tender.setCreatedDate(new Date());
		tender.setCreatedBy(Context.getAuthenticatedUser());

		hospitalRestCoreService.saveOrUpdateTender(tender);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/edit-tender", method = RequestMethod.PUT)
	public ResponseEntity<Void> editTender(@Valid @RequestBody TenderPayload tenderPayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		if (tenderPayload.getUuid() != null) {
			Tender tender = hospitalRestCoreService.getTenderByUuid(tenderPayload.getUuid());
			if (tender == null) {
				throw new ResourceNotFoundException(String.format(
						OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_TENDER_UUID, tenderPayload.getUuid()));
			}

			tender.setNumber(tenderPayload.getNumber());
			tender.setName(tenderPayload.getName());
			tender.setDescription(tenderPayload.getDescription());
			tender.setOpeningDate(tenderPayload.getOpeningDate());
			tender.setClosingDate(tenderPayload.getClosingDate());
			tender.setPrice(tenderPayload.getPrice());
			tender.setLastModifiedDate(new Date());
			tender.setLastModifiedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateTender(tender);

		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-tenders", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTenders(@RequestBody List<String> tenderUuids, HttpServletResponse response,
			HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		for (String tenderUuid : tenderUuids) {
			Tender tender = hospitalRestCoreService.getTenderByUuid(tenderUuid);
			if (tender == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_TENDER_UUID, tenderUuid));
			}

			tender.setDeleted(true);
			tender.setDeletedDate(new Date());
			tender.setDeletedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateTender(tender);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public TenderDetailsResponse getTenderDetails(Tender tender) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		TenderDetailsResponse tdr = new TenderDetailsResponse();
		tdr.setName(tender.getName());
		tdr.setNumber(tender.getNumber());
		tdr.setPrice(tender.getPrice());
		tdr.setDescription(tender.getDescription());
		tdr.setOpeningDate(formatter.format(tender.getOpeningDate()));
		tdr.setClosingDate(formatter.format(tender.getClosingDate()));
		tdr.setUuid(tender.getUuid());
		return tdr;
	}
}