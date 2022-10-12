package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderIssueDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugOrderIssueDetailDAO;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugOrderIssueDetailDAO extends HibernateSingleClassDAO implements InventoryStoreDrugOrderIssueDetailDAO {


    public HibernateInventoryStoreDrugOrderIssueDetailDAO() {
        super(InventoryStoreDrugOrderIssueDetail.class);
    }

    @Override
    public List<InventoryStoreDrugOrderIssueDetail> listAllInventoryStoreDrugOrderIssueDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
