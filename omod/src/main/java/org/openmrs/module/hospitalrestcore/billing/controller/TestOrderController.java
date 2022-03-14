/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.DepartmentConcept;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.OrderDetails;
import org.openmrs.module.hospitalrestcore.visit.VisitComparator;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/orders")
public class TestOrderController extends BaseRestController {

	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public ResponseEntity<Void> generateOrders(HttpServletResponse response, HttpServletRequest request,
			@RequestBody OrderDetails orderDetails)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		// response.setContentType("application/json");
		// ServletOutputStream out = response.getOutputStream();

		// new ObjectMapper().writeValue(out, patientVisitDetails);

		HttpSession httpSession = request.getSession();

		User user = Context.getAuthenticatedUser();

		PatientService ps = Context.getPatientService();
		Patient patient = ps.getPatientByUuid(orderDetails.getPatient());

		ConceptService conceptService = Context.getService(ConceptService.class);

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		LocationService locationService = Context.getService(LocationService.class);

		Date date = new Date();

		Concept conpro = conceptService.getConceptByName("Post for procedure");
		Concept coninvt = conceptService.getConceptByName("Investigation");

		EncounterType encounterType = Context.getEncounterService()
				.getEncounterType(OpenmrsCustomConstants.ENCOUNTER_TYPE_CHECK_IN);

		Location location = locationService.getLocationByUuid(orderDetails.getLocation());

		if (location == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_LOCATION, orderDetails.getLocation()));
		}

		CategoryLocation categoryLocation = hospitalRestCoreService.getCategoryLocationByLocation(location);

		if (categoryLocation == null) {
			throw new ResourceNotFoundException(
					String.format(OpenmrsCustomConstants.VALIDATION_ERROR_CATEGORY_LOCATION, location.getName()));
		}

		List<Visit> visits = Context.getService(VisitService.class).getVisitsByPatient(patient);
		if (visits != null && visits.size() > 0) {
			Collections.sort(visits, new VisitComparator());
		}

		Encounter encounter = new Encounter();

		encounter.setPatient(patient);
		encounter.setCreator(user);
		encounter.setEncounterDatetime(date);
		encounter.setEncounterType(encounterType);
		encounter.setLocation(location);
		encounter.setVisit(visits.get(0));

		if (patient != null) {
			for (String prcd : orderDetails.getProcedures()) {
				Concept concept = conceptService.getConceptByUuid(prcd);
				if (concept != null) {
					BillableService billableService = hospitalRestCoreService
							.getServicesByServiceConceptAndPriceCategory(concept,
									categoryLocation.getPriceCategoryConcept());
					if (billableService == null) {
						throw new ResourceNotFoundException(
								String.format(OpenmrsCustomConstants.VALIDATION_ERROR_BILLABLE_SERVICE_PROCEDURE,
										concept.getName().getName(),
										categoryLocation.getPriceCategoryConcept().getName().getName()));
					}
					OpdTestOrder opdTestOrder = new OpdTestOrder();
					opdTestOrder.setPatient(patient);
					opdTestOrder.setEncounter(encounter);
					opdTestOrder.setServiceConcept(conpro);
					opdTestOrder.setTypeConcept(DepartmentConcept.TYPES[1]);
					opdTestOrder.setValueCoded(concept);
					opdTestOrder.setCreator(user);
					opdTestOrder.setCreatedOn(date);
					opdTestOrder.setBillableService(billableService);
					opdTestOrder.setScheduleDate(date);
					opdTestOrder.setLocation(location);
					hospitalRestCoreService.saveOrUpdateOpdTestOrder(opdTestOrder);
				}
			}

			for (String inv : orderDetails.getInvestigations()) {
				Concept concept = conceptService.getConceptByUuid(inv);
				if (concept != null) {
					BillableService billableService = hospitalRestCoreService
							.getServicesByServiceConceptAndPriceCategory(concept,
									categoryLocation.getPriceCategoryConcept());
					if (billableService == null) {
						throw new ResourceNotFoundException(
								String.format(OpenmrsCustomConstants.VALIDATION_ERROR_BILLABLE_SERVICE_INVESTIGATION,
										concept.getName().getName(),
										categoryLocation.getPriceCategoryConcept().getName().getName()));
					}
					OpdTestOrder opdTestOrder = new OpdTestOrder();
					opdTestOrder.setPatient(patient);
					opdTestOrder.setEncounter(encounter);
					opdTestOrder.setServiceConcept(coninvt);
					opdTestOrder.setTypeConcept(DepartmentConcept.TYPES[2]);
					opdTestOrder.setValueCoded(concept);
					opdTestOrder.setCreator(user);
					opdTestOrder.setCreatedOn(date);
					opdTestOrder.setBillableService(billableService);
					opdTestOrder.setScheduleDate(date);
					opdTestOrder.setLocation(location);
					hospitalRestCoreService.saveOrUpdateOpdTestOrder(opdTestOrder);
				}
			}
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
