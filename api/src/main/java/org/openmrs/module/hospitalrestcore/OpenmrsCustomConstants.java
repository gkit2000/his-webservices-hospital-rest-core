/**
 * 
 */
package org.openmrs.module.hospitalrestcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ghanshyam
 *
 */
public final class OpenmrsCustomConstants {
	private static final Logger log = LoggerFactory.getLogger(OpenmrsCustomConstants.class);

	private OpenmrsCustomConstants() {
		throw new IllegalStateException("Utility class used for constants");
	}

	public static final String VALIDATION_ERROR_NOT_VALID_PATIENT_UUID = "Patient UUID %s is not valid";

	public static final String VALIDATION_ERROR_AADHAR = "AADHAR number has been entered already, please enter the different one";

	public static final String VALIDATION_ERROR_LOCATION = "Invalid location uuid %s";

	public static final String VALIDATION_ERROR_CATEGORY_LOCATION = "Price category is not mapped for location %s";

	public static final String VALIDATION_ERROR_PRICE_CATEGORY = "Price category concept uuid %s is not valid";

	public static final String VALIDATION_ERROR_BILLABLE_SERVICE_PROCEDURE = "Billable service for procedure %s and price category %s is not configured.";

	public static final String VALIDATION_ERROR_BILLABLE_SERVICE_INVESTIGATION = "Billable service for investigation %s and price category %s is not configured.";

	public static final String VALIDATION_ERROR_NOT_VALID_OPD_ORDER_ID = "Opd Order Id %S is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_SERVICE_CONCEPT_UUID = "Service concept uuid %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_QUANTITY = "Not valid quantity for service %s.Please enter valid quantity";

	// general ward prices category concept uuid
	public static final String GENERAL_WARD_PRICES = "073c598b-9e66-4c1d-a833-6557567a599b";

	public static final String APPOINTMENT_REASON_NEW_REGISTRATION = "New Registration";

	public static final String APPOINTMENT_REASON_ONLINE_APPOINTMENT = "Online Appointment";

	public static final String ENCOUNTER_TYPE_CHECK_IN = "Check In";

	public static final String VALIDATION_ERROR_CONSENT_UUID = "Invalid consent uuid %s";

	public static final String VALIDATION_ERROR_NOT_VALID_CONSENT_TYPE_CONCEPT_UUID = "Consent type concept uuid %s is not valid";

	public static final String ACCESS_TOKEN_URL = "https://dev.abdm.gov.in/gateway/v0.5/sessions";

	public static final String GENERATE_OTP_URL = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/generateOtp";

	public static final String VERIFY_OTP = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/verifyOTP";

	public static final String GENERATE_MOBILE_OTP = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/generateMobileOTP";

	public static final String VERIFY_MOBILE_OTP = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/verifyMobileOTP";

	public static final String CREATE_HEALTH_ID_WITH_PRE_VERIFIED = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/createHealthIdWithPreVerified";

	public static final String SEARCH_BY_HEALTH_ID = "https://healthidsbx.abdm.gov.in/api/v1/search/searchByHealthId";
}
