package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugReceiptDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugReceiptDetailDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mujuzi Moses
 */
public class HibernateInventoryStoreDrugReceiptDetailDAO extends HibernateSingleClassDAO implements InventoryStoreDrugReceiptDetailDAO {

    public HibernateInventoryStoreDrugReceiptDetailDAO() {
        super(InventoryStoreDrugReceiptDetail.class);
    }

    @Override
    @Transactional
    public List<InventoryStoreDrugReceiptDetail> getStoreDrugReceiptDetailByReceiptId(Integer receiptId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "receiptDetail")
                .createAlias("receiptDetail.receipt", "receipt");
        criteria.add(Restrictions.eq("receipt.id", receiptId));
        return criteria.list();
    }
}
