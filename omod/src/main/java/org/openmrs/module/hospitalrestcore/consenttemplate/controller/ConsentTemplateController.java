/**
 * 
 */
package org.openmrs.module.hospitalrestcore.consenttemplate.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.billing.api.BillingService;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplate;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplateDTO;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplateDetails;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ghanshyam
 *
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/consentTemplate")
public class ConsentTemplateController extends BaseRestController {

	@RequestMapping(method = RequestMethod.GET)
	public void getConsentTemplate(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "typeUuid", required = false) String typeUuid)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		ConceptService conceptService = Context.getService(ConceptService.class);

		BillingService restCoreService = Context.getService(BillingService.class);

		if (typeUuid != null) {
			Concept type = conceptService.getConceptByUuid(typeUuid);
			if (type == null) {
				throw new ResourceNotFoundException(String
						.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_CONSENT_TYPE_CONCEPT_UUID, typeUuid));
			}
			List<ConsentTemplate> consentTemplates = restCoreService.getConsentTemplateByType(type);
			ConsentTemplate consentTemplate = consentTemplates.get(0);
			ConsentTemplateDetails consentTemplateDetails = new ConsentTemplateDetails();
			consentTemplateDetails.setName(consentTemplate.getName());
			consentTemplateDetails.setConsentTemplateUuid(consentTemplate.getUuid());
			consentTemplateDetails.setMailSubject(consentTemplate.getMailSubject());
			consentTemplateDetails.setDescription(consentTemplate.getDescription());
			consentTemplateDetails.setConsentType(consentTemplate.getType().getName().getName());
			consentTemplateDetails.setConsentTypeConUuid(consentTemplate.getType().getUuid());
			consentTemplateDetails.setConsentTemplateContent(consentTemplate.getTemplateContent());
			consentTemplateDetails.setDeleted(consentTemplate.getDeleted());
			new ObjectMapper().writeValue(out, consentTemplateDetails);
		} else {
			List<ConsentTemplate> consentTemplates = restCoreService.getAllConsentTemplate();
			List<ConsentTemplateDetails> consentTemplateDetailsList = new LinkedList<ConsentTemplateDetails>();
			for (ConsentTemplate consentTemplate : consentTemplates) {
				ConsentTemplateDetails consentTemplateDetails = new ConsentTemplateDetails();
				consentTemplateDetails.setName(consentTemplate.getName());
				consentTemplateDetails.setConsentTemplateUuid(consentTemplate.getUuid());
				consentTemplateDetails.setMailSubject(consentTemplate.getMailSubject());
				consentTemplateDetails.setDescription(consentTemplate.getDescription());
				consentTemplateDetails.setConsentType(consentTemplate.getType().getName().getName());
				consentTemplateDetails.setConsentTypeConUuid(consentTemplate.getType().getUuid());
				consentTemplateDetails.setConsentTemplateContent(consentTemplate.getTemplateContent());
				consentTemplateDetails.setDeleted(consentTemplate.getDeleted());
				consentTemplateDetailsList.add(consentTemplateDetails);
			}

			new ObjectMapper().writeValue(out, consentTemplateDetailsList);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> processConsentTemplate(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody ConsentTemplateDTO consentTemplateDTO)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		ConsentTemplate consentTemplate;

		BillingService restCoreService = Context.getService(BillingService.class);

		ConceptService conceptService = Context.getService(ConceptService.class);

		if (consentTemplateDTO.getConsentTemplateUuid() != null) {
			consentTemplate = restCoreService.getConsentTemplateByUuid(consentTemplateDTO.getConsentTemplateUuid());
			if (consentTemplate == null) {
				throw new ResourceNotFoundException(String.format(OpenmrsCustomConstants.VALIDATION_ERROR_CONSENT_UUID,
						consentTemplateDTO.getConsentTemplateUuid()));
			}
			Concept type = conceptService.getConceptByUuid(consentTemplateDTO.getConsentTypeConUuid());
			if (type == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_CONSENT_TYPE_CONCEPT_UUID,
								consentTemplateDTO.getConsentTypeConUuid()));
			}
			consentTemplate.setName(consentTemplateDTO.getName());
			consentTemplate.setMailSubject(consentTemplateDTO.getMailSubject());
			consentTemplate.setDescription(consentTemplateDTO.getDescription());
			consentTemplate.setType(type);
			consentTemplate.setTemplateContent(consentTemplateDTO.getConsentTemplateContent());
			consentTemplate.setDeleted(consentTemplateDTO.getDeleted());
			consentTemplate.setDateChanged(new Date());
			consentTemplate.setChangedBy(Context.getAuthenticatedUser());
		} else {
			consentTemplate = new ConsentTemplate();
			Concept type = conceptService.getConceptByUuid(consentTemplateDTO.getConsentTypeConUuid());
			if (type == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_CONSENT_TYPE_CONCEPT_UUID,
								consentTemplateDTO.getConsentTypeConUuid()));
			}
			consentTemplate.setName(consentTemplateDTO.getName());
			consentTemplate.setMailSubject(consentTemplateDTO.getMailSubject());
			consentTemplate.setDescription(consentTemplateDTO.getDescription());
			consentTemplate.setType(type);
			consentTemplate.setTemplateContent(consentTemplateDTO.getConsentTemplateContent());
			consentTemplate.setDeleted(consentTemplateDTO.getDeleted());
			consentTemplate.setDateCreated(new Date());
			consentTemplate.setCreator(Context.getAuthenticatedUser());
		}
		restCoreService.saveOrUpdateConsentTemplate(consentTemplate);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
