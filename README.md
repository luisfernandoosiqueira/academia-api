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

---

### 2) Alunos (v2)

**POST `/api/v2/academia/alunos`** — criar aluno

<img width="958" height="894" alt="image" src="https://github.com/user-attachments/assets/4564efa3-9a88-4d56-8691-d2cf33ea68b8" />

---

**GET `/api/v2/academia/alunos`** — listar alunos

<img width="961" height="904" alt="image" src="https://github.com/user-attachments/assets/027644f5-bade-4d95-a391-1fc4030f92cc" />

---

**GET `/api/v2/academia/alunos/{id}`** — buscar por id

<img width="959" height="886" alt="image" src="https://github.com/user-attachments/assets/86bfdc31-6b0a-494c-9464-decece864042" />

---

**PUT `/api/v2/academia/alunos/{id}`** — atualizar aluno

<img width="962" height="888" alt="image" src="https://github.com/user-attachments/assets/682a94f6-17f5-45ec-9146-e48d0796aa33" />

---

**DELETE `/api/v2/academia/alunos/{id}`** — remover aluno

<img width="953" height="569" alt="image" src="https://github.com/user-attachments/assets/fe5b9662-85c7-4f97-9738-ba174d3b25b5" />

---

**POST `/api/v2/academia/alunos/{alunoId}/treinos/{treinoId}`** — vincular treino

<img width="958" height="688" alt="image" src="https://github.com/user-attachments/assets/facb5477-9699-4629-8a62-8afed0bc6cd0" />

---

**DELETE `/api/v2/academia/alunos/{alunoId}/treinos/{treinoId}`** — desvincular treino

<img width="952" height="708" alt="image" src="https://github.com/user-attachments/assets/dbfcd6e3-358b-4ddb-8bcb-dd84223dd865" />

---

### 3) Planos (v2)

**POST `/api/v2/academia/planos`** — criar plano

<img width="1078" height="491" alt="image" src="https://github.com/user-attachments/assets/c7cf7ebc-a479-454f-9259-c623ca2eaa4f" />

<img width="1075" height="658" alt="image" src="https://github.com/user-attachments/assets/dff4adba-ccc4-4bc1-bfd4-65433ce77c19" />

---

**GET `/api/v2/academia/planos`** — listar planos

<img width="958" height="766" alt="image" src="https://github.com/user-attachments/assets/c3638fa0-f1b9-4d18-a228-a4162aab7b08" />

---

**GET `/api/v2/academia/planos/{id}`** — buscar por id

<img width="960" height="603" alt="image" src="https://github.com/user-attachments/assets/a3af7668-a202-45c4-a640-cddc85a8c4e1" />

---

**PUT `/api/v2/academia/planos/{id}`** — atualizar plano

<img width="959" height="892" alt="image" src="https://github.com/user-attachments/assets/b47c85a3-1b5c-4618-b4fa-2df4c4711711" />

---

**DELETE `/api/v2/academia/planos/{id}`** — remover plano

<img width="961" height="576" alt="image" src="https://github.com/user-attachments/assets/1cfe752d-a9a0-4142-b88f-f138fbff9cf0" />

---

### 4) Treinos (v2)

**POST `/api/v2/academia/treinos`** — criar treino

<img width="944" height="814" alt="image" src="https://github.com/user-attachments/assets/cc352736-adf0-488c-9630-2890cd92e223" />

---

**GET `/api/v2/academia/treinos`** — listar treinos

<img width="961" height="808" alt="image" src="https://github.com/user-attachments/assets/5e40eba3-d2a5-4073-8036-38127ec3bfb3" />

---

**GET `/api/v2/academia/treinos/{id}`** — buscar por id

<img width="958" height="800" alt="image" src="https://github.com/user-attachments/assets/25ae0cf4-d482-4206-9f9d-63f1577d21ef" />

---

**PUT `/api/v2/academia/treinos/{id}`** — atualizar treino

<img width="957" height="900" alt="image" src="https://github.com/user-attachments/assets/6c6d6289-d878-49b1-97de-14e3dde799d9" />

---

**DELETE `/api/v2/academia/treinos/{id}`** — remover treino

<img width="959" height="575" alt="image" src="https://github.com/user-attachments/assets/0629e162-4ad8-48c1-9631-c37993aca12e" />

---

### 5) Pagamentos (v2)

**POST `/api/v2/academia/alunos/{alunoId}/pagamentos`** — registrar pagamento

<img width="945" height="677" alt="image" src="https://github.com/user-attachments/assets/7a3152d1-e6cd-4831-9d17-fe809e48a821" />

---

**GET `/api/v2/academia/pagamentos`** — listar pagamentos

<img width="962" height="565" alt="image" src="https://github.com/user-attachments/assets/35ba9972-3702-492c-aa29-9decef2c091b" />

---

**GET `/api/v2/academia/pagamentos/{id}`** — buscar por id

<img width="965" height="625" alt="image" src="https://github.com/user-attachments/assets/ecb82585-8a1d-459e-99b3-b5d724c5ec09" />

---

**DELETE `/api/v2/academia/pagamentos/{id}`** — remover pagamento

<img width="952" height="573" alt="image" src="https://github.com/user-attachments/assets/e6e52595-7f0f-4fe1-ba89-014bbe78b91c" />







