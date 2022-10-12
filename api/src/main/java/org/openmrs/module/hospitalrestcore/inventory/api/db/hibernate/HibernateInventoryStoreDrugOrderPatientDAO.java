package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderPatient;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderPatientDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugOrderPatientDAO;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugOrderPatientDAO extends HibernateSingleClassDAO implements InventoryStoreDrugOrderPatientDAO {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public HibernateInventoryStoreDrugOrderPatientDAO() {
        super(InventoryStoreDrugOrderPatient.class);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryStoreDrugOrderPatient getDrugOrderPatientByIdentifier(String identifier, String date) throws DAOException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("identifier", identifier));


        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        try {
            criteria.add(Restrictions.and(
                    Restrictions.ge("createdDate", formatter.parse(startTime)),
                    Restrictions.le("createdDate", formatter.parse(endTime))));
        } catch (Exception e) {// TODO: handle exception
            System.out.println("getDrugOrderPatientByIdentifier>>Error convert date: " + e.toString());
            e.printStackTrace();
        }

        InventoryStoreDrugOrderPatient patient = (InventoryStoreDrugOrderPatient) criteria.uniqueResult();
        List<InventoryStoreDrugOrderPatientDetail> orderPatientDetail = hospitalRestCoreService.getDrugOrderPatientDetail(patient, date);

        if (!CollectionUtils.isEmpty(orderPatientDetail))
            return patient;
        else
            return null;

    }

    @Override
    @Transactional(readOnly = true)
    public Integer countDrugOrderPatient(String identifierOrName, String date) throws DAOException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);

        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        try {
            criteria.add(Restrictions.and(
                    Restrictions.ge("createdDate", formatter.parse(startTime)),
                    Restrictions.le("createdDate", formatter.parse(endTime))));
        } catch (Exception e) {// TODO: handle exception
            System.out.println("CountDrugOrderPatient>>Error convert date: " + e.toString());
            e.printStackTrace();
        }

        if (identifierOrName != null) {
            Criterion isIdentifier = Restrictions.eq("identifier", identifierOrName).ignoreCase();
            Criterion isName = Restrictions.like("name", identifierOrName, MatchMode.ANYWHERE).ignoreCase();
            criteria.add(Restrictions.or(isIdentifier, isName));
        }

        List<InventoryStoreDrugOrderPatient> patientList = criteria.list();
        List<InventoryStoreDrugOrderPatient> list = new ArrayList<InventoryStoreDrugOrderPatient>();

        for (InventoryStoreDrugOrderPatient p : patientList) {
            List<InventoryStoreDrugOrderPatientDetail> orderPatientDetail = hospitalRestCoreService.getDrugOrderPatientDetail(p, date);

            if (!CollectionUtils.isEmpty(orderPatientDetail))
                list.add(p);
        }

        Number total = 0;
        if (!CollectionUtils.isEmpty(list)) {
            total = (Number) list.size();
        }
        return total.intValue();

    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryStoreDrugOrderPatient> listDrugOrderPatient(String identifierOrName, String date, int min, int max) throws DAOException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);

        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        try {
            criteria.add(Restrictions.and(
                    Restrictions.ge("createdDate", formatter.parse(startTime)),
                    Restrictions.le("createdDate", formatter.parse(endTime))));
        } catch (Exception e) {// TODO: handle exception
            System.out.println("ListDrugOrderPatient>>Error convert date: " + e.toString());
            e.printStackTrace();
        }

        if (identifierOrName != null) {
            Criterion isIdentifier = Restrictions.eq("identifier", identifierOrName).ignoreCase();
            Criterion isName = Restrictions.like("name", identifierOrName, MatchMode.ANYWHERE).ignoreCase();
            criteria.add(Restrictions.or(isIdentifier, isName));
        }

        if (max > 0)
            criteria.setFirstResult(min).setMaxResults(max);

        List<InventoryStoreDrugOrderPatient> patientList = criteria.list();
        List<InventoryStoreDrugOrderPatient> list = new ArrayList<InventoryStoreDrugOrderPatient>();

        for (InventoryStoreDrugOrderPatient p : patientList) {
            List<InventoryStoreDrugOrderPatientDetail> orderPatientDetail = hospitalRestCoreService.getDrugOrderPatientDetail(p, date);

            if (!CollectionUtils.isEmpty(orderPatientDetail))
                list.add(p);
        }

        return list;
    }
}
