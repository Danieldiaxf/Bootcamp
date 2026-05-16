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

## 🧪 Testes

- Teste de contexto Spring Boot
- Possibilidade de expansão para testes unitários e integração

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

Antes de executar a aplicação, certifique-se de ter instalado em sua máquina:

| Ferramenta | Versão mínima | Download |
|---|---|---|
| Java (JDK) | 17 | [https://adoptium.net](https://adoptium.net) |
| Maven | 3.8+ | [https://maven.apache.org](https://maven.apache.org) |
| PostgreSQL | 13+ | [https://www.postgresql.org](https://www.postgresql.org) |
| Git | qualquer | [https://git-scm.com](https://git-scm.com) |

> 💡 Para testar os endpoints, você precisará de **curl** (já incluso no Linux/Mac) ou do **Postman** ([https://www.postman.com](https://www.postman.com)).

---

### 🗄️ Passo 1 — Configurar o banco de dados

Antes de iniciar a aplicação, crie o banco de dados no PostgreSQL.

Abra o terminal e acesse o PostgreSQL:

```bash
psql -U postgres
```

Dentro do prompt do PostgreSQL, execute:

```sql
CREATE DATABASE transacao_db;
```

Confirme que o banco foi criado e saia:

```sql
\l
\q
```

> ⚠️ Certifique-se de que o usuário `postgres` tem a senha definida como `senha`, ou ajuste o arquivo `application.properties` com suas credenciais reais antes de prosseguir.

---

### 📥 Passo 2 — Clonar o repositório

```bash
git clone https://github.com/Danieldiaxf/Bootcamp.git
```

Entre na pasta do projeto:

```bash
cd Bootcamp/transacao-simplificada
```

---

### ⚙️ Passo 3 — Configurar as credenciais do banco

Abra o arquivo `src/main/resources/application.properties` e verifique (ou ajuste) as configurações:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/transacao_db
spring.datasource.username=postgres
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> O `ddl-auto=update` faz com que o Spring crie automaticamente as tabelas no banco na primeira execução. Não é necessário rodar nenhum script SQL manualmente.

---

### 🚀 Passo 4 — Compilar e iniciar a aplicação

Execute o comando abaixo na raiz do módulo (`transacao-simplificada/`):

```bash
./mvnw spring-boot:run
```

> No Windows, use `mvnw.cmd spring-boot:run` em vez de `./mvnw spring-boot:run`.

Aguarde até ver no terminal uma mensagem semelhante a:

```
Started TransacaoSimplificadaApplication in 3.2 seconds (JVM running for 3.8)
```

Isso indica que a aplicação está **rodando e pronta para receber requisições** na porta `8080`.

---

### 🔌 Passo 5 — Verificar se a aplicação está no ar

Abra um novo terminal e execute:

```bash
curl http://localhost:8080
```

Se a aplicação estiver rodando, você receberá uma resposta (mesmo que seja um erro 404 — isso é esperado, pois não há endpoint raiz).

---

## 🧪 Como Realizar uma Transação

A interação com a API é feita via requisições HTTP. Os exemplos abaixo usam **curl**, mas você pode usar o Postman, Insomnia ou qualquer cliente HTTP.

---

### 📋 Cenário de exemplo

Imagine dois usuários já cadastrados no banco:

| id | Nome | Tipo | Saldo |
|---|---|---|---|
| 1 | João Silva | COMUM | R$ 500,00 |
| 2 | Loja do Zé | LOJISTA | R$ 0,00 |

---

### 💸 Realizar uma transferência

**João (id 1) transfere R$ 200,00 para a Loja do Zé (id 2):**

```bash
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "value": 200.0,
    "payer": 1,
    "payee": 2
  }'
```

**Resposta esperada (sucesso):**

```
"Transacao realizada com sucesso"
```

---

### ❌ Tentativas que resultam em erro

**1. Lojista tentando enviar dinheiro (não permitido):**

```bash
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "value": 50.0,
    "payer": 2,
    "payee": 1
  }'
```

**Resposta esperada:**

```
HTTP 400 Bad Request
"Lojistas nao podem realizar transferencias"
```

---

**2. Saldo insuficiente:**

```bash
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "value": 9999.0,
    "payer": 1,
    "payee": 2
  }'
```

**Resposta esperada:**

```
HTTP 400 Bad Request
"Saldo insuficiente"
```

---

**3. Transferência bloqueada pela API de autorização externa:**

Quando a API externa `https://util.devi.tools/api/v2/authorize` retorna negativa, a transferência é cancelada.

**Resposta esperada:**

```
HTTP 400 Bad Request
"Transacao nao autorizada pela API!"
```

---

### 🔎 Dicas para testar com Postman

1. Abra o Postman e clique em **New Request**
2. Selecione o método **POST**
3. Insira a URL: `http://localhost:8080/transfer`
4. Vá na aba **Body** → selecione **raw** → escolha **JSON**
5. Cole o corpo da requisição:

```json
{
  "value": 200.0,
  "payer": 1,
  "payee": 2
}
```

6. Clique em **Send** e observe a resposta

---

### 📝 Observações importantes

- Os IDs de `payer` e `payee` devem corresponder a usuários **existentes no banco de dados**. Caso contrário, a aplicação retornará um erro.
- A API de autorização externa pode **simular falhas aleatórias** — se a transação não for autorizada, tente novamente.
- O campo `value` deve ser um número positivo. Valores zerados ou negativos não são válidos.

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
