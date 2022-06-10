/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Ghanshyam
 *
 */
public class AmbulanceSearchPayload {

	@NotNull(message = "Please provide search parameter")
	@NotBlank(message = "Please provide search parameter")
	private String searchText;

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}
