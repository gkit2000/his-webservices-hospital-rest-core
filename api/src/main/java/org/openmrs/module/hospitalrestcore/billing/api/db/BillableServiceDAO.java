/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.BillableService;

/**
 * @author Ghanshyam
 *
 */
public interface BillableServiceDAO extends SingleClassDAO {

	public List<BillableService> getAllServices() throws DAOException;

	public List<BillableService> getServicesByPriceCategory(Concept priceCategory) throws DAOException;

	public BillableService getServicesByServiceConceptAndPriceCategory(Concept serviceConcept, Concept priceCategory)
			throws DAOException;

}
