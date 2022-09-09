package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransaction;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugTransactionDAO;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugTransactionDAO extends HibernateSingleClassDAO implements InventoryStoreDrugTransactionDAO {

    public HibernateInventoryStoreDrugTransactionDAO() {
        super(InventoryStoreDrugTransaction.class);
    }

    @Override
    public List<InventoryStoreDrugTransaction> listAllInventoryStoreDrugTransaction() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("retired", false))
                .add(Restrictions.eq("retired", false));
        return criteria.list();
    }
}
