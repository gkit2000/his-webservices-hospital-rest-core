package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.api.db.PatientDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernatePatientDAO extends HibernateSingleClassDAO implements PatientDAO {

    public HibernatePatientDAO() {
        super(Patient.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatientByPatientId(Integer patientId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("patientId", patientId))
                .add(Restrictions.eq("voided", false));
        return (Patient) criteria.uniqueResult();
    }
}
