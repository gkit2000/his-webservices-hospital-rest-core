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
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
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
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			if (appointment != null) {
				lastVisitDetails.setRegistrationDate(formatter.format(appointment.getDateCreated()));
				lastVisitDetails.setDepartmentName(appointment.getAppointmentType().getName());
				lastVisitDetails
						.setReportingPlace(appointment.getTimeSlot().getAppointmentBlock().getLocation().getName());
				lastVisitDetails.setDoctorName(appointment.getTimeSlot().getAppointmentBlock().getProvider().getName());
				if (appointment.getReason() != null && (appointment.getReason()
						.equalsIgnoreCase(OpenmrsCustomConstants.APPOINTMENT_REASON_NEW_REGISTRATION)
						|| appointment.getReason()
								.equalsIgnoreCase(OpenmrsCustomConstants.APPOINTMENT_REASON_ONLINE_APPOINTMENT))) {
					List<Encounter> encounters = Context.getService(EncounterService.class).getEncountersByVisit(visit,
							false);
					for (Encounter encounter : encounters) {
						if (encounter.getEncounterType().getName()
								.equalsIgnoreCase(OpenmrsCustomConstants.ENCOUNTER_TYPE_CHECK_IN)) {
							lastVisitDetails.setReportingTime(formatter.format(encounter.getDateCreated()));
						}
					}
				} else {
					lastVisitDetails.setReportingTime(formatter.format(appointment.getDateCreated()));
				}
			}

			for (VisitAttribute va : visit.getActiveAttributes()) {
				if (va.getAttributeType().getUuid().equals(OpenmrsCustomConstants.PATIENT_CATEGORY)) {
					lastVisitDetails.setPatientCategory(va.getValueReference());
				}
				if (OpenmrsCustomConstants.PATIENT_SUBCATEGORY1.contains(va.getAttributeType().getUuid())) {
					lastVisitDetails.setPatientSubCategory1(va.getValueReference());
				}
				if (OpenmrsCustomConstants.PATIENT_SUBCATEGORY2.contains(va.getAttributeType().getUuid())) {
					lastVisitDetails.setPatientSubCategory2(va.getValueReference());
				}
				if (va.getAttributeType().getUuid().equals(OpenmrsCustomConstants.MLC)) {
					lastVisitDetails.setMlc(va.getValueReference());
				}
				if (va.getAttributeType().getUuid().equals(OpenmrsCustomConstants.MLC_SUBCATEGORY)) {
					lastVisitDetails.setMlcSubCategory(va.getValueReference());
				}
			}
		}
		new ObjectMapper().writeValue(out, lastVisitDetails);
	}
}
