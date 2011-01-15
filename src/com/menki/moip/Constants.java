/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */
package com.menki.moip;

import java.text.SimpleDateFormat;


public interface Constants 
{
	//Payment types support by the button
	public static enum PaymentType { NONE, PAGAMENTO_DIRETO }; 
	
	//Connection remote server
	public static enum RemoteServer { NONE, TEST, PRODUCTION}; 
	
	//Connectioin URLs
	public static final String TEST_SERVER = "https://desenvolvedor.moip.com.br/sandbox";
	public static final String PRODUCTION_SERVER = "https://www.moip.com.br";
	
	//Date formats
	public static final SimpleDateFormat MONTH_AND_YEAR = new SimpleDateFormat("MM/yyyy");
	public static final SimpleDateFormat DAY_MONTH_AND_YEAR = new SimpleDateFormat("dd/MM/yyyy");
}
