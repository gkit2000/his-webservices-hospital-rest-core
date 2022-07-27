/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStorePayload;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreResponseDetails;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/manage-store")
public class MainStoreController extends BaseRestController {

	@RequestMapping(value = "/store-form-details ", method = RequestMethod.GET)
	public void getStore(HttpServletRequest request, HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		List<Role> roles = hospitalRestCoreService.getAllRoles();
		List<InventoryStore> stores = hospitalRestCoreService.listAllInventoryStore();
		List<InventoryStore> listParents = new ArrayList<InventoryStore>();
		listParents.addAll(stores);
		for (InventoryStore is : stores) {
			if (is.getParent() != null) {
				listParents.remove(is);
			}

		}

		List<InventoryStoreDetails> isds = listParents.stream().map(isd -> getInventoryStoreDetails(isd))
				.collect(Collectors.toList());

		InventoryStoreResponseDetails inventoryStoreResponseDetails = new InventoryStoreResponseDetails();
		inventoryStoreResponseDetails.setRoles(roles);
		inventoryStoreResponseDetails.setInventoryStoreDetails(isds);

		new ObjectMapper().writeValue(out, inventoryStoreResponseDetails);
	}

	@RequestMapping(value = "/all-store-details", method = RequestMethod.GET)
	public void getAllStores(HttpServletRequest request, HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		List<InventoryStore> stores = hospitalRestCoreService.listAllInventoryStore();

		List<InventoryStoreDetails> isds = stores.stream().map(isd -> getInventoryStoreDetails(isd))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, isds);
	}

	@RequestMapping(value = "/add-store", method = RequestMethod.POST)
	public ResponseEntity<Void> addStore(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody InventoryStorePayload inventoryStorePayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryStore store = new InventoryStore();
		store.setName(inventoryStorePayload.getName());
		Role role = hospitalRestCoreService.getRoleByUuid(inventoryStorePayload.getRoleUuid());
		if (role == null) {
			throw new ResourceNotFoundException(String.format(
					OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_ROLE_UUID, inventoryStorePayload.getRoleUuid()));
		}
		store.setRole(role);
		store.setCode(inventoryStorePayload.getCode());
		store.setIsDrug(inventoryStorePayload.getIsDrug());
		if (inventoryStorePayload.getParentUuid() != null) {
			InventoryStore parentStore = hospitalRestCoreService
					.getInventoryStoreByUuid(inventoryStorePayload.getParentUuid());
			if (parentStore == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_PARENT_UUID,
								inventoryStorePayload.getParentUuid()));
			}
		} else {
			store.setParent(null);
		}
		store.setCreatedDate(new Date());
		store.setCreatedBy(Context.getAuthenticatedUser());
		hospitalRestCoreService.saveOrUpdateInventoryStore(store);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/edit-store", method = RequestMethod.PUT)
	public ResponseEntity<Void> editStore(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody InventoryStorePayload inventoryStorePayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		if (inventoryStorePayload.getStoreUuid() != null) {
			InventoryStore inventoryStore = hospitalRestCoreService
					.getInventoryStoreByUuid(inventoryStorePayload.getStoreUuid());
			if (inventoryStore == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_INVENTORY_STORE_UUID,
								inventoryStorePayload.getStoreUuid()));
			}
			if (inventoryStorePayload.getRetired()) {
				inventoryStore.setRetired(true);
				inventoryStore.setRetiredDate(new Date());
				inventoryStore.setRetiredBy(Context.getAuthenticatedUser());
			} else {
				inventoryStore.setName(inventoryStorePayload.getName());
				Role role = hospitalRestCoreService.getRoleByUuid(inventoryStorePayload.getRoleUuid());
				if (role == null) {
					throw new ResourceNotFoundException(
							String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_ROLE_UUID,
									inventoryStorePayload.getRoleUuid()));
				}
				inventoryStore.setRole(role);
				inventoryStore.setCode(inventoryStorePayload.getCode());
				inventoryStore.setIsDrug(inventoryStorePayload.getIsDrug());
				if (inventoryStorePayload.getParentUuid() != null) {
					InventoryStore parentStore = hospitalRestCoreService
							.getInventoryStoreByUuid(inventoryStorePayload.getParentUuid());
					if (parentStore == null) {
						throw new ResourceNotFoundException(
								String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_PARENT_UUID,
										inventoryStorePayload.getParentUuid()));
					}
				} else {
					inventoryStore.setParent(null);
				}
				inventoryStore.setLastModifiedDate(new Date());
				inventoryStore.setLastModifiedBy(Context.getAuthenticatedUser());
			}

			hospitalRestCoreService.saveOrUpdateInventoryStore(inventoryStore);

		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-stores", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStore(@RequestBody List<String> storeUuids, HttpServletRequest request,
			HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		for (String storeUuid : storeUuids) {
			InventoryStore inventoryStore = hospitalRestCoreService.getInventoryStoreByUuid(storeUuid);
			if (inventoryStore == null) {
				throw new ResourceNotFoundException(String.format(
						OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_INVENTORY_STORE_UUID, inventoryStore));
			}

			inventoryStore.setDeleted(true);
			inventoryStore.setDeletedDate(new Date());
			inventoryStore.setDeletedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateInventoryStore(inventoryStore);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public InventoryStoreDetails getInventoryStoreDetails(InventoryStore inventoryStore) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		InventoryStoreDetails isd = new InventoryStoreDetails();
		isd.setId(inventoryStore.getId());
		isd.setName(inventoryStore.getName());
		isd.setRole(inventoryStore.getRole());
		isd.setCode(inventoryStore.getCode());
		isd.setUuid(inventoryStore.getUuid());
		return isd;
	}
}
