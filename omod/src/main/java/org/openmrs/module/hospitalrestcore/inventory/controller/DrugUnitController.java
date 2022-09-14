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
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugUnit;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugUnitDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugUnitPayload;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/drug-unit")
public class DrugUnitController extends BaseRestController {

	@RequestMapping(value = "/all-unit-details", method = RequestMethod.GET)
	public void getAllUnits(HttpServletRequest request, HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		List<InventoryDrugUnit> inventoryDrugUnits = hospitalRestCoreService.listAllInventoryDrugUnit();

		List<InventoryDrugUnitDetails> idud = inventoryDrugUnits.stream().map(idu -> getInventoryDrugUnitDetails(idu))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, idud);
	}

	@RequestMapping(value = "/unit-details", method = RequestMethod.GET)
	public void getUnit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "unitUuid") String unitUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrugUnit inventoryDrugUnit = hospitalRestCoreService.getInventoryDrugUnitByUuidString(unitUuid);

		new ObjectMapper().writeValue(out, getInventoryDrugUnitDetails(inventoryDrugUnit));
	}

	@RequestMapping(value = "/add-drug-unit", method = RequestMethod.POST)
	public ResponseEntity<InventoryDrugUnitDetails> addStore(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody InventoryDrugUnitPayload inventoryDrugUnitPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrugUnit inventoryDrugUnit = new InventoryDrugUnit();
		inventoryDrugUnit.setName(inventoryDrugUnitPayload.getName());
		inventoryDrugUnit.setDescription(inventoryDrugUnitPayload.getDescription());
		inventoryDrugUnit.setRetired(inventoryDrugUnitPayload.getRetired());
		inventoryDrugUnit.setCreatedDate(new Date());
		inventoryDrugUnit.setCreatedBy(Context.getAuthenticatedUser());
		inventoryDrugUnit = hospitalRestCoreService.saveOrUpdateInventoryDrugUnit(inventoryDrugUnit);

		return new ResponseEntity<InventoryDrugUnitDetails>(getInventoryDrugUnitDetails(inventoryDrugUnit),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/edit-drug-unit", method = RequestMethod.PUT)
	public ResponseEntity<InventoryDrugUnitDetails> editStore(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody InventoryDrugUnitPayload inventoryDrugUnitPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		InventoryDrugUnit inventoryDrugUnit = hospitalRestCoreService
				.getInventoryDrugUnitByUuidString(inventoryDrugUnitPayload.getUuid());
		if (inventoryDrugUnit == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_UNIT_UUID,
							inventoryDrugUnitPayload.getUuid()));
		}
		if (inventoryDrugUnit.getRetired()) {
			inventoryDrugUnit.setRetired(true);
			inventoryDrugUnit.setRetiredDate(new Date());
			inventoryDrugUnit.setRetiredBy(Context.getAuthenticatedUser());
		} else {
			inventoryDrugUnit.setName(inventoryDrugUnitPayload.getName());
			inventoryDrugUnit.setDescription(inventoryDrugUnitPayload.getDescription());
			inventoryDrugUnit.setLastModifiedDate(new Date());
			inventoryDrugUnit.setLastModifiedBy(Context.getAuthenticatedUser());
		}

		inventoryDrugUnit = hospitalRestCoreService.saveOrUpdateInventoryDrugUnit(inventoryDrugUnit);

		return new ResponseEntity<InventoryDrugUnitDetails>(getInventoryDrugUnitDetails(inventoryDrugUnit),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-drug-units", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStore(@RequestBody List<String> drugUnitUuids, HttpServletRequest request,
			HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		for (String drugUnitUuid : drugUnitUuids) {
			InventoryDrugUnit inventoryDrugUnit = hospitalRestCoreService
					.getInventoryDrugUnitByUuidString(drugUnitUuid);
			if (inventoryDrugUnit == null) {
				throw new ResourceNotFoundException(String
						.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY_UUID, drugUnitUuid));
			}

			inventoryDrugUnit.setDeleted(true);
			inventoryDrugUnit.setDeletedDate(new Date());
			inventoryDrugUnit.setDeletedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateInventoryDrugUnit(inventoryDrugUnit);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public InventoryDrugUnitDetails getInventoryDrugUnitDetails(InventoryDrugUnit inventoryDrugUnit) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		InventoryDrugUnitDetails idud = new InventoryDrugUnitDetails();
		idud.setId(inventoryDrugUnit.getId());
		idud.setName(inventoryDrugUnit.getName());
		idud.setDescription(inventoryDrugUnit.getDescription());
		idud.setUuid(inventoryDrugUnit.getUuid());
		idud.setDeleted(inventoryDrugUnit.getDeleted());
		// idud.setRetired(inventoryDrugUnit.getRetired());
		idud.setCreatedBy(PulseUtil.getName(inventoryDrugUnit.getCreatedBy().getPerson()));
		idud.setCreatedDate(formatter.format(inventoryDrugUnit.getCreatedDate()));
		return idud;
	}
}
