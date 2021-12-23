/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class TestOrderDetails {

	private String encounterUuid;
	private List<BillableServiceDetails> billableServiceDetails;

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
