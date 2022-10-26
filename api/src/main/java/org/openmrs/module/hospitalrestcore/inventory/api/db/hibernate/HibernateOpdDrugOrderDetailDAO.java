package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.OpdDrugOrderDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.OpdDrugOrderDetailDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateOpdDrugOrderDetailDAO extends HibernateSingleClassDAO implements OpdDrugOrderDetailDAO {

    public HibernateOpdDrugOrderDetailDAO() {
        super(OpdDrugOrderDetail.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpdDrugOrderDetail> getDrugOrderDetailByOrderId(Integer orderId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "detail")
                .createAlias("detail.opdDrugOrder", "order");
        criteria.add(Restrictions.eq("order.opdDrugOrderId", orderId))
                .add(Restrictions.eq("deleted", false));
        return criteria.list();
    }

    @Override
    @Transactional
    public List<OpdDrugOrderDetail> listAllDrugOrderDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
