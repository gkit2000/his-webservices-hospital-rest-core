package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.OpdDrugOrderDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface OpdDrugOrderDetailDAO extends SingleClassDAO {

    List<OpdDrugOrderDetail> getDrugOrderDetailByOrderId(Integer orderId) throws DAOException;

    List<OpdDrugOrderDetail> listAllDrugOrderDetail() throws DAOException;
}
