/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openmrs.module.hospitalrestcore.abdm.AccessTokenPayload;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * @author Ghanshyam
 *
 */
public class AbdmUtil {
	static Client client = Client.create();

	public static JSONObject getAccessToken(AccessTokenPayload accessTokenPayload) {
		WebResource webResource = client.resource("https://dev.abdm.gov.in/gateway/v0.5/sessions");
		String payload = new Gson().toJson(accessTokenPayload);

		String clientResponse = webResource.type("application/json").post(String.class, payload);

		JSONParser parser = new JSONParser();
		JSONObject clientResponseJson = null;

		try {
			clientResponseJson = (JSONObject) parser.parse(clientResponse);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		return clientResponseJson;
	}
}
