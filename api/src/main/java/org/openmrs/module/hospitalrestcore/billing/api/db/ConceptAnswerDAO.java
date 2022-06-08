/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;

/**
 * @author Ghanshyam
 *
 */
public interface ConceptAnswerDAO extends SingleClassDAO {

	public List<ConceptAnswer> getConceptAnswerByAnswerConcept(Concept answerConcept) throws DAOException;

}
