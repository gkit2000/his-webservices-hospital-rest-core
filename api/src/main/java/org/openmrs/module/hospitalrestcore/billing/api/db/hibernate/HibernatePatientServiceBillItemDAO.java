/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;
import org.openmrs.module.hospitalrestcore.billing.api.db.PatientServiceBillItemDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernatePatientServiceBillItemDAO extends HibernateSingleClassDAO implements PatientServiceBillItemDAO {
	
	public HibernatePatientServiceBillItemDAO() {
		super(PatientServiceBillItem.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PatientServiceBillItem> getPatientServiceBillItem(
			PatientServiceBill patientServiceBill)
			throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("patientServiceBill", patientServiceBill));
		return criteria.list();
	}
}
