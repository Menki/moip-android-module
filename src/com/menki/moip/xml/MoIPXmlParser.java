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

package com.menki.moip.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class MoIPXmlParser 
{
	private String parsedResponse = null;
	
	public MoIPXmlParser( ){ }
	
	public MoIPXmlParser(InputStream msg)
	{
		String response = parseDirectPaymentResponse(msg);
		this.parsedResponse = response;
	}
	
	public String parseDirectPaymentResponse(InputStream msg)
	{
		String responseToken = "ERROR";
		
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(msg);
			NodeList nodeList = doc.getElementsByTagName("Token");
			if(nodeList.item(0) != null)
				responseToken = nodeList.item(0).getNodeValue( );
		}
		catch(ParserConfigurationException pce)
		{
			Log.e("MENKI [parseDirectPaymentResponse] ", pce.getMessage());
			pce.printStackTrace( );
		}
		catch(SAXException se)
		{
			Log.e("MENKI [parseDirectPaymentResponse] ", se.getMessage());
			se.printStackTrace( );
		}
		catch(IOException ioe)
		{
			Log.e("MENKI [parseDirectPaymentResponse] ", ioe.getMessage());
			ioe.printStackTrace( );
		}
		catch(DOMException de)
		{
			Log.e("MENKI [parseDirectPaymentResponse] ", de.getMessage());
			de.printStackTrace( );
		}
		
		return responseToken;
	}

	public String getParsedResponse() 
	{
		return parsedResponse;
	}
}
