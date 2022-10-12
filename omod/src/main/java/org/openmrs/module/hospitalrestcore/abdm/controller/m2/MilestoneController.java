package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

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
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.abdm.AccessTokenPayload;
import org.openmrs.module.hospitalrestcore.abdm.controller.AbdmUtil;
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
@RequestMapping("/rest")
public class MilestoneController extends BaseRestController {

	Client client = Client.create();

	@RequestMapping(value = "/v0.5/users/auth/fetch-modes", method = RequestMethod.POST)
	public void getFetchModes(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthFetchModesPayload fetchModesPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource("https://dev.abdm.gov.in/gateway/v0.5/users/auth/fetch-modes");
		String payload = new Gson().toJson(fetchModesPayload);
		System.out.println("wwwwwwwrrrrrrrr-"+payload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("X-CM-ID", "sbx")
				.header("Authorization", "Bearer " + accessToken).post(String.class, payload);
		System.out.println("LLLLLLLLLLLLLLL-" + clientResponse);

		/*
		 * JSONParser parser = new JSONParser(); JSONObject clientResponseJson = null;
		 * 
		 * try { clientResponseJson = (JSONObject) parser.parse(clientResponse); } catch
		 * (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
		 * 
		 * new ObjectMapper().writeValue(out, clientResponseJson);
		 */
	}

	@RequestMapping(value = "/v0.5/users/auth/on-fetch-modes", method = RequestMethod.POST)
	public void getAuthFetch(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthOnFetchModesPayload authOnFetchModesPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		System.out.println("xxxxxxxxxxxxxxxx-" + out);
		System.out.println("yyyyyyyyyyyyyyy-" + request);
		System.out.println("zzzzzzzzzzzzzzz-" + response);
		System.out.println("aaaaaaaaaaaaaaa-" + authOnFetchModesPayload.getAuth().getModes().get(0));
		System.out.println("bbbbbbbbbbbbbb-" + request.getInputStream().read());
		System.out.println("cccccccccccccccc-" + response.getWriter());
		new ObjectMapper().writeValue(out, authOnFetchModesPayload);
	}

	@RequestMapping(value = "/v0.5/users/auth/init", method = RequestMethod.POST)
	public void getAuthInitCall(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthInitPayload authInitPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource("https://dev.abdm.gov.in/gateway/v0.5/users/auth/init");
		String payload = new Gson().toJson(authInitPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("X-CM-ID", "sbx")
				.header("Authorization", "Bearer " + accessToken).post(String.class, payload);
		System.out.println("LLLLLLLLLLLLLLL-" + clientResponse);

		/*
		 * JSONParser parser = new JSONParser(); JSONObject clientResponseJson = null;
		 * 
		 * try { clientResponseJson = (JSONObject) parser.parse(clientResponse); } catch
		 * (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
		 * 
		 * new ObjectMapper().writeValue(out, clientResponseJson);
		 */
	}

	@RequestMapping(value = "/v0.5​/users​/auth​/on-init", method = RequestMethod.POST)
	public void getAuthOnInitCall(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthOnInitPayload authOnInitPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		System.out.println("xxxxxxxxxxxxxxxx-" + out);
		System.out.println("yyyyyyyyyyyyyyy-" + request);
		System.out.println("zzzzzzzzzzzzzzz-" + response);
		System.out.println("aaaaaaaaaaaaaaa-" + authOnInitPayload.getAuth().getTransactionId());
		System.out.println("bbbbbbbbbbbbbb-" + request.getInputStream().read());
		System.out.println("cccccccccccccccc-" + response.getWriter());
		new ObjectMapper().writeValue(out, authOnInitPayload);
	}

	@RequestMapping(value = "/v0.5/users/auth/confirm", method = RequestMethod.POST)
	public void getAuthConfirm(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthConfirmPayload authConfirmPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource("https://dev.abdm.gov.in/gateway/v0.5/users/auth/confirm");
		String payload = new Gson().toJson(authConfirmPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("X-CM-ID", "sbx")
				.header("Authorization", "Bearer " + accessToken).post(String.class, payload);
		System.out.println("LLLLLLLLLLLLLLL-" + clientResponse);

		/*
		 * JSONParser parser = new JSONParser(); JSONObject clientResponseJson = null;
		 * 
		 * try { clientResponseJson = (JSONObject) parser.parse(clientResponse); } catch
		 * (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
		 * 
		 * new ObjectMapper().writeValue(out, clientResponseJson);
		 */
	}

	@RequestMapping(value = "/v0.5/users/auth/on-confirm", method = RequestMethod.POST)
	public void getAuthNotify(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthOnConfirmPayload authOnConfirmPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		System.out.println("xxxxxxxxxxxxxxxx-" + out);
		System.out.println("yyyyyyyyyyyyyyy-" + request);
		System.out.println("zzzzzzzzzzzzzzz-" + response);
		System.out.println("aaaaaaaaaaaaaaa-" + authOnConfirmPayload.getAuth());
		System.out.println("bbbbbbbbbbbbbb-" + request.getInputStream().read());
		System.out.println("cccccccccccccccc-" + response.getWriter());
		new ObjectMapper().writeValue(out, authOnConfirmPayload);
	}

	@RequestMapping(value = "/v0.5/users/auth/on-notify", method = RequestMethod.POST)
	public void getAuthOnNotify(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthOnNotifyPayload authOnNotifyPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource("https://dev.abdm.gov.in/gatewayv0.5/users/auth/on-notify");
		String payload = new Gson().toJson(authOnNotifyPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("X-CM-ID", "sbx")
				.header("Authorization", "Bearer " + accessToken).post(String.class, payload);
		System.out.println("LLLLLLLLLLLLLLL-" + clientResponse);

		/*
		 * JSONParser parser = new JSONParser(); JSONObject clientResponseJson = null;
		 * 
		 * try { clientResponseJson = (JSONObject) parser.parse(clientResponse); } catch
		 * (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
		 * 
		 * new ObjectMapper().writeValue(out, clientResponseJson);
		 */
	}

	@RequestMapping(value = "/v0.5/users/auth/notify", method = RequestMethod.POST)
	public void getAuthNotify(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody AuthNotifyPayload authNotifyPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		System.out.println("xxxxxxxxxxxxxxxx-" + out);
		System.out.println("yyyyyyyyyyyyyyy-" + request);
		System.out.println("zzzzzzzzzzzzzzz-" + response);
		System.out.println("aaaaaaaaaaaaaaa-" + authNotifyPayload.getAuth().getTransactionId());
		System.out.println("bbbbbbbbbbbbbb-" + request.getInputStream().read());
		System.out.println("cccccccccccccccc-" + response.getWriter());
		new ObjectMapper().writeValue(out, authNotifyPayload);
	}
	
	@RequestMapping(value = "/v0.5/links/link/add-contexts", method = RequestMethod.POST)
	public void getAuthOnNotifyy(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody LinkAddContextsPayload linkAddContextsPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		WebResource webResource = client.resource("https://dev.abdm.gov.in/gatewayv0.5/links/link/add-contexts");
		String payload = new Gson().toJson(linkAddContextsPayload);
		GlobalProperty clientId = Context.getAdministrationService().getGlobalPropertyObject("clientId");
		GlobalProperty clientSecret = Context.getAdministrationService().getGlobalPropertyObject("clientSecret");

		AccessTokenPayload tokenPayload = new AccessTokenPayload();
		tokenPayload.setClientId(clientId.getPropertyValue());
		tokenPayload.setClientSecret(clientSecret.getPropertyValue());

		JSONObject accessTokenJson = AbdmUtil.getAccessToken(tokenPayload);
		String accessToken = (String) accessTokenJson.get("accessToken");

		String clientResponse = webResource.type("application/json").header("X-CM-ID", "sbx")
				.header("Authorization", "Bearer " + accessToken).post(String.class, payload);
		System.out.println("LLLLLLLLLLLLLLL-" + clientResponse);

		/*
		 * JSONParser parser = new JSONParser(); JSONObject clientResponseJson = null;
		 * 
		 * try { clientResponseJson = (JSONObject) parser.parse(clientResponse); } catch
		 * (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
		 * 
		 * new ObjectMapper().writeValue(out, clientResponseJson);
		 */
	}

	@RequestMapping(value = "/v0.5/links/link/on-add-contexts", method = RequestMethod.POST)
	public void getLinkOnAddContexts(HttpServletResponse response, HttpServletRequest request,
			@Valid @RequestBody LinkOnAddContextsPayload linkOnAddContextsPayload)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();
		System.out.println("xxxxxxxxxxxxxxxx-" + out);
		System.out.println("yyyyyyyyyyyyyyy-" + request);
		System.out.println("zzzzzzzzzzzzzzz-" + response);
		System.out.println("aaaaaaaaaaaaaaa-" + linkOnAddContextsPayload);
		System.out.println("bbbbbbbbbbbbbb-" + request.getInputStream().read());
		System.out.println("cccccccccccccccc-" + response.getWriter());
		new ObjectMapper().writeValue(out, linkOnAddContextsPayload);
	}
}
