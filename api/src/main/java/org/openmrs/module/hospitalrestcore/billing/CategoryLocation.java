/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ghanshyam
 *
 */
public class CategoryLocation extends BaseOpenmrsObject implements Serializable {
	
	public static final long serialVersionUID = 57332L;
	
	private static final Logger log = LoggerFactory.getLogger(Concept.class);
	
	private Integer categoryLocationMappingId;
	
	private Concept priceCategoryConcept;
	
	private Location location;
	
	private Boolean deleted = false;
	
    private User creator;
	
	private Date dateCreated;
	
	private User changedBy;
	
	private Date dateChanged;

	/**
	 * @return the categoryLocationMappingId
	 */
	public Integer getCategoryLocationMappingId() {
		return categoryLocationMappingId;
	}

	/**
	 * @param categoryLocationMappingId the categoryLocationMappingId to set
	 */
	public void setCategoryLocationMappingId(Integer categoryLocationMappingId) {
		this.categoryLocationMappingId = categoryLocationMappingId;
	}

	/**
	 * @return the priceCategoryConcept
	 */
	public Concept getPriceCategoryConcept() {
		return priceCategoryConcept;
	}

	/**
	 * @param priceCategoryConcept the priceCategoryConcept to set
	 */
	public void setPriceCategoryConcept(Concept priceCategoryConcept) {
		this.priceCategoryConcept = priceCategoryConcept;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the changedBy
	 */
	public User getChangedBy() {
		return changedBy;
	}

	/**
	 * @param changedBy the changedBy to set
	 */
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	/**
	 * @return the dateChanged
	 */
	public Date getDateChanged() {
		return dateChanged;
	}

	/**
	 * @param dateChanged the dateChanged to set
	 */
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

}
