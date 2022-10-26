package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptFormDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryReceiptFormDetailDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryReceiptFormDetailDAO extends HibernateSingleClassDAO implements InventoryReceiptFormDetailDAO {


    public HibernateInventoryReceiptFormDetailDAO() {
        super(InventoryReceiptFormDetail.class);
    }

    @Override
    @Transactional
    public List<InventoryReceiptFormDetail> listAllInventoryReceiptFormDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
