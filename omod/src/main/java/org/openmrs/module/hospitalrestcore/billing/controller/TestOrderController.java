/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.module.hospitalrestcore.billing.OrderDetails;
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
	public ResponseEntity<Void> getLastVisit(HttpServletResponse response, HttpServletRequest request,
			@RequestBody OrderDetails orderDetails)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		
		//response.setContentType("application/json");
		//ServletOutputStream out = response.getOutputStream();
		
		//new ObjectMapper().writeValue(out, patientVisitDetails);
		  return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
