/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Company;

/**
 * @author Ghanshyam
 *
 */
public interface CompanyDAO extends SingleClassDAO {
	
	public List<Company> searchCompany(String searchText);
	
	public List<Company> getAllCompanies();
	
	public Company getCompanyByUuid(String uuid) throws DAOException;
	
}
