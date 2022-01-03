/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Ghanshyam
 *
 */
public class ServiceDetails {

	@NotNull(message = "Please provide billable service concept")
	@NotBlank(message = "Please provide billable service concept")
	private String conUuid;
	
	@NotNull(message = "Please provide billable service price")
	private BigDecimal price;
	
	private Boolean enable;

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
	 * @return the enable
	 */
	public Boolean getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
