# Desafio Java Concrete Solutions

Criar uma aplicação que exponha uma API RESTful de criação de usuário e login.

## Endpoints

### Cadastro: `POST /user/register`

Esse endpoint deverá receber no body um usuário com os campos "nome", "email", "senha", mais uma lista de objetos "telefone", seguindo o formato abaixo:

>```json
>   {
>        "name": "João da Silva",
>        "email": "joao@silva.org",
>        "password": "hunter2",
>        "phones": [
>            {
>                "number": "987654321",
>                "ddd": "21"
>            }
>        ]
>    }
>```

#### Respostas: 

* Código: `HTTP 201 Created`
Corpo:

>```json
>    {   
>        "id": "00c6de58-6582-11eb-ae93-0242ac130002",
>         "name": "João da Silva",
>         "email": "joao@silva.org",
>         "password": "hunter2",
>         "phones": [
>             {
>                 "number": "987654321",
>                 "ddd": "21"
>             }
>         ],
>         "created": "2020-10-03T19:30:00",
>         "modified": "2020-10-03T19:30:00",
>         "last_login": "2020-10-03T19:30:00",
>         "token": "eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA"
>     }
> ```

* Código: `HTTP 409 Conflict`       Mensagem: `E-mail já existente`

### Login: `POST /login`

Este endpoint deverá ser utilizado para que o usuário, utilizando um e-mail e senha cadastrados, seguindo o formato abaixo:

> ```json
>     {
>         "email": "joao@silva.org",
>         "password": "hunter2"
>     }
> ```

#### Respostas:

* Código: `HTTP 200 OK`
Corpo:

>```json
>    {   
>        "id": "00c6de58-6582-11eb-ae93-0242ac130002",
>         "name": "João da Silva",
>         "email": "joao@silva.org",
>         "password": "hunter2",
>         "phones": [
>             {
>                 "number": "987654321",
>                 "ddd": "21"
>             }
>         ],
>         "created": "2020-10-03T19:30:00",
>         "modified": "2020-10-03T19:30:00",
>         "last_login": "2020-10-03T19:30:00",
>         "token": "eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA"
>     }
> ```

* Código: `HTTP 404 Not Found`      Mensagem: `Usuário e/ou senha inválidos`

* Código: `HTTP 401 Unauthorized`   Mensagem: `Usuário e/ou senha inválidos`

### Perfil do Usuário: `GET /user/{id}`

Este endpoint deverá receber no header um token (jwt ou uuid), e um id de usuário no path, seguindo o formato abaixo:

* URL: `/user/00c6de58-6582-11eb-ae93-0242ac130002`
* Header: `Key = token, Value = eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA`

#### Respostas:

* Código: `HTTP 200 OK`
Corpo:

>```json
>    {   
>        "id": "00c6de58-6582-11eb-ae93-0242ac130002",
>         "name": "João da Silva",
>         "email": "joao@silva.org",
>         "password": "hunter2",
>         "phones": [
>             {
>                 "number": "987654321",
>                 "ddd": "21"
>             }
>         ],
>         "created": "2020-10-03T19:30:00",
>         "modified": "2020-10-03T19:30:00",
>         "last_login": "2020-10-03T19:30:00",
>         "token": "eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA"
>     }
> `


* Código: `HTTP 400 Bad Request`    Mensagem: `Não autorizado`

* Código: `HTTP 404 Not Found`      Mensagem: `Não autorizado`

* Código: `HTTP 404 Not Found`      Mensagem: `Usuário não encontrado`

* Código: `HTTP 410 Gone`           Mensagem: `Sessão inválida`
