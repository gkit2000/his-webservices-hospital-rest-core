/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

/**
 * @author Ghanshyam
 *
 */
public class DriverPayload {

	private String name;

	private String uuid;

	private String address;

	private String description;

	private String phone;

	private String idCardTypeUuid;

	private String idCardValue;

	private boolean retired;

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
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the idCardTypeUuid
	 */
	public String getIdCardTypeUuid() {
		return idCardTypeUuid;
	}

	/**
	 * @param idCardTypeUuid the idCardTypeUuid to set
	 */
	public void setIdCardTypeUuid(String idCardTypeUuid) {
		this.idCardTypeUuid = idCardTypeUuid;
	}

	/**
	 * @return the idCardValue
	 */
	public String getIdCardValue() {
		return idCardValue;
	}

	/**
	 * @param idCardValue the idCardValue to set
	 */
	public void setIdCardValue(String idCardValue) {
		this.idCardValue = idCardValue;
	}

	/**
	 * @return the retired
	 */
	public boolean isRetired() {
		return retired;
	}

	/**
	 * @param retired the retired to set
	 */
	public void setRetired(boolean retired) {
		this.retired = retired;
	}

}
