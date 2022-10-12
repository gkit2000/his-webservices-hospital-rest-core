/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.BaseDetails;

/**
 * @author Ghanshyam
 *
 */
public class InventoryStoreDetails extends BaseDetails {

	private Integer id;
	private String name;
	private String roleName;
	private String roleUuid;
	private String code;
	private String storeUuid;
	private Boolean isPharmacy;
	private String parentUuid;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleUuid
	 */
	public String getRoleUuid() {
		return roleUuid;
	}

	/**
	 * @param roleUuid the roleUuid to set
	 */
	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the storeUuid
	 */
	public String getStoreUuid() {
		return storeUuid;
	}

	/**
	 * @param storeUuid the storeUuid to set
	 */
	public void setStoreUuid(String storeUuid) {
		this.storeUuid = storeUuid;
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

	/**
	 * @return the parentUuid
	 */
	public String getParentUuid() {
		return parentUuid;
	}

	/**
	 * @param parentUuid the parentUuid to set
	 */
	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

}
