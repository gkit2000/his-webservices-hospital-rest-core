/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
/**
 * 
 */
package org.openmrs.module.hospitalrestcore.visit;

/**
 * @author Ghanshyam
 */
public class PatientVisitDetails {
	
	Integer visitId;
	
	String visitUuid;
	
	/**
	 * @return the visitId
	 */
	public Integer getVisitId() {
		return visitId;
	}
	
	/**
	 * @param visitId the visitId to set
	 */
	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}
	
	/**
	 * @return the visitUuid
	 */
	public String getVisitUuid() {
		return visitUuid;
	}
	
	/**
	 * @param visitUuid the visitUuid to set
	 */
	public void setVisitUuid(String visitUuid) {
		this.visitUuid = visitUuid;
	}
	
}
