package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugPatientDetail extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryStoreDrugPatient storeDrugPatient;
    private InventoryStoreDrugTransactionDetail transactionDetail;
    private Integer quantity;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventoryStoreDrugPatient getStoreDrugPatient() {
        return storeDrugPatient;
    }

    public void setStoreDrugPatient(InventoryStoreDrugPatient storeDrugPatient) {
        this.storeDrugPatient = storeDrugPatient;
    }

    public InventoryStoreDrugTransactionDetail getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(InventoryStoreDrugTransactionDetail transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
