/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospital-core module.
 *
 *  Hospital-core module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospital-core module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospital-core module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.Driver;
import org.openmrs.module.hospitalrestcore.billing.api.db.DriverDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate specific Idcards database methods
 */
public class HibernateDriverDAO extends HibernateSingleClassDAO implements DriverDAO {

	public HibernateDriverDAO() {
		super(Driver.class);
	}

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	@Transactional(readOnly = true)
	public List<Driver> searchDriver(String searchText) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.like("name", searchText + "%")).add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Driver> getAllActiveDriver() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.addOrder(Order.asc("name")).add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Driver getDriverByUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (Driver) criteria.uniqueResult();
	}

}
