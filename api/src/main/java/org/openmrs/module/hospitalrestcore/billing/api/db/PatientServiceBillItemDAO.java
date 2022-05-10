/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBill;
import org.openmrs.module.hospitalrestcore.billing.PatientServiceBillItem;

/**
 * @author Ghanshyam
 *
 */
public interface PatientServiceBillItemDAO extends SingleClassDAO {

	List<PatientServiceBillItem> getPatientServiceBillItem(PatientServiceBill patientServiceBill) throws APIException;

}
