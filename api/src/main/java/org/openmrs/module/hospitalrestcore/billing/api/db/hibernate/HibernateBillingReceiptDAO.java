/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.BillingReceipt;
import org.openmrs.module.hospitalrestcore.billing.api.db.BillingReceiptDAO;

/**
 * @author Ghanshyam
 *
 */
public class HibernateBillingReceiptDAO extends HibernateSingleClassDAO implements BillingReceiptDAO {

	public HibernateBillingReceiptDAO() {
		super(BillingReceipt.class);
	}
	
}
