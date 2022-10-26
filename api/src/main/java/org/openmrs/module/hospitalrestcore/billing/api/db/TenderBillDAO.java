/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Company;
import org.openmrs.module.hospitalrestcore.billing.TenderBill;

/**
 * @author Ghanshyam
 *
 */
public interface TenderBillDAO extends SingleClassDAO {

	TenderBill getTenderBillByUuid(String uuid) throws APIException;
	
	List<TenderBill> getTenderBillByCompany(Company company) throws APIException;
}
