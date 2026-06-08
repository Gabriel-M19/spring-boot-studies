# 🎬 ScreenMatch — Back-end API

Aplicação back-end desenvolvida com **Java 24** e **Spring Boot 4**, responsável por gerenciar séries e episódios com persistência em banco de dados relacional e integração com inteligência artificial.

> Projeto desenvolvido como parte dos estudos em Spring Boot, Spring Data JPA e integração com APIs externas.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 24 | Linguagem principal |
| Spring Boot | 4.0.5 | Framework back-end |
| Spring Data JPA | — | Persistência e ORM |
| Spring Web | — | Criação de endpoints REST |
| PostgreSQL | — | Banco de dados relacional |
| OpenAI API | — | Integração com IA (resumos e traduções) |
| Maven | — | Gerenciamento de dependências |

---

## ✅ Funcionalidades

- Busca e cadastro de séries por nome
- Listagem de episódios com dados detalhados
- Persistência de dados no PostgreSQL via Spring Data JPA
- Mapeamento de relacionamentos entre entidades (série → temporadas → episódios)
- Consultas customizadas com JPQL e Spring Data
- Integração com OpenAI para geração de resumos automáticos via IA
- API REST com endpoints para consumo pelo front-end

---

## 🗄️ Modelagem do Banco de Dados

```
Serie
 ├── id (PK)
 ├── titulo
 ├── totalTemporadas
 ├── avaliacao
 ├── genero
 ├── atores
 ├── poster
 ├── sinopse
 └── episodios (OneToMany)

Episodio
 ├── id (PK)
 ├── temporada
 ├── titulo
 ├── numeroEpisodio
 ├── avaliacao
 ├── dataLancamento
 └── serie (ManyToOne)
```

---

## ⚙️ Como Executar

### Pré-requisitos

- Java 24+
- Maven 3.9+
- PostgreSQL instalado e em execução

### Configuração do banco de dados

Crie um banco no PostgreSQL:

```sql
CREATE DATABASE screenmatch;
```

Configure as variáveis no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/screenmatch
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### Rodando o projeto

```bash
# Clone o repositório
git clone https://github.com/Gabriel-M19/spring-boot-studies.git

# Entre na pasta
cd spring-boot-studies

# Execute
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

## 📁 Estrutura do Projeto

```
src/
└── main/
    └── java/
        └── br/com/alura/screenmatch/
            ├── model/          # Entidades JPA (Serie, Episodio)
            ├── repository/     # Interfaces Spring Data JPA
            ├── service/        # Regras de negócio e integração com APIs
            └── principal/      # Classe principal e menus de interação
```

---

## 📚 Aprendizados Aplicados

- Criação e evolução de entidades com anotações JPA (`@Entity`, `@OneToMany`, `@ManyToOne`)
- Consultas com JPQL e métodos derivados do Spring Data
- Integração com APIs externas via `RestTemplate`
- Consumo da API da OpenAI para funcionalidades de IA
- Boas práticas com Spring Boot e separação de responsabilidades

---

## 👨‍💻 Autor

**Gabriel de Melo Rodrigues**
- GitHub: [@Gabriel-M19](https://github.com/Gabriel-M19)
- LinkedIn: [gabriel-melo-6780ab261](https://www.linkedin.com/in/gabriel-melo-6780ab261)
