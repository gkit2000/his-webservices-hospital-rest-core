package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.OpdDrugOrder;
import org.openmrs.module.hospitalrestcore.inventory.OpdDrugOrderDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.OpdDrugOrderDAO;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateOpdDrugOrderDAO extends HibernateSingleClassDAO implements OpdDrugOrderDAO {

    public HibernateOpdDrugOrderDAO() {
        super(OpdDrugOrder.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpdDrugOrder> getDrugOrderByOrderId(Integer orderId) throws DAOException {
        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("opdDrugOrderId", orderId));

        List<OpdDrugOrder> orders = criteria.list();
        List<OpdDrugOrder> drugOrders = new ArrayList<OpdDrugOrder>();

        for (OpdDrugOrder o : orders) {
            List<OpdDrugOrderDetail> details = hospitalRestCoreService.getDrugOrderDetailByOrderId(o.getOpdDrugOrderId());
            if (!CollectionUtils.isEmpty(details))
                drugOrders.add(o);
        }

        return drugOrders;
    }

    @Override
    @Transactional
    public List<OpdDrugOrder> listAllDrugOrder() throws DAOException {
        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        List<OpdDrugOrder> orders = criteria.list();
        List<OpdDrugOrder> drugOrders = new ArrayList<OpdDrugOrder>();

        for (OpdDrugOrder o : orders) {
            List<OpdDrugOrderDetail> details = hospitalRestCoreService.getDrugOrderDetailByOrderId(o.getOpdDrugOrderId());
            if (!CollectionUtils.isEmpty(details))
                drugOrders.add(o);
        }

        return drugOrders;
    }

}
