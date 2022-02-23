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

	public static final String VALIDATION_ERROR_NOT_VALID_UUID = "Patient UUID is not valid";

	public static final String VALIDATION_ERROR_AADHAR = "AADHAR number has been entered already, please enter the different one";
	
	public static final String VALIDATION_ERROR_LOCATION = "Invalid location uuid %s";
	
	public static final String VALIDATION_ERROR_CATEGORY_LOCATION = "Price category is not mapped for location %s";
	
	public static final String VALIDATION_ERROR_PRICE_CATEGORY = "Price category concept uuid %s is not valid";
	
	public static final String VALIDATION_ERROR_BILLABLE_SERVICE_PROCEDURE = "Billable service for procedure %s and price category %s is not configured.";
	
	public static final String VALIDATION_ERROR_BILLABLE_SERVICE_INVESTIGATION = "Billable service for investigation %s and price category %s is not configured.";

	public static final String VALIDATION_ERROR_NOT_VALID_OPD_ORDER_ID = "Opd Order Id is not valid";
	
	public static final String APPOINTMENT_REASON_NEW_REGISTRATION = "New Registration";
	
	public static final String APPOINTMENT_REASON_ONLINE_APPOINTMENT = "Online Appointment";
	
	public static final String ENCOUNTER_TYPE_CHECK_IN = "Check In";
}
