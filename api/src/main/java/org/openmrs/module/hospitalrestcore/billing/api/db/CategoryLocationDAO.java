/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing.api.db;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.billing.CategoryLocation;

/**
 * @author Ghanshyam
 *
 */
public interface CategoryLocationDAO extends SingleClassDAO {
	
	public List<CategoryLocation> getAllCategoryLocation() throws DAOException;

	public List<CategoryLocation> getCategoryLocationByPriceCategory(Concept priceCategoryConcept) throws DAOException;
	
	public CategoryLocation getCategoryLocationByLocation(Location location) throws DAOException;
	
	public CategoryLocation getCategoryLocationByPriceCategoryAndLocation(Concept priceCategoryConcept,Location location) throws DAOException;

}
