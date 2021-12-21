/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospital-core module.
 *
 *  Hospital-core module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospital-core module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospital-core module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/



package org.openmrs.module.hospitalrestcore.billing;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.Concept;

/**
 * @author Ghanshyam
 *
 */
public class DepartmentConcept implements Serializable {
	public static final int[] TYPES = {1,2,3,4}; // 1= Diagnosis, 2=Procedure, 3=Investigation,4=Symptom
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer typeConcept;
	//private Department department;
	private Concept concept;
	private Date createdOn;
	private String createdBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTypeConcept() {
		return typeConcept;
	}
	public void setTypeConcept(Integer typeConcept) {
		this.typeConcept = typeConcept;
	}
	public Concept getConcept() {
		return concept;
	}
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
