/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.math.BigDecimal;

/**
 * @author Ghanshyam
 *
 */
public class BillableServiceDetails {

	private String serviceConUuid;
	private String serviceConName;
	private BigDecimal price;
	private Integer quantity;
	private Integer opdOrderId;

	/**
	 * @return the serviceConUuid
	 */
	public String getServiceConUuid() {
		return serviceConUuid;
	}

	/**
	 * @param serviceConUuid the serviceConUuid to set
	 */
	public void setServiceConUuid(String serviceConUuid) {
		this.serviceConUuid = serviceConUuid;
	}

	/**
	 * @return the serviceConName
	 */
	public String getServiceConName() {
		return serviceConName;
	}

	/**
	 * @param serviceConName the serviceConName to set
	 */
	public void setServiceConName(String serviceConName) {
		this.serviceConName = serviceConName;
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
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the opdOrderId
	 */
	public Integer getOpdOrderId() {
		return opdOrderId;
	}

	/**
	 * @param opdOrderId the opdOrderId to set
	 */
	public void setOpdOrderId(Integer opdOrderId) {
		this.opdOrderId = opdOrderId;
	}

}
