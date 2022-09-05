package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugTransactionDetailDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugTransactionDetailDAO extends HibernateSingleClassDAO implements InventoryStoreDrugTransactionDetailDAO {

    public HibernateInventoryStoreDrugTransactionDetailDAO() {
        super(InventoryStoreDrugTransactionDetail.class);
    }

    @Override
    public List<InventoryStoreDrugTransactionDetail> listAllStoreDrugTransactionDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("retired", false));
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryStoreDrugTransactionDetail getDrugTransactionDetailByUuidString(String uuid) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("uuid", uuid));
        return (InventoryStoreDrugTransactionDetail) criteria.uniqueResult();
    }
}
