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

* Em caso de sucesso, retorne no body de resposta, o usuário, mais os campos:
    * `id`: id do usuário (pode ser o próprio gerado pelo banco, porém seria interessante se fosse um UUID)
    * `created`: data da criação do usuário
    * `modified`: data da última atualização do usuário
    * `last_login`: data do último login (no caso da criação, será a mesma data que a  data de criação)
    * `token`: token de acesso para o endpoint de perfil (pode ser um UUID ou um JWT)

Código: `HTTP 201 Created`
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

* Caso o e-mail já exista, deverá retornar erro com a mensagem "E-mail já existente".

Código: `HTTP 409 Conflict`       Mensagem: `E-mail já existente`

### Login: `POST /login`

Este endpoint deverá ser utilizado para que o usuário, utilizando um e-mail e senha cadastrados, seguindo o formato abaixo:

> ```json
>     {
>         "email": "joao@silva.org",
>         "password": "hunter2"
>     }
> ```

#### Respostas:

* Caso o e-mail e a senha correspondam a um usuário existente

Código: `HTTP 200 OK`
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

* Caso o e-mail não exista, retornar status apropriado e utilizar o formato de mensagem de erro com a mensagem "Usuário e/ou senha inválidos".

Código: `HTTP 404 Not Found`      Mensagem: `Usuário e/ou senha inválidos`

* Caso o e-mail exista mas a senha não bata, retornar o status 401 e utilizar o formato de mensagem de erro com a mensagem "Usuário e/ou senha inválidos".

Código: `HTTP 401 Unauthorized`   Mensagem: `Usuário e/ou senha inválidos`

### Perfil do Usuário: `GET /user/{id}`

Este endpoint deverá receber no header um token (jwt ou uuid), e um id de usuário no path, seguindo o formato abaixo:

* URL: `/user/00c6de58-6582-11eb-ae93-0242ac130002`
* Header: `Key = token, Value = eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA`

#### Respostas:

* Caso tudo esteja ok

Código: `HTTP 200 OK`
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

* Caso o token não seja passado no header, deverá retornar erro com status apropriado e com a mensagem "Não autorizado".

Código: `HTTP 400 Bad Request`    Mensagem: `Não autorizado`

* Caso o token seja diferente do persistido, retornar erro com status apropriado e com a mensagem "Não autorizado".

Código: `HTTP 404 Not Found`      Mensagem: `Não autorizado`

* Caso o usuário não seja encontrado pelo id, retornar com status e mensagem de erro apropriados.

Código: `HTTP 404 Not Found`      Mensagem: `Usuário não encontrado`

* Verificar se o último login foi há MENOS de 30 minutos atrás. 
     * Caso não seja há MENOS de 30 minutos atrás, retornar erro com status apropriado e com a mensagem "Sessão inválida".

Código: `HTTP 410 Gone`           Mensagem: `Sessão inválida`
