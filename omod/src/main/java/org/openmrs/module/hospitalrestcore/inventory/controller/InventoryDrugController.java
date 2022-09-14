/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.controller.PulseUtil;
import org.openmrs.module.hospitalrestcore.inventory.Action;
import org.openmrs.module.hospitalrestcore.inventory.ActionValue;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategory;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategoryDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulation;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulationDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugPayload;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugUnit;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugUnitDetails;
import org.openmrs.module.hospitalrestcore.util.PagingUtil;
import org.openmrs.module.hospitalrestcore.util.RequestUtil;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/drug")
public class InventoryDrugController extends BaseRestController {

	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	public void getAttributes(HttpServletRequest request, HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		List<Action> attributes = ActionValue.getListDrugAttribute();

		new ObjectMapper().writeValue(out, attributes);
	}

	@RequestMapping(value = "/all-drug-details", method = RequestMethod.GET)
	public void getAllInventoryDrugs(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "searchName", required = false) String searchName,
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "currentPage", required = false) Integer currentPage)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		int total = hospitalRestCoreService.countListDrug(categoryId, searchName);
		String temp = "";
		if (!StringUtils.isBlank(searchName)) {
			temp = "?searchName=" + searchName;
		}
		if (categoryId != null) {
			if (StringUtils.isBlank(temp)) {
				temp = "?categoryId=" + categoryId;
			} else {
				temp += "&categoryId=" + categoryId;
			}
		}
		PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage,
				total);

		List<InventoryDrug> drugs = hospitalRestCoreService.listDrug(categoryId, searchName, pagingUtil.getStartPos(),
				pagingUtil.getPageSize());
		List<InventoryDrugDetails> idds = drugs.stream().map(drug -> getInventoryDrugDetails(drug))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, idds);
	}

	@RequestMapping(value = "/drug-details", method = RequestMethod.GET)
	public void getInventoryDrug(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "inventoryDrugUuid") String inventoryDrugUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		InventoryDrug drug = hospitalRestCoreService.getInventoryDrugByUuidString(inventoryDrugUuid);

		new ObjectMapper().writeValue(out, getInventoryDrugDetails(drug));
	}

	@RequestMapping(value = "/add-drug", method = RequestMethod.POST)
	public ResponseEntity<InventoryDrugDetails> addDrug(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody InventoryDrugPayload inventoryDrugPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrug inventoryDrug = new InventoryDrug();
		List<InventoryDrugFormulation> idfs = inventoryDrugPayload.getFormulationUuids().stream()
				.map(idfuid -> getInventoryDrugFormulation(idfuid)).collect(Collectors.toList());
		InventoryDrugUnit idu = hospitalRestCoreService
				.getInventoryDrugUnitByUuidString(inventoryDrugPayload.getUnitUuid());
		if (idu == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_UNIT_UUID,
							inventoryDrugPayload.getUnitUuid()));
		}
		InventoryDrugCategory idc = hospitalRestCoreService
				.getInventoryDrugCategoryByUuidString(inventoryDrugPayload.getCategoryUuid());

		if (idc == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY_UUID,
							inventoryDrugPayload.getCategoryUuid()));
		}

		Drug drug = hospitalRestCoreService.getDrugByUuid(inventoryDrugPayload.getDrugUuid());

		if (drug == null) {
			throw new ResourceNotFoundException(String.format(
					OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_UUID, inventoryDrugPayload.getDrugUuid()));
		}

		Set<InventoryDrugFormulation> idfsHashSet = new HashSet<>(idfs);
		inventoryDrug.setName(inventoryDrugPayload.getName());
		inventoryDrug.setDrugCore(drug);
		inventoryDrug.setFormulations(idfsHashSet);
		inventoryDrug.setUnit(idu);
		inventoryDrug.setCategory(idc);
		inventoryDrug.setAttribute(inventoryDrugPayload.getAttributeId());
		inventoryDrug.setRetired(false);
		inventoryDrug.setCreatedDate(new Date());
		inventoryDrug.setCreatedBy(Context.getAuthenticatedUser());
		inventoryDrug = hospitalRestCoreService.saveOrUpdateInventoryDrug(inventoryDrug);

		return new ResponseEntity<InventoryDrugDetails>(getInventoryDrugDetails(inventoryDrug), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/edit-drug", method = RequestMethod.PUT)
	public ResponseEntity<InventoryDrugDetails> editDrug(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody InventoryDrugPayload inventoryDrugPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		InventoryDrug inventoryDrug = hospitalRestCoreService
				.getInventoryDrugByUuidString(inventoryDrugPayload.getInventoryDrugUuid());
		if (inventoryDrug == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_INVENTORY_DRUG_UUID,
							inventoryDrugPayload.getInventoryDrugUuid()));
		}

		List<InventoryDrugFormulation> idfs = inventoryDrugPayload.getFormulationUuids().stream()
				.map(idfuid -> getInventoryDrugFormulation(idfuid)).collect(Collectors.toList());
		InventoryDrugUnit idu = hospitalRestCoreService
				.getInventoryDrugUnitByUuidString(inventoryDrugPayload.getUnitUuid());
		if (idu == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_UNIT_UUID,
							inventoryDrugPayload.getUnitUuid()));
		}
		InventoryDrugCategory idc = hospitalRestCoreService
				.getInventoryDrugCategoryByUuidString(inventoryDrugPayload.getCategoryUuid());
		if (idc == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY_UUID,
							inventoryDrugPayload.getCategoryUuid()));
		}
		Set<InventoryDrugFormulation> idfsHashSet = new HashSet<>(idfs);

		if (inventoryDrug.getRetired()) {
			inventoryDrug.setRetired(true);
			inventoryDrug.setRetiredDate(new Date());
			inventoryDrug.setRetiredBy(Context.getAuthenticatedUser());
		} else {
			inventoryDrug.setName(inventoryDrugPayload.getName());
			// inventoryDrug.setDrugCore(inventoryDrugPayload.getGenericName());
			inventoryDrug.setFormulations(idfsHashSet);
			inventoryDrug.setUnit(idu);
			inventoryDrug.setCategory(idc);
			inventoryDrug.setAttribute(0);

			inventoryDrug.setLastModifiedDate(new Date());
			inventoryDrug.setLastModifiedBy(Context.getAuthenticatedUser());
		}

		inventoryDrug = hospitalRestCoreService.saveOrUpdateInventoryDrug(inventoryDrug);

		return new ResponseEntity<InventoryDrugDetails>(getInventoryDrugDetails(inventoryDrug), HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-drug", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDrug(@RequestBody List<String> drugUuids, HttpServletRequest request,
			HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		for (String drugUuid : drugUuids) {
			InventoryDrug inventoryDrug = hospitalRestCoreService.getInventoryDrugByUuidString(drugUuid);
			if (inventoryDrug == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_INVENTORY_DRUG_UUID, drugUuid));
			}

			inventoryDrug.setDeleted(true);
			inventoryDrug.setDeletedDate(new Date());
			inventoryDrug.setDeletedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateInventoryDrug(inventoryDrug);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public InventoryDrugDetails getInventoryDrugDetails(InventoryDrug inventoryDrug) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		InventoryDrugDetails idd = new InventoryDrugDetails();
		idd.setId(inventoryDrug.getId());
		idd.setName(inventoryDrug.getName());
		idd.setInventoryDrugUnitDetails(getInventoryDrugUnitDetails(inventoryDrug.getUnit()));
		idd.setInventoryDrugCategoryDetails(getInventoryDrugCategoryDetails(inventoryDrug.getCategory()));
		// idd.setInventoryDrugFormulationDetails(inventoryDrug.getFormulations());
		idd.setDrugId(inventoryDrug.getDrugCore().getId());
		idd.setDrugName(inventoryDrug.getDrugCore().getName());
		idd.setDrugUuid(inventoryDrug.getDrugCore().getUuid());

		idd.setUuid(inventoryDrug.getUuid());
		idd.setDeleted(inventoryDrug.getDeleted());
		//idd.setRetired(inventoryDrug.getRetired());
		idd.setCreatedBy(PulseUtil.getName(inventoryDrug.getCreatedBy().getPerson()));
		idd.setCreatedDate(formatter.format(inventoryDrug.getCreatedDate()));
		if (inventoryDrug.getLastModifiedDate() != null) {
			idd.setLastModifiedBy(PulseUtil.getName(inventoryDrug.getLastModifiedBy().getPerson()));
			idd.setLastModifiedDate(formatter.format(inventoryDrug.getLastModifiedDate()));
		}
		return idd;
	}

	public InventoryDrugUnitDetails getInventoryDrugUnitDetails(InventoryDrugUnit inventoryDrugUnit) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		InventoryDrugUnitDetails idud = new InventoryDrugUnitDetails();
		idud.setId(inventoryDrugUnit.getId());
		idud.setName(inventoryDrugUnit.getName());
		idud.setDescription(inventoryDrugUnit.getDescription());
		idud.setUuid(inventoryDrugUnit.getUuid());
		idud.setDeleted(inventoryDrugUnit.getDeleted());
		//idud.setRetired(inventoryDrugUnit.getRetired());
		idud.setCreatedBy(PulseUtil.getName(inventoryDrugUnit.getCreatedBy().getPerson()));
		idud.setCreatedDate(formatter.format(inventoryDrugUnit.getCreatedDate()));
		return idud;
	}

	public InventoryDrugCategoryDetails getInventoryDrugCategoryDetails(InventoryDrugCategory inventoryDrugCategory) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		InventoryDrugCategoryDetails idcd = new InventoryDrugCategoryDetails();
		idcd.setId(inventoryDrugCategory.getId());
		idcd.setName(inventoryDrugCategory.getName());
		idcd.setDescription(inventoryDrugCategory.getDescription());
		idcd.setUuid(inventoryDrugCategory.getUuid());
		idcd.setDeleted(inventoryDrugCategory.getDeleted());
		//idcd.setRetired(inventoryDrugCategory.getRetired());
		idcd.setCreatedBy(PulseUtil.getName(inventoryDrugCategory.getCreatedBy().getPerson()));
		idcd.setCreatedDate(formatter.format(inventoryDrugCategory.getCreatedDate()));
		return idcd;
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
		//idud.setRetired(inventoryDrugFormulation.getRetired());
		idud.setCreatedBy(PulseUtil.getName(inventoryDrugFormulation.getCreatedBy().getPerson()));
		idud.setCreatedDate(formatter.format(inventoryDrugFormulation.getCreatedDate()));
		return idud;
	}

	public InventoryDrugFormulation getInventoryDrugFormulation(String inventoryDrugFormulation) {
		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryDrugFormulation idf = hospitalRestCoreService
				.getInventoryDrugFormulationByUuidString(inventoryDrugFormulation);
		if (idf == null) {
			throw new ResourceNotFoundException(String.format(
					OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_FORMULATION_UUID, inventoryDrugFormulation));
		}

		return idf;
	}
}
