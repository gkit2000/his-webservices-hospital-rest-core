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
import java.math.BigDecimal;

import org.openmrs.Concept;

/**
 *
 */

public class BillableService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer serviceId;

	private Concept serviceConcept;

	private String name;

	private String shortName;

	private Concept serviceCategoryConcept;

	private Concept priceCategoryConcept;

	private BigDecimal price;

	private Boolean enable = true;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Concept getServiceConcept() {
		return serviceConcept;
	}

	public void setServiceConcept(Concept serviceConcept) {
		this.serviceConcept = serviceConcept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Concept getServiceCategoryConcept() {
		return serviceCategoryConcept;
	}

	public void setServiceCategoryConcept(Concept serviceCategoryConcept) {
		this.serviceCategoryConcept = serviceCategoryConcept;
	}

	public Concept getPriceCategoryConcept() {
		return priceCategoryConcept;
	}

	public void setPriceCategoryConcept(Concept priceCategoryConcept) {
		this.priceCategoryConcept = priceCategoryConcept;
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
