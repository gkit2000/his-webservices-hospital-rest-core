/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

import java.util.ArrayList;

/**
 * @author Ghanshyam
 *
 */
public class Patient {
	private String id;
    private String name;
    private String gender;
    private int yearOfBirth;
    private Address address;
    private ArrayList<Identifier> identifiers;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the yearOfBirth
	 */
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	/**
	 * @param yearOfBirth the yearOfBirth to set
	 */
	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return the identifiers
	 */
	public ArrayList<Identifier> getIdentifiers() {
		return identifiers;
	}
	/**
	 * @param identifiers the identifiers to set
	 */
	public void setIdentifiers(ArrayList<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
}
