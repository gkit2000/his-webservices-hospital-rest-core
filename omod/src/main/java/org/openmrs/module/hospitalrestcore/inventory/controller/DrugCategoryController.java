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
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategory;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategoryDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategoryPayload;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/drug-category")
public class DrugCategoryController extends BaseRestController {

	@RequestMapping(value = "/all-category-details", method = RequestMethod.GET)
	public void getAllCategories(HttpServletRequest request, HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		List<InventoryDrugCategory> inventoryDrugCategories = hospitalRestCoreService.listAllInventoryDrugCategory();

		List<InventoryDrugCategoryDetails> isds = inventoryDrugCategories.stream()
				.map(idc -> getInventoryDrugCategoryDetails(idc)).collect(Collectors.toList());

		new ObjectMapper().writeValue(out, isds);
	}

	@RequestMapping(value = "/category-details", method = RequestMethod.GET)
	public void getCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "categoryUuid") String categoryUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrugCategory inventoryDrugCategory = hospitalRestCoreService
				.getInventoryDrugCategoryByUuidString(categoryUuid);

		new ObjectMapper().writeValue(out, getInventoryDrugCategoryDetails(inventoryDrugCategory));
	}

	@RequestMapping(value = "/add-drug-category", method = RequestMethod.POST)
	public ResponseEntity<InventoryDrugCategoryDetails> addStore(HttpServletRequest request,
			HttpServletResponse response, @Valid @RequestBody InventoryDrugCategoryPayload inventoryDrugCategoryPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrugCategory inventoryDrugCategory = new InventoryDrugCategory();
		inventoryDrugCategory.setName(inventoryDrugCategoryPayload.getName());
		inventoryDrugCategory.setDescription(inventoryDrugCategoryPayload.getDescription());
		inventoryDrugCategory.setRetired(inventoryDrugCategoryPayload.getRetired());
		inventoryDrugCategory.setCreatedDate(new Date());
		inventoryDrugCategory.setCreatedBy(Context.getAuthenticatedUser());
		inventoryDrugCategory = hospitalRestCoreService.saveOrUpdateInventoryDrugCategory(inventoryDrugCategory);

		return new ResponseEntity<InventoryDrugCategoryDetails>(getInventoryDrugCategoryDetails(inventoryDrugCategory),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/edit-drug-category", method = RequestMethod.PUT)
	public ResponseEntity<InventoryDrugCategoryDetails> editStore(HttpServletRequest request,
			HttpServletResponse response, @Valid @RequestBody InventoryDrugCategoryPayload inventoryDrugCategoryPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		InventoryDrugCategory inventoryDrugCategory = hospitalRestCoreService
				.getInventoryDrugCategoryByUuidString(inventoryDrugCategoryPayload.getUuid());
		if (inventoryDrugCategory == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY_UUID,
							inventoryDrugCategoryPayload.getUuid()));
		}
		if (inventoryDrugCategory.getRetired()) {
			inventoryDrugCategory.setRetired(true);
			inventoryDrugCategory.setRetiredDate(new Date());
			inventoryDrugCategory.setRetiredBy(Context.getAuthenticatedUser());
		} else {
			inventoryDrugCategory.setName(inventoryDrugCategoryPayload.getName());
			inventoryDrugCategory.setDescription(inventoryDrugCategoryPayload.getDescription());
			inventoryDrugCategory.setLastModifiedDate(new Date());
			inventoryDrugCategory.setLastModifiedBy(Context.getAuthenticatedUser());
		}

		inventoryDrugCategory = hospitalRestCoreService.saveOrUpdateInventoryDrugCategory(inventoryDrugCategory);

		return new ResponseEntity<InventoryDrugCategoryDetails>(getInventoryDrugCategoryDetails(inventoryDrugCategory),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-drug-categories", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStore(@RequestBody List<String> drugCategoryUuids, HttpServletRequest request,
			HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		for (String drugCategoryUuid : drugCategoryUuids) {
			InventoryDrugCategory inventoryDrugCategory = hospitalRestCoreService
					.getInventoryDrugCategoryByUuidString(drugCategoryUuid);
			if (inventoryDrugCategory == null) {
				throw new ResourceNotFoundException(String.format(
						OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY_UUID, drugCategoryUuid));
			}

			inventoryDrugCategory.setDeleted(true);
			inventoryDrugCategory.setDeletedDate(new Date());
			inventoryDrugCategory.setDeletedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateInventoryDrugCategory(inventoryDrugCategory);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public InventoryDrugCategoryDetails getInventoryDrugCategoryDetails(InventoryDrugCategory inventoryDrugCategory) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		InventoryDrugCategoryDetails idcd = new InventoryDrugCategoryDetails();
		idcd.setId(inventoryDrugCategory.getId());
		idcd.setName(inventoryDrugCategory.getName());
		idcd.setDescription(inventoryDrugCategory.getDescription());
		idcd.setUuid(inventoryDrugCategory.getUuid());
		idcd.setDeleted(inventoryDrugCategory.getDeleted());
		// idcd.setRetired(inventoryDrugCategory.getRetired());
		//idcd.setCreatedBy(PulseUtil.getName(inventoryDrugCategory.getCreatedBy().getPerson()));
		idcd.setCreatedDate(formatter.format(inventoryDrugCategory.getCreatedDate()));
		idcd.setCreatedBy("Admin");
		return idcd;
	}
}
