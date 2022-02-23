/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Ghanshyam
 *
 */
public class CategoryLocationDTO {

	@NotNull(message = "Please provide price category concept uuid")
	@NotBlank(message = "Please provide price category concept uuid")
	private String priceCategoryConUuid;

	@NotNull(message = "Please provide location uuid")
	@NotBlank(message = "Please provide locatiion uuid")
	private List<String> locationUuids;
	
	private Boolean deleted;

	/**
	 * @return the priceCategoryConUuid
	 */
	public String getPriceCategoryConUuid() {
		return priceCategoryConUuid;
	}

	/**
	 * @param priceCategoryConUuid the priceCategoryConUuid to set
	 */
	public void setPriceCategoryConUuid(String priceCategoryConUuid) {
		this.priceCategoryConUuid = priceCategoryConUuid;
	}

	/**
	 * @return the locationUuids
	 */
	public List<String> getLocationUuids() {
		return locationUuids;
	}

	/**
	 * @param locationUuids the locationUuids to set
	 */
	public void setLocationUuids(List<String> locationUuids) {
		this.locationUuids = locationUuids;
	}

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
