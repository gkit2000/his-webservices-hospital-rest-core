package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

public class InventoryItemCategory extends CustomBaseOpenmrsObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameShort() {
		return !StringUtils.isBlank(name) && name.length() > 20 ? name.substring(0, 20) + "..." : name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
