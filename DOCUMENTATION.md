Documentação - Solução técnica
========

* * * * *

> Essa API possui endpoints para criar usuário, efetuar login e efetuar transferências somente.
> Os outros possíveis endpoints relacionados à essa API não foram mapeados no challenge.

Conteúdo
=================

* * * * *

1. [Stack utilizada](#stack-utilizada)
2. [Arquitetura](#arquitetura)
3. [Banco de dados - Schema](#banco-de-dados---schema)
4. [Endpoints](#endpoints)

Stack Utilizada
========

* * * * *

- Kotlin
- Spring Boot 2.7.2
- JPA Hibernate
- Criptografia BCrypt (para senhas)
- Open API
- PostgreSQL
- Valiktor
- Mockk

Arquitetura
========

* * * * *

- A aplicação foi segmentada entre os domínios de users e transactions para isolar as regras de negócio.
Common é o que gera impacto em todos os domínios.

- A arquitetura escolhida foi a Hexagonal, dividindo a implementação entre adapters, domain e usecases.
A separação de domain e usecases é somente para segmentar a escrita, nada impede que usecases esteja dentro de domain.

Banco de dados - Schema
========

* * * * *

O schema do banco foi gerado através da ferramenta https://sqldbm.com para o banco PostgreSQL.
O .sql gerado está disponível em docker/schema.sql

![DB](https://github.com/DiogoMurano/picpay-desafio-backend/blob/master/database.png)

Endpoints
========

* * * * *

Toda a implementação foi feita considerando problemas de concorrência de threads, performance e principalmente confiabilidade de dados.

Erros HTTP
=================

* * * * *

|          |                        Description                         |
|:--------:|:----------------------------------------------------------:|
| HTTP 4xx | Código de retorno quanto o erro é cometido pelo remetente. |
| HTTP 5xx |  Código de retorno quando o erro é emitido pelo servidor.  |

Endpoints de usuário
=================

* * * * *

### [POST] - /api/v1/users

Registra um novo usuário na API

|                     |   Nome    |              Descriçao               | Obrigatorio |
|:-------------------:|:---------:|:------------------------------------:|:-----------:|
| Corpo da requisição | full_name |       Nome completo do usuario       |     sim     |
| Corpo da requisição | document  |        CPF ou CNPJ do usuario        |     sim     |
| Corpo da requisição |   email   |           Email do usuario           |     sim     |
| Corpo da requisição | password  |           Senha do usuario           |     sim     |
| Corpo da requisição |   type    | COMMON ou RETAILER - tipo do usuario |     sim     |

#### Corpo de exemplo

```json
{
    "full_name": "User Name",
    "document": "000.000.000-00",
    "email": "user@email.com",
    "password": "password123",
    "type": "COMMON"
}
```

#### Resposta - HTTP 201

Location header:
```
/api/v1/users/{user_id}
```

### [POST] - /api/v1/auth/login

Autentica usuário

|                     |   Nome   |    Descriçao     | Obrigatorio |
|:-------------------:|:--------:|:----------------:|:-----------:|
| Corpo da requisição |  email   | Email do usuario |     sim     |
| Corpo da requisição | password | Senha do usuario |     sim     |

#### Corpo de exemplo

```json
{
    "email": "user@email.com",
    "password": "password123"
}
```

#### Resposta - HTTP 200

```json
{
    "user_id": "6b1f0baa-4759-4db0-80b9-839403930e05",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "expires_in": 600
}
```

Endpoints de transferência
=================

* * * * *

### [POST] - /api/v1/users/{user_id}/transfer

Envia uma transferência à outro usuário

|                     |     Nome      |               Descriçao                | Obrigatorio |
|:-------------------:|:-------------:|:--------------------------------------:|:-----------:|
|    Path variable    |    user_id    |        Id do usuario remetente         |     sim     |
| Corpo da requisição |   target_id   |         Id do usuario destino          |     sim     |
| Corpo da requisição |     value     | Valor da transferencia, numero decimal |     sim     |
| Corpo da requisição |  description  |  Mensagem relacionada à transferencia  |     sim     |
| Corpo da requisição | Authorization |              Bearer token              |     sim     |

#### Corpo de exemplo

```json
{
    "target_id": "957dbbb7-56e4-4f18-8ac4-edae6432f6ee",
    "value": 53.71,
    "description": "Pizza de ontem"
}
```

#### Resposta - HTTP 200

```json
{
    "sent_date": "2022-08-06T18:20",
    "message": "Transfer sent successfully."
}
```
