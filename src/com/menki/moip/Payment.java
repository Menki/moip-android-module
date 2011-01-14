package com.menki.moip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Payment implements Serializable{
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attibutes
	 */
	private String brand;
	private String creditCardNumber;
	private Date expirationDate;
	private String secureCode;
	private String ownerName;
	private String ownerIdentificationType;
	private String ownerIdentificationNumber;
	private String ownerPhoneNumber;
	private Date bornDate;
	private int installments;
	private String paymentType;
	private String fullName;
	private String email;
	private String cellPhone;
	private String payerIdentificationType;
	private String payerIdentificationNumber;
	private String streetAddress;
	private int streetNumber;
	private String streetComplement;
	private String neighborhood;
	private String city;
	private String state;
	private String zipCode;
	private String fixPhone;
	private ArrayList<String> changes;
	private ArrayList<String> errors;
	
	public Boolean save() {
		if (!isChangesValid())	return false;
		
		//TODO: Implement this method, that must save every changed attribute in preferences.
		
		return true;
	}
	
	public Boolean isChangesValid() {
		if(false) errors.add("");
		
		//TODO: Implement this method, that must check if for any of the changed attributes the new value is valid.
		
		return errors.isEmpty();
	}

	/**
	 * Getters and Setters
	 */
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		if (!brand.equals(getBrand())) {
			changes.add("brand");
			this.brand = brand;
		}
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		if (!creditCardNumber.equals(getCreditCardNumber())) {
			changes.add("credit_card");
			this.creditCardNumber = creditCardNumber;
		}
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		if (!expirationDate.equals(getExpirationDate())) {
			changes.add("expiration_date");
			this.expirationDate = expirationDate;
		}
	}
	public String getSecureCode() {
		return secureCode;
	}
	public void setSecureCode(String secureCode) {
		if (!secureCode.equals(getSecureCode())) {
			changes.add("secure_code");
			this.secureCode = secureCode;
		}
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		if (!ownerName.equals(getOwnerName())) {
			changes.add("owner_name");
			this.ownerName = ownerName;
		}
	}
	public String getOwnerIdentificationType() {
		return ownerIdentificationType;
	}
	public void setOwnerIdentificationType(String ownerIdentificationType) {
		if (!ownerIdentificationType.equals(getOwnerIdentificationType())) {
			changes.add("identification_type");
			this.ownerIdentificationType = ownerIdentificationType;
		}
	}
	public String getOwnerIdentificationNumber() {
		return ownerIdentificationNumber;
	}
	public void setOwnerIdentificationNumber(String ownerIdentificationNumber) {
		if (!ownerIdentificationNumber.equals(getOwnerIdentificationNumber())) {
			changes.add("owner_identification_numbers");
			this.ownerIdentificationNumber = ownerIdentificationNumber;
		}
	}
	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}
	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		if (!ownerPhoneNumber.equals(getOwnerPhoneNumber())) {
			changes.add("owner_phone_number");
			this.ownerPhoneNumber = ownerPhoneNumber;
		}
	}
	public Date getBornDate() {
		return bornDate;
	}
	public void setBornDate(Date bornDate) {
		if (!bornDate.equals(getBornDate())) {
			changes.add("born_date");
			this.bornDate = bornDate;
		}
	}
	public int getInstallments() {
		return installments;
	}
	public void setInstallments(int installments) {
		if (installments != getInstallments()) {
			changes.add("installments");
			this.installments = installments;
		}
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		if (!paymentType.equals(getPaymentType())) {
			changes.add("payment_type");
			this.paymentType = paymentType;
		}
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		if (!fullName.equals(getFullName())) {
			changes.add("full_name");
			this.fullName = fullName;
		}
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if (!email.equals(getEmail())) {
			changes.add("email");
			this.email = email;
		}
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		if (!cellPhone.equals(getCellPhone())) {
			changes.add("cell_phone");
			this.cellPhone = cellPhone;
		}
	}
	public String getPayerIdentificationType() {
		return payerIdentificationType;
	}
	public void setPayerIdentificationType(String payerIdentificationType) {
		if (!payerIdentificationType.equals(getPayerIdentificationType())) {
			changes.add("identification_type");
			this.payerIdentificationType = payerIdentificationType;
		}
	}
	public String getPayerIdentificationNumber() {
		return payerIdentificationNumber;
	}
	public void setPayerIdentificationNumber(String payerIdentificationNumber) {
		if (!payerIdentificationNumber.equals(getPayerIdentificationNumber())) {
			changes.add("payer_identification_number");
			this.payerIdentificationNumber = payerIdentificationNumber;
		}
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		if (!streetAddress.equals(getStreetAddress())) {
			changes.add("street_address");
			this.streetAddress = streetAddress;
		}
	}
	public int getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(int streetNumber) {
		if (streetNumber != getStreetNumber()) {
			changes.add("street_number");
			this.streetNumber = streetNumber;
		}
	}
	public String getStreetComplement() {
		return streetComplement;
	}
	public void setStreetComplement(String streetComplement) {
		if (!streetAddress.equals(getStreetAddress())) {
			changes.add("street_address");
			this.streetComplement = streetComplement;
		}
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		if (!neighborhood.equals(getNeighborhood())) {
			changes.add("neighborhood");
			this.neighborhood = neighborhood;
		}
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		if (!city.equals(getCity())) {
			changes.add("city");
			this.city = city;
		}
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if (!state.equals(getState())) {
			changes.add("state");
			this.state = state;
		}
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		if (!zipCode.equals(getZipCode())) {
			changes.add("zip_code");
			this.zipCode = zipCode;
		}
	}
	public String getFixPhone() {
		return fixPhone;
	}
	public void setFixPhone(String fixPhone) {
		if (!fixPhone.equals(getFixPhone())) {
			changes.add("fix_phone");
			this.fixPhone = fixPhone;
		}
	}
}
