package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugPatientDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugPatientDetailDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugPatientDetailDAO extends HibernateSingleClassDAO implements InventoryStoreDrugPatientDetailDAO {

    public HibernateInventoryStoreDrugPatientDetailDAO() {
        super(InventoryStoreDrugPatientDetail.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryStoreDrugPatientDetail> listAllInventoryStoreDrugPatientDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        return criteria.list();
    }
}
