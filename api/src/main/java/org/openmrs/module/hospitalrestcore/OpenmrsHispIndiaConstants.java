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
public class OpenmrsHispIndiaConstants {
	private static final Logger log = LoggerFactory.getLogger(OpenmrsHispIndiaConstants.class);

	private OpenmrsHispIndiaConstants() {
		throw new IllegalStateException("Utility class used for constants");
	}

	public static final String VALIDATION_ERROR_NOT_VALID_UUID = "Patient UUID is not valid";

	public static final String VALIDATION_ERROR_AADHAR = "AADHAR number has been entered already, please enter the different one";
	
	public static final String VALIDATION_ERROR_NOT_VALID_OPD_ORDER_ID = "Opd Order Id is not valid";
}
