# Offer App

## Descrição
Projeto de estudos desenvolvido em Spring Boot para gerenciamento de ofertas.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 4.0.0-M2**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **PostgreSQL**
- **Maven**

## Pré-requisitos
- Java 21 ou superior
- Maven 3.6+
- PostgreSQL 12+
- Git

## Configuração do Banco de Dados

### 1. Instalação do PostgreSQL
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install postgresql postgresql-contrib

# CentOS/RHEL
sudo yum install postgresql-server postgresql-contrib
```

### 2. Configuração do Banco
```sql
-- Criar banco de dados
CREATE DATABASE "sa-comercializacao";

-- Criar usuário (se necessário)
CREATE USER postgres WITH PASSWORD 'xxx@123';
GRANT ALL PRIVILEGES ON DATABASE "sa-comercializacao" TO postgres;
```

### 3. Configuração da Aplicação
O arquivo `application.yml` já está configurado com:
- URL: `jdbc:postgresql://localhost:5432/offer`
- Usuário: `postgres`
- Senha: `xxx@123`

## Como Executar

### 1. Clone do Repositório
```bash
git clone <url-do-repositorio>
cd br.com.offer.app
```

### 2. Compilação
```bash
mvn clean compile
```

### 3. Execução via Maven
```bash
mvn spring-boot:run
```

### 4. Execução via JAR
```bash
mvn clean package
java -jar target/app-1.0.0-SNAPSHOT.jar
```

## Estrutura do Projeto
```
src/
├── main/
│   ├── java/
│   │   └── br/com/offer/app/
│   │       └── br.com.offer.app.AppApplication.java
│   └── resources/
│       ├── application.yml
│       ├── log4jdbc.log4j2.properties
│       └── templates/
│           └── hello.html
└── test/
    └── java/
        └── br/com/offer/app/
```

## Configurações Importantes

### Pool de Conexões HikariCP
- **Minimum Idle**: 0
- **Maximum Pool Size**: 18
- **Idle Timeout**: 100000ms
- **Connection Timeout**: 200000ms
- **Max Lifetime**: 300000ms
- **Transaction Isolation**: TRANSACTION_READ_COMMITTED
- **Auto Commit**: false

### Logging
O projeto utiliza log4jdbc para monitoramento de queries SQL através do arquivo `log4jdbc.log4j2.properties`.

## Endpoints Disponíveis
A aplicação roda por padrão na porta 8080:
- **Base URL**: http://localhost:8080

## Desenvolvimento

### Adicionar Nova Dependência
Edite o arquivo `pom.xml` e execute:
```bash
mvn dependency:resolve
```

### Testes
```bash
mvn test
```

### Profiles
Para diferentes ambientes, você pode criar arquivos de configuração específicos:
- `application-dev.yml`
- `application-prod.yml`

E executar com:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Troubleshooting

### Problemas Comuns

1. **Erro de Conexão com Banco**
   - Verifique se o PostgreSQL está rodando
   - Confirme as credenciais no `application.yml`
   - Teste a conectividade: `psql -h localhost -U postgres -d sa-comercializacao`

2. **Porta 8080 em Uso**
   - Altere a porta no `application.yml`:
   ```yaml
   server:
     port: 8081
   ```

3. **Problemas de Compilação**
   - Verifique a versão do Java: `java -version`
   - Limpe o cache do Maven: `mvn clean`

## Logs
Os logs da aplicação ficam disponíveis no console durante a execução. Para configuração avançada de logs, edite o arquivo `application.yml`.

## Contribuição
1. Faça um fork do projeto
2. Crie uma branch para sua feature: `git checkout -b feature/nova-funcionalidade`
3. Commit suas mudanças: `git commit -m 'Adiciona nova funcionalidade'`
4. Push para a branch: `git push origin feature/nova-funcionalidade`
5. Abra um Pull Request

## Licença
Este é um projeto de estudos.

## Contato
Para dúvidas ou sugestões, entre em contato com a equipe de desenvolvimento.
