/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Role;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategory;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulation;
import org.openmrs.module.hospitalrestcore.inventory.InventoryItem;
import org.openmrs.module.hospitalrestcore.inventory.InventoryItemSpecification;
import org.openmrs.module.hospitalrestcore.inventory.InventoryItemSubCategory;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreItemTransactionDetail;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ghanshyam
 *
 */
public class HibernateInventoryStoreDAO extends HibernateSingleClassDAO implements InventoryStoreDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	public HibernateInventoryStoreDAO() {
		super(InventoryStore.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> getAllRoles() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class);
		return criteria.list();
	}

	@Override
	public Role getRoleByUuid(String uuid) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (Role) criteria.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public InventoryStore getStoreByCollectionRole(List<Role> roles) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.in("role", roles));
		criteria.setMaxResults(1);
		List<InventoryStore> list = criteria.list();
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public InventoryStore getInventoryStoreByUuid(String uuid) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (InventoryStore) criteria.uniqueResult();
	}

	@Override
	public List<InventoryStore> listAllInventoryStore() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate,
			String toDate, boolean isExpiry) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(InventoryStoreDrugTransactionDetail.class, "transactionDetail")
				.createAlias("transactionDetail.transaction", "transaction")
				.createAlias("transactionDetail.drug", "drugAlias");

		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("drug")).add(Projections.groupProperty("formulation"))
				.add(Projections.sum("currentQuantity")).add(Projections.sum("quantity"))
				.add(Projections.sum("issueQuantity"));
		criteria.add(Restrictions.eq("transaction.store.id", storeId));
		if (categoryId != null) {
			criteria.add(Restrictions.eq("drugAlias.category.id", categoryId));
		}
		if (!StringUtils.isBlank(drugName)) {
			criteria.add(Restrictions.like("drugAlias.name", "%" + drugName + "%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(
						Restrictions.ge("transactionDetail.createdDate", formatter.parse(startFromDate)),
						Restrictions.le("transactionDetail.createdDate", formatter.parse(endFromDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listInventorySubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		}
		if (isExpiry) {
			criteria.add(Restrictions.lt("transactionDetail.dateExpiry", new Date()));
		} else {
			criteria.add(Restrictions.ge("transactionDetail.dateExpiry", new Date()));
		}
		criteria.setProjection(proList);
		List<Object> list = criteria.list();
		Number total = 0;
		if (!CollectionUtils.isEmpty(list)) {
			total = (Number) list.size();
		}
		return total.intValue();
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId,
			String drugName, String fromDate, String toDate, boolean isExpiry, int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(InventoryStoreDrugTransactionDetail.class, "transactionDetail")
				.createAlias("transactionDetail.transaction", "transaction")
				.createAlias("transactionDetail.drug", "drugAlias").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("drug")).add(Projections.groupProperty("formulation"))
				.add(Projections.sum("currentQuantity")).add(Projections.sum("quantity"))
				.add(Projections.sum("issueQuantity"));
		criteria.add(Restrictions.eq("transaction.store.id", storeId));
		if (categoryId != null) {
			criteria.add(Restrictions.eq("drugAlias.category.id", categoryId));
		}
		if (!StringUtils.isBlank(drugName)) {
			criteria.add(Restrictions.like("drugAlias.name", "%" + drugName + "%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(
						Restrictions.ge("transactionDetail.createdDate", formatter.parse(startFromDate)),
						Restrictions.le("transactionDetail.createdDate", formatter.parse(endFromDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listInventorySubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		}
		if (isExpiry) {
			criteria.add(Restrictions.lt("transactionDetail.dateExpiry", new Date()));
		} else {
			criteria.add(Restrictions.ge("transactionDetail.dateExpiry", new Date()));
		}

		criteria.add(Restrictions.ge("transactionDetail.currentQuantity", 0));

		criteria.add(Restrictions.eq("transactionDetail.expireStatus", 0));

		criteria.setProjection(proList);
		if (max > 0) {
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<Object> lst = criteria.list();
		if (lst == null || lst.size() == 0)
			return null;
		List<InventoryStoreDrugTransactionDetail> list = new ArrayList<InventoryStoreDrugTransactionDetail>();
		for (int i = 0; i < lst.size(); i++) {
			Object[] row = (Object[]) lst.get(i);
			InventoryStoreDrugTransactionDetail tDetail = new InventoryStoreDrugTransactionDetail();
			tDetail.setDrug((InventoryDrug) row[0]);
			tDetail.setFormulation((InventoryDrugFormulation) row[1]);
			tDetail.setCurrentQuantity(Integer.parseInt(row[2].toString()));
			tDetail.setQuantity(Integer.parseInt(row[3].toString()));
			tDetail.setIssueQuantity(Integer.parseInt(row[4].toString()));
			list.add(tDetail);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryDrugCategory> listDrugCategory(String name, int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryDrugCategory.class,
				"drugCategory");
		if (!StringUtils.isBlank(name)) {
			criteria.add(Restrictions.like("drugCategory.name", "%" + name + "%"));
		}
		if (max > 0) {
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<InventoryDrugCategory> l = criteria.list();

		return l;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName, String fromDate,
			String toDate) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(InventoryStoreItemTransactionDetail.class, "transactionDetail")
				.createAlias("transactionDetail.transaction", "transaction")
				.createAlias("transactionDetail.item", "itemAlias");

		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("item")).add(Projections.groupProperty("specification"))
				.add(Projections.sum("currentQuantity")).add(Projections.sum("quantity"))
				.add(Projections.sum("issueQuantity"));
		criteria.add(Restrictions.eq("transaction.store.id", storeId));
		if (categoryId != null) {
			criteria.add(Restrictions.eq("itemAlias.subCategory.id", categoryId));
		}
		if (!StringUtils.isBlank(itemName)) {
			criteria.add(Restrictions.like("itemAlias.name", "%" + itemName + "%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(
						Restrictions.ge("transactionDetail.createdDate", formatter.parse(startFromDate)),
						Restrictions.le("transactionDetail.createdDate", formatter.parse(endFromDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listInventorySubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		}
		criteria.setProjection(proList);
		List<Object> list = criteria.list();
		Number total = 0;
		if (!CollectionUtils.isEmpty(list)) {
			total = (Number) list.size();
		}
		return total.intValue();
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
			String itemName, String fromDate, String toDate, int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(InventoryStoreItemTransactionDetail.class, "transactionDetail")
				.createAlias("transactionDetail.transaction", "transaction")
				.createAlias("transactionDetail.item", "itemAlias").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("item")).add(Projections.groupProperty("specification"))
				.add(Projections.sum("currentQuantity")).add(Projections.sum("quantity"))
				.add(Projections.sum("issueQuantity"));
		criteria.add(Restrictions.eq("transaction.store.id", storeId));
		if (categoryId != null) {
			criteria.add(Restrictions.eq("itemAlias.subCategory.id", categoryId));
		}
		if (!StringUtils.isBlank(itemName)) {
			criteria.add(Restrictions.like("itemAlias.name", "%" + itemName + "%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(
						Restrictions.ge("transactionDetail.createdDate", formatter.parse(startFromDate)),
						Restrictions.le("transactionDetail.createdDate", formatter.parse(endFromDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(
						Restrictions.and(Restrictions.ge("transactionDetail.createdDate", formatter.parse(startToDate)),
								Restrictions.le("transactionDetail.createdDate", formatter.parse(endToDate))));
			} catch (Exception e) {
// TODO: handle exception
				System.out.println("listInventorySubStoreIndent>>Error convert date: " + e.toString());
				e.printStackTrace();
			}
		}

		criteria.setProjection(proList);
		if (max > 0) {
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<Object> lst = criteria.list();
		if (lst == null || lst.size() == 0)
			return null;
		List<InventoryStoreItemTransactionDetail> list = new ArrayList<InventoryStoreItemTransactionDetail>();
		for (int i = 0; i < lst.size(); i++) {
			Object[] row = (Object[]) lst.get(i);
			InventoryStoreItemTransactionDetail tDetail = new InventoryStoreItemTransactionDetail();
			tDetail.setItem((InventoryItem) row[0]);
			tDetail.setSpecification((InventoryItemSpecification) row[1]);
			tDetail.setCurrentQuantity((Integer) row[2]);
			tDetail.setQuantity((Integer) row[3]);
			tDetail.setIssueQuantity((Integer) row[4]);
			list.add(tDetail);
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryItemSubCategory> listItemSubCategory(String name, int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryItemSubCategory.class,
				"itemSubCategory");
		if (!StringUtils.isBlank(name)) {
			criteria.add(Restrictions.like("itemSubCategory.name", "%" + name + "%"));
		}
		if (max > 0) {
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<InventoryItemSubCategory> l = criteria.list();

		return l;
	}
}
