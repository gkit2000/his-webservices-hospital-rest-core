package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIssueDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugIssueDetailDAO;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugIssueDetailDAO extends HibernateSingleClassDAO implements InventoryStoreDrugIssueDetailDAO {

    public HibernateInventoryStoreDrugIssueDetailDAO() {
        super(InventoryStoreDrugIssueDetail.class);
    }

    @Override
    public List<InventoryStoreDrugIssueDetail> listAllInventoryStoreDrugIssueDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
