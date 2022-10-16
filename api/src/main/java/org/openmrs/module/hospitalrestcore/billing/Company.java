package org.openmrs.module.hospitalrestcore.billing;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

/**
 * @author Ghanshyam
 *
 */
public class Company extends CustomBaseOpenmrsObject implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer companyId;

    private String name;
    
    private String address;
    
    private String description;
    
    private String phone;

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public String getAddress() {
    	return address;
    }

	
    public void setAddress(String address) {
    	this.address = address;
    }

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
    }

	
    public String getPhone() {
    	return phone;
    }

	
    public void setPhone(String phone) {
    	this.phone = phone;
    }


	
    public Integer getCompanyId() {
    	return companyId;
    }


	
    public void setCompanyId(Integer companyId) {
    	this.companyId = companyId;
    }
}
