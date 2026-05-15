# 💸 Transações

API REST desenvolvida em **Java + Spring Boot** para simulação de transferências financeiras entre usuários, com regras de negócio, integração externa e persistência em banco de dados.

---

## 🚀 Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Lombok
* OpenFeign
* Maven
* Docker (opcional)
* GitHub Actions (CI)

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

* `COMUM` → pode enviar e receber dinheiro
* `LOJISTA` → **não pode enviar**, apenas receber

---

### ✔️ Validações

* ❌ Lojista não pode ser pagador
* ❌ Saldo insuficiente bloqueia a transação
* ❌ Transferência depende de autorização externa
* ❌ Falha na autorização cancela operação

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

* ✅ 200 OK

```json
"Transacao realizada com sucesso"
```

* ❌ 400 BAD REQUEST

```json
"Transacao nao autorizada pela API!"
```

* ❌ 500 INTERNAL SERVER ERROR

```json
"Erro interno no servidor"
```
---

## 🌐 API's Utilizadas

* **GET** : https://util.devi.tools/api/v2/authorize
* **POST** : https://util.devi.tools/api/v1/notify

---

## 🔗 Integrações Externas

### ✔️ Autorização de Transação

* API: https://util.devi.tools/api/v2/authorize
* Responsável por validar se a transação pode ocorrer

---

### ✔️ Notificação

* API: https://util.devi.tools/api/v1/notify
* Simula envio de notificação após transferência

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

* id
* nome
* email
* cpf
* tipoUsuario

---

### 💰 Carteira

* id
* saldo
* usuario

---

### 🔁 Transacoes

* id
* valor
* pagador
* recebedor

---

## 🧪 Testes

* Teste de contexto Spring Boot
* Possibilidade de expansão para testes unitários e integração

---

## ⚠️ Tratamento de Erros

Centralizado com:

```java
@RestControllerAdvice
```

### Tratamentos:

* `IllegalArgumentException` → 400
* `Exception` → 500

---

## 🔐 Boas Práticas Implementadas

* Injeção de dependência com `@RequiredArgsConstructor`
* Uso de `@Transactional` para consistência
* Separação de responsabilidades
* Tratamento global de exceções
* Integração com API externa via Feign

---

## 🛠️ CI/CD

Pipeline configurado com **GitHub Actions**:

* Build automático
* Execução de testes
* Validação do projeto

---

## ▶️ Como Executar

```bash
# Clonar repositório
git clone https://github.com/Danieldiaxf/Bootcamp.git

# Entrar no projeto
cd Bootcamp

# Rodar aplicação
./mvnw spring-boot:run
```

---

## 📌 Melhorias Futuras

* Autenticação com JWT
* Histórico de transações (extrato)
* Paginação de dados
* Testes automatizados completos
* Resilience4j (circuit breaker)
* Docker Compose com PostgreSQL

---

## 👨‍💻 Autor

Desenvolvido por **Daniel Alves**

---

## 📄 Licença

Projeto para fins educacionais.

