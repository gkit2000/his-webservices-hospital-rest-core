/**
 * 
 */
package org.openmrs.module.hospitalrestcore.controller;

import org.openmrs.Patient;
import org.openmrs.Person;

/**
 * @author Ghanshyam
 *
 */
public class PulseUtil {

	public static String getName(Person person) {
		String name = "";
		if (person.getGivenName() != null) {
			name = person.getGivenName();
		}
		if (person.getMiddleName() != null) {
			name = name + " " + person.getMiddleName();
		}
		if (person.getFamilyName() != null) {
			name = name + " " + person.getFamilyName();
		}
		return name;
	}
	
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
