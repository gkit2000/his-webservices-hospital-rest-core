package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.PersonName;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface PersonNameDAO extends SingleClassDAO {

    List<PersonName> listAllPersonName() throws DAOException;

    PersonName getPersonNameByPersonId(Integer personId) throws DAOException;

    PersonName getPersonNameByNameString(String name) throws DAOException;
}

