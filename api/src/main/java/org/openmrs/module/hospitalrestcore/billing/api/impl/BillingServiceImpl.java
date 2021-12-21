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
package org.openmrs.module.hospitalrestcore.billing.api.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.api.BillingService;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillableServiceDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.OpdTestOrderDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * It is a default implementation of {@link BillingService}.
 */

public class BillingServiceImpl extends BaseOpenmrsService implements BillingService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private BillableServiceDAO billableServiceDAO;

	private OpdTestOrderDAO opdTestOrderDAO;

	/**
	 * @return the billableServiceDAO
	 */
	public BillableServiceDAO getBillableServiceDAO() {
		return billableServiceDAO;
	}

	/**
	 * @param billableServiceDAO the billableServiceDAO to set
	 */
	public void setBillableServiceDAO(BillableServiceDAO billableServiceDAO) {
		this.billableServiceDAO = billableServiceDAO;
	}

	/**
	 * @return the opdTestOrderDAO
	 */
	public OpdTestOrderDAO getOpdTestOrderDAO() {
		return opdTestOrderDAO;
	}

	/**
	 * @param opdTestOrderDAO the opdTestOrderDAO to set
	 */
	public void setOpdTestOrderDAO(OpdTestOrderDAO opdTestOrderDAO) {
		this.opdTestOrderDAO = opdTestOrderDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BillableService> getAllServices() throws APIException {
		return getBillableServiceDAO().getAllServices();
	}

	@Override
	@Transactional
	public BillableService saveBillableService(BillableService billableService) throws APIException {
		// ValidateUtil.validate(appointmentType);
		return (BillableService) getBillableServiceDAO().saveOrUpdate(billableService);
	}
	
	@Override
	@Transactional
	public List<BillableService> saveBillableService(Collection<BillableService> billableServices) throws APIException {
		return (List<BillableService>) getBillableServiceDAO().saveOrUpdate(billableServices);
	}

	@Override
	@Transactional(readOnly = true)
	public BillableService getServiceByConcept(Concept concept) throws APIException {
		return getBillableServiceDAO().getServiceByConcept(concept);
	}

	@Override
	@Transactional
	public OpdTestOrder saveOpdTestOrder(OpdTestOrder opdTestOrder) throws APIException {
		return (OpdTestOrder) getOpdTestOrderDAO().saveOrUpdate(opdTestOrder);
	}

}
