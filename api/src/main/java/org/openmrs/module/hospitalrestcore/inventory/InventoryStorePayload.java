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
	private Boolean isPharmacy = false;;
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

	/**
	 * @return the isPharmacy
	 */
	public Boolean getIsPharmacy() {
		return isPharmacy;
	}

	/**
	 * @param isPharmacy the isPharmacy to set
	 */
	public void setIsPharmacy(Boolean isPharmacy) {
		this.isPharmacy = isPharmacy;
	}

	public Boolean getRetired() {
		return retired;
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}
}
