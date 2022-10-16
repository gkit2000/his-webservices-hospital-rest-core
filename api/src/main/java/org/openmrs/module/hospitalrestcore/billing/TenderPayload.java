/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ghanshyam
 *
 */
public class TenderPayload {

	private int number;
	private String name;
	private String description;
	private Date openingDate;
	private Date closingDate;
	private BigDecimal price;
	private String uuid;

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the openingDate
	 */
	public Date getOpeningDate() {
		return openingDate;
	}

	/**
	 * @param openingDate the openingDate to set
	 */
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	/**
	 * @return the closingDate
	 */
	public Date getClosingDate() {
		return closingDate;
	}

	/**
	 * @param closingDate the closingDate to set
	 */
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
