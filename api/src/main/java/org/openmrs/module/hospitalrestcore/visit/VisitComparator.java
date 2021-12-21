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

import java.util.Comparator;

import org.openmrs.Visit;

/**
 * @author Ghanshyam
 */
public class VisitComparator implements Comparator<Visit> {

	@Override
	public int compare(Visit v1, Visit v2) {
		// return v1.getVisitId() < v2.getVisitId() ? -1 : v1.getVisitId() == v2.getVisitId() ? 0 : 1;
		if (v1.getVisitId() == v2.getVisitId()) {
			return 0;
		} else if (v1.getVisitId() < v2.getVisitId()) {
			return 1;
		} else {
			return -1;
		}
	}
}
