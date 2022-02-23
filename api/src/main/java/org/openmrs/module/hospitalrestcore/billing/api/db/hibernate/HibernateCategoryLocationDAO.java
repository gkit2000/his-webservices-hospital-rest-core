/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;
import org.openmrs.module.hospitalrestcore.billing.api.db.CategoryLocationDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateCategoryLocationDAO extends HibernateSingleClassDAO implements CategoryLocationDAO {

	public HibernateCategoryLocationDAO() {
		super(CategoryLocation.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryLocation> getAllCategoryLocation() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("deleted", false));
		return (List<CategoryLocation>) criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryLocation> getCategoryLocationByPriceCategory(Concept priceCategoryConcept) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("priceCategoryConcept", priceCategoryConcept));
		criteria.add(Restrictions.eq("deleted", false));
		return (List<CategoryLocation>) criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryLocation getCategoryLocationByLocation(Location location) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("location", location));
		criteria.add(Restrictions.eq("deleted", false));
		return (CategoryLocation) criteria.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryLocation getCategoryLocationByPriceCategoryAndLocation(Concept priceCategoryConcept,
			Location location) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("priceCategoryConcept", priceCategoryConcept));
		criteria.add(Restrictions.eq("location", location));
		criteria.add(Restrictions.eq("deleted", false));
		return (CategoryLocation) criteria.uniqueResult();
	}

}
