/**
 *  Copyright 2011 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospital-core module.
 *
 *  Hospital-core module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospital-core module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospital-core module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;
import java.util.Set;

import org.openmrs.Role;
import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

public class InventoryStore extends CustomBaseOpenmrsObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Role role;
	private String code;
	private int isDrug;
	private InventoryStore parent;
	private Set<InventoryStore> subStores;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InventoryStore getParent() {
		return parent;
	}

	public void setParent(InventoryStore parent) {
		this.parent = parent;
	}

	public Set<InventoryStore> getSubStores() {
		return subStores;
	}

	public void setSubStores(Set<InventoryStore> subStores) {
		this.subStores = subStores;
	}

	public int getIsDrug() {
		return isDrug;
	}

	public String getIsDrugName() {

		return isDrug == 1 ? "Yes" : "No";
	}

	public void setIsDrug(int isDrug) {
		this.isDrug = isDrug;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
