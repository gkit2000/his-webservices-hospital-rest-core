/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.billing.Company;
import org.openmrs.module.hospitalrestcore.billing.CompanyDetailsResponse;
import org.openmrs.module.hospitalrestcore.billing.CompanyPayload;
import org.openmrs.module.hospitalrestcore.billing.CompanySearchPayload;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/company")
public class CompanyController extends BaseRestController {

	@RequestMapping(value = "/search-company ", method = RequestMethod.POST)
	public void searchCompany(@Valid @RequestBody CompanySearchPayload companySearchPayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		List<Company> companies = hospitalRestCoreService.searchCompany(companySearchPayload.getSearchText());
		List<CompanyDetailsResponse> cdr = companies.stream().map(comp -> getCompanyDetails(comp))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, cdr);
	}

	@RequestMapping(value = "/all-companies", method = RequestMethod.GET)
	public void getAllCompanies(HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		List<Company> companies = hospitalRestCoreService.getAllCompanies();
		List<CompanyDetailsResponse> cdr = companies.stream().map(comp -> getCompanyDetails(comp))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, cdr);
	}

	@RequestMapping(value = "/add-company", method = RequestMethod.POST)
	public ResponseEntity<Void> addDriver(@Valid @RequestBody CompanyPayload companyPayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		Company company = new Company();
		company.setName(companyPayload.getName());
		company.setDescription(companyPayload.getDescription());
		company.setPhone(companyPayload.getPhone());
		company.setAddress(companyPayload.getAddress());
		company.setCreatedDate(new Date());
		company.setCreatedBy(Context.getAuthenticatedUser());

		hospitalRestCoreService.saveOrUpdateCompany(company);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/edit-company", method = RequestMethod.PUT)
	public ResponseEntity<Void> editDriver(@Valid @RequestBody CompanyPayload companyPayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		if (companyPayload.getUuid() != null) {
			Company company = hospitalRestCoreService.getCompanyByUuid(companyPayload.getUuid());
			if (company == null) {
				throw new ResourceNotFoundException(String.format(
						OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_COMPANY_UUID, companyPayload.getUuid()));
			}
			if (companyPayload.isRetired()) {
				company.setRetired(true);
				company.setRetiredDate(new Date());
				company.setRetiredBy(Context.getAuthenticatedUser());
			} else {
				company.setName(companyPayload.getName());
				company.setDescription(companyPayload.getDescription());
				company.setPhone(companyPayload.getPhone());
				company.setAddress(companyPayload.getAddress());
				company.setLastModifiedDate(new Date());
				company.setLastModifiedBy(Context.getAuthenticatedUser());
			}

			hospitalRestCoreService.saveOrUpdateCompany(company);

		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-companies", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCompanies(@RequestBody List<String> companyUuids, HttpServletResponse response,
			HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		for (String companyUuid : companyUuids) {
			Company company = hospitalRestCoreService.getCompanyByUuid(companyUuid);
			if (company == null) {
				throw new ResourceNotFoundException(
						String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_COMPANY_UUID, companyUuid));
			}

			company.setDeleted(true);
			company.setDeletedDate(new Date());
			company.setDeletedBy(Context.getAuthenticatedUser());

			hospitalRestCoreService.saveOrUpdateCompany(company);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public CompanyDetailsResponse getCompanyDetails(Company comp) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		CompanyDetailsResponse cdr = new CompanyDetailsResponse();
		cdr.setName(comp.getName());
		cdr.setAddress(comp.getAddress());
		cdr.setDescription(comp.getDescription());
		cdr.setPhone(comp.getPhone());
		cdr.setUuid(comp.getUuid());
		return cdr;
	}
}