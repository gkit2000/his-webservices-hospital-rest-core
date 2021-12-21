/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db.hibernate;

import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.OpdTestOrder;
import org.openmrs.module.hospitalrestcore.billing.api.db.OpdTestOrderDAO;

/**
 * @author Ghanshyam
 *
 */
public class HibernateOpdTestOrderDAO extends HibernateSingleClassDAO implements OpdTestOrderDAO {
	
	public HibernateOpdTestOrderDAO() {
		super(OpdTestOrder.class);
	}
}
