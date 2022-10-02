package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugDetails implements Serializable, Comparable<InventoryStoreDrugDetails> {

    private String store;
    private String name;
    private Integer mainStoreStatus;
    private Integer subStoreStatus;
    private String createdDate;
    private String uuid;
    private String transactionUuid;

    SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMainStoreStatus() {
        return mainStoreStatus;
    }

    public void setMainStoreStatus(Integer mainStoreStatus) {
        this.mainStoreStatus = mainStoreStatus;
    }

    public Integer getSubStoreStatus() {
        return subStoreStatus;
    }

    public void setSubStoreStatus(Integer subStoreStatus) {
        this.subStoreStatus = subStoreStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTransactionUuid() {
        return transactionUuid;
    }

    public void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    @Override
    public int compareTo(InventoryStoreDrugDetails o) {
        return (this.store).compareTo(o.store);
    }
}
