/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Tender;

/**
 * @author Ghanshyam
 *
 */
public interface TenderDAO extends SingleClassDAO {

	List<Tender> searchTender(String searchText) throws APIException;;

	List<Tender> getAllTender() throws APIException;;

	Tender getTenderByUuid(String uuid) throws APIException;
}
