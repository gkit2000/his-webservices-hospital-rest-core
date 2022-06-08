/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.ConceptAnswer;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Visit;
import org.openmrs.VisitAttribute;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.BillingInfoForPatient;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItemInfo;
import org.openmrs.module.hospitalrestcore.billing.WalkingPatientDetails;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/patientBillingInfoDetails")
public class PatientBillingInfoDetailsController extends BaseRestController {
	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public void getWalkingPatientDetails(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "patientUuid", required = false) String patientUuid,
			@RequestParam(value = "billId", required = false) Integer billId)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		PatientService ps = Context.getPatientService();
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

		WalkingPatientDetails walkingPatientDetails = new WalkingPatientDetails();

		walkingPatientDetails.setPatientId(patient.getPatientIdentifier(pit).getIdentifier());
		walkingPatientDetails.setPatientUuid(patient.getUuid());
		walkingPatientDetails.setPatientName(PulseUtil.getName(patient));
		walkingPatientDetails.setGender(patient.getGender());
		walkingPatientDetails.setAge(patient.getAge());
		if (patient.getBirthdate() != null) {
			walkingPatientDetails.setBirthDate(formatter.format(patient.getBirthdate()));
		}
		if (lastVisit != null) {
			walkingPatientDetails.setRevisitDate(formatter.format(lastVisit.getDateCreated()));
		}

		for (VisitAttribute va : lastVisit.getActiveAttributes()) {
			if (va.getAttributeType().getUuid().equals(OpenmrsCustomConstants.PATIENT_CATEGORY)) {
				walkingPatientDetails.setPatientCategory(va.getValueReference());
			}
			if (OpenmrsCustomConstants.PATIENT_SUBCATEGORY1.contains(va.getAttributeType().getUuid())) {
				walkingPatientDetails.setPatientSubCategory1(va.getValueReference());
			}
			if (OpenmrsCustomConstants.PATIENT_SUBCATEGORY2.contains(va.getAttributeType().getUuid())) {
				walkingPatientDetails.setPatientSubCategory2(va.getValueReference());
			}
		}

		List<PatientServiceBill> patientServiceBills = new ArrayList<PatientServiceBill>();
		if (billId != null) {
			PatientServiceBill patientServiceBill = hospitalRestCoreService.getPatientServiceBillByIdAndPatient(billId,
					patient);
			if (patientServiceBill != null) {
				patientServiceBills.add(patientServiceBill);
			}
		} else {
			patientServiceBills = hospitalRestCoreService.getPatientServiceBill(patient);
		}

		List<BillingInfoForPatient> billingInfoForPatient = new LinkedList<BillingInfoForPatient>();

		for (PatientServiceBill patientServiceBill : patientServiceBills) {
			BillingInfoForPatient bifp = new BillingInfoForPatient();
			bifp.setBillId(patientServiceBill.getPatientServiceBillId());
			bifp.setDescription(patientServiceBill.getDescription());
			bifp.setBillType(patientServiceBill.getBillType());
			bifp.setBillingDate(formatter.format(patientServiceBill.getCreatedDate()));
			if (patientServiceBill.getVoided()) {
				bifp.setBillVoided(patientServiceBill.getVoided());
				bifp.setVoidedBy(PulseUtil.getName(patientServiceBill.getVoidedBy().getPerson()));
				bifp.setVoidedDate(formatter.format(patientServiceBill.getVoidedDate()));
			}
			bifp.setAmount(patientServiceBill.getAmount());
			bifp.setActualAmount(patientServiceBill.getActualAmount());
			bifp.setComment(patientServiceBill.getComment());
			bifp.setWaiverPercentage(patientServiceBill.getWaiverPercentage());
			bifp.setWaiverAmount(patientServiceBill.getWaiverAmount());
			bifp.setAmountPayable(patientServiceBill.getAmountPayable());
			bifp.setAmountGiven(patientServiceBill.getAmountGiven());
			bifp.setAmountReturned(patientServiceBill.getAmountReturned());
			if (patientServiceBill.getEdited()) {
				bifp.setEdited(patientServiceBill.getEdited());
				bifp.setEditedBy(PulseUtil.getName(patientServiceBill.getEditedBy().getPerson()));
				bifp.setEditedDate(formatter.format(patientServiceBill.getEditedDate()));
			}

			List<PatientServiceBillItem> patientServiceBillItems = hospitalRestCoreService
					.getPatientServiceBillItem(patientServiceBill);

			List<PatientServiceBillItemInfo> patientServiceBillItemInfo = new LinkedList<PatientServiceBillItemInfo>();
			for (PatientServiceBillItem patientServiceBillItem : patientServiceBillItems) {
				PatientServiceBillItemInfo psbii = new PatientServiceBillItemInfo();
				psbii.setPatientServiceBillItemId(patientServiceBillItem.getPatientServiceBillItemId());
				psbii.setUnitPrice(patientServiceBillItem.getUnitPrice());
				psbii.setAmount(patientServiceBillItem.getAmount());
				psbii.setActualAmount(patientServiceBillItem.getActualAmount());
				psbii.setQuantity(patientServiceBillItem.getQuantity());
				psbii.setName(patientServiceBillItem.getName());
				psbii.setCreatedDate(formatter.format(patientServiceBillItem.getCreatedDate()));
				if (patientServiceBillItem.getVoided()) {
					psbii.setVoided(patientServiceBillItem.getVoided());
					psbii.setVoidedBy(PulseUtil.getName(patientServiceBillItem.getVoidedBy().getPerson()));
					psbii.setVoidedDate(formatter.format(patientServiceBillItem.getVoidedDate()));
				}
				if (patientServiceBillItem.getEdited()) {
					psbii.setEdited(patientServiceBillItem.getEdited());
					psbii.setEditedBy(PulseUtil.getName(patientServiceBillItem.getEditedBy().getPerson()));
					psbii.setEditedDate(formatter.format(patientServiceBillItem.getEditedDate()));
				}
				List<ConceptAnswer> conceptAnswersFirstLevel = hospitalRestCoreService.getConceptAnswerByAnswerConcept(
						patientServiceBillItem.getBillableService().getServiceConcept());
				if (conceptAnswersFirstLevel.size() > 1) {
					psbii.setParentServicesName(conceptAnswersFirstLevel.get(0).getAnswerConcept().getName().getName());
				} else {
					List<ConceptAnswer> conceptAnswersSecondLevel = hospitalRestCoreService
							.getConceptAnswerByAnswerConcept(conceptAnswersFirstLevel.get(0).getConcept());
					if (conceptAnswersSecondLevel.size() > 1) {
						psbii.setParentServicesName(
								conceptAnswersSecondLevel.get(0).getAnswerConcept().getName().getName());
					} else {
						psbii.setParentServicesName(conceptAnswersSecondLevel.get(0).getConcept().getName().getName());
					}
				}
				patientServiceBillItemInfo.add(psbii);
			}
			bifp.setPatientServiceBillItemInfo(patientServiceBillItemInfo);
			billingInfoForPatient.add(bifp);
		}

		walkingPatientDetails.setBillingInfoForPatient(billingInfoForPatient);

		new ObjectMapper().writeValue(out, walkingPatientDetails);

	}
}
