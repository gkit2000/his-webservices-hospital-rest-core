/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class ServiceDetailsForTestOrder {

	private Integer encounterId;
	private String encounterUuid;
	private Integer locationId;
	private String locationUuid;
	private String locationName;
	private List<BillableServiceDetails> billableServiceDetails;

	/**
	 * @return the encounterId
	 */
	public Integer getEncounterId() {
		return encounterId;
	}

	/**
	 * @param encounterId the encounterId to set
	 */
	public void setEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
	}

	/**
	 * @return the encounterUuid
	 */
	public String getEncounterUuid() {
		return encounterUuid;
	}

	/**
	 * @param encounterUuid the encounterUuid to set
	 */
	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the billableServiceDetails
	 */
	public List<BillableServiceDetails> getBillableServiceDetails() {
		return billableServiceDetails;
	}

	/**
	 * @param billableServiceDetails the billableServiceDetails to set
	 */
	public void setBillableServiceDetails(List<BillableServiceDetails> billableServiceDetails) {
		this.billableServiceDetails = billableServiceDetails;
	}
}
