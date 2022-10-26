/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Company;
import org.openmrs.module.hospitalrestcore.billing.api.db.CompanyDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateCompanyDAO extends HibernateSingleClassDAO implements CompanyDAO {

	public HibernateCompanyDAO() {
		super(Company.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> searchCompany(String searchText) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.like("name", searchText + "%")).add(Restrictions.eq("retired", false));
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> getAllCompanies() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		// criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Company getCompanyByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (Company) criteria.uniqueResult();
	}
}
