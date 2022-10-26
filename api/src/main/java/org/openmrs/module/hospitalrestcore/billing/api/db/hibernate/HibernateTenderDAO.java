/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Tender;
import org.openmrs.module.hospitalrestcore.billing.api.db.TenderDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateTenderDAO extends HibernateSingleClassDAO implements TenderDAO {

	public HibernateTenderDAO() {
		super(Tender.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tender> searchTender(String searchText) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.like("name", searchText + "%")).add(Restrictions.eq("retired", false));
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tender> getAllTender() throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		// criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Tender getTenderByUuid(String uuid) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (Tender) criteria.uniqueResult();
	}
}
