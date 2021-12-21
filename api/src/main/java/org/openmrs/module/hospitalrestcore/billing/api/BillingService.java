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
package org.openmrs.module.hospitalrestcore.billing.api;

import java.util.Collection;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;

/**
 * This service exposes module's core functionality. It is a Spring managed bean
 * which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(AppointmentService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
public interface BillingService extends OpenmrsService {

	List<BillableService> getAllServices() throws APIException;

	BillableService saveBillableService(BillableService billableService) throws APIException;
	
	List<BillableService> saveBillableService(Collection<BillableService> billableServices) throws APIException;

	BillableService getServiceByConcept(Concept concept) throws APIException;

	OpdTestOrder saveOpdTestOrder(OpdTestOrder OpdTestOrder) throws APIException;
}
