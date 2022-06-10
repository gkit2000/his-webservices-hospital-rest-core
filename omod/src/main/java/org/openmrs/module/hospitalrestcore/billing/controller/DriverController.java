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
import org.openmrs.module.hospitalrestcore.billing.Driver;
import org.openmrs.module.hospitalrestcore.billing.DriverDetailsResponse;
import org.openmrs.module.hospitalrestcore.billing.DriverPayload;
import org.openmrs.module.hospitalrestcore.billing.DriverSearchPayload;
import org.openmrs.module.hospitalrestcore.controller.PulseUtil;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/driver")
public class DriverController extends BaseRestController {

	@RequestMapping(value = "/searchDriver ", method = RequestMethod.POST)
	public void searchDriver(@Valid @RequestBody DriverSearchPayload driverSearchPayload, HttpServletResponse response,
			HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		List<Driver> drivers = hospitalRestCoreService.searchDriver(driverSearchPayload.getSearchText());
		List<DriverDetailsResponse> ddrs = drivers.stream().map(driv -> getDriverDetails(driv))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, ddrs);
	}

	@RequestMapping(value = "/allDrivers", method = RequestMethod.GET)
	public void getAllDrivers(HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		List<Driver> drivers = hospitalRestCoreService.getAllDriver();
		List<DriverDetailsResponse> ddrs = drivers.stream().map(driv -> getDriverDetails(driv))
				.collect(Collectors.toList());

		new ObjectMapper().writeValue(out, ddrs);
	}

	@RequestMapping(value = "/addDriver", method = RequestMethod.POST)
	public ResponseEntity<Void> addDriver(@Valid @RequestBody DriverPayload driverPayload, HttpServletResponse response,
			HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		Driver driver = new Driver();
		driver.setName(driverPayload.getName());
		driver.setAddress(driverPayload.getAddress());
		driver.setDescription(driverPayload.getDescription());
		driver.setPhone(driverPayload.getPhone());
		driver.setCreatedDate(new Date());
		driver.setCreatedBy(Context.getAuthenticatedUser());

		hospitalRestCoreService.saveOrUpdateDriver(driver);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/editDriver", method = RequestMethod.PUT)
	public ResponseEntity<Void> editDriver(@Valid @RequestBody DriverPayload driverPayload,
			HttpServletResponse response, HttpServletRequest request)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

		if (driverPayload.getUuid() != null) {
			Driver driver = hospitalRestCoreService.getDriverByUuid(driverPayload.getUuid());
			if (driver == null) {
				throw new ResourceNotFoundException(String.format(
						OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRIVER_UUID, driverPayload.getUuid()));
			}
			if (driverPayload.isRetired()) {
				driver.setRetired(true);
				driver.setRetiredDate(new Date());
				driver.setRetiredBy(Context.getAuthenticatedUser());
			} else {
				driver.setName(driverPayload.getName());
				driver.setAddress(driverPayload.getAddress());
				driver.setDescription(driverPayload.getDescription());
				driver.setPhone(driverPayload.getPhone());
				driver.setLastModifiedDate(new Date());
				driver.setLastModifiedBy(Context.getAuthenticatedUser());
			}

			hospitalRestCoreService.saveOrUpdateDriver(driver);

		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public DriverDetailsResponse getDriverDetails(Driver driver) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DriverDetailsResponse ddr = new DriverDetailsResponse();
		ddr.setDriverId(driver.getDriverId());
		ddr.setName(driver.getName());
		ddr.setAddress(driver.getAddress());
		ddr.setDescription(driver.getDescription());
		ddr.setCreatedDate(formatter.format(driver.getCreatedDate()));
		ddr.setCreatedBy(PulseUtil.getName(driver.getCreatedBy().getPerson()));
		ddr.setLastModifiedDate(formatter.format(driver.getLastModifiedDate()));
		ddr.setLastModifiedBy(PulseUtil.getName(driver.getLastModifiedBy().getPerson()));
		ddr.setRetired(driver.getRetired());
		ddr.setRetiredDate(formatter.format(driver.getRetiredDate()));
		ddr.setRetiredBy(PulseUtil.getName(driver.getRetiredBy().getPerson()));
		ddr.setUuid(driver.getUuid());
		return ddr;
	}
}