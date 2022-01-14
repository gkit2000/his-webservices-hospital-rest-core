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

	@NotNull(message = "Please provide billable service concept uuid")
	@NotBlank(message = "Please provide billable service concept uuid")
	private String serviceConUuid;
	
	@NotNull(message = "Please provide billable service price category concept uuid")
	@NotBlank(message = "Please provide billable service price category concept uuid")
	private String priceCategoryConUuid;
	
	@NotNull(message = "Please provide billable service price")
	private BigDecimal price;
	
	private Boolean enable;
	

	public String getServiceConUuid() {
		return serviceConUuid;
	}

	public void setServiceConUuid(String serviceConUuid) {
		this.serviceConUuid = serviceConUuid;
	}

	public String getPriceCategoryConUuid() {
		return priceCategoryConUuid;
	}

	public void setPriceCategoryConUuid(String priceCategoryConUuid) {
		this.priceCategoryConUuid = priceCategoryConUuid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
