package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.PatientIdentifier;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface PatientIdentifierDAO extends SingleClassDAO {

    List<PatientIdentifier> listAllPatientIdentifier() throws DAOException;

    PatientIdentifier getPatientIdentifierByPatientId(Integer patientId) throws DAOException;

    Integer getPatientIdByIdentifierString(String identifier) throws DAOException;

}
