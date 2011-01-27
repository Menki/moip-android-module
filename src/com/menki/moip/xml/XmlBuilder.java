/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */
package com.menki.moip.xml;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;
import android.util.Xml;

import com.menki.moip.PaymentMgr;



public class XmlBuilder 
{
	
	/**
	 * MoIP Payment TAGs
	 */
	private static final String TAG_ENVIARINSTRUCAO = "EnviarInstrucao";
	private static final String TAG_INSTRUCAOUNICA = "InstrucaoUnica";
	private static final String TAG_RAZAO = "Razao";
	private static final String TAG_VALORES = "Valores";
	private static final String TAG_VALOR = "Valor";
	private static final String TAG_IDPROPRIO = "IdProprio";
	private static final String TAG_PAGAMENTODIRETO = "PagamentoDireto";
	private static final String TAG_FORMA = "Forma";
	private static final String TAG_INSTITUICAO = "Instituicao";
	private static final String TAG_CARTAOCREDITO = "CartaoCredito";
	private static final String TAG_NUMERO = "Numero";
	private static final String TAG_EXPIRACAO = "Expiracao";
	private static final String TAG_CODIGOSEGURANCA = "CodigoSeguranca";
	private static final String TAG_PORTADOR = "Portador";
	private static final String TAG_NOME = "Nome";
	private static final String TAG_IDENTIDADE = "Identidade";
	private static final String TAG_TELEFONE = "Telefone";
	private static final String TAG_DATANASCIMENTO = "DataNascimento";
	private static final String TAG_PARCELAMENTO = "Parcelamento";
	private static final String TAG_PARCELAS = "Parcelas";
	private static final String TAG_RECEBIMENTO = "Recebimento";
	private static final String TAG_PAGADOR = "Pagador";
	private static final String TAG_LOGINMOIP = "LoginMoIP";
	private static final String TAG_EMAIL = "Email";
	private static final String TAG_TELEFONECELULAR = "TelefoneCelular";
	private static final String TAG_APELIDO = "Apelido";
	private static final String TAG_ENDERECOCOBRANCA = "EnderecoCobranca";
	private static final String TAG_LOGRADOURO = "Logradouro";
	private static final String TAG_COMPLEMENTO = "Complemento";
	private static final String TAG_BAIRRO = "Bairro";
	private static final String TAG_CIDADE = "Cidade";
	private static final String TAG_ESTADO = "Estado";
	private static final String TAG_PAIS = "Pais";
	private static final String TAG_CEP = "CEP";
	private static final String TAG_TELEFONEFIXO = "TelefoneFixo";
	
	
	/**
	 * MoIP Payment Attributes
	 */
	private static final String ATTR_MOEDA = "moeda";
	private static final String ATTR_TIPO = "Tipo";
	
	/**
	 * Creating XML Message for Direct Payment
	  
	 	  <EnviarInstrucao>
		  <InstrucaoUnica>
		     <Razao>Pagamento direto com cartão de crédito</Razao>
		     <Valores>
		        <Valor moeda="BRL">213.25</Valor>
		     </Valores>
		     <IdProprio>dir_card_2</IdProprio>
		     <PagamentoDireto>
		        <Forma>CartaoCredito</Forma>
		        <Instituicao>AmericanExpress</Instituicao>
		        <CartaoCredito>
		         <Numero>345678901234564</Numero>
		         <Expiracao>08/11</Expiracao>
		         <CodigoSeguranca>1234</CodigoSeguranca>
		         <Portador>
		             <Nome>Nome do Portador</Nome>
		             <Identidade Tipo="CPF">111.111.111-11</Identidade>
		             <Telefone>(11)1111-1111</Telefone>
		             <DataNascimento>30/11/1980</DataNascimento>
		         </Portador>
		        </CartaoCredito>
		        <Parcelamento>
		         <Parcelas>2</Parcelas>
		         <Recebimento>AVista</Recebimento>
		        </Parcelamento>
		     </PagamentoDireto>
		     <Pagador>
		         <Nome>Luiz Inácio Lula da Silva</Nome>
		         <LoginMoIP>lula</LoginMoIP>
		         <Email>presidente@planalto.gov.br</Email>
		         <TelefoneCelular>(61)9999-9999</TelefoneCelular>
		         <Apelido>Lula</Apelido>
		         <Identidade>111.111.111-11</Identidade>
		         <EnderecoCobranca>
		             <Logradouro>Praça dos Três Poderes</Logradouro>
		             <Numero>0</Numero>
		             <Complemento>Palácio do Planalto</Complemento>
		             <Bairro>Zona Cívico-Administrativa</Bairro>
		             <Cidade>Brasília</Cidade>
		             <Estado>DF</Estado>
		             <Pais>BRA</Pais>
		             <CEP>70100-000</CEP>
		             <TelefoneFixo>(61)3211-1221</TelefoneFixo>
		         </EnderecoCobranca>
		     </Pagador>
		  </InstrucaoUnica>
		</EnviarInstrucao>
	*/
	public String getDirectPaymentMessage( )
	{
        XmlSerializer serializer = Xml.newSerializer( );
		StringWriter writer = new StringWriter( );
		PaymentMgr mgr = PaymentMgr.getInstance( );
		
	    try 
	    {
	        serializer.setOutput(writer);
	        
	        serializer.startDocument("UTF-8", true);
	        	serializer.startTag("", TAG_ENVIARINSTRUCAO);
	        		serializer.startTag("", TAG_INSTRUCAOUNICA);
	        			serializer.startTag("", TAG_RAZAO);
	        				serializer.text("Pagamento direto com cartão de crédito");
	        			serializer.endTag("", TAG_RAZAO);
	        			serializer.startTag("", TAG_VALORES);
	        				serializer.startTag("", TAG_VALOR);
	        				serializer.attribute("", ATTR_MOEDA, "BRL");
	        					serializer.text("00"); //TODO: getAmount
	        				serializer.endTag("", TAG_VALOR);
	        			serializer.endTag("", TAG_VALORES);
	        			serializer.startTag("", TAG_IDPROPRIO);
	        				serializer.text("dir_card_2");
	        			serializer.endTag("", TAG_IDPROPRIO);
	        			serializer.startTag("", TAG_PAGAMENTODIRETO);
	        				serializer.startTag("", TAG_FORMA);
	        					serializer.text("CartaoCredito");
	        				serializer.endTag("", TAG_FORMA);
	        				serializer.startTag("", TAG_INSTITUICAO);
	        					serializer.text(mgr.getPaymentDetails( ).getBrand( ));
	        				serializer.endTag("", TAG_INSTITUICAO);
	        				serializer.startTag("", TAG_CARTAOCREDITO);
	        					serializer.startTag("", TAG_NUMERO);
	        						serializer.text(mgr.getPaymentDetails( ).getCreditCardNumber( ));
	        					serializer.endTag("", TAG_NUMERO);
	        					serializer.startTag("", TAG_EXPIRACAO);
	        						serializer.text("XX/XX");
	        					serializer.endTag("", TAG_EXPIRACAO);
	        					serializer.startTag("", TAG_CODIGOSEGURANCA);
	        						serializer.text(mgr.getPaymentDetails( ).getSecureCode( ));
	        					serializer.endTag("", TAG_CODIGOSEGURANCA);
	        					serializer.startTag("", TAG_PORTADOR);
	        						serializer.startTag("", TAG_NOME);
	        							serializer.text(mgr.getPaymentDetails( ).getOwnerName( ));
	        						serializer.endTag("", TAG_NOME);
	        						serializer.startTag("", TAG_IDENTIDADE);
	        						serializer.attribute("", ATTR_TIPO, mgr.getPaymentDetails( ).getOwnerIdentificationType( ));
	        							serializer.text(mgr.getPaymentDetails( ).getOwnerIdentificationNumber( ));
	        						serializer.endTag("", TAG_IDENTIDADE);
	        						serializer.startTag("", TAG_TELEFONE);
	        							serializer.text(mgr.getPaymentDetails( ).getOwnerPhoneNumber( ));
	        						serializer.endTag("", TAG_TELEFONE);
	        						serializer.startTag("", TAG_DATANASCIMENTO);
	        							serializer.text(mgr.getPaymentDetails( ).getBornDate( ).toString( ));//TODO: 
	        						serializer.endTag("", TAG_DATANASCIMENTO);
	        					serializer.endTag("", TAG_PORTADOR);
	        				serializer.endTag("", TAG_CARTAOCREDITO);
	        				serializer.startTag("", TAG_PARCELAMENTO);
	        					serializer.startTag("", TAG_PARCELAS);
	        						Integer installments = mgr.getPaymentDetails( ).getInstallments( ); 
	        						serializer.text(installments.toString( ));
	        					serializer.endTag("", TAG_PARCELAS);
	        					serializer.startTag("", TAG_RECEBIMENTO);
	        						String receb = "AVista";
	        						if(installments > 0)
	        							receb = "APrazo"; //TODO
	        						serializer.text(receb);
	        					serializer.endTag("", TAG_RECEBIMENTO);
	        				serializer.endTag("", TAG_PARCELAMENTO);
	        			serializer.endTag("", TAG_PAGAMENTODIRETO);
	        			serializer.startTag("", TAG_PAGADOR);
	        				serializer.startTag("", TAG_NOME);
	        					serializer.text(mgr.getPaymentDetails( ).getFullName( ));
	        				serializer.endTag("", TAG_NOME);
	        				serializer.startTag("", TAG_LOGINMOIP);
	        					serializer.text("LOGINMOIP");
	        				serializer.endTag("", TAG_LOGINMOIP);
	        				serializer.startTag("", TAG_EMAIL);
	        					serializer.text(mgr.getPaymentDetails( ).getEmail( ));
	        				serializer.endTag("", TAG_EMAIL);
	        				serializer.startTag("", TAG_TELEFONECELULAR);
	        					serializer.text(mgr.getPaymentDetails( ).getCellPhone( ));
	        				serializer.endTag("", TAG_TELEFONECELULAR);
	        				serializer.startTag("", TAG_APELIDO);
        						serializer.text("APELIDO");
        					serializer.endTag("", TAG_APELIDO);
	        				serializer.startTag("", TAG_IDENTIDADE);
        						serializer.text(mgr.getPaymentDetails( ).getPayerIdentificationNumber( ));
        					serializer.endTag("", TAG_IDENTIDADE);
        					serializer.startTag("", TAG_ENDERECOCOBRANCA);
        						serializer.startTag("", TAG_LOGRADOURO);
        							serializer.text(mgr.getPaymentDetails( ).getStreetAddress( ));
        						serializer.endTag("", TAG_LOGRADOURO);
        						serializer.startTag("", TAG_NUMERO);
        							Integer nro = mgr.getPaymentDetails( ).getStreetNumber( );
    								serializer.text(nro.toString( ));
    							serializer.endTag("", TAG_NUMERO);
    							serializer.startTag("", TAG_COMPLEMENTO);
									serializer.text(mgr.getPaymentDetails( ).getStreetComplement( ));
								serializer.endTag("", TAG_COMPLEMENTO);
								serializer.startTag("", TAG_BAIRRO);
									serializer.text(mgr.getPaymentDetails( ).getNeighborhood( ));
								serializer.endTag("", TAG_BAIRRO);
								serializer.startTag("", TAG_CIDADE);
									serializer.text(mgr.getPaymentDetails( ).getCity( ));
								serializer.endTag("", TAG_CIDADE);
								serializer.startTag("", TAG_ESTADO);
									serializer.text(mgr.getPaymentDetails( ).getState( ));
								serializer.endTag("", TAG_ESTADO);
								serializer.startTag("", TAG_PAIS);
									serializer.text("BRA"); //TODO:
								serializer.endTag("", TAG_PAIS);
								serializer.startTag("", TAG_CEP);
									serializer.text(mgr.getPaymentDetails( ).getZipCode( ));
								serializer.endTag("", TAG_CEP);
								serializer.startTag("", TAG_TELEFONEFIXO);
									serializer.text(mgr.getPaymentDetails( ).getOwnerPhoneNumber( ));
								serializer.endTag("", TAG_TELEFONEFIXO);
							serializer.endTag("", TAG_ENDERECOCOBRANCA);
	        			serializer.endTag("", TAG_PAGADOR);
		        	serializer.endTag("", TAG_INSTRUCAOUNICA);
	        serializer.endTag("", TAG_ENVIARINSTRUCAO);
	        serializer.endDocument();
	        return writer.toString();
	    } 
	    catch (Exception e) 
	    {
	    	//IOException
	    	//IllegalArgumentException
	    	//IllegalStateException
	        //throw new RuntimeException(e);
	    }
		
		return writer.toString( );
	}
	
	//TODO: implement getXXXXPaymentMessage( )
}
