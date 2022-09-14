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
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulation;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryDrugFormulationDAO;

/**
 * @author Ghanshyam
 *
 */
public class HibernateInventoryDrugFormulationDAO extends HibernateSingleClassDAO
		implements InventoryDrugFormulationDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	public HibernateInventoryDrugFormulationDAO() {
		super(InventoryDrugFormulation.class);
	}

	@Override
	public InventoryDrugFormulation getInventoryDrugFormulationByUuidString(String uuid) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("deleted", false));
		return (InventoryDrugFormulation) criteria.uniqueResult();
	}

	@Override
	public List<InventoryDrugFormulation> listAllInventoryDrugFormulation() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("deleted", false));
		return criteria.list();
	}

}
