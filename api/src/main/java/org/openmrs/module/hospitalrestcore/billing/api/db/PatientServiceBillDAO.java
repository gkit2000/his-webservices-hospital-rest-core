/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;

/**
 * @author Ghanshyam
 *
 */
public interface PatientServiceBillDAO extends SingleClassDAO {

	List<PatientServiceBill> getPatientServiceBill(Patient patient) throws APIException;

}
