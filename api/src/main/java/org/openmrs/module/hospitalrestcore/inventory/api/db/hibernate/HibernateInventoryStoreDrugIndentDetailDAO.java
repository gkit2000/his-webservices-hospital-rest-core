package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIndentDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugIndentDetailDAO;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugIndentDetailDAO extends HibernateSingleClassDAO implements InventoryStoreDrugIndentDetailDAO {

    public HibernateInventoryStoreDrugIndentDetailDAO() {
        super(InventoryStoreDrugIndentDetail.class);
    }

    @Override
    public List<InventoryStoreDrugIndentDetail> listAllInventoryStoreDrugIndentDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
