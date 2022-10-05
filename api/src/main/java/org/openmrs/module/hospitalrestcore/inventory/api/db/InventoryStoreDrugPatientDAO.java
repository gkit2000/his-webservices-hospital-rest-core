package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugPatient;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugPatientDAO extends SingleClassDAO {

    public List<InventoryStoreDrugPatient> listAllInventoryStoreDrugPatient() throws DAOException;

    public InventoryStoreDrugPatient getInventoryStoreDrugPatientByIdentifier(String identifier) throws DAOException;

    public InventoryStoreDrugPatient getInventoryStoreDrugPatientById(Integer id) throws DAOException;

    public Integer countStoreDrugPatient(Integer storeId, String identifierOrName, Integer billNo, String fromDate,
            String toDate) throws DAOException;

    public List<InventoryStoreDrugPatient> listStoreDrugPatient(Integer storeId, String identifierOrName, Integer billNo,
            String fromDate, String toDate, int min, int max) throws DAOException;
}
