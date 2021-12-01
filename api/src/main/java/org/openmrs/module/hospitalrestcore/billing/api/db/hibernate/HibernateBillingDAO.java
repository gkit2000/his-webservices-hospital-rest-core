/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillingDAO;

public class HibernateBillingDAO extends HibernateSingleClassDAO implements BillingDAO {

	protected HibernateBillingDAO() {
		super(OpdTestOrder.class);
	}

}
