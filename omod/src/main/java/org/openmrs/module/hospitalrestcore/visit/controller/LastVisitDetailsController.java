/**
 * 
 */
package org.openmrs.module.hospitalrestcore.visit.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Encounter;
import org.openmrs.Visit;
import org.openmrs.VisitAttribute;
import org.openmrs.api.EncounterService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.hospitalrestcore.visit.LastVisitDetails;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ghanshyam
 *
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/lastVisitDeatils")
public class LastVisitDetailsController {
	// AppointmentRestController.APPOINTMENT_SCHEDULING_REST_NAMESPACE
	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public void getLastVisitAppointmentDeatils(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "visit") String visitUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		LastVisitDetails lastVisitDetails = new LastVisitDetails();
		ServletOutputStream out = response.getOutputStream();
		Visit visit = Context.getService(VisitService.class).getVisitByUuid(visitUuid);
		if (visit != null) {
			Appointment appointment = Context.getService(AppointmentService.class).getAppointmentByVisit(visit);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			if (appointment != null) {
				lastVisitDetails.setRegistrationDate(formatter.format(appointment.getDateCreated()));
				lastVisitDetails.setDepartmentName(appointment.getAppointmentType().getName());
				lastVisitDetails
						.setReportingPlace(appointment.getTimeSlot().getAppointmentBlock().getLocation().getName());
				lastVisitDetails.setDoctorName(appointment.getTimeSlot().getAppointmentBlock().getProvider().getName());
				if (appointment.getReason() != null && (appointment.getReason().equalsIgnoreCase("New Registration")
						|| appointment.getReason().equalsIgnoreCase("Online Appointment"))) {
					List<Encounter> encounters = Context.getService(EncounterService.class).getEncountersByVisit(visit,
							false);
					for (Encounter encounter : encounters) {
						if (encounter.getEncounterType().getName().equalsIgnoreCase("Check In")) {
							lastVisitDetails.setReportingTime(formatter.format(encounter.getDateCreated()));
						}
					}
				} else {
					lastVisitDetails.setReportingTime(formatter.format(appointment.getDateCreated()));
				}
			}

			for (VisitAttribute va : visit.getActiveAttributes()) {
				if (va.getAttributeType().getUuid().equals("80c68ebe-d696-4b8e-8aa0-53018f8e5d7b")) {
					lastVisitDetails.setPatientCategory(va.getValueReference());
				}
				if (va.getAttributeType().getUuid().equals("f494e0a2-a6d0-471b-bec5-fae8cc082ef8")
						|| va.getAttributeType().getUuid().equals("7ac406bc-f9bd-42ff-a12e-1b7d0b59096e")
						|| va.getAttributeType().getUuid().equals("bec1304d-f522-47d8-b209-4572c5628f87")) {
					lastVisitDetails.setPatientSubCategory1(va.getValueReference());
				}
				if (va.getAttributeType().getUuid().equals("81216581-8155-4abe-89c8-ab45afdcd77c")
						|| va.getAttributeType().getUuid().equals("81216581-8155-4abe-89c8-ab45afdcd77c")
						|| va.getAttributeType().getUuid().equals("2e88efe2-b4d4-46d4-810b-a0e6d0c0004e")
						|| va.getAttributeType().getUuid().equals("63772745-4658-4db6-864c-d25dd6319ebd")
						|| va.getAttributeType().getUuid().equals("9fcbe4f4-36bb-455e-80a9-979341b72a68")
						|| va.getAttributeType().getUuid().equals("38caa20b-b767-4074-868f-5e858de0f38a")
						|| va.getAttributeType().getUuid().equals("2a289585-5ec6-4113-86f9-b4e16df3abf1")
						|| va.getAttributeType().getUuid().equals("8c753c94-dc67-4daf-acf5-887279e236f2")
						|| va.getAttributeType().getUuid().equals("18b13ded-5f66-43be-ba62-26baf8208f77")
						|| va.getAttributeType().getUuid().equals("f59437df-9878-4e7a-9fec-c1f77d337d17")) {
					lastVisitDetails.setPatientSubCategory2(va.getValueReference());
				}
				if (va.getAttributeType().getUuid().equals("ecd9f478-40ac-4716-b63f-ff2dbb2bfa74")) {
					lastVisitDetails.setMlc(va.getValueReference());
				}
				if (va.getAttributeType().getUuid().equals("70f8d129-3136-4d2e-bdee-05f88cd33f13")) {
					lastVisitDetails.setMlcSubCategory(va.getValueReference());
				}
			}
		}
		new ObjectMapper().writeValue(out, lastVisitDetails);
	}
}
