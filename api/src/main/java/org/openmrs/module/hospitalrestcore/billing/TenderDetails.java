/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

/**
 * @author Ghanshyam
 *
 */
public class TenderDetails {

	private String tenderUuid;
	private int quantity;

	/**
	 * @return the tenderUuid
	 */
	public String getTenderUuid() {
		return tenderUuid;
	}

	/**
	 * @param tenderUuid the tenderUuid to set
	 */
	public void setTenderUuid(String tenderUuid) {
		this.tenderUuid = tenderUuid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
