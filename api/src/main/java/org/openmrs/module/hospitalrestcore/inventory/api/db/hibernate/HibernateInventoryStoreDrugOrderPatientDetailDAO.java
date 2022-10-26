package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderPatient;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderPatientDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugOrderPatientDetailDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugOrderPatientDetailDAO extends HibernateSingleClassDAO
        implements InventoryStoreDrugOrderPatientDetailDAO {

    public HibernateInventoryStoreDrugOrderPatientDetailDAO() {
        super(InventoryStoreDrugOrderPatientDetail.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryStoreDrugOrderPatientDetail> getDrugOrderPatientDetail(InventoryStoreDrugOrderPatient patient,
            String date) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("drugOrderPatient", patient))
                .add(Restrictions.eq("orderDate", date));
        return criteria.list();
    }

}
