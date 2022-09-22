package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulation;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugTransactionDetailDAO;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugTransactionDetailDAO extends HibernateSingleClassDAO implements InventoryStoreDrugTransactionDetailDAO {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public HibernateInventoryStoreDrugTransactionDetailDAO() {
        super(InventoryStoreDrugTransactionDetail.class);
    }

    @Override
    public List<InventoryStoreDrugTransactionDetail> listAllStoreDrugTransactionDetail() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("retired", false));
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryStoreDrugTransactionDetail getDrugTransactionDetailByUuidString(String uuid) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("uuid", uuid))
                .add(Restrictions.eq("retired", false));
        return (InventoryStoreDrugTransactionDetail) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countViewStockBalanceExpiry(Integer storeId, String category, String drugName, String fromDate, String toDate) throws DAOException {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "transactionDetail")
                .createAlias("transactionDetail.drug", "drugAlias")
                .createAlias("transactionDetail.transaction", "transaction")
                .createAlias("transaction.store", "store")
                .createAlias("drugAlias.category", "category");

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("drug"))
                .add(Projections.max("createdDate"))
                .add(Projections.groupProperty("formulation"))
                .add(Projections.groupProperty("currentQuantity"));

        criteria.add(Restrictions.eq("expireStatus", 1))
                .add(Restrictions.eq("retired", false))
                .add(Restrictions.eq("store.id", storeId));

        if (!StringUtils.isBlank(category))
            criteria.add(Restrictions.eq("category.name", category));

        if (!StringUtils.isBlank(drugName))
            criteria.add(Restrictions.like("drugAlias.name", drugName));

        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountViewStockBalanceExpiry>>Error convert date: " + e.toString());
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
                System.out.println("CountViewStockBalanceExpiry>>Error convert date: " + e.toString());
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
                System.out.println("CountViewStockBalanceExpiry>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }

        criteria.setProjection(proList);
        List<Object> list = criteria.list();

        Number total = 0;
        if (!CollectionUtils.isEmpty(list))
            total = (Number) list.size();

        return total.intValue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, String category, String drugName,
                                                                                    String fromDate, String toDate, int min, int max) throws DAOException {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "transactionDetail")
                .createAlias("transactionDetail.drug", "drugAlias")
                .createAlias("transactionDetail.transaction", "transaction")
                .createAlias("transaction.store", "store")
                .createAlias("drugAlias.category", "category")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("drug"))
                .add(Projections.max("createdDate"))
                .add(Projections.groupProperty("formulation"))
                .add(Projections.groupProperty("currentQuantity"));
        criteria.add(Restrictions.eq("expireStatus", 1))
                .add(Restrictions.eq("retired", false))
                .addOrder(Order.desc("createdDate"))
                .add(Restrictions.eq("store.id", storeId));
        if (!StringUtils.isBlank(category))
            criteria.add(Restrictions.eq("category.name", category));

        if (!StringUtils.isBlank(drugName))
            criteria.add(Restrictions.like("drugAlias.name", drugName));

        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListStoreDrugTransactionDetail>>Error convert date: " + e.toString());
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
                System.out.println("ListStoreDrugTransactionDetail>>Error convert date: " + e.toString());
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
                System.out.println("ListStoreDrugTransactionDetail>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }

        criteria.setProjection(proList);
        if (max > 0)
            criteria.setFirstResult(min).setMaxResults(max);
        List<Object> list = criteria.list();

        if (list == null || list.size() == 0)
            return null;

        List<InventoryStoreDrugTransactionDetail> lst = new ArrayList<InventoryStoreDrugTransactionDetail>();
        for (Object o : list) {
            Object[] row = (Object[]) o;
            InventoryStoreDrugTransactionDetail sdtd = new InventoryStoreDrugTransactionDetail();
            sdtd.setDrug((InventoryDrug) row[0]);
            sdtd.setFormulation((InventoryDrugFormulation) row[2]);
            sdtd.setCurrentQuantity(Integer.parseInt(row[3].toString()));
            lst.add(sdtd);
        }

        return lst;
    }
}