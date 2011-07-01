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

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.menki.moip.utils.Base64;
import com.menki.moip.utils.Config;
import com.menki.moip.utils.Config.RemoteServer;
import com.menki.moip.utils.MoIPResponse;
import com.menki.moip.utils.MoIPXmlBuilder;
import com.menki.moip.utils.MoIPXmlParser;

public class PagamentoDireto implements Parcelable
{
	/**
	 * 
	 */
//	private static final long serialVersionUID = -1435309736184833378L;

	public static enum OwnerIdType {CPF, RG};
	public static enum PaymentType {AVISTA, PRAZO};
	
	private MoIPResponse response;
	public static OnPaymentListener listener = null;
	
	private RemoteServer serverType;
	
	private String token;
	private String key;
	private String value;	
	private String brand; 
	private String creditCardNumber; 	
	private String expirationDate;	
	private String secureCode;
	private String ownerName;
	private OwnerIdType ownerIdType; //Determines if this is a CPF or RG number.
	private String ownerIdNumber;
	private String ownerBirthDate;
	private PaymentType paymentType; //Determines if payment type is AVISTA or PRAZO.
	private String installmentsQuantity;
	private String email;
	private String cellPhone;
	private String streetAddress;
	private String streetNumberAddress;
	private String addressComplement;
	private String neighborhood;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String fixedPhone;

	public PagamentoDireto() 
	{
		super();
		
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
		this.ownerBirthDate = null;
		this.paymentType = null;
		this.installmentsQuantity = null;
		this.email = null;
		this.cellPhone = null;
		this.streetAddress = null;
		this.streetNumberAddress = null;
		this.addressComplement = null;
		this.neighborhood = null;
		this.city = null;
		this.state = null;
		this.country = null;
		this.zipCode = null;
		this.fixedPhone = null;
	}

	public PagamentoDireto(Parcel in) 
	{					
		this.listener = this.getListener();
		this.response = new MoIPResponse();
		
		int server = in.readInt(); 
		
		if (server == RemoteServer.NONE.ordinal())
		{
			this.serverType = RemoteServer.NONE;	
		}
		else if (server == RemoteServer.PRODUCTION.ordinal())
		{
			this.serverType = RemoteServer.PRODUCTION;
		}
		else
		{
			this.serverType = RemoteServer.TEST;
		}
		
		this.key = in.readString();
		this.token = in.readString();
		this.value = in.readString();	
		this.brand = in.readString(); 
		this.creditCardNumber = in.readString(); 	
		this.expirationDate = in.readString();	
		this.secureCode = in.readString();
		this.ownerName = in.readString();
				
		int owner = in.readInt(); 
		
		if (owner == OwnerIdType.CPF.ordinal())
		{
			this.ownerIdType = OwnerIdType.CPF;	
		}
		else
		{
			this.ownerIdType = OwnerIdType.RG;
		}
		
		
		this.ownerIdNumber = in.readString();
		this.ownerBirthDate = in.readString();
		this.installmentsQuantity = in.readString();
		this.streetAddress = in.readString();
		this.streetNumberAddress = in.readString();
		this.addressComplement = in.readString();
		this.neighborhood = in.readString();
		this.city = in.readString();
		this.state = in.readString();
		this.country = in.readString();
		this.zipCode = in.readString();
	}
	

	public void setOnPaymentListener(OnPaymentListener listener)
	{
		PagamentoDireto.listener = listener;
	}
	
	public OnPaymentListener getOnPaymentListener()
	{
		return PagamentoDireto.listener;
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
			
			byte[] auth = (token + ":" + key).getBytes();
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
		
		
		
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				if (PagamentoDireto.this.response.getResponseStatus() == "Sucesso")
				{
					PagamentoDireto.listener.onPaymentSuccess(PagamentoDireto.this.response);
				}
				else
				{
					PagamentoDireto.listener.onPaymentFail(PagamentoDireto.this.response);
				}
				Looper.loop();
			}
		};
		// TODO: Verificar como chamar as callbacks para o caso de integração direta (sem UI)
		
		Thread thread = new Thread(runnable);
		thread.start();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getOwnerBirthDate() {
		return ownerBirthDate;
	}

	public void setOwnerBirthDate(String ownerBirthDate) {
		this.ownerBirthDate = ownerBirthDate;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	public void setPaymentType(String paymentType) {
		if (paymentType.equals("PARCELADO")) 
			this.paymentType = PaymentType.PRAZO;
		else
			this.paymentType = PaymentType.AVISTA;
	}
	
	public String getInstallmentsQuantity() {
		return installmentsQuantity;
	}

	public void setInstallmentsQuantity(String installmentsQuantity) {
		this.installmentsQuantity = installmentsQuantity;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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
	
	public String getFixedPhone() {
		return fixedPhone;
	}

	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
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

	public void setOwnerIdType(String ownerIdType) {
		if (ownerIdType.equals("CPF")) 
			this.ownerIdType = OwnerIdType.CPF;
		else
			this.ownerIdType = OwnerIdType.RG;
	}
	
	public void setOwnerIdType(OwnerIdType ownerIdType) {
		this.ownerIdType = ownerIdType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel source, int arg1) 
	{
		source.writeInt(this.serverType.ordinal());
		source.writeString(this.key);
		source.writeString(this.token);
		source.writeString(this.value);	
		source.writeString(this.brand); 
		source.writeString(this.creditCardNumber); 	
		source.writeString(this.expirationDate);	
		source.writeString(this.secureCode);
		source.writeString(this.ownerName);
		
		if (this.ownerIdType != null)
			source.writeInt(this.ownerIdType.ordinal());
		
		source.writeString(this.ownerIdNumber);
		source.writeString(this.ownerBirthDate);
		source.writeString(this.installmentsQuantity);
		source.writeString(this.streetAddress);
		source.writeString(this.streetNumberAddress);
		source.writeString(this.addressComplement);
		source.writeString(this.neighborhood);
		source.writeString(this.city);
		source.writeString(this.state);
		source.writeString(this.country);
		source.writeString(this.zipCode);
	}
	
	public static final Parcelable.Creator<PagamentoDireto> CREATOR	= new Parcelable.Creator<PagamentoDireto>() 
	{
		public PagamentoDireto createFromParcel(Parcel in) {
			return new PagamentoDireto(in);
		}

		public PagamentoDireto[] newArray(int size) {
			return new PagamentoDireto[size];
		}
	};
	
	
}
