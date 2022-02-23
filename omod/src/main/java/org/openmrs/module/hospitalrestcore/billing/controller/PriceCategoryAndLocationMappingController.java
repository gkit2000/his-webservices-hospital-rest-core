/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocationDTO;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocationDetails;
import org.openmrs.module.hospitalrestcore.billing.api.BillingService;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/categoryLocation")
public class PriceCategoryAndLocationMappingController extends BaseRestController {

	@RequestMapping(value = "/mapping", method = RequestMethod.GET)
	public void getCategoryLocation(HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		BillingService billingService = Context.getService(BillingService.class);
		List<CategoryLocation> categoryLocations = billingService.getAllCategoryLocation();
		Set<Concept> priceCategorySet = new HashSet<Concept>();
		for (CategoryLocation categoryLocation : categoryLocations) {
			priceCategorySet.add(categoryLocation.getPriceCategoryConcept());
		}

		Map<String, List<CategoryLocationDetails>> priceCategoryAndLocationMap = new LinkedHashMap<String, List<CategoryLocationDetails>>();

		for (Concept priceCategory : priceCategorySet) {
			List<CategoryLocation> categoryLocationsByPriceCategory = billingService
					.getCategoryLocationByPriceCategory(priceCategory);
			List<CategoryLocationDetails> categoryLocationDetailsList = new LinkedList<CategoryLocationDetails>();
			categoryLocationDetailsList.clear();
			Map<String, CategoryLocationDetails> priceCategoryLocationMap = new LinkedHashMap<String, CategoryLocationDetails>();
			for (CategoryLocation categoryLocation : categoryLocationsByPriceCategory) {
				CategoryLocationDetails pcld = new CategoryLocationDetails();
				pcld.setLocationUuid(categoryLocation.getLocation().getUuid());
				priceCategoryLocationMap.put(categoryLocation.getPriceCategoryConcept().getUuid(), pcld);
				categoryLocationDetailsList.add(pcld);
			}
			priceCategoryAndLocationMap.put(priceCategory.getUuid(), categoryLocationDetailsList);
		}

		new ObjectMapper().writeValue(out, priceCategoryAndLocationMap);
	}

	@RequestMapping(value = "/mapping", method = RequestMethod.POST)
	public ResponseEntity<Void> processCategoryLocation(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody CategoryLocationDTO categoryLocationDTO)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		ConceptService conceptService = Context.getConceptService();
		Concept priceCategoryConcept = conceptService.getConceptByUuid(categoryLocationDTO.getPriceCategoryConUuid());

		if (priceCategoryConcept == null) {
			throw new ResourceNotFoundException(String.format(OpenmrsCustomConstants.VALIDATION_ERROR_PRICE_CATEGORY,
					categoryLocationDTO.getPriceCategoryConUuid()));
		}

		LocationService locationService = Context.getLocationService();

		BillingService billingService = Context.getService(BillingService.class);

		for (String locationUuid : categoryLocationDTO.getLocationUuids()) {
			Location location = locationService.getLocationByUuid(locationUuid);
			CategoryLocation categoryLocation = billingService.getCategoryLocationByLocation(location);

			if (categoryLocation != null) {
				if (categoryLocationDTO.getDeleted() == false) {
					categoryLocation.setPriceCategoryConcept(priceCategoryConcept);
					categoryLocation.setLocation(location);
					categoryLocation.setDeleted(false);
					categoryLocation.setDateChanged(new Date());
					categoryLocation.setChangedBy(Context.getAuthenticatedUser());
				} else {
					categoryLocation.setPriceCategoryConcept(priceCategoryConcept);
					categoryLocation.setLocation(location);
					categoryLocation.setDeleted(true);
					categoryLocation.setDateChanged(new Date());
					categoryLocation.setChangedBy(Context.getAuthenticatedUser());
				}
			} else {
				categoryLocation = new CategoryLocation();
				categoryLocation.setPriceCategoryConcept(priceCategoryConcept);
				categoryLocation.setLocation(location);
				categoryLocation.setDeleted(false);
				categoryLocation.setDateCreated(new Date());
				categoryLocation.setCreator(Context.getAuthenticatedUser());
			}

			billingService.saveOrUpdateCategoryLocation(categoryLocation);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
