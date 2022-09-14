/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugUnit;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryDrugUnitDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateInventoryDrugUnitDAO extends HibernateSingleClassDAO implements InventoryDrugUnitDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	public HibernateInventoryDrugUnitDAO() {
		super(InventoryDrugUnit.class);
	}

	@Override
	@Transactional(readOnly = true)
	public InventoryDrugUnit getInventoryDrugUnitByUuidString(String uuid) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryDrugUnit.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("deleted", false));
		return (InventoryDrugUnit) criteria.uniqueResult();
	}

	@Override
	public List<InventoryDrugUnit> listAllInventoryDrugUnit() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryDrugUnit.class);
		criteria.add(Restrictions.eq("deleted", false));
		return criteria.list();
	}

}
