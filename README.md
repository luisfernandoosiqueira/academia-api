## ▶️ Instruções para rodar o projeto

### Requisitos

* **Java 21+**
* **Maven 3.9+**
* (Opcional) IDE: IntelliJ / Eclipse / VS Code

### 1) Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd <PASTA_DO_PROJETO>
```

### 2) Executar com Maven

```bash
mvn spring-boot:run
# ou
mvn clean package
java -jar target/academia-0.0.1-SNAPSHOT.jar
```

### 3) Endereços úteis

* **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
* **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`
* **H2 Console:** `http://localhost:8080/h2-console`

### 4) (Opcional) Configuração do H2 em arquivo

No `application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/academia-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

> **Login do H2 Console**
> JDBC URL: `jdbc:h2:file:./data/academia-db`
> User: `sa`
> Password: *(vazio, a menos que tenha definido)*

## 🧩 Descrição breve da solução

A API gerencia **alunos**, **planos**, **treinos** e **pagamentos**, seguindo uma arquitetura limpa e simples:

* **Arquitetura em camadas:** `Controller → Service → Repository`.
* **Versionamento:**

  * **v1:** controllers/services usam **entidades** diretamente.
  * **v2:** controllers/services usam **DTOs**, com conversão via **Mapper**.
* **Entidades & Relacionamentos (JPA):**

  * `Aluno` ↔ `Plano` (**ManyToOne**, LAZY)
  * `Aluno` ↔ `Treino` (**ManyToMany**, LAZY, `Set` para evitar duplicatas)
  * `Pagamento` ↔ `Aluno` (**ManyToOne**, LAZY)
* **Regras de negócio:**

  * **Aluno ativo** no cadastro (`ativo = true`) e **CPF único** (validação + constraint).
  * **Um plano por aluno** (troca substitui o anterior).
  * **Pagamento:** `dataPagamento` automática e **status** calculado (PAGO/PENDENTE/ATRASADO).
  * **Treino:** **não remover** se houver vínculo com alunos.
* **Boas práticas REST:** códigos HTTP adequados (`201 Created`, `204 No Content`, etc.) e rotas versionadas (`/api/v1/...`, `/api/v2/...`).
* **Documentação:** **Swagger/OpenAPI** com `springdoc-openapi`.
* **Banco de dados:** **H2** (modo arquivo) para desenvolvimento.
* **Validações & erros:** `GlobalExceptionHandler` retornando **`ErrorResponse`** padronizado.
* **Notificações:** hooks simples no Service (ex.: e-mail/SMS) após ações relevantes.


## 📸 Prints dos endpoints testados (Swagger) — v2

> Substitua os placeholders abaixo pelas imagens dos seus testes no Swagger UI.

### 1) Tela inicial — Swagger v2

<img width="1323" height="910" alt="image" src="https://github.com/user-attachments/assets/47503897-1a2d-437a-8133-095f1997eaa5" />


### 2) Alunos (v2)

**POST `/api/v2/academia/alunos`** — criar aluno

<img width="958" height="894" alt="image" src="https://github.com/user-attachments/assets/4564efa3-9a88-4d56-8691-d2cf33ea68b8" />


**GET `/api/v2/academia/alunos`** — listar alunos



**GET `/api/v2/academia/alunos/{id}`** — buscar por id


**PUT `/api/v2/academia/alunos/{id}`** — atualizar aluno



**DELETE `/api/v2/academia/alunos/{id}`** — remover aluno


**POST `/api/v2/academia/alunos/{alunoId}/treinos/{treinoId}`** — vincular treino


**DELETE `/api/v2/academia/alunos/{alunoId}/treinos/{treinoId}`** — desvincular treino


---

### 3) Planos (v2)

**POST `/api/v2/academia/planos`** — criar plano

<img width="1078" height="491" alt="image" src="https://github.com/user-attachments/assets/c7cf7ebc-a479-454f-9259-c623ca2eaa4f" />

<img width="1075" height="658" alt="image" src="https://github.com/user-attachments/assets/dff4adba-ccc4-4bc1-bfd4-65433ce77c19" />


**GET `/api/v2/academia/planos`** — listar planos

<img width="958" height="766" alt="image" src="https://github.com/user-attachments/assets/c3638fa0-f1b9-4d18-a228-a4162aab7b08" />


**GET `/api/v2/academia/planos/{id}`** — buscar por id

<img width="960" height="603" alt="image" src="https://github.com/user-attachments/assets/a3af7668-a202-45c4-a640-cddc85a8c4e1" />


**PUT `/api/v2/academia/planos/{id}`** — atualizar plano

<img width="959" height="892" alt="image" src="https://github.com/user-attachments/assets/b47c85a3-1b5c-4618-b4fa-2df4c4711711" />


**DELETE `/api/v2/academia/planos/{id}`** — remover plano

<img width="961" height="576" alt="image" src="https://github.com/user-attachments/assets/1cfe752d-a9a0-4142-b88f-f138fbff9cf0" />


---

### 4) Treinos (v2)

**POST `/api/v2/academia/treinos`** — criar treino

<img width="944" height="814" alt="image" src="https://github.com/user-attachments/assets/cc352736-adf0-488c-9630-2890cd92e223" />


**GET `/api/v2/academia/treinos`** — listar treinos

<img width="961" height="808" alt="image" src="https://github.com/user-attachments/assets/5e40eba3-d2a5-4073-8036-38127ec3bfb3" />


**GET `/api/v2/academia/treinos/{id}`** — buscar por id

<img width="958" height="800" alt="image" src="https://github.com/user-attachments/assets/25ae0cf4-d482-4206-9f9d-63f1577d21ef" />


**PUT `/api/v2/academia/treinos/{id}`** — atualizar treino

<img width="957" height="900" alt="image" src="https://github.com/user-attachments/assets/6c6d6289-d878-49b1-97de-14e3dde799d9" />


**DELETE `/api/v2/academia/treinos/{id}`** — remover treino

<img width="959" height="575" alt="image" src="https://github.com/user-attachments/assets/0629e162-4ad8-48c1-9631-c37993aca12e" />

---

### 5) Pagamentos (v2)

**POST `/api/v2/academia/alunos/{alunoId}/pagamentos`** — registrar pagamento


**GET `/api/v2/academia/pagamentos`** — listar pagamentos


**GET `/api/v2/academia/pagamentos/{id}`** — buscar por id


**DELETE `/api/v2/academia/pagamentos/{id}`** — remover pagamento


---


