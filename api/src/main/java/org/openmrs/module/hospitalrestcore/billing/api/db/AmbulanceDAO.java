/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Ambulance;

/**
 * @author Ghanshyam
 *
 */
public interface AmbulanceDAO extends SingleClassDAO {

	public List<Ambulance> searchAmbulance(String searchText) throws DAOException;

	public List<Ambulance> getAllAmbulance() throws DAOException;

	public Ambulance getAmbulanceByUuid(String uuid) throws DAOException;
}
