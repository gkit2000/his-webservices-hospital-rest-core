/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Company;
import org.openmrs.module.hospitalrestcore.billing.TenderBill;
import org.openmrs.module.hospitalrestcore.billing.api.db.TenderBillDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateTenderBillDAO extends HibernateSingleClassDAO implements TenderBillDAO {

	@SuppressWarnings("unchecked")
	public HibernateTenderBillDAO() {
		super(TenderBill.class);
	}

	@Override
	public TenderBill getTenderBillByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (TenderBill) criteria.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TenderBill> getTenderBillByCompany(Company company) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("company", company));
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}
}