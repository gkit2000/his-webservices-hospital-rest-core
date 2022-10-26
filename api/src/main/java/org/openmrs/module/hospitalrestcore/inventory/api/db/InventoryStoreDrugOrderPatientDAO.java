package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderPatient;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugOrderPatientDAO extends SingleClassDAO {

    Integer countDrugOrderPatient(String identifierOrName, String date) throws DAOException;

    List<InventoryStoreDrugOrderPatient> listDrugOrderPatient(String identifierOrName, String date, int min, int max) throws DAOException;

    InventoryStoreDrugOrderPatient getDrugOrderPatientByIdentifier(String identifier, String date) throws DAOException;

}
