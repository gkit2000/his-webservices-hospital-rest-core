package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;

/**
 * @author Mujuzi Moses
 *
 */

public interface PatientDAO extends SingleClassDAO {

    Patient getPatientByPatientId(Integer patientId) throws DAOException;

}
