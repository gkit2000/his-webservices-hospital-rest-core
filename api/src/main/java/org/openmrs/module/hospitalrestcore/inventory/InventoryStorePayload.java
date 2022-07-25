/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory;

/**
 * @author Ghanshyam
 *
 */
public class InventoryStorePayload {

	private String name;
	private String code;
	private String parentUuid;
	private String storeUuid;
	private String roleUuid;
	private Boolean isDrug = false;;
	private Boolean retired = false;

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

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

	public String getStoreUuid() {
		return storeUuid;
	}

	public void setStoreUuid(String storeUuid) {
		this.storeUuid = storeUuid;
	}

	public String getRoleUuid() {
		return roleUuid;
	}

	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid;
	}

	public Boolean getIsDrug() {
		return isDrug;
	}

	public void setIsDrug(Boolean isDrug) {
		this.isDrug = isDrug;
	}

	public Boolean getRetired() {
		return retired;
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}
}
