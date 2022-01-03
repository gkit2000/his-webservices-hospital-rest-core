/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.ServiceDetails;
import org.openmrs.module.hospitalrestcore.billing.ServicesDetails;
import org.openmrs.module.hospitalrestcore.billing.api.BillingService;
import org.openmrs.module.hospitalrestcore.concept.ConceptNode;
import org.openmrs.module.hospitalrestcore.concept.TestTree;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/services")
public class ManageBillableServicesController extends BaseRestController {

	@RequestMapping(value = "/billable", method = RequestMethod.GET)
	public void getServicesPrice(HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		
		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		
		BillingService billingService = Context.getService(BillingService.class);
		List<BillableService> services = billingService.getAllServices();

		Map<String, BigDecimal> servicesPriceMap = new LinkedHashMap<String, BigDecimal>();
		for (BillableService ser : services) {
			servicesPriceMap.put(ser.getConcept().getUuid(), ser.getPrice());
		}
		
		new ObjectMapper().writeValue(out, servicesPriceMap);
	}

	@RequestMapping(value = "/billable", method = RequestMethod.POST)
	public ResponseEntity<Void> manageBillableServices(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody ServicesDetails servicesDetails)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
		HttpSession httpSession = request.getSession();

		ConceptService conceptService = Context.getService(ConceptService.class);
		Concept rootServiceconcept = Context.getConceptService().getConceptByName("SERVICES ORDERED");

		BillingService billingService = Context.getService(BillingService.class);
		List<BillableService> services = billingService.getAllServices();

		Map<Integer, BillableService> mapServices = new LinkedHashMap<Integer, BillableService>();

		for (BillableService ser : services) {
			mapServices.put(ser.getConcept().getId(), ser);
		}

		// servicesDetails
		List<ServiceDetails> servicesList = servicesDetails.getServicesDetails();

		for (ServiceDetails serviceDetails : servicesList) {
			Concept serviceConcept = conceptService.getConceptByUuid(serviceDetails.getConUuid());
			BillableService service = mapServices.get(serviceConcept.getId());
			if (service == null) {
				if (serviceConcept != null) {
					service = new BillableService();
					service.setConcept(serviceConcept);
					service.setName(serviceConcept.getName().getName());
					if (serviceConcept.getShortNameInLocale(Locale.ENGLISH) != null) {
						service.setShortName(serviceConcept.getShortNameInLocale(Locale.ENGLISH).getName());
					}
					if (serviceDetails.getPrice() != null) {
						service.setPrice(serviceDetails.getPrice());
					}
					service.setEnable(serviceDetails.getEnable());
					if (rootServiceconcept != null) {
						TestTree tree = new TestTree(rootServiceconcept);
						ConceptNode node = tree.findNode(serviceConcept);
						if (node != null) {
							while (!node.getParent().equals(tree.getRootLab())) {
								node = node.getParent();
							}
							service.setCategory(node.getConcept());
						}
					}
					mapServices.put(serviceConcept.getId(), service);
					billingService.saveBillableService(service);
				}
			} else {
				service.setName(serviceConcept.getName().getName());
				if (serviceConcept.getShortNameInLocale(Locale.ENGLISH) != null) {
					service.setShortName(serviceConcept.getShortNameInLocale(Locale.ENGLISH).getName());
				}
				if (serviceDetails.getPrice() != null) {
					service.setPrice(serviceDetails.getPrice());
				}
				service.setEnable(serviceDetails.getEnable());
				if (rootServiceconcept != null) {
					TestTree tree = new TestTree(rootServiceconcept);
					ConceptNode node = tree.findNode(serviceConcept);
					if (node != null) {
						while (!node.getParent().equals(tree.getRootLab())) {
							node = node.getParent();
						}
						service.setCategory(node.getConcept());
					}
				}
				mapServices.remove(serviceConcept.getId());
				mapServices.put(serviceConcept.getId(), service);
				billingService.saveBillableService(service);
			}
		}
		// billingService.saveBillableService(mapServices.values());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
