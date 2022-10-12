package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugOrderPatientDetail extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryStoreDrugOrderPatient drugOrderPatient;
    private String orderDate;
    private OpdDrugOrder opdDrugOrder;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventoryStoreDrugOrderPatient getDrugOrderPatient() {
        return drugOrderPatient;
    }

    public void setDrugOrderPatient(InventoryStoreDrugOrderPatient drugOrderPatient) {
        this.drugOrderPatient = drugOrderPatient;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public OpdDrugOrder getOpdDrugOrder() {
        return opdDrugOrder;
    }

    public void setOpdDrugOrder(OpdDrugOrder opdDrugOrder) {
        this.opdDrugOrder = opdDrugOrder;
    }
}
