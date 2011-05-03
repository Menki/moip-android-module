/**
 * Copyright (c) 2011, MENKI MOBILE SOLUTIONS - http://www.menkimobile.com.br
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation and/or 
 *   other materials provided with the distribution.
 * * Neither the name of the MENKI MOBILE SOLUTIONS nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission.
 *   
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 *  SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 *  TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 *  BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 *  ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF 
 *  SUCH DAMAGE. 
 *  
 *  @version 0.0.1
 */


package com.menki.moip.paymentmethods;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.menki.moip.utils.Base64;
import com.menki.moip.utils.Config;
import com.menki.moip.utils.MoIPResponse;
import com.menki.moip.utils.MoIPXmlBuilder;
import com.menki.moip.utils.MoIPXmlParser;
import com.menki.moip.utils.Config.RemoteServer;

public class PagamentoDireto implements Serializable
{
	public static enum OwnerIdType {CPF, RG};
	
	private MoIPResponse response;
	
	private RemoteServer serverType;
	
	private String value;	
	private String brand; 
	private String creditCardNumber; 	
	private String expirationDate;	
	private String secureCode;
	private String ownerName;
	private OwnerIdType ownerIdType; //Determines if this is a CPF or RG number.
	private String ownerIdNumber;
	private String ownerPhoneNumber;
	private String ownerBirthDate;
	private String installmentsQuantity;
	private String streetAddress;
	private String streetNumberAddress;
	private String addressComplement;
	private String neighborhood;
	private String city;
	private String state;
	private String country;
	private String zipCode;

	public PagamentoDireto() 
	{
		super();
		
		this.listener = null;
		this.response = new MoIPResponse();
		this.serverType = RemoteServer.NONE;
		this.value = null;
		this.brand = null; 
		this.creditCardNumber = null;
		this.expirationDate = null;	
		this.secureCode = null;
		this.ownerName = null;
		this.ownerIdType = null;
		this.ownerIdNumber = null;
		this.ownerPhoneNumber = null;
		this.ownerBirthDate = null;
		this.installmentsQuantity = null;
		this.streetAddress = null;
		this.streetNumberAddress = null;
		this.addressComplement = null;
		this.neighborhood = null;
		this.city = null;
		this.state = null;
		this.country = null;
		this.zipCode = null;		
	}

	private OnPaymentListener listener;
	
	public void setOnPaymentListener(OnPaymentListener listener)
	{
		this.listener = listener;
	}
	
	public void pay()
	{
		MoIPXmlBuilder builder = new MoIPXmlBuilder();
		MoIPXmlParser parser = new MoIPXmlParser();
		String msg = builder.getDirectPaymentMessage(this);
				
		try
		{
			DefaultHttpClient client = new DefaultHttpClient();
		 
			HttpPost post;
			if(this.serverType == RemoteServer.TEST)
			{
				post = new HttpPost(Config.TEST_SERVER);
			}
			else
			{
				post = new HttpPost(Config.PRODUCTION_SERVER);
			}
			
			byte[] auth = (Config.TOKEN + ":" + Config.KEY).getBytes();
			post.addHeader("Authorization", "Basic " + new String(Base64.encodeBytes(auth)));
			StringEntity entity = new StringEntity(msg.trim(), "UTF-8");
			entity.setContentType("application/x-www-formurlencoded");
			post.setEntity(entity);
			Log.i("MENKI [PostPaymentMessage] ", entity.getContent().toString());
			  
			HttpResponse response = client.execute(post);
			
			InputStream in = response.getEntity().getContent(); 
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{ 
				//TODO: handle server erros
				this.response.setResponseStatus("Server error");
				
			}
			else
			{
				if(parser.parseDirectPaymentResponse(in))
				{
					this.response = parser.getParsedResponse();
				}
				else
				{
					Log.e("[MENKI]","[performDirectPaymentTransaction] - Error parsing response");
				}
			}
			
		}
		catch (ClientProtocolException e) 
		{
			Log.e("MENKI [performDirectPaymentTransaction] ", e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			Log.e("MENKI [performDirectPaymentTransaction] ", e.getMessage());
			e.printStackTrace();
		}	
		catch (IllegalArgumentException e)
		{
			Log.e("MENKI [performDirectPaymentTransaction] ", e.getMessage());
			e.printStackTrace();
		}
		
		if (this.response.getResponseStatus() == "Sucesso")
		{
			this.listener.onPaymentSuccess(this.response);
		}
		else
		{
			this.listener.onPaymentFail(this.response);
		}
	}

	public MoIPResponse getResponse() {
		return response;
	}

	public void setResponse(MoIPResponse response) {
		this.response = response;
	}

	public RemoteServer getServerType() {
		return serverType;
	}

	public void setServerType(RemoteServer serverType) {
		this.serverType = serverType;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getSecureCode() {
		return secureCode;
	}

	public void setSecureCode(String secureCode) {
		this.secureCode = secureCode;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerIdNumber() {
		return ownerIdNumber;
	}

	public void setOwnerIdNumber(String ownerIdNumber) {
		this.ownerIdNumber = ownerIdNumber;
	}

	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}

	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}

	public String getOwnerBirthDate() {
		return ownerBirthDate;
	}

	public void setOwnerBirthDate(String ownerBirthDate) {
		this.ownerBirthDate = ownerBirthDate;
	}

	public String getInstallmentsQuantity() {
		return installmentsQuantity;
	}

	public void setInstallmentsQuantity(String installmentsQuantity) {
		this.installmentsQuantity = installmentsQuantity;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getAddressComplement() {
		return addressComplement;
	}

	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public OnPaymentListener getListener() {
		return listener;
	}

	public void setListener(OnPaymentListener listener) {
		this.listener = listener;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

	public String getStreetNumberAddress() {
		return streetNumberAddress;
	}

	public void setStreetNumberAddress(String streetNumberAddress) {
		this.streetNumberAddress = streetNumberAddress;
	}

	public OwnerIdType getOwnerIdType() {
		return ownerIdType;
	}

	public void setOwnerIdType(OwnerIdType ownerIdType) {
		this.ownerIdType = ownerIdType;
	}
}