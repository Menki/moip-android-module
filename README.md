
# Módulo de integração com o MoIP para Android

## Instalação

Primeiramente, clone o projeto:

    $ git clone git@github.com:Menki/moip-android-module.git
  
Em seguida, adicione o projeto do módulo de integração com o MoIP ao seu workspace do [Eclipse](http://www.eclipse.org/downloads/) utilizando a opção de importar projetos existentes. 
    
Caso ocorram erros durante a compilação do projeto clique com o botão da direita do mouse sobre o projeto do módulo de integração com o MoIP vá em properties e em seguida na opção "java build path":    

* Na aba "source", garanta que somente as pastas "src/" e "gen/" estejam adicionadas
* Na aba "library", garanta que a biblioteca do Android 1.5 esteja sendo utilizada

Agora, não devem mais ocorrer "building errors" no projeto. 

## Utilizando o módulo em meu aplicativo Android

Vamos adicioná-lo ao seu aplicativo. Para isto, clique com o botão direito sobre o projeto do seu aplicativo no [Eclipse](http://www.eclipse.org/downloads/) e vá em "properties".

1. Selecione a opção "Android" no menu ao lado esquerdo
2. Na seção "Library" vá em "Add"
3. Selecione o projeto do módulo de integração com o MoIP (moip-android-module)

### Integração utilizando as interfaces do módulo

### Integração direta (sem as interfaces do módulo)

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