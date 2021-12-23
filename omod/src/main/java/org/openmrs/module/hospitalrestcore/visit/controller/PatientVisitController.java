/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hospitalrestcore.visit.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.visit.PatientVisitDetails;
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
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/lastVisit")
public class PatientVisitController extends BaseRestController {

	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public void getLastVisit(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "patient") String patientUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		PatientVisitDetails patientVisitDetails = new PatientVisitDetails();
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		Patient patient = Context.getService(PatientService.class).getPatientByUuid(patientUuid);
		if (patient != null) {
			List<Visit> visits = Context.getService(VisitService.class).getVisitsByPatient(patient);
			if (visits != null && visits.size() > 0) {
				Collections.sort(visits, new VisitComparator());
				patientVisitDetails.setVisitId(visits.get(0).getVisitId());
				patientVisitDetails.setVisitUuid(visits.get(0).getUuid());
			}
		}
		new ObjectMapper().writeValue(out, patientVisitDetails);
	}
}
