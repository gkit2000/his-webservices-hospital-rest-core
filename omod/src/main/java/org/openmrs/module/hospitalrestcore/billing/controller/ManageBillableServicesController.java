/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.module.hospitalrestcore.billing.ServicesDetails;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/services")
public class ManageBillableServicesController {

	@RequestMapping(value = "/billable", method = RequestMethod.POST)
	public ResponseEntity<Void> manageBillableServices(HttpServletResponse response, HttpServletRequest request,
			@RequestBody ServicesDetails servicesDetails)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
