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
private Integer opdOrderId;
private Integer locationId;
private String locationUuid;
private String locationName;

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

}
