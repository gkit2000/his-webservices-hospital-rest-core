/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.api.db.PatientServiceBillDAO;

/**
 * @author Ghanshyam
 *
 */
public class HibernatePatientServiceBillDAO extends HibernateSingleClassDAO implements PatientServiceBillDAO {

	public HibernatePatientServiceBillDAO() {
		super(PatientServiceBill.class);
	}
	
}
