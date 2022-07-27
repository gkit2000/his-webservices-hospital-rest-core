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
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategory;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryDrugCategoryDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateInventoryDrugCategoryDAO extends HibernateSingleClassDAO implements InventoryDrugCategoryDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	public HibernateInventoryDrugCategoryDAO() {
		super(InventoryDrugCategory.class);
	}

	@Override
	@Transactional(readOnly = true)
	public InventoryDrugCategory getInventoryDrugCategoryByUuidString(String uuid) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (InventoryDrugCategory) criteria.uniqueResult();
	}

	@Override
	public List<InventoryDrugCategory> listAllInventoryDrugCategory() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

}
