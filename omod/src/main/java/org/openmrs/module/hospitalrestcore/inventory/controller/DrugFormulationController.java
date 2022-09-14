/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.controller;

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
import org.openmrs.module.hospitalrestcore.controller.PulseUtil;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulation;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulationDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulationPayload;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/drug-formulation")
public class DrugFormulationController extends BaseRestController {

	@RequestMapping(value = "/all-formulation-details", method = RequestMethod.GET)
	public void getAllFormulations(HttpServletRequest request, HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		List<InventoryDrugFormulation> inventoryDrugFormulations = hospitalRestCoreService
				.listAllInventoryDrugFormulation();

		List<InventoryDrugFormulationDetails> idfd = inventoryDrugFormulations.stream()
				.map(idf -> getInventoryDrugFormulationDetails(idf)).collect(Collectors.toList());

		new ObjectMapper().writeValue(out, idfd);
	}

	@RequestMapping(value = "/formulation-details", method = RequestMethod.GET)
	public void getFormulation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "formulationUuid") String formulationUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrugFormulation inventoryDrugFormulation = hospitalRestCoreService
				.getInventoryDrugFormulationByUuidString(formulationUuid);

		new ObjectMapper().writeValue(out, getInventoryDrugFormulationDetails(inventoryDrugFormulation));
	}

	@RequestMapping(value = "/add-drug-formulation", method = RequestMethod.POST)
	public ResponseEntity<InventoryDrugFormulationDetails> addStore(HttpServletRequest request,
			HttpServletResponse response,
			@Valid @RequestBody InventoryDrugFormulationPayload inventoryDrugFormulationPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrugFormulation inventoryDrugFormulation = new InventoryDrugFormulation();
		inventoryDrugFormulation.setName(inventoryDrugFormulationPayload.getName());
		inventoryDrugFormulation.setDozage(inventoryDrugFormulationPayload.getDozage());
		inventoryDrugFormulation.setDescription(inventoryDrugFormulationPayload.getDescription());
		inventoryDrugFormulation.setRetired(inventoryDrugFormulationPayload.getRetired());
		inventoryDrugFormulation.setCreatedDate(new Date());
		inventoryDrugFormulation.setCreatedBy(Context.getAuthenticatedUser());
		inventoryDrugFormulation = hospitalRestCoreService
				.saveOrUpdateInventoryDrugFormulation(inventoryDrugFormulation);

		return new ResponseEntity<InventoryDrugFormulationDetails>(
				getInventoryDrugFormulationDetails(inventoryDrugFormulation), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/edit-drug-formulation", method = RequestMethod.PUT)
	public ResponseEntity<InventoryDrugFormulationDetails> editStore(HttpServletRequest request,
			HttpServletResponse response,
			@Valid @RequestBody InventoryDrugFormulationPayload inventoryDrugFormulationPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		InventoryDrugFormulation inventoryDrugFormulation = hospitalRestCoreService
				.getInventoryDrugFormulationByUuidString(inventoryDrugFormulationPayload.getUuid());
		if (inventoryDrugFormulation == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_UNIT_UUID,
							inventoryDrugFormulationPayload.getUuid()));
		}
		if (inventoryDrugFormulation.getRetired()) {
			inventoryDrugFormulation.setRetired(true);
			inventoryDrugFormulation.setRetiredDate(new Date());
			inventoryDrugFormulation.setRetiredBy(Context.getAuthenticatedUser());
		} else {
			inventoryDrugFormulation.setName(inventoryDrugFormulationPayload.getName());
			inventoryDrugFormulation.setDozage(inventoryDrugFormulationPayload.getDozage());
			inventoryDrugFormulation.setDescription(inventoryDrugFormulationPayload.getDescription());
			inventoryDrugFormulation.setLastModifiedDate(new Date());
			inventoryDrugFormulation.setLastModifiedBy(Context.getAuthenticatedUser());
		}

		inventoryDrugFormulation = hospitalRestCoreService
				.saveOrUpdateInventoryDrugFormulation(inventoryDrugFormulation);

		return new ResponseEntity<InventoryDrugFormulationDetails>(
				getInventoryDrugFormulationDetails(inventoryDrugFormulation), HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-drug-formulations", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStore(@RequestBody List<String> drugFormulationUuids, HttpServletRequest request,
			HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		for (String drugFormulationUuid : drugFormulationUuids) {
			InventoryDrugFormulation inventoryDrugFormulation = hospitalRestCoreService
					.getInventoryDrugFormulationByUuidString(drugFormulationUuid);
			if (inventoryDrugFormulation == null) {
				throw new ResourceNotFoundException(String.format(
						OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY_UUID, drugFormulationUuid));
			}

			inventoryDrugFormulation.setDeleted(true);
			inventoryDrugFormulation.setDeletedDate(new Date());
			inventoryDrugFormulation.setDeletedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateInventoryDrugFormulation(inventoryDrugFormulation);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public InventoryDrugFormulationDetails getInventoryDrugFormulationDetails(
			InventoryDrugFormulation inventoryDrugFormulation) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		InventoryDrugFormulationDetails idud = new InventoryDrugFormulationDetails();
		idud.setId(inventoryDrugFormulation.getId());
		idud.setName(inventoryDrugFormulation.getName());
		idud.setDozage(inventoryDrugFormulation.getDozage());
		idud.setDescription(inventoryDrugFormulation.getDescription());
		idud.setUuid(inventoryDrugFormulation.getUuid());
		idud.setDeleted(inventoryDrugFormulation.getDeleted());
		// idud.setRetired(inventoryDrugFormulation.getRetired());
		idud.setCreatedBy(PulseUtil.getName(inventoryDrugFormulation.getCreatedBy().getPerson()));
		idud.setCreatedDate(formatter.format(inventoryDrugFormulation.getCreatedDate()));
		return idud;
	}
}
