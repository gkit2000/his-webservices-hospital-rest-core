package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptForm;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryReceiptFormDAO;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    @Transactional(readOnly = true)
    public Integer countReceiptsToGeneralStore(String companyName, String fromDate, String toDate)
            throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("companyName"))
                .add(Projections.max("billAmount"))
                .add(Projections.groupProperty("receiptNumber"))
                .add(Projections.groupProperty("receiptDate"));

        if (companyName != null) {
            criteria.add(Restrictions.like("companyName", companyName));
        }
        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("receiptDate", formatter.parse(startFromDate)),
                        Restrictions.le("receiptDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountReceiptToGeneralStore>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = toDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("receiptDate", formatter.parse(startToDate)),
                                Restrictions.le("receiptDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountReceiptToGeneralStore>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = fromDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("receiptDate", formatter.parse(startToDate)),
                                Restrictions.le("receiptDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountReceiptToGeneralStore>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }
        criteria.setProjection(proList);
        List<Object> list = criteria.list();

        Number total = 0;
        if (!CollectionUtils.isEmpty(list)) {
            total = (Number) list.size();
        }
        return total.intValue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryReceiptForm> listReceiptsToGeneralStore(String companyName, String fromDate, String toDate,
                                                                 int min, int max) throws DAOException{

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("companyName"))
                .add(Projections.max("billAmount"))
                .add(Projections.groupProperty("receiptNumber"))
                .add(Projections.groupProperty("receiptDate"));
        if (companyName != null) {
            criteria.add(Restrictions.like("companyName", companyName));
        }
        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("receiptDate", formatter.parse(startFromDate)),
                        Restrictions.le("receiptDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListReceiptToGeneralStore>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = toDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("receiptDate", formatter.parse(startToDate)),
                                Restrictions.le("receiptDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListReceiptToGeneralStore>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        } else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
            String startToDate = fromDate + " 00:00:00";
            String endToDate = toDate + " 23:59:59";
            try {
                criteria.add(
                        Restrictions.and(Restrictions.ge("receiptDate", formatter.parse(startToDate)),
                                Restrictions.le("receiptDate", formatter.parse(endToDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("listIReceiptToGeneralStore>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }

        criteria.setProjection(proList);
        if (max > 0)
            criteria.setFirstResult(min).setMaxResults(max);

        List<Object> list = criteria.list();
        if(list == null || list.size() == 0)
            return null;
        List<InventoryReceiptForm>  lst = new ArrayList<InventoryReceiptForm>();
        for (int i = 0; i < list.size();i++) {
            Object[] row = (Object[]) list.get(i);
            InventoryReceiptForm rfDetails = new InventoryReceiptForm();
            rfDetails.setCompanyName(row[0].toString());
            rfDetails.setBillAmount((BigDecimal) row[1]);
            rfDetails.setReceiptNumber(row[2].toString());
            rfDetails.setReceiptDate((Date) row[3]);
            lst.add(rfDetails);
        }
        return lst;
    }
}
