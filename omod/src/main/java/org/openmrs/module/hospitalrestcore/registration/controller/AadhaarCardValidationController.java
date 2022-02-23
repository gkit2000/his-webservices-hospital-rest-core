/**
 * 
 */
package org.openmrs.module.hospitalrestcore.registration.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ghanshyam
 *
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/aadhaarValidation")
public class AadhaarCardValidationController extends BaseRestController {

	private static final Logger log = LoggerFactory.getLogger(AadhaarCardValidationController.class);

	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public void getServicesPrice(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "aadhaarNo", required = true) String aadhaarNo,
			@RequestParam(value = "dob", required = true) String dob)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date birthDate = formatter.parse(dob);
		Date now = new Date();
		Long timeBetween = now.getTime() - birthDate.getTime();
		Double yearsBetween = timeBetween / 3.15576e+10;
		log.info("yearsBetween-" + yearsBetween);
		List<Person> patients = Context.getPersonService().getPeople(aadhaarNo, false, false);
		log.info("patients size-" + patients.size());
		if (yearsBetween > 10) {
			if (patients.size() > 0) {
				throw new ResourceNotFoundException(OpenmrsCustomConstants.VALIDATION_ERROR_AADHAR);
			}
		}

	}
}
