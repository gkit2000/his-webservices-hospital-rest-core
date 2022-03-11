/**
 * 
 */
package org.openmrs.module.hospitalrestcore.consent.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.consent.ConsentTemplate;
import org.openmrs.module.hospitalrestcore.consent.api.db.ConsentTemplateDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateConsentTemplateDAO extends HibernateSingleClassDAO implements ConsentTemplateDAO {

	public HibernateConsentTemplateDAO() {
		super(ConsentTemplate.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsentTemplate> getAllConsentTemplate() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		//criteria.add(Restrictions.eq("deleted", false));
		return (List<ConsentTemplate>) criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public ConsentTemplate getConsentTemplateByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("deleted", false));
		return (ConsentTemplate) criteria.uniqueResult();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ConsentTemplate> getConsentTemplateByType(Concept type) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("deleted", false));
		criteria.add(Restrictions.eq("type", type));
		return (List<ConsentTemplate>) criteria.list();
	}
}
