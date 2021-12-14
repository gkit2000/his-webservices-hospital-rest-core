/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class ServicesDetails {

	private List<ServiceDetails> servicesDetails;

	/**
	 * @return the servicesDetails
	 */
	public List<ServiceDetails> getServicesDetails() {
		return servicesDetails;
	}

	/**
	 * @param servicesDetails the servicesDetails to set
	 */
	public void setServicesDetails(List<ServiceDetails> servicesDetails) {
		this.servicesDetails = servicesDetails;
	}

}
