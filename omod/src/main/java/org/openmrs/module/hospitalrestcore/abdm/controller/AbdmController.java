package org.openmrs.module.hospitalrestcore.abdm.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.abdm.AccessTokenPayload;
import org.openmrs.module.hospitalrestcore.abdm.CreateHealthIdWithPreVerifiedFromAadhaarPayload;
import org.openmrs.module.hospitalrestcore.abdm.GenerateAadhaarOtpPayload;
import org.openmrs.module.hospitalrestcore.abdm.GenerateMobileOTPForAadhaarPayload;
import org.openmrs.module.hospitalrestcore.abdm.HealthIdSearchPayload;
import org.openmrs.module.hospitalrestcore.abdm.VerifyOtpPayload;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1)
public class AbdmController extends BaseRestController {

	Client client = Client.create();

	@RequestMapping(value = "/gateway/v0.5/sessions", method = RequestMethod.POST)
	public void generateAccessToken(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AccessTokenPayload tokenPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		JSONObject clientResponseJson = AbdmUtil.getAccessToken(tokenPayload);

		new ObjectMapper().writeValue(out, clientResponseJson);
	}

	@RequestMapping(value = "/registration/aadhaar/generateOtp", method = RequestMethod.POST)
	public void generateAadhaarOtp(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody GenerateAadhaarOtpPayload generateAadhaarOtpPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource(OpenmrsCustomConstants.GENERATE_OTP_URL);
		String payload = new Gson().toJson(generateAadhaarOtpPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("Authorization", "Bearer " + accessToken)
				.post(String.class, payload);

		JSONParser parser = new JSONParser();
		JSONObject clientResponseJson = null;

		try {
			clientResponseJson = (JSONObject) parser.parse(clientResponse);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		new ObjectMapper().writeValue(out, clientResponseJson);
	}

	@RequestMapping(value = "/registration/aadhaar/verifyOTP", method = RequestMethod.POST)
	public void verifyAadhaarOtp(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody VerifyOtpPayload verifyOtpPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource(OpenmrsCustomConstants.VERIFY_OTP);
		String payload = new Gson().toJson(verifyOtpPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("Authorization", "Bearer " + accessToken)
				.post(String.class, payload);

		JSONParser parser = new JSONParser();
		JSONObject clientResponseJson = null;

		try {
			clientResponseJson = (JSONObject) parser.parse(clientResponse);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		new ObjectMapper().writeValue(out, clientResponseJson);
	}

	@RequestMapping(value = "/registration/aadhaar/generateMobileOTP", method = RequestMethod.POST)
	public void generateMobileOTPForAadhaar(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody GenerateMobileOTPForAadhaarPayload generateMobileOTPForAadhaarPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource(OpenmrsCustomConstants.GENERATE_MOBILE_OTP);
		String payload = new Gson().toJson(generateMobileOTPForAadhaarPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("Authorization", "Bearer " + accessToken)
				.post(String.class, payload);

		JSONParser parser = new JSONParser();
		JSONObject clientResponseJson = null;

		try {
			clientResponseJson = (JSONObject) parser.parse(clientResponse);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		new ObjectMapper().writeValue(out, clientResponseJson);
	}

	@RequestMapping(value = "/registration/aadhaar/verifyMobileOTP", method = RequestMethod.POST)
	public void verifyMobileOTPForAadhaar(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody VerifyOtpPayload verifyOtpPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource(OpenmrsCustomConstants.VERIFY_MOBILE_OTP);
		String payload = new Gson().toJson(verifyOtpPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("Authorization", "Bearer " + accessToken)
				.post(String.class, payload);

		JSONParser parser = new JSONParser();
		JSONObject clientResponseJson = null;

		try {
			clientResponseJson = (JSONObject) parser.parse(clientResponse);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		new ObjectMapper().writeValue(out, clientResponseJson);
	}

	@RequestMapping(value = "/registration/aadhaar/createHealthIdWithPreVerified", method = RequestMethod.POST)
	public void createHealthIdWithPreVerifiedFromAadhaar(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody CreateHealthIdWithPreVerifiedFromAadhaarPayload createHealthIdWithPreVerifiedFromAadhaarPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource(OpenmrsCustomConstants.CREATE_HEALTH_ID_WITH_PRE_VERIFIED);
		String payload = new Gson().toJson(createHealthIdWithPreVerifiedFromAadhaarPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("Authorization", "Bearer " + accessToken)
				.post(String.class, payload);

		JSONParser parser = new JSONParser();
		JSONObject clientResponseJson = null;

		try {
			clientResponseJson = (JSONObject) parser.parse(clientResponse);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		new ObjectMapper().writeValue(out, clientResponseJson);
	}

	@RequestMapping(value = "/search/searchByHealthId", method = RequestMethod.POST)
	public void searchHealthId(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody HealthIdSearchPayload healthIdSearchPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource(OpenmrsCustomConstants.SEARCH_BY_HEALTH_ID);
		String payload = new Gson().toJson(healthIdSearchPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("Authorization", "Bearer " + accessToken)
				.post(String.class, payload);

		JSONParser parser = new JSONParser();
		JSONObject clientResponseJson = null;

		try {
			clientResponseJson = (JSONObject) parser.parse(clientResponse);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		new ObjectMapper().writeValue(out, clientResponseJson);
	}
}
