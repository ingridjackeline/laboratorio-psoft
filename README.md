# Mercado Fácil
 
Um supermercado da cidade de Campina Grande precisa de um sistema que gerencie o estoque e venda de produtos na sua loja. Neste sistema, o administrador deve obter uma visão geral e o controle sobre o funcionamento do supermercado, por exemplo, ele deve poder adicionar novos produtos, acompanhar quantas unidades do produto estão disponíveis, alterar preços, ser notificado sobre eventos críticos, gerenciar as vendas e oferecer alguns serviços personalizados para o cliente.

## User Stories já implementadas

- Eu, como administrador, gostaria de adicionar um novo produto no sistema, informando seu nome, fabricante e preço;
- Eu, como usuário, gostaria de consultar as informações de um produto específico do supermercado;
- Eu, como usuário, gostaria de consultar todos os produtos do catálogo do supermercado.

## Estrutura básica

- Um projeto: MercadoFacil;
- Um Controller ProdutoController que implementa os endpoints da API Rest relacionados a operações com produtos.
- Dois repositórios são utilizados: ProdutoRepository e LoteRepository, que são responsáveis por manipular as entidades Produto e Lote em um catálogo (Mapa);
- O modelo é composto pelas classes Produto.java e Lote.java que podem ser encontradas no pacote model;
- O pacote exceptions guarda as classes de exceções que podem ser lançadas dentro do sistema;
- Não há implementação de frontend, mas o projeto fornece uma interface de acesso à API via swagger.

## Tecnologias
Código base gerado via [start.sprint.io](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.3.3.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.example&artifactId=EstoqueFacil&name=EstoqueFacil&description=Projeto%20Estoque%20Facil&packageName=com.example.EstoqueFacil&dependencies=web,actuator,devtools,data-jpa,h2) com as seguintes dependências:  

- Spring Web
- Spring Actuator
- Spring Boot DevTools

## Endereços úteis

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Contato e Dúvidas

- fabio@computacao.ufcg.edu.br
