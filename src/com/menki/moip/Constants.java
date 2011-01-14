/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */
package com.menki.moip;


public interface Constants 
{
	//Define o tipo de pagamento a ser passado para o Botão
	public static enum PaymentType { NONE, PAGAMENTO_DIRETO }; 
	
	//Define o destino da conexão
	public static enum RemoteServer { NONE, TEST, PRODUCTION}; 
	
	//URLs de conexão
	public static final String TEST_SERVER = "https://desenvolvedor.moip.com.br/sandbox";
	public static final String PRODUCTION_SERVER = "https://www.moip.com.br";
}
