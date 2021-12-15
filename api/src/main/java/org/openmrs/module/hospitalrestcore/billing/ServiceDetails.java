/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.math.BigDecimal;

/**
 * @author Ghanshyam
 *
 */
public class ServiceDetails {

	private String conUuid;
	private BigDecimal price;
	private Boolean disable;

	/**
	 * @return the conUuid
	 */
	public String getConUuid() {
		return conUuid;
	}

	/**
	 * @param conUuid the conUuid to set
	 */
	public void setConUuid(String conUuid) {
		this.conUuid = conUuid;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the disable
	 */
	public Boolean getDisable() {
		return disable;
	}

	/**
	 * @param disable the disable to set
	 */
	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

}
