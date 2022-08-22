package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptForm;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryReceiptFormDAO;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryReceiptFormDAO extends HibernateSingleClassDAO implements InventoryReceiptFormDAO {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    SimpleDateFormat formattterExt = new SimpleDateFormat("dd/MM/yyyy");

    public HibernateInventoryReceiptFormDAO() {
        super(InventoryReceiptForm.class);
    }


    @Override
    @Transactional(readOnly = true)
    public InventoryReceiptForm getInventoryReceiptFormByUuidString(String uuid) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("uuid", uuid));
        return (InventoryReceiptForm) criteria.uniqueResult();
    }

    @Override
    public List<InventoryReceiptForm> listAllInventoryReceiptForm() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("retired", false));
        return criteria.list();
    }
}
