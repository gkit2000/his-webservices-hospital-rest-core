/**
 * 
 */
package org.openmrs.module.hospitalrestcore.concept.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.concept.ConceptDetails;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/conceptDetails")
public class ConceptController {
	@RequestMapping(value = "/concept", method = RequestMethod.GET)
	public void getConceptDetails(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "concept") String conceptUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		ConceptDetails conceptDetails = new ConceptDetails();
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		Concept concept = Context.getService(ConceptService.class).getConceptByUuid(conceptUuid);
		if(concept!=null) {
			conceptDetails.setShortName(concept.getShortNameInLocale(Locale.ENGLISH).getName());
		}
		new ObjectMapper().writeValue(out, conceptDetails);
	}
}
