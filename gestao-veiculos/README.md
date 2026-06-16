# Sistema de Gestão de Estoque de Veículos

Sistema CRUD feito em Java com Spring Boot pra gerenciar o estoque de veículos de uma concessionária. Dá pra cadastrar marcas, modelos e veículos, buscar com filtros, atualizar informações e remover quando o carro é vendido ou sai de linha.

Esse projeto é a parte prática do trabalho de Programação Orientada a Objetos.

## O que dá pra fazer

- Cadastrar veículos com modelo, marca, ano, cor, preço, quilometragem e status
- Cadastrar marcas e modelos e relacionar tudo (uma marca tem vários modelos, um modelo tem vários veículos)
- Buscar e filtrar veículos por marca, modelo, preço, ano, cor e status
- Atualizar preço, quilometragem e status
- Remover veículos
- Registrar venda e reserva de um veículo
- Ver estatísticas do estoque (quantos disponíveis, vendidos, preço médio, etc.)

## Tecnologias

- Java 17
- Spring Boot 3.2 (Web + Data JPA + Validation)
- MySQL 8
- Maven

## Como os pilares da POO aparecem no código

**Encapsulamento** — todos os atributos das entidades são privados e o acesso é feito por getters e setters. Em alguns setters tem validação (por exemplo, a quantidade não pode ser negativa). As regras de negócio ficam guardadas dentro das classes.

**Herança** — os repositórios herdam de `JpaRepository`, ganhando todos os métodos de CRUD prontos sem precisar reescrever.

**Polimorfismo** — o `GlobalExceptionHandler` trata vários tipos de exceção de formas diferentes. O `RespostaApi<T>` é genérico e funciona com qualquer tipo de dado.

**Abstração** — as interfaces dos repositórios escondem como as consultas são feitas no banco. Os DTOs escondem os detalhes das entidades e mostram só o que a API precisa devolver.

## Estrutura do projeto

```
src/main/java/com/gestaoveiculos/
├── modelo/         -> entidades (Marca, Modelo, Veiculo, StatusVeiculo)
├── repositorio/    -> acesso ao banco (interfaces JpaRepository)
├── servico/        -> regras de negócio
├── controlador/    -> endpoints REST
├── dto/            -> objetos de transferência de dados
└── excecao/        -> tratamento de erros
```

A organização segue a ideia do MVC: o Model são as entidades + serviços, o Controller cuida das requisições e a View seria o front-end (que aqui é opcional).

## Como rodar

1. Tenha o Java 17, Maven e MySQL instalados.

2. No arquivo `src/main/resources/application.properties`, coloque o usuário e a senha do seu MySQL:

```properties
spring.datasource.username=root
spring.datasource.password=sua_senha_aqui
```

3. O banco é criado sozinho na primeira execução (tem `createDatabaseIfNotExist=true` na URL e o Hibernate cria as tabelas). Se preferir criar na mão, use o `esquema.sql`.

4. Rode o projeto:

```bash
mvn spring-boot:run
```

5. A API sobe em `http://localhost:8080`.

Se quiser popular o banco com dados de teste, rode o `dados-exemplo.sql` no MySQL depois que as tabelas existirem.

## Endpoints principais

### Marcas
| Método | Rota | O que faz |
|--------|------|-----------|
| POST | `/api/brands` | cadastra marca |
| GET | `/api/brands` | lista todas |
| GET | `/api/brands/{id}` | busca por id |
| PUT | `/api/brands/{id}` | atualiza |
| DELETE | `/api/brands/{id}` | remove |

### Modelos
| Método | Rota | O que faz |
|--------|------|-----------|
| POST | `/api/models` | cadastra modelo |
| GET | `/api/models` | lista (filtra por brandId, category ou name) |
| GET | `/api/models/{id}` | busca por id |
| PUT | `/api/models/{id}` | atualiza |
| DELETE | `/api/models/{id}` | remove |

### Veículos
| Método | Rota | O que faz |
|--------|------|-----------|
| POST | `/api/vehicles` | cadastra veículo |
| GET | `/api/vehicles` | lista todos (ou filtra por status) |
| GET | `/api/vehicles/{id}` | busca por id |
| GET | `/api/vehicles/filter` | busca com vários filtros juntos |
| GET | `/api/vehicles/statistics` | estatísticas do estoque |
| PUT | `/api/vehicles/{id}` | atualiza preço, km e status |
| PATCH | `/api/vehicles/{id}/sell` | registra venda |
| PATCH | `/api/vehicles/{id}/reserve` | reserva |
| DELETE | `/api/vehicles/{id}` | remove |

## Exemplos de requisição

Cadastrar uma marca:

```bash
curl -X POST http://localhost:8080/api/brands \
  -H "Content-Type: application/json" \
  -d '{"name":"Toyota","description":"Montadora japonesa","countryOfOrigin":"Japão"}'
```

Cadastrar um modelo (precisa do id da marca):

```bash
curl -X POST http://localhost:8080/api/models \
  -H "Content-Type: application/json" \
  -d '{"name":"Corolla","category":"Sedan","brandId":1}'
```

Cadastrar um veículo (precisa do id do modelo):

```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{"color":"Prata","manufactureYear":2022,"price":125000.00,"mileage":30000,"vehicleModelId":1}'
```

Filtrar veículos disponíveis de uma marca até certo preço:

```bash
curl "http://localhost:8080/api/vehicles/filter?brandId=1&status=DISPONIVEL&priceMax=130000"
```

## Status possíveis de um veículo

- `DISPONIVEL`
- `VENDIDO`
- `RESERVADO`
- `DESCONTINUADO`
- `EM_MANUTENCAO`

## Testes

Tem alguns testes das regras de negócio da entidade Veiculo (venda, reserva). Pra rodar:

```bash
mvn test
```
