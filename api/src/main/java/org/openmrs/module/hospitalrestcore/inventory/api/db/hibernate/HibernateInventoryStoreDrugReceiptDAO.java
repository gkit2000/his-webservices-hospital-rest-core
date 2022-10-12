package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugReceipt;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugReceiptDAO;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugReceiptDAO extends HibernateSingleClassDAO implements InventoryStoreDrugReceiptDAO {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public HibernateInventoryStoreDrugReceiptDAO() {
        super(InventoryStoreDrugReceipt.class);
    }


    @Override
    public List<InventoryStoreDrugReceipt> listAllInventoryStoreDrugReceipt() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass).addOrder(Order.desc("createdDate"));
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countStoreDrugReceipt(String vendorName, String fromDate, String toDate) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass).addOrder(Order.desc("createdDate"));

        if (vendorName != null)
            criteria.add(Restrictions.eq("vendorName", vendorName));


        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountStoreDrugReceipt>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = toDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("createdDate", formatter.parse(startToDate)),
                                Restrictions.le("createdDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountStoreDrugReceipt>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = fromDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("createdDate", formatter.parse(startToDate)),
                                Restrictions.le("createdDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountStoreDrugReceipt>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }

        List<Object> list = criteria.list();

        Number total = 0;
        if (!CollectionUtils.isEmpty(list))
            total = (Number) list.size();

        return total.intValue();

    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryStoreDrugReceipt> listStoreDrugReceipt(String vendorName, String fromDate, String toDate, int min, int max) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass).addOrder(Order.desc("createdDate"));

        if (vendorName != null)
            criteria.add(Restrictions.eq("vendorName", vendorName));


        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListStoreDrugReceipt>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = toDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("createdDate", formatter.parse(startToDate)),
                                Restrictions.le("createdDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListStoreDrugReceipt>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = fromDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("createdDate", formatter.parse(startToDate)),
                                Restrictions.le("createdDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListStoreDrugReceipt>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }

        if (max > 0)
            criteria.setFirstResult(min).setMaxResults(max);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryStoreDrugReceipt> getInventoryStoreDrugReceiptByName(String name) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("vendorName", name));
        return criteria.list();
    }
}
