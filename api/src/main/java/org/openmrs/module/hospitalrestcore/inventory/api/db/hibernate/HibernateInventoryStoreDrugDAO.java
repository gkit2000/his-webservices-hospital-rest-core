package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulation;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrug;
import org.openmrs.module.hospitalrestcore.inventory.api.db.InventoryStoreDrugDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernateInventoryStoreDrugDAO extends HibernateSingleClassDAO implements InventoryStoreDrugDAO {

    public HibernateInventoryStoreDrugDAO() {
        super(InventoryStoreDrug.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryStoreDrug> listAllInventoryStoreDrug(InventoryStore store) throws DAOException {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("store"))
                .add(Projections.property("drug"))
                .add(Projections.property("formulation"))
                .add(Projections.property("currentQuantity"))
                .add(Projections.property("receiptQuantity"))
                .add(Projections.property("issueQuantity"))
                .add(Projections.property("statusIndent"))
                .add(Projections.property("reorderQuantity"))
                .add(Projections.property("openingBalance"))
                .add(Projections.property("closingBalance"))
                .add(Projections.property("status"))
                .add(Projections.property("id"))
                .add(Projections.property("createdDate"));

        criteria.add(Restrictions.eq("store", store)).addOrder(Order.desc("createdDate"))
                .setProjection(proList);

        List<Object> list = criteria.list();
        if (list == null || list.size() == 0)
            return null;

        List<InventoryStoreDrug> drugs = new ArrayList<InventoryStoreDrug>();
        for (Object o : list) {
            Object[] row = (Object[]) o;
            InventoryStoreDrug drug = new InventoryStoreDrug();
            drug.setStore((InventoryStore) row[0]);
            drug.setDrug((InventoryDrug) row[1]);
            drug.setFormulation((InventoryDrugFormulation) row[2]);
            drug.setCurrentQuantity((Integer) row[3]);
            drug.setReceiptQuantity((Integer) row[4]);
            drug.setIssueQuantity((Integer) row[5]);
            drug.setStatusIndent((Integer) row[6]);
            drug.setReorderQuantity((Integer) row[7]);
            drug.setOpeningBalance((Integer) row[8]);
            drug.setClosingBalance((Integer) row[9]);
            drug.setStatus((Integer) row[10]);
            drug.setId((Integer) row[11]);
            drug.setCreatedDate((Date) row[12]);
            drugs.add(drug);
        }

        return drugs;
    }
}
