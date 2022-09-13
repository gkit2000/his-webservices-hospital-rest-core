/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Drug;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryDrugDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateInventoryDrugDAO extends HibernateSingleClassDAO implements InventoryDrugDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	public HibernateInventoryDrugDAO() {
		super(InventoryDrug.class);
	}

	@Override
	@Transactional(readOnly = true)
	public InventoryDrug getInventoryDrugByUuidString(String uuid) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (InventoryDrug) criteria.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public int countListDrug(Integer categoryId, String name) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "drug");
		if (!StringUtils.isBlank(name)) {
			criteria.add(Restrictions.like("drug.name", "%" + name + "%"));
		}
		if (categoryId != null) {
			criteria.add(Restrictions.eq("drug.category.id", categoryId));
		}
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}

	public List<InventoryDrug> listDrug(Integer categoryId, String name, int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "drug");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("drug.name", "%" + name + "%"));
		}
		if (categoryId != null) {
			criteria.add(Restrictions.eq("drug.category.id", categoryId));
		}
		if (max > 0) {
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<InventoryDrug> l = criteria.list();

		return l;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Drug getDrugByUuid(String uuid) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Drug.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (Drug) criteria.uniqueResult();
	}
}
