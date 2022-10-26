package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderPatient;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderPatientDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugOrderPatientDetailDAO extends SingleClassDAO {

    List<InventoryStoreDrugOrderPatientDetail> getDrugOrderPatientDetail(InventoryStoreDrugOrderPatient patient,
            String date) throws DAOException;

}
