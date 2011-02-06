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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.menki.moip.models.MoIPResponse;

import android.util.Log;

public class MoIPXmlParser 
{
	private MoIPResponse responseObj = new MoIPResponse( );;
	
	public MoIPXmlParser( ){ }
	
	
   /**
	* Parse the direct payment transaction response received from the server
	* Response template:
	*<pre>
	* &lt;ns1:EnviarInstrucaoUnicaResponse xmlns:ns1="http://www.moip.com.br/ws/alpha/"&gt;
	*   &lt;Resposta&gt;
	*	&lt;ID&gt;000000000000000000&lt;/ID&gt;
	*	&lt;Status&gt;Falha&lt;/Status&gt;
	*	&lt;Erro Codigo="XXX"&gt;Message;/Erro&gt;
	*	&lt;Erro Codigo="XXX"&gt;Message&lt;/Erro&gt;
	*  &lt;/Resposta&gt;
	*&lt;/ns1:EnviarInstrucaoUnicaResponse&gt;
	*
	*
	*&lt;ns1:EnviarInstrucaoUnicaResponse xmlns:ns1="https://desenvolvedor.moip.com.br/sandbox/"&gt;
	* &lt;Resposta&gt;
	*   &lt;ID&gt;200807272314444710000000000022&lt;/ID&gt;
	*   &lt;Status&gt;Sucesso&lt;/Status&gt;
	*   &lt;Token&gt;T2N0L0X8E0S71217U2H3W1T4F4S4G4K731D010V0S0V0S080M010E0Q082X2&lt;/Token&gt;
	*    &lt;RespostaPagamentoDireto&gt;
	*      &lt;TotalPago&gt;213.25&lt;/TotalPago&gt;
	*      &lt;TaxaMoIP&gt;15.19&lt;/TaxaMoIP&gt;
	*      &lt;Status&gt;EmAnalise&lt;/Status&gt;
	*      &lt;CodigoMoIP&gt;0000.0006.9922&lt;/CodigoMoIP&gt;
	*      &lt;Mensagem&gt;Transação com Sucesso&lt;/Mensagem&gt;
	*      &lt;CodigoAutorizacao&gt;396822&lt;/CodigoAutorizacao&gt;
	*      &lt;CodigoRetorno0&lt;/CodigoRetorno&gt;
	*   &lt;/RespostaPagamentoDireto&gt;
	* &lt;/Resposta&gt;
	*&lt;/ns1:EnviarInstrucaoUnicaResponse&gt;
	* </pre>
    *
    * @param msg the InputStream received from the server
    * @return true or false
    * @throws ParserConfigurationException
    * @throws IOException
    * @throws SAXException
    * @throws DOMException
	*/
	public boolean parseDirectPaymentResponse(InputStream msg)
	{
		String responseToken = "ERROR";
		
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(msg);
			NodeList nodeList = doc.getElementsByTagName("Status");
			if(nodeList.item(0) != null)
			{
				responseToken = nodeList.item(0).getChildNodes( ).item(0).getNodeValue( );
				if(responseToken.equalsIgnoreCase("Sucesso"))
				{
					responseObj.setResponseStatus("Sucesso");
					nodeList = doc.getElementsByTagName("ID");
					if(nodeList.item(0) != null)
						responseObj.setId(nodeList.item(0).getChildNodes( ).item(0).getNodeValue( ));
					nodeList = doc.getElementsByTagName("Token");
					if(nodeList.item(0) != null)
						responseObj.setToken(nodeList.item(0).getChildNodes( ).item(0).getNodeValue( ));
					nodeList = doc.getElementsByTagName("TotalPago");
					if(nodeList.item(0) != null)
						responseObj.setAmount(nodeList.item(0).getChildNodes( ).item(0).getNodeValue( ));
					nodeList = doc.getElementsByTagName("TaxaMoIP");
					if(nodeList.item(0) != null)
						responseObj.setMoIPTax(nodeList.item(0).getChildNodes( ).item(0).getNodeValue( ));
					nodeList = doc.getElementsByTagName("Status");
					//assuming second ocurrence of "Status"
					if(nodeList.item(1) != null)
						responseObj.setTransactionStatus(nodeList.item(1).getChildNodes( ).item(0).getNodeValue( ));
					nodeList = doc.getElementsByTagName("CodigoMoIP");
					if(nodeList.item(0) != null)
						responseObj.setMoIPCode(nodeList.item(0).getChildNodes( ).item(0).getNodeValue( ));
					nodeList = doc.getElementsByTagName("CodigoRetorno");
					if(nodeList.item(0) != null)
						responseObj.setReturnCode(nodeList.item(0).getChildNodes( ).item(0).getNodeValue( ));
					nodeList = doc.getElementsByTagName("Mensagem");
					if(nodeList.item(0) != null)
						responseObj.setMessage(nodeList.item(0).getChildNodes( ).item(0).getNodeValue( ));

				}
				else
				{
					responseObj.setResponseStatus("Falha");
					nodeList = doc.getElementsByTagName("Erro");
					if(nodeList.item(0) != null)
					{
						for(int i = 0; i < nodeList.getLength( ); i++)
							responseObj.setMessage(nodeList.item(i).getChildNodes( ).item(0).getNodeValue( ));
					}
						
						
				}
			}
			else
				return false;
		
//			//Reading as String
//			BufferedReader rd = new BufferedReader(new InputStreamReader(msg));
//			String line;
//			StringBuilder sb =  new StringBuilder( );
//			while ((line = rd.readLine()) != null)
//			{	sb.append(line); }
//			rd.close();
//			if(sb.length( ) > 0)
//				responseToken = sb.toString( );

			
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
		
		return true;
	}

	public MoIPResponse getParsedResponse( ) 
	{
		return this.responseObj;
	}
}
