# Projeto Locadora de Filmes

Este é um projeto de uma Locadora de Filmes desenvolvido utilizando Spring Boot. O objetivo é criar um sistema de gestão de filmes onde os usuários podem visualizar o catálogo de filmes disponíveis, alugar filmes e gerenciar sua conta.

**Tecnologias Utilizadas:**
- Spring Boot
- H2 Database
- Spring Security

**Funcionalidades:**
- Catálogo de Filmes: Visualize a lista de filmes disponíveis para aluguel.
- Aluguel de Filmes: Alugue filmes disponíveis.
- Cadastro de usuario e gerenciamento de usuario.

**Instruções de Uso:**
1. Clone este repositório.
2. Certifique-se de ter o Java JDK e o Maven instalados em seu sistema.
3. Configure o projeto para executar localmente:
   - O banco de dados utilizado é o H2, então não é necessário configurar um banco de dados externo.
   - A autenticação e autorização são tratadas pelo Spring Security.
4. Compile e execute o projeto utilizando o Maven:
   ```
   mvn spring-boot:run
   ```
5. Acesse a aplicação em seu navegador no endereço: `http://localhost:8080`.

**Importante:**
- Este projeto não possui uma interface de usuário (front-end) implementada. Você pode interagir com as funcionalidades através de chamadas à API utilizando ferramentas como Postman ou cURL.
- As rotas protegidas exigem autenticação. Utilize as credenciais de usuário fornecidas para acessar essas rotas.

**Credenciais de Usuário:**
- **Usuário:** kauan.123
- **Senha:** St@rG@z3r^#

