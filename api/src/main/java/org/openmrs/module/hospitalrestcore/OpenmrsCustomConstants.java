/**
 * 
 */
package org.openmrs.module.hospitalrestcore;

import java.util.Arrays;
import java.util.List;

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

	public static final String VALIDATION_ERROR_BILLABLE_SERVICE = "Billable service %s and price category %s is not configured.";

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

	public static final String PATIENT_IDENTIFIER_TYPE = "05a29f94-c0ed-11e2-94be-8c13b969e334";

	public static final String PATIENT_CATEGORY = "80c68ebe-d696-4b8e-8aa0-53018f8e5d7b";

	public static final List<String> PATIENT_SUBCATEGORY1 = Arrays.asList("f494e0a2-a6d0-471b-bec5-fae8cc082ef8",
			"7ac406bc-f9bd-42ff-a12e-1b7d0b59096e", "bec1304d-f522-47d8-b209-4572c5628f87");

	public static final List<String> PATIENT_SUBCATEGORY2 = Arrays.asList("81216581-8155-4abe-89c8-ab45afdcd77c",
			"2e88efe2-b4d4-46d4-810b-a0e6d0c0004e", "63772745-4658-4db6-864c-d25dd6319ebd",
			"9fcbe4f4-36bb-455e-80a9-979341b72a68", "38caa20b-b767-4074-868f-5e858de0f38a",
			"2a289585-5ec6-4113-86f9-b4e16df3abf1", "8c753c94-dc67-4daf-acf5-887279e236f2",
			"18b13ded-5f66-43be-ba62-26baf8208f77", "f59437df-9878-4e7a-9fec-c1f77d337d17");
	
	public static final String MLC = "ecd9f478-40ac-4716-b63f-ff2dbb2bfa74";
	
	public static final String MLC_SUBCATEGORY = "70f8d129-3136-4d2e-bdee-05f88cd33f13";

	public static final String DRUG_ERROR_NOT_ENOUGH_DRUG_QUANTITY = "Current quantity of drug %s is not enough";
	
	public static final String VALIDATION_ERROR_NOT_VALID_BILL_ITEM_ID = "Bill item id %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_BILL_ITEM_ID_AND_BILL_ID = "Bill item id %s not corresponds to bill id %s";
	
	public static final String VALIDATION_ERROR_NOT_VALID_DRIVER_UUID = "Driver uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_AMBULANCE_UUID = "Ambulance uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_ID_CARD_TYPE_CONCEPT = "Id Card Type concept uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_ROLE_UUID = "Role uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_PARENT_UUID = "Parent uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_INVENTORY_STORE_UUID = "Inventory Store uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY_UUID = "Drug category uuid %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_DRUG = "Drug %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_RECEIPT_FORM = "Receipt form for drug %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_FORMULATION = "Drug formulation %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_INDENT = "Drug indent %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION = "Drug transaction %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION_DETAIL = "Drug transaction detail %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_PATIENT_IDENTIFIER = "Patient Identifier %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_BILL_NO = "Bill Number %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_PATIENT_IDENTIFIER_OR_NAME = "Patient Identifier or Name %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_PERSON_NAME = "Person Name %s is not valid";

	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_UNIT_UUID = "Drug unit uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_FORMULATION_UUID = "Drug formulation uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_INVENTORY_DRUG_UUID = "Inventory Drug uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_NOT_VALID_DRUG_UUID = "Drug uuid %s is not valid";

	public static final String ACCESS_TOKEN_URL = "https://dev.abdm.gov.in/gateway/v0.5/sessions";

	public static final String GENERATE_OTP_URL = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/generateOtp";

	public static final String VERIFY_OTP = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/verifyOTP";

	public static final String GENERATE_MOBILE_OTP = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/generateMobileOTP";

	public static final String VERIFY_MOBILE_OTP = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/verifyMobileOTP";

	public static final String CREATE_HEALTH_ID_WITH_PRE_VERIFIED = "https://healthidsbx.abdm.gov.in/api/v1/registration/aadhaar/createHealthIdWithPreVerified";

	public static final String SEARCH_BY_HEALTH_ID = "https://healthidsbx.abdm.gov.in/api/v1/search/searchByHealthId";
}
