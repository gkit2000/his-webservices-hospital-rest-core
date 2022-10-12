package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.OpdDrugOrder;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface OpdDrugOrderDAO extends SingleClassDAO {

    List<OpdDrugOrder> getDrugOrderByOrderId(Integer orderId) throws DAOException;

    List<OpdDrugOrder> listAllDrugOrder() throws DAOException;
}
