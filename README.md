
# Módulo de integração com o MoIP para Android

## Instalação

Primeiramente, clone o projeto:

    $ git clone git@github.com:Menki/moip-android-module.git
  
Em seguida, adicione o projeto do módulo de integração com o MoIP ao seu workspace do [Eclipse](http://www.eclipse.org/downloads/) utilizando a opção de importar projetos existentes. 
    
Caso ocorram erros durante a compilação do projeto clique com o botão da direita do mouse sobre o projeto do módulo de integração com o MoIP vá em properties e em seguida na opção "java build path":    

* Na aba "source", garanta que somente as pastas "src/" e "gen/" estejam adicionadas
* Na aba "library", garanta que a biblioteca do Android 1.5 esteja sendo utilizada

Agora, não devem mais ocorrer "building errors" no projeto. 

## Utilizando o módulo em seu aplicativo Android

Vamos adicioná-lo ao seu aplicativo. Para isto, clique com o botão direito sobre o projeto do seu aplicativo no [Eclipse](http://www.eclipse.org/downloads/) e vá em "properties".

1. Selecione a opção "Android" no menu ao lado esquerdo
2. Na seção "Library" vá em "Add"
3. Selecione o projeto do módulo de integração com o MoIP (moip-android-module)

### Integração utilizando as interfaces do módulo

Edite o arquivo AndroidManifest.xml para permitir que seu aplicativo execute a Activity pronta no Móudlo. Para isto, adicione a seguinte linha dentro da tag "&lt;application>":
    
    <activity android:name="com.menki.moip.PaymentInfo" android:theme="@style/Theme.backmoip" />

Ainda editando o AndroidManifest.xml, você deve permitir que seu aplicativo acesse a internet, pois toda integração é feita através da API REST do MoIP. Para isto, dentro da tag "&lt;manifest>", adicione a seguinte linha:

    <uses-permission android:name="android.permission.INTERNET" />
    
A activity "PaymentInfo" do Módulo deve ser invocada por você. Para isto, inclua as seguintes linhas onde achar apropriado (por exemplo, no "onClick" do botão "Pagar!"):

    Intent intent = new Intent(PagamentoDiretoWithUIActivity.this, PaymentInfo.class);
  	intent.putExtra("PagamentoDireto", pagDir);
  	startActivity(intent);

Repare que adicionamos ao intent um objeto chamado "pagDir", este deve ser um objeto da classe PagamentoDireto que você deve instanciar e atribuir alguns valores, por exemplo:

    pagDir = new PagamentoDireto();
    pagDir.setOnPaymentListener(this);
    pagDir.setServerType(RemoteServer.TEST); //utilize RemoteServer.PRODUCTION para o aplicativo oficial que for para o Android Market.
    pagDir.setToken("SEU TOKEN DO MOIP");
    pagDir.setKey("SUA KEY DO MOIP");

Criamos um projeto de integração com MoIP utilizando as interfaces do módulo para exemplificar este processo. Visite-o [aqui](https://github.com/Menki/pagamento-direto-with-ui).

### Integração direta (sem as interfaces do módulo)

Edite o arquivo AndroidManifest.xml, você deve permitir que seu aplicativo acesse a internet, pois toda integração é feita através da API REST do MoIP. Para isto, dentro da tag "&lt;manifest>", adicione a seguinte linha:

    <uses-permission android:name="android.permission.INTERNET" />
    
Para integração direta, basta instanciar um objeto PagamentoDireto settar seus atributos e quando desejar realizar o pagamento basta chamar o método "pay()", por exemplo:

    pagDir = new PagamentoDireto();
    pagDir.setOnPaymentListener(this);
    pagDir.setServerType(RemoteServer.TEST); //utilize RemoteServer.PRODUCTION para o aplicativo oficial que for para o Android Market.
    pagDir.setToken("SEU TOKEN DO MOIP");
    pagDir.setKey("SUA KEY DO MOIP");
    pagDir.setAddressComplement("");
    pagDir.setBrand("Visa");
    pagDir.setCity("Sucupira");
    pagDir.setCountry("BRA");
    pagDir.setCreditCardNumber("3456789012345640");
    pagDir.setExpirationDate("08/11");
    pagDir.setInstallmentsQuantity("2");
    pagDir.setNeighborhood("Vila Vintem");
    pagDir.setOwnerBirthDate("01/01/1983");
    pagDir.setOwnerIdNumber("111.111.111-11");
    pagDir.setOwnerIdType(OwnerIdType.CPF);
    pagDir.setOwnerName("Lindolfo Pires");
    pagDir.setCellPhone("(11)1111-1111");
    pagDir.setFixedPhone("111.111.111-11");
    pagDir.setSecureCode("101");
    pagDir.setServerType(RemoteServer.TEST);
    pagDir.setState("AC");
    pagDir.setStreetAddress("Avenida Brasil");
    pagDir.setStreetNumberAddress("100");
    pagDir.setValue("213.25");
    pagDir.setZipCode("10100-100");
    
    pagDir.pay();

Criamos um projeto de integração direta com MoIP para exemplificar este processo. Visite-o [aqui](https://github.com/Menki/pagamento-direto-without-ui).

## Restrições

* Integração somente com a API de pagamento direto
* Validações dos campos
* [Alguns bugs](https://github.com/Menki/moip-android-module/issues)

## Como ajudar

* Fork
* Utilize
* Submeta pull requests
* Teste
* Abra bugs
* Conserte bugs
* Documente
* [Nos dê feedback por email](mailto:info@menkimobile.com.br)