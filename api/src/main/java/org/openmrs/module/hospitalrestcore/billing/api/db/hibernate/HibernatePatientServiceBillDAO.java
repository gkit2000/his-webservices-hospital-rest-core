/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.api.db.PatientServiceBillDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernatePatientServiceBillDAO extends HibernateSingleClassDAO implements PatientServiceBillDAO {

	public HibernatePatientServiceBillDAO() {
		super(PatientServiceBill.class);
	}

	@Override
	@Transactional(readOnly = true)
	public PatientServiceBill getPatientServiceBillById(Integer billId) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("patientServiceBillId", billId));
		return (PatientServiceBill) criteria.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public PatientServiceBill getPatientServiceBillByIdAndPatient(Integer billId, Patient patient) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("patientServiceBillId", billId));
		criteria.add(Restrictions.eq("patient", patient));
		return (PatientServiceBill) criteria.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PatientServiceBill> getPatientServiceBill(Patient patient) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("patient", patient));
		return criteria.list();
	}

}
