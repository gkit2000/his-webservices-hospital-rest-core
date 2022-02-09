/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.BillableService;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillableServiceDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateBillableServiceDAO extends HibernateSingleClassDAO implements BillableServiceDAO {

	public HibernateBillableServiceDAO() {
		super(BillableService.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BillableService> getAllServices() {
		String sQuery = "from BillableService as billableService";

		Query query = super.sessionFactory.getCurrentSession().createQuery(sQuery);

		return query.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<BillableService> getServicesByPriceCategory(Concept priceCategory) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("priceCategoryConcept", priceCategory));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public BillableService getServicesByServiceConceptAndPriceCategory(Concept serviceConcept,
			Concept priceCategory) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("serviceConcept", serviceConcept));
		criteria.add(Restrictions.eq("priceCategoryConcept", priceCategory));
		return (BillableService) criteria.uniqueResult();
	}

	/*@Override
	@Transactional(readOnly = true)
	public BillableService getServiceByConcept(Concept serviceConcept) {
		String sQuery = "from BillableService as billableService";

		Query query = super.sessionFactory.getCurrentSession().createQuery(
				sQuery);
		
		query.setParameter("serviceConcept", serviceConcept);

		return (BillableService) query.uniqueResult();
	}*/

	@Override
	@Transactional(readOnly = true)
	public BillableService getServiceByConcept(Concept serviceConcept) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("serviceConcept", serviceConcept));
		return (BillableService) criteria.uniqueResult();
	}

}
