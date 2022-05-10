/**
 * 
 */
package org.openmrs.module.hospitalrestcore.controller;

import org.openmrs.Patient;

/**
 * @author Ghanshyam
 *
 */
public class PulseUtil {

	public static String getName(Patient patient) {
		String name = "";
		if (patient.getGivenName() != null) {
			name = patient.getGivenName();
		}
		if (patient.getMiddleName() != null) {
			name = name + " " + patient.getMiddleName();
		}
		if (patient.getFamilyName() != null) {
			name = name + " " + patient.getFamilyName();
		}
		return name;
	}

}
