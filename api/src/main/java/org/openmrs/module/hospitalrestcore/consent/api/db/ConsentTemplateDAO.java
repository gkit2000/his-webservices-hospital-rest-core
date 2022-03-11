/**
 * 
 */
package org.openmrs.module.hospitalrestcore.consent.api.db;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplate;

/**
 * @author Ghanshyam
 *
 */
public interface ConsentTemplateDAO extends SingleClassDAO {

	public List<ConsentTemplate> getAllConsentTemplate() throws DAOException;

	public ConsentTemplate getConsentTemplateByUuid(String uuid) throws DAOException;

	public List<ConsentTemplate> getConsentTemplateByType(Concept type) throws DAOException;
}
