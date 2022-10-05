package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreIssuePatientDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreIssuePatientDetailDAO;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreIssuePatientDetailDAO extends HibernateSingleClassDAO implements InventoryStoreIssuePatientDetailDAO {

    public HibernateInventoryStoreIssuePatientDetailDAO() {
        super(InventoryStoreIssuePatientDetail.class);
    }

    @Override
    public InventoryStoreIssuePatientDetail getInventoryStoreIssuePatientDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("deleted", false));
        return (InventoryStoreIssuePatientDetail) criteria.uniqueResult();
    }
}
