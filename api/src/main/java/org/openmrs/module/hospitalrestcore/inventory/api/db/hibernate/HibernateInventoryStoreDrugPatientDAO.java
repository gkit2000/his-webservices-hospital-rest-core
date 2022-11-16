package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugPatient;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugPatientDAO;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugPatientDAO extends HibernateSingleClassDAO implements InventoryStoreDrugPatientDAO {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public HibernateInventoryStoreDrugPatientDAO() {
        super(InventoryStoreDrugPatient.class);
    }

    @Override
    public List<InventoryStoreDrugPatient> listAllInventoryStoreDrugPatient() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryStoreDrugPatient getInventoryStoreDrugPatientByIdentifier(String identifier) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("identifier", identifier));
        return (InventoryStoreDrugPatient) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryStoreDrugPatient getInventoryStoreDrugPatientById(Integer id) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("id", id));
        return (InventoryStoreDrugPatient) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countStoreDrugPatient(Integer storeId, String identifierOrName, Integer billNo, String fromDate,
                                         String toDate) throws DAOException {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "patient")
                .createAlias("patient.store", "store");

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("id"))
                .add(Projections.groupProperty("identifier"))
                .add(Projections.groupProperty("name"))
                .add(Projections.groupProperty("patient"))
                .add(Projections.groupProperty("createdDate"))
                .add(Projections.groupProperty("prescription"));

        criteria.add(Restrictions.eq("store.id", storeId));
        if (identifierOrName != null) {
            Criterion isIdentifier = Restrictions.eq("identifier", identifierOrName).ignoreCase();
            Criterion isName = Restrictions.like("name", identifierOrName, MatchMode.ANYWHERE).ignoreCase();
            criteria.add(Restrictions.or(isIdentifier, isName));
        }

        if (billNo != null)
            criteria.add(Restrictions.eq("id", billNo));

        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("CountStoreDrugPatient>>Error convert date: " + e.toString());
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
                System.out.println("CountStoreDrugPatient>>Error convert date: " + e.toString());
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
                System.out.println("CountStoreDrugPatient>>Error convert date: " + e.toString());
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
    public List<InventoryStoreDrugPatient> listStoreDrugPatient(Integer storeId, String identifierOrName, Integer billNo,
                                                                String fromDate, String toDate, int min, int max) throws DAOException {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "patient")
                .createAlias("patient.store", "store").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("id"))
                .add(Projections.groupProperty("identifier"))
                .add(Projections.groupProperty("name"))
                .add(Projections.groupProperty("patient"))
                .add(Projections.groupProperty("createdDate"))
                .add(Projections.groupProperty("prescription"));

        criteria.add(Restrictions.eq("store.id", storeId));
        if (identifierOrName != null) {
            Criterion isIdentifier = Restrictions.eq("identifier", identifierOrName).ignoreCase();
            Criterion isName = Restrictions.like("name", identifierOrName, MatchMode.ANYWHERE).ignoreCase();
            criteria.add(Restrictions.or(isIdentifier, isName));
        }

        if (billNo != null)
            criteria.add(Restrictions.eq("id", billNo));

        if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
            String startFromDate = fromDate + " 00:00:00";
            String endFromDate = fromDate + " 23:59:59";
            try {
                criteria.add(Restrictions.and(
                        Restrictions.ge("createdDate", formatter.parse(startFromDate)),
                        Restrictions.le("createdDate", formatter.parse(endFromDate))));
            } catch (Exception e) {
// TODO: handle exception
                System.out.println("ListStoreDrugPatient>>Error convert date: " + e.toString());
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
                System.out.println("ListStoreDrugPatient>>Error convert date: " + e.toString());
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
                System.out.println("ListStoreDrugPatient>>Error convert date: " + e.toString());
                e.printStackTrace();
            }
        }

        criteria.setProjection(proList);
        if (max > 0)
            criteria.setFirstResult(min).setMaxResults(max);

        List<Object> list = criteria.list();
        if (list == null || list.size() == 0)
            return null;

        List<InventoryStoreDrugPatient> lst = new ArrayList<InventoryStoreDrugPatient>();
        for (Object o : list) {
            Object[] row = (Object[]) o;
            InventoryStoreDrugPatient sdp = new InventoryStoreDrugPatient();
            sdp.setId((Integer) row[0]);
            sdp.setIdentifier((String) row[1]);
            sdp.setName((String) row[2]);
            sdp.setPatient((Patient) row[3]);
            sdp.setCreatedDate((Date) row[4]);
            sdp.setPrescription((String) row[5]);
            lst.add(sdp);
        }

        return lst;
    }
}