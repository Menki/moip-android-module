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

package com.menki.moip.utils;

import java.io.Serializable;
import java.util.ArrayList;


public class MoIPResponse implements Serializable 
{	
	public String id;
	public String responseStatus;
	public String token;
	public String amount;
	public String MoIPTax;
	public String transactionStatus;
	public String moIPCode;
	public String authCode;
	public String returnCode;
	/* messages will be set with error messages in case
	 * responseStatus is "Falha" */
	public  ArrayList<String> messagesList;
	
	public MoIPResponse( )
	{
		this.id = null;
		this.responseStatus = null;
		this.token = null;
		this.amount = null;
		this.MoIPTax = null;
		this.transactionStatus = null;
		this.moIPCode = null;
		this.authCode = null;
		this.returnCode = null;
		this.messagesList = new ArrayList<String>( );
	}
	
//	private void writeObject(java.io.ObjectOutputStream out) throws IOException
//	{
//		out.writeObject(id);
//		out.writeObject(responseStatus);
//		out.writeObject(token);
//		out.writeObject(amount);
//		out.writeObject(MoIPTax);
//		out.writeObject(transactionStatus);
//		out.writeObject(moIPCode);
//		out.writeObject(authCode);
//		out.writeObject(returnCode);
//		//out.writeObject(messagesList);	
//	}
//	
//	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
//	{
//		responseStatus = (String)in.readObject( );
//		token = (String)in.readObject( );
//		amount = (String)in.readObject( );
//		MoIPTax = (String)in.readObject( );
//		transactionStatus = (String)in.readObject( );
//		moIPCode = (String)in.readObject( );
//		authCode = (String)in.readObject( );
//		returnCode = (String)in.readObject( );
//		messagesList = (List<String>)in.readObject( );
//	}

	public String getId( ) 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getResponseStatus( ) 
	{
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) 
	{
		this.responseStatus = responseStatus;
	}

	public String getToken( ) 
	{
		return token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}

	public String getAmount( ) 
	{
		return amount;
	}

	public void setAmount(String amount) 
	{
		this.amount = amount;
	}

	public String getMoIPTax( ) 
	{
		return MoIPTax;
	}

	public void setMoIPTax(String moIPTax) 
	{
		MoIPTax = moIPTax;
	}

	public String getTransactionStatus( ) 
	{
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) 
	{
		this.transactionStatus = transactionStatus;
	}

	public String getMoIPCode( ) 
	{
		return moIPCode;
	}

	public void setMoIPCode(String moIPCode) 
	{
		this.moIPCode = moIPCode;
	}

	public String getAuthCode( ) 
	{
		return authCode;
	}

	public void setAuthCode(String authCode) 
	{
		this.authCode = authCode;
	}

	public String getReturnCode( ) 
	{
		return returnCode;
	}

	public void setReturnCode(String returnCode) 
	{
		this.returnCode = returnCode;
	}

	public ArrayList<String> getMessagesList( ) 
	{
		return messagesList;
	}

	public void setMessage(String message) 
	{
		this.messagesList.add(message);
	}
	
	
	
}
