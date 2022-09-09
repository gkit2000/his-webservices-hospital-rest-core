package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryDrugDAO;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryDrugDAO extends HibernateSingleClassDAO implements InventoryDrugDAO {

    public HibernateInventoryDrugDAO() {
        super(InventoryDrug.class);
    }

    @Override
    public List<InventoryDrug> listAllInventoryDrug() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("retired", false));
        return criteria.list();
    }
}
