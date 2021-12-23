/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class OrderDTO {

	private List<TestOrderDetails> testOrderDetails;

	/**
	 * @return the testOrderDetails
	 */
	public List<TestOrderDetails> getTestOrderDetails() {
		return testOrderDetails;
	}

	/**
	 * @param testOrderDetails the testOrderDetails to set
	 */
	public void setTestOrderDetails(List<TestOrderDetails> testOrderDetails) {
		this.testOrderDetails = testOrderDetails;
	}

}
