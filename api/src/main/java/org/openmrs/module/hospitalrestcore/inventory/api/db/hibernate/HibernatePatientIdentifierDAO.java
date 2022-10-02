package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.api.db.PatientIdentifierDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernatePatientIdentifierDAO extends HibernateSingleClassDAO implements PatientIdentifierDAO {

    public HibernatePatientIdentifierDAO() {
        super(PatientIdentifier.class);
    }

    @Override
    public List<PatientIdentifier> listAllPatientIdentifier() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("voided", false)); //TODO: remove restriction
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public PatientIdentifier getPatientIdentifierByPatientId(Integer patientId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "patientIdentifier")
                .createAlias("patientIdentifier.patient", "patient");
        criteria.add(Restrictions.eq("patient.patientId", patientId))
                .add(Restrictions.eq("voided", false));
        return (PatientIdentifier) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPatientIdByIdentifierString(String identifier) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("identifier", identifier))
                .add(Restrictions.eq("voided", false));

        PatientIdentifier patientIdentifier = (PatientIdentifier) criteria.uniqueResult();

        if (patientIdentifier == null)
            return null;

        return patientIdentifier.getPatient().getPatientId();
    }
}
