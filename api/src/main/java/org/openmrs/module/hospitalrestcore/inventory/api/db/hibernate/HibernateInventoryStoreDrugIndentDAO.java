package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIndent;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugIndentDAO;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.openmrs.module.hospitalrestcore.inventory.ActionValue.INDENT_MAINSTORE_NAMES;
import static org.openmrs.module.hospitalrestcore.inventory.ActionValue.INDENT_SUBSTORE_NAMES;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugIndentDAO extends HibernateSingleClassDAO implements InventoryStoreDrugIndentDAO {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

    public HibernateInventoryStoreDrugIndentDAO() {
        super(InventoryStoreDrugIndent.class);
    }

    @Override
    public List<InventoryStoreDrugIndent> listAllInventoryStoreDrugIndent() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("retired", false));
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryStoreDrugIndent getInventoryStoreDrugIndentByUuidString(String uuid) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("uuid", uuid))
                .add(Restrictions.eq("retired", false));
        return (InventoryStoreDrugIndent) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countStoreDrugIndent(Integer storeId, String storeName, String indentStatus, String indentName, String fromDate,
                                        String toDate) throws DAOException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "indent")
                .createAlias("indent.store", "store");

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("store"))
                .add(Projections.groupProperty("name"))
                .add(Projections.groupProperty("createdDate"));

        if (storeId == 1)
            proList.add(Projections.groupProperty("mainStoreStatus"));
        else {
            proList.add(Projections.groupProperty("subStoreStatus"));
            criteria.add(Restrictions.eq("store.id", storeId));
        }

        criteria.add(Restrictions.eq("retired", false));
        if (storeName != null) {
            List<InventoryStore> inventoryStores = hospitalRestCoreService.listAllInventoryStore();
            InventoryStore inventoryStore = new InventoryStore();
            for (InventoryStore store : inventoryStores)
                if (Objects.equals(store.getName(), storeName))
                    inventoryStore = store;

            criteria.add(Restrictions.like("store", inventoryStore));
        }
        if (indentStatus != null) {
            if (storeId == 1) {
                int index = Arrays.asList(INDENT_MAINSTORE_NAMES).indexOf(indentStatus);
                criteria.add(Restrictions.like("mainStoreStatus", (index + 1)));
            } else {
                int index = Arrays.asList(INDENT_SUBSTORE_NAMES).indexOf(indentStatus);
                criteria.add(Restrictions.like("subStoreStatus", (index + 1)));
            }
        }

        if (indentName != null)
            criteria.add(Restrictions.like("name", indentName));

        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountStoreDrugIndent>>Error convert date: " + e.toString());
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
                System.out.println("CountStoreDrugIndent>>Error convert date: " + e.toString());
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
                System.out.println("CountStoreDrugIndent>>Error convert date: " + e.toString());
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
    public List<InventoryStoreDrugIndent> listStoreDrugIndent(Integer storeId, String storeName, String indentStatus,
                                                              String indentName, String fromDate, String toDate, int min, int max) throws DAOException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "indent")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).createAlias("indent.store", "store");

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("store"))
                .add(Projections.groupProperty("name"))
                .add(Projections.groupProperty("createdDate"));

        if (storeId == 1)
            proList.add(Projections.groupProperty("mainStoreStatus"));
        else {
            proList.add(Projections.groupProperty("subStoreStatus"));
            criteria.add(Restrictions.eq("store.id", storeId));
        }

        criteria.add(Restrictions.eq("retired", false));
        if (storeName != null) {
            List<InventoryStore> inventoryStores = hospitalRestCoreService.listAllInventoryStore();
            InventoryStore inventoryStore = new InventoryStore();
            for (InventoryStore store : inventoryStores)
                if (Objects.equals(store.getName(), storeName))
                    inventoryStore = store;

            criteria.add(Restrictions.like("store", inventoryStore));
        }
        if (indentStatus != null) {
            if (storeId == 1) {
                int index = Arrays.asList(INDENT_MAINSTORE_NAMES).indexOf(indentStatus);
                criteria.add(Restrictions.like("mainStoreStatus", (index + 1)));
            } else {
                int index = Arrays.asList(INDENT_SUBSTORE_NAMES).indexOf(indentStatus);
                criteria.add(Restrictions.like("subStoreStatus", (index + 1)));
            }
        }

        if (indentName != null)
            criteria.add(Restrictions.like("name", indentName));

        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListStoreDrugIndent>>Error convert date: " + e.toString());
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
                System.out.println("ListStoreDrugIndent>>Error convert date: " + e.toString());
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
                System.out.println("ListStoreDrugIndent>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }

        criteria.setProjection(proList);
        if (max > 0)
            criteria.setFirstResult(min).setMaxResults(max);

        List<Object> list = criteria.list();
        if (list == null || list.size() == 0)
            return null;

        List<InventoryStoreDrugIndent> lst = new ArrayList<InventoryStoreDrugIndent>();
        for (Object o : list) {
            Object[] row = (Object[]) o;
            InventoryStoreDrugIndent sdi = new InventoryStoreDrugIndent();
            sdi.setStore((InventoryStore) row[0]);
            sdi.setName(row[1].toString());
            sdi.setCreatedDate((Date) row[2]);
            if (storeId == 1)
                sdi.setMainStoreStatus((Integer) row[3]);
            else
                sdi.setSubStoreStatus((Integer) row[3]);
            lst.add(sdi);
        }

        return lst;
    }
}
