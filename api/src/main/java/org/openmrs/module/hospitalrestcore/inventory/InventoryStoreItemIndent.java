package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

public class InventoryStoreItemIndent extends CustomBaseOpenmrsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private InventoryStore store;
	private String name;
	private Integer subStoreStatus;
	private Integer mainStoreStatus;
	private InventoryStoreItemTransaction transaction;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InventoryStore getStore() {
		return store;
	}

	public void setStore(InventoryStore store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSubStoreStatus() {
		return subStoreStatus;
	}

	public void setSubStoreStatus(Integer subStoreStatus) {
		this.subStoreStatus = subStoreStatus;
	}

	public Integer getMainStoreStatus() {
		return mainStoreStatus;
	}

	public String getMainStoreStatusName() {
		return ActionValue.getIndentMainbStoreName(mainStoreStatus);
	}

	public String getSubStoreStatusName() {
		return ActionValue.getIndentSubStoreName(subStoreStatus);
	}

	public void setMainStoreStatus(Integer mainStoreStatus) {
		this.mainStoreStatus = mainStoreStatus;
	}

	public InventoryStoreItemTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(InventoryStoreItemTransaction transaction) {
		this.transaction = transaction;
	}

}
