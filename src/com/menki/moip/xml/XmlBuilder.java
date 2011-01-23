/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */
package com.menki.moip.xml;

import java.io.StringWriter;


public class XmlBuilder 
{
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
		StringWriter writer = new StringWriter( );
		
		return writer.toString( );
	}
}
