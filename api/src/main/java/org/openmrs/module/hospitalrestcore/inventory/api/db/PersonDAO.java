package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.Person;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;

/**
 * @author Mujuzi Moses
 *
 */

public interface PersonDAO extends SingleClassDAO {

    Person getPersonByPersonId(Integer personId) throws DAOException;

}
