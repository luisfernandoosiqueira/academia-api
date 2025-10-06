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

![Uploading image.png…]()


**GET `/api/v2/academia/planos/{id}`** — buscar por id


**PUT `/api/v2/academia/planos/{id}`** — atualizar plano


**DELETE `/api/v2/academia/planos/{id}`** — remover plano


---

### 4) Treinos (v2)

**POST `/api/v2/academia/treinos`** — criar treino


**GET `/api/v2/academia/treinos`** — listar treinos


**GET `/api/v2/academia/treinos/{id}`** — buscar por id


**PUT `/api/v2/academia/treinos/{id}`** — atualizar treino


**DELETE `/api/v2/academia/treinos/{id}`** — remover treino


---

### 5) Pagamentos (v2)

**POST `/api/v2/academia/alunos/{alunoId}/pagamentos`** — registrar pagamento


**GET `/api/v2/academia/pagamentos`** — listar pagamentos


**GET `/api/v2/academia/pagamentos/{id}`** — buscar por id


**DELETE `/api/v2/academia/pagamentos/{id}`** — remover pagamento


---

