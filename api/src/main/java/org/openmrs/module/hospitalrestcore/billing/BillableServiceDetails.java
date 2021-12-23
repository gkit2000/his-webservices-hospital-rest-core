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
	
private String conUuid;
private String conName;
private BigDecimal price;

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
 * @return the conName
 */
public String getConName() {
	return conName;
}
/**
 * @param conName the conName to set
 */
public void setConName(String conName) {
	this.conName = conName;
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

}
