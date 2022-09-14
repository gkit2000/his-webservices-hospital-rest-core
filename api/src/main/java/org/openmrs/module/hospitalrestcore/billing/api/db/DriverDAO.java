/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Driver;

/**
 * @author Ghanshyam
 *
 */
public interface DriverDAO extends SingleClassDAO {

	public List<Driver> searchDriver(String searchText) throws DAOException;

	public List<Driver> getAllDriver() throws DAOException;
	
	public Driver getDriverByUuid(String uuid) throws DAOException;

}
