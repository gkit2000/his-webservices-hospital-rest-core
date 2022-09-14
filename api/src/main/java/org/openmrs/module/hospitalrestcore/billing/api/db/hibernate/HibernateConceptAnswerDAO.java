/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.api.db.ConceptAnswerDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateConceptAnswerDAO extends HibernateSingleClassDAO implements ConceptAnswerDAO {

	public HibernateConceptAnswerDAO() {
		super(ConceptAnswer.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConceptAnswer> getConceptAnswerByAnswerConcept(Concept answerConcept) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("answerConcept", answerConcept));
		return criteria.list();
	}
}