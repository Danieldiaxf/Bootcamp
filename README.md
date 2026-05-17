# 💸 Transações

API REST desenvolvida em **Java + Spring Boot** para simulação de transferências financeiras entre usuários, com regras de negócio, integração externa e persistência em banco de dados.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- OpenFeign
- Maven
- Docker (opcional)
- GitHub Actions (CI)

---

## 🧠 Arquitetura

A aplicação segue uma arquitetura em camadas:

```
Controller → Service → Repository → Database
```

### 📂 Estrutura de pacotes

```
com.bootcamp.transacao_simplificada
│
├── infrastructure
│   ├── controller
│   ├── entity
│   ├── repository
│   ├── exceptions
│   └── clients
│
├── service
│
└── TransacaoSimplificadaApplication
```

---

## ⚙️ Regras de Negócio

### ✔️ Tipos de usuário

- `COMUM` → pode enviar e receber dinheiro
- `LOJISTA` → **não pode enviar**, apenas receber

---

### ✔️ Validações

- ❌ Lojista não pode ser pagador
- ❌ Saldo insuficiente bloqueia a transação
- ❌ Transferência depende de autorização externa
- ❌ Falha na autorização cancela operação

---

## 🔄 Fluxo da Transação

1. Recebe requisição HTTP
2. Busca pagador e recebedor
3. Valida tipo de usuário
4. Valida saldo
5. Consulta API externa de autorização
6. Atualiza saldo das carteiras
7. Persiste transação
8. Envia notificação

---

## 🌐 Endpoint Principal

### 🔹 Realizar transferência

**POST** `/transfer`

#### 📥 Request

```json
{
  "value": 200.0,
  "payer": 1,
  "payee": 2
}
```

#### 📤 Response

- ✅ 200 OK

```json
"Transacao realizada com sucesso"
```

- ❌ 400 BAD REQUEST

```json
"Transacao nao autorizada pela API!"
```

- ❌ 500 INTERNAL SERVER ERROR

```json
"Erro interno no servidor"
```

---

## 🌐 API's Utilizadas

- **GET** : <https://util.devi.tools/api/v2/authorize>
- **POST** : <https://util.devi.tools/api/v1/notify>

---

## 🔗 Integrações Externas

### ✔️ Autorização de Transação

- API: <https://util.devi.tools/api/v2/authorize>
- Responsável por validar se a transação pode ocorrer

---

### ✔️ Notificação

- API: <https://util.devi.tools/api/v1/notify>
- Simula envio de notificação após transferência

---

## 🗄️ Banco de Dados

### PostgreSQL

Configuração no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/transacao_db
spring.datasource.username=postgres
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 📊 Entidades

### 👤 Usuario

- id
- nome
- email
- cpf
- tipoUsuario

---

### 💰 Carteira

- id
- saldo
- usuario

---

### 🔁 Transacoes

- id
- valor
- pagador
- recebedor

---

## 🧪 Testes Automatizados

Os testes validam o fluxo de comunicação com o serviço externo de autorização **sem depender de internet**, usando **WireMock** para simular as respostas da API e **H2** como banco em memória.

### 🛠️ Tecnologias utilizadas nos testes

| Biblioteca | Função |
|---|---|
| JUnit 5 | Framework de testes |
| WireMock | Simula a API externa de autorização |
| H2 | Banco em memória (substitui PostgreSQL nos testes) |

---

### 📂 Localização do teste

```
src/test/java/com/bootcamp/transacao_simplificada/
└── infrastructure/
    └── clients/
        └── AutorizacaoClientTest.java
```

---

### ✅ Cenários cobertos

| # | Cenário | Comportamento esperado |
|---|---|---|
| 1 | API externa **autoriza** a transação | Nenhuma exceção é lançada |
| 2 | API externa **nega** a autorização | Lança `IllegalArgumentException` |
| 3 | API externa retorna **erro 500** | Lança exceção (serviço indisponível) |

---

### ⚙️ Dependências necessárias

Adicione no `pom.xml` dentro de `<dependencies>`:

```xml
<!-- WireMock: simula a API externa nos testes -->
<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock-jre8-standalone</artifactId>
    <version>2.35.0</version>
    <scope>test</scope>
</dependency>

<!-- H2: banco em memória para os testes -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

---

### ▶️ Como executar os testes

```bash
./mvnw test
```

> No Windows: `mvnw.cmd test`

Resultado esperado no terminal:

```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

### 🗂️ Perfil de teste

Crie o arquivo `src/test/resources/application-test.properties` com o conteúdo abaixo para isolar o ambiente de teste:

```properties
# Aponta para o WireMock em vez da API real
autorizacao.api.url=http://localhost:8089

# Banco em memória — não precisa do PostgreSQL rodando
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
```

---

## ⚠️ Tratamento de Erros

Centralizado com:

```java
@RestControllerAdvice
```

### Tratamentos:

- `IllegalArgumentException` → 400
- `Exception` → 500

---

## 🔐 Boas Práticas Implementadas

- Injeção de dependência com `@RequiredArgsConstructor`
- Uso de `@Transactional` para consistência
- Separação de responsabilidades
- Tratamento global de exceções
- Integração com API externa via Feign

---

## 🛠️ CI/CD

Pipeline configurado com **GitHub Actions**:

- Build automático
- Execução de testes
- Validação do projeto

---

## ▶️ Como Executar

### ✅ Pré-requisitos

| Ferramenta | Versão mínima | Download |
|---|---|---|
| Java (JDK) | 17 | [https://adoptium.net](https://adoptium.net) |
| Maven | 3.8+ | [https://maven.apache.org](https://maven.apache.org) |
| PostgreSQL | 13+ | [https://www.postgresql.org](https://www.postgresql.org) |
| Git | qualquer | [https://git-scm.com](https://git-scm.com) |

> 💡 Para testar os endpoints, use **curl** ou **Postman** ([https://www.postman.com](https://www.postman.com)).

---

### 🗄️ Passo 1 — Configurar o banco de dados

```bash
psql -U postgres
```

```sql
CREATE DATABASE transacao_db;
\q
```

---

### 📥 Passo 2 — Clonar o repositório

```bash
git clone https://github.com/Danieldiaxf/Bootcamp.git
cd Bootcamp/transacao-simplificada
```

---

### ⚙️ Passo 3 — Configurar as credenciais do banco

Abra `src/main/resources/application.properties` e ajuste se necessário:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/transacao_db
spring.datasource.username=postgres
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> O `ddl-auto=update` cria as tabelas automaticamente na primeira execução.

---

### 🚀 Passo 4 — Iniciar a aplicação

```bash
./mvnw spring-boot:run
```

> No Windows: `mvnw.cmd spring-boot:run`

Aguarde a mensagem:

```
Started TransacaoSimplificadaApplication in 3.2 seconds
```

---

### 🔌 Passo 5 — Verificar se está no ar

```bash
curl http://localhost:8080
```

---

## 💸 Como Realizar uma Transação

### Transferência bem-sucedida

```bash
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "value": 200.0,
    "payer": 1,
    "payee": 2
  }'
```

**Resposta:** `"Transacao realizada com sucesso"`

---

### ❌ Lojista tentando enviar (bloqueado)

```bash
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "value": 50.0,
    "payer": 2,
    "payee": 1
  }'
```

**Resposta:** `HTTP 400 — "Lojistas nao podem realizar transferencias"`

---

### ❌ Saldo insuficiente

```bash
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "value": 9999.0,
    "payer": 1,
    "payee": 2
  }'
```

**Resposta:** `HTTP 400 — "Saldo insuficiente"`

---

## 📌 Melhorias Futuras

- Autenticação com JWT
- Histórico de transações (extrato)
- Paginação de dados
- Testes automatizados completos
- Resilience4j (circuit breaker)
- Docker Compose com PostgreSQL

---

## 👨‍💻 Autor

Desenvolvido por **Daniel Alves**

---

## 📄 Licença

Projeto para fins educacionais.
