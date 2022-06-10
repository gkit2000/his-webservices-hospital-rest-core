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
import org.openmrs.module.hospitalrestcore.billing.Ambulance;
import org.openmrs.module.hospitalrestcore.billing.AmbulanceDetailsResponse;
import org.openmrs.module.hospitalrestcore.billing.AmbulancePayload;
import org.openmrs.module.hospitalrestcore.billing.AmbulanceSearchPayload;
import org.openmrs.module.hospitalrestcore.controller.PulseUtil;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/ambulance")
public class AmbulanceController extends BaseRestController {

	@RequestMapping(value = "/searchAmbulance ", method = RequestMethod.POST)
	public void searchAmbulance(@Valid @RequestBody AmbulanceSearchPayload ambulanceSearchPayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		List<Ambulance> ambulances = hospitalRestCoreService.searchAmbulance(ambulanceSearchPayload.getSearchText());
		List<AmbulanceDetailsResponse> adrs = ambulances.stream().map(amb -> getAmbulanceDetails(amb))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, adrs);
	}

	@RequestMapping(value = "/allDrivers", method = RequestMethod.GET)
	public void getAllAmbulances(HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		List<Ambulance> ambulances = hospitalRestCoreService.getAllAmbulance();
		List<AmbulanceDetailsResponse> adrs = ambulances.stream().map(amb -> getAmbulanceDetails(amb))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, adrs);
	}

	@RequestMapping(value = "/addAmbulance", method = RequestMethod.POST)
	public ResponseEntity<Void> addAmbulance(@Valid @RequestBody AmbulancePayload ambulancePayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		Ambulance ambulance = new Ambulance();
		ambulance.setName(ambulancePayload.getName());
		ambulance.setDescription(ambulancePayload.getDescription());
		ambulance.setCreatedDate(new Date());
		ambulance.setCreatedBy(Context.getAuthenticatedUser());

		hospitalRestCoreService.saveOrUpdateAmbulance(ambulance);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/editAmbulance", method = RequestMethod.PUT)
	public ResponseEntity<Void> editDriver(@Valid @RequestBody AmbulancePayload ambulancePayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		if (ambulancePayload.getUuid() != null) {
			Ambulance ambulance = hospitalRestCoreService.getAmbulanceByUuid(ambulancePayload.getUuid());
			if (ambulance == null) {
				throw new ResourceNotFoundException(String.format(
						OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_AMBULANCE_UUID, ambulancePayload.getUuid()));
			}
			if (ambulancePayload.isRetired()) {
				ambulance.setRetired(true);
				ambulance.setRetiredDate(new Date());
				ambulance.setRetiredBy(Context.getAuthenticatedUser());
			} else {
				ambulance.setName(ambulancePayload.getName());
				ambulance.setDescription(ambulancePayload.getDescription());
				ambulance.setLastModifiedDate(new Date());
				ambulance.setLastModifiedBy(Context.getAuthenticatedUser());
			}

			hospitalRestCoreService.saveOrUpdateAmbulance(ambulance);

		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public AmbulanceDetailsResponse getAmbulanceDetails(Ambulance ambulance) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		AmbulanceDetailsResponse adr = new AmbulanceDetailsResponse();
		adr.setAmbulanceId(ambulance.getAmbulanceId());
		adr.setName(ambulance.getName());
		adr.setDescription(ambulance.getDescription());
		adr.setCreatedDate(formatter.format(ambulance.getCreatedDate()));
		adr.setCreatedBy(PulseUtil.getName(ambulance.getCreatedBy().getPerson()));
		adr.setLastModifiedDate(formatter.format(ambulance.getLastModifiedDate()));
		adr.setLastModifiedBy(PulseUtil.getName(ambulance.getLastModifiedBy().getPerson()));
		adr.setRetired(ambulance.getRetired());
		adr.setRetiredDate(formatter.format(ambulance.getRetiredDate()));
		adr.setRetiredBy(PulseUtil.getName(ambulance.getRetiredBy().getPerson()));
		adr.setUuid(ambulance.getUuid());
		return adr;
	}
}
