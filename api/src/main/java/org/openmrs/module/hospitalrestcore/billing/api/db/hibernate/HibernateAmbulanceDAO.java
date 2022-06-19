/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Ambulance;
import org.openmrs.module.hospitalrestcore.billing.api.db.AmbulanceDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateAmbulanceDAO extends HibernateSingleClassDAO implements AmbulanceDAO {

	public HibernateAmbulanceDAO() {
		super(Ambulance.class);
	}

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	@Transactional(readOnly = true)
	public List<Ambulance> searchAmbulance(String searchText) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.like("name", searchText + "%")).add(Restrictions.eq("retired", false));
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ambulance> getAllAmbulance() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		//criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}
	
	public List<Ambulance> getAllActiveAmbulance() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.addOrder(Order.asc("name")).add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Ambulance getAmbulanceByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (Ambulance) criteria.uniqueResult();
	}
}
