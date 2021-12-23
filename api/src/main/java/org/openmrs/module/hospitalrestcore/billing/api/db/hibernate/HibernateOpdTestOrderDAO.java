/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.api.db.OpdTestOrderDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateOpdTestOrderDAO extends HibernateSingleClassDAO implements OpdTestOrderDAO {

	public HibernateOpdTestOrderDAO() {
		super(OpdTestOrder.class);
	}

	/*@Override
	@Transactional(readOnly = true)
	public List<OpdTestOrder> getOpdTestOrder(Patient patient, Date creationDate) {
		String sQuery = "from BillableService as billableService";

		Query query = super.sessionFactory.getCurrentSession().createQuery(sQuery);

		return query.list();
	}*/

	@Override
	@Transactional(readOnly = true)
	public List<OpdTestOrder> getOpdTestOrder(Patient patient, Date creationDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		if (patient != null) {
			criteria.add(Restrictions.eq("patient", patient));
		}
		if (creationDate != null) {
			Date dateBefore = new Date(creationDate.getTime() - 1 * 24 * 3600 * 1000);
			Date dateAfter = new Date(creationDate.getTime() + 1 * 24 * 3600 * 1000);
			criteria.add(Restrictions.gt("createdOn", dateBefore));
			criteria.add(Restrictions.lt("createdOn", dateAfter));
		}
		return (List<OpdTestOrder>) criteria.list();
	}
}
