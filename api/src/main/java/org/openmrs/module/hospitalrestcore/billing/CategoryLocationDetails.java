/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

/**
 * @author Ghanshyam
 *
 */
public class CategoryLocationDetails {

	private String priceCategoryUuid;
	private String locationUuid;
	/**
	 * @return the priceCategoryUuid
	 */
	public String getPriceCategoryUuid() {
		return priceCategoryUuid;
	}
	/**
	 * @param priceCategoryUuid the priceCategoryUuid to set
	 */
	public void setPriceCategoryUuid(String priceCategoryUuid) {
		this.priceCategoryUuid = priceCategoryUuid;
	}
	/**
	 * @return the locationUuid
	 */
	public String getLocationUuid() {
		return locationUuid;
	}
	/**
	 * @param locationUuid the locationUuid to set
	 */
	public void setLocationUuid(String locationUuid) {
		this.locationUuid = locationUuid;
	}

}
