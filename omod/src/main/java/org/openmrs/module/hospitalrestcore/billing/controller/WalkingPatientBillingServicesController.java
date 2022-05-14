/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Visit;
import org.openmrs.VisitAttribute;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.BillableServiceDetails;
import org.openmrs.module.hospitalrestcore.billing.WalkinPatientServicesDetails;
import org.openmrs.module.hospitalrestcore.controller.PulseUtil;
import org.openmrs.module.hospitalrestcore.visit.VisitComparator;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ghanshyam
 *
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/walkingPatientBillingServicesDetails")
public class WalkingPatientBillingServicesController extends BaseRestController {
	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public void getProcedureInvestigationOrders(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "patientUuid", required = false) String patientUuid,
			@RequestParam(value = "priceCategoryConceptUuid", required = false) String priceCategoryConceptUuid,
			@RequestParam(value = "serviceConceptUuids", required = false) List<String> serviceConceptUuids)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		PatientService ps = Context.getPatientService();
		ConceptService conceptService = Context.getConceptService();
		LocationService ls = Context.getLocationService();
		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		Patient patient = null;
		if (patientUuid != null && !patientUuid.isEmpty()) {
			patient = ps.getPatientByUuid(patientUuid);
			if (patient == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_PATIENT_UUID, patientUuid));
			}
		}

		PatientIdentifierType pit = ps.getPatientIdentifierTypeByUuid(OpenmrsCustomConstants.PATIENT_IDENTIFIER_TYPE);
		Visit lastVisit = null;

		if (patient != null) {
			List<Visit> visits = Context.getService(VisitService.class).getVisitsByPatient(patient);
			if (visits != null && visits.size() > 0) {
				Collections.sort(visits, new VisitComparator());
				lastVisit = (visits.get(0));
			}
		}

		WalkinPatientServicesDetails wpsd = new WalkinPatientServicesDetails();
		wpsd.setPatientId(patient.getPatientIdentifier(pit).getIdentifier());
		wpsd.setPatientUuid(patient.getUuid());
		wpsd.setPatientName(PulseUtil.getName(patient));
		wpsd.setGender(patient.getGender());
		wpsd.setAge(patient.getAge());
		if (patient.getBirthdate() != null) {
			wpsd.setBirthDate(formatter.format(patient.getBirthdate()));
		}
		for (VisitAttribute va : lastVisit.getActiveAttributes()) {
			if (va.getAttributeType().getUuid().equals(OpenmrsCustomConstants.PATIENT_CATEGORY)) {
				wpsd.setPatientCategory(va.getValueReference());
			}
			if (OpenmrsCustomConstants.PATIENT_SUBCATEGORY1.contains(va.getAttributeType().getUuid())) {
				wpsd.setPatientSubCategory1(va.getValueReference());
			}
			if (OpenmrsCustomConstants.PATIENT_SUBCATEGORY2.contains(va.getAttributeType().getUuid())) {
				wpsd.setPatientSubCategory2(va.getValueReference());
			}
		}

		List<BillableServiceDetails> billableServiceDetails = new LinkedList<BillableServiceDetails>();

		if (patientUuid != null && priceCategoryConceptUuid != null && serviceConceptUuids != null) {
			Concept priceCategoryConcept = conceptService.getConceptByUuid(priceCategoryConceptUuid);
			if (priceCategoryConcept == null) {
				throw new ResourceNotFoundException(String
						.format(OpenmrsCustomConstants.VALIDATION_ERROR_PRICE_CATEGORY, priceCategoryConceptUuid));
			}
			wpsd.setPriceCategoryConceptUuid(priceCategoryConceptUuid);
			for (String serviceConceptUuid : serviceConceptUuids) {
				Concept serviceConcept = conceptService.getConceptByUuid(serviceConceptUuid);
				if (serviceConcept == null) {
					throw new ResourceNotFoundException(
							String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_SERVICE_CONCEPT_UUID,
									serviceConceptUuid));
				}
				BillableService billableService = hospitalRestCoreService
						.getServicesByServiceConceptAndPriceCategory(serviceConcept, priceCategoryConcept);
				if (billableService == null) {
					throw new ResourceNotFoundException(
							String.format(OpenmrsCustomConstants.VALIDATION_ERROR_BILLABLE_SERVICE,
									serviceConcept.getName().getName(), priceCategoryConcept.getName().getName()));
				}
				BillableServiceDetails bsd = new BillableServiceDetails();
				bsd.setServiceConUuid(serviceConcept.getUuid());
				bsd.setServiceConName(serviceConcept.getName().getName());
				bsd.setPrice(billableService.getPrice());
				bsd.setQuantity(1);
				bsd.setOpdOrderId(null);
				billableServiceDetails.add(bsd);
			}
		}

		wpsd.setBillableServiceDetails(billableServiceDetails);

		new ObjectMapper().writeValue(out, wpsd);
	}
}
