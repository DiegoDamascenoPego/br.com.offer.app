# CORE

## Neste projeto utilizamos várias tecnologias e seguimos certos padrões de implementação.

## Tecnologias
- **Java**: Linguagem de programação principal do projeto.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java, facilitando a configuração e o desenvolvimento de APIs RESTful.
- **Maven**: Gerenciador de dependências e build do projeto.
- **JUnit**: Framework para testes unitários.
- **Mockito**: Biblioteca para criação de mocks em testes unitários.
- **Lombok**: Biblioteca que reduz o boilerplate code, gerando automaticamente getters, setters, construtores, etc.
- **Swagger**: Ferramenta para documentação de APIs RESTful, permitindo a visualização e teste das APIs diretamente na interface web.
- **Spring Data JPA**: Facilita a interação com bancos de dados relacionais, utilizando repositórios para operações CRUD.
- **Spring Security**: Framework para implementar segurança em aplicações Spring, permitindo autenticação e autorização de usuários.
- **Spring Boot Test**: Módulo do Spring Boot que facilita a criação de testes integrados.
- **Spring Boot Actuator**: Fornece endpoints para monitoramento e gerenciamento da aplicação em produção.

## Padrões de Implementação
- **Injeção de Dependência**: Utilizamos o Spring para gerenciar as dependências do projeto.
- **Testes Unitários**: Implementamos testes unitários com JUnit e Mockito para garantir a qualidade do código.
- **Documentação**: Utilizamos Swagger para documentar as APIs RESTful.
- **Boas Práticas de Código**: Seguindo as convenções do Java e utilizamos o Lombok para reduzir boilerplate code.
- **Estrutura de Pacotes**: Organizamos o código em pacotes lógicos, como `controller`, `service`, `repository`, e `model`.
- **Tratamento de Exceções**: Implementamos um mecanismo global de tratamento de exceções para garantir que erros sejam tratados de forma consistente.
- **Configuração de Banco de Dados**: Utilizamos o Spring Data JPA para interagir com o banco de dados, seguindo as melhores práticas de repositórios.
- **Configuração de Segurança**: Implementamos segurança básica com Spring Security, garantindo que as APIs estejam protegidas.
- **Versionamento de API**: Utilizamos versionamento de API para garantir compatibilidade entre versões.
- **Configuração de Perfis**: Utilizamos perfis do Spring para gerenciar diferentes ambientes (local, dev, staging, prod).
- **Configuração de Propriedades**: Utilizamos arquivos `application.yml` para gerenciar configurações e propriedades do app.

## Script de banco de dados    
 - **Banco de dados**: PostgreSQL versão 15.0 ou superior.
 - **Script de crição de novas tabelas**: Utilizamos Script SQL para criar novas tabelas.
 - **Script de atualização de tabelas**: Utilizamos Script SQL para atualizar tabelas existentes.
 - **Migration**: Utilizamos o Flyway para gerenciar migrações de banco de dados, e registramos os novos scripts no diretório conforme configuração definida no application.yml flyway.locations.
 - **locations**: Os arquivos de migração devem ser colocados no diretório `src/main/resources/db/migration/v2` do módulo sa-taco-service.
 - **Convenção de Nomenclatura**: Utilizamos nomes descritivos para tabelas e colunas aplicando a convenção snake_case.
 - **Versionamento**: Para aplicarmos o versionamento dos scripts de banco de dados, utilizamos a convenção `[Prefix].[Version]__[Description].[Suffix]` `[V2].[AAAAMMDDHHMM]__[descricao_da_migracao].sql` e para a informação de data/hora no nome do arquivo utilize a data/hora de agora, e para a descrição utilize a convenção snake_case e produza a descrição inspirado no DDL, e.g. create_table_, alter_table_, drop_index_, create_index.

## Exemplo de Controller:
```java
@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/safra", produces = APPLICATION_JSON_VALUE)
@Tag(name = "safra-controller")
public class SafraController {

    private final RegistrarSafraUseCase registrarSafraAppService;
    private final AlterarSafraUseCase alterarSafraAppService;
    private final RemoverSafraUseCase removerSafraAppService;

    @RolesAllowed({ ADMINISTRADOR, MESA_OPERACOES, COMERCIAL })
    @PostMapping
    public ResponseEntity<Void> registrarSafra(@RequestBody RegistrarSafra safra) {
        SafraId id = registrarSafraAppService.handle(safra);

        return ResponseEntity.created(fromCurrentRequest()
                .path("/").path(id.asString()).build().toUri())
            .build();
    }

    @RolesAllowed({ ADMINISTRADOR })
    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void alterar(@PathVariable SafraId id, @RequestBody AlterarSafra cmd) {
        alterarSafraAppService.handle(cmd.from(id));
    }

    @RolesAllowed({ ADMINISTRADOR })
    @PutMapping(path = "/{id}/remover")
    public void remover(@PathVariable SafraId id) {
        removerSafraAppService.handle(RemoverSafra.from(id));
    }
}

```

## Exemplo de UseCase Interface:
```java
public interface RegistrarSafraUseCase {

    SafraId handle(RegistrarSafra command);

    void on(SafraRegistrada event);


    @Value
    @Builder
    class RegistrarSafra {

        @Valid
        @NotNull(message = "{Execucao.Safra.descricao.NotNull}")
        DescricaoResumida descricao;
    }

    @Value
    @Builder(access = PRIVATE)
    class SafraRegistrada implements ExecEventMessage, DomainEvent {

        SafraId id;

        DescricaoResumida descricao;

        public static SafraRegistrada from(Safra safra) {
            return SafraRegistrada.builder()
                .id(safra.getId())
                .descricao(safra.getDescricao())
                .build();
        }
    }
}
```


## Exemplo de AppService:
```java
@RequiredArgsConstructor

@Service
@Transactional
public class RegistrarSafraAppService implements RegistrarSafraUseCase {

    private final ExecPublisher publisher;

    private final SafraDomainRepository safraDomainRepository;

    @Override
    public SafraId handle(RegistrarSafra command) {
        Safra safra = Safra.builder()
            .descricao(command.getDescricao())
            .descricaoDuplicatedConstraint(safraDomainRepository::existsByDescricaoAndIdNot)
            .build();

        safraDomainRepository.save(safra);

        return safra.getId();
    }

    @Override
    @EventListener
    public void on(SafraRegistrada event) {
        publisher.dispatch(event);
    }
}
```

## Exemplo de Repository:
```java
public interface SafraDomainRepository extends Repository<Safra, SafraId> {

    void save(Safra safra);

    Optional<Safra> findById(SafraId id);

    default Safra get(SafraId id) {
        return findById(id).orElseThrow(id::notFoundException);
    }
}
```

## Exemplo de Model:
```java
@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@DynamicUpdate
@Entity
public class Safra extends AbstractEntitySoftDelete<Safra, SafraId> {

    @EmbeddedId
    @AttributeOverride(name = SafraId.ATTR, column = @Column(name = "id"))
    private SafraId id;

    @AttributeOverride(name = DescricaoResumida.ATTR, column = @Column(name = "descricao"))
    private DescricaoResumida descricao;

    public static SafraBuilder builder() {
        return new SafraBuilder();
    }

    public Safra(SafraBuilder builder) {

        this.id = requireNonNull(builder.id);
        this.descricao = requireNonNull(builder.descricao);

        registerEvent(RegistrarSafraUseCase.SafraRegistrada.from(this));
    }

    public SafraBuilderUpdate alterar() {
        return new SafraBuilderUpdate(id, form -> {
            descricao = requireNonNull(form.getDescricao());

            registerEvent(AlterarSafraUseCase.SafraAlterada.from(this));
        });
    }

    public void remover() {
        markAsDeleted();
        registerEvent(SafraRemovida.from(this.id));
    }
}
```

Builder
```java
public class SafraBuilder {

    protected SafraId id;

    protected DescricaoResumida descricao;

    private BiPredicate<DescricaoResumida, SafraId> descricaoConstraint;

    public SafraBuilder descricao(DescricaoResumida descricao) {
        this.descricao = descricao;
        return this;
    }

    public SafraBuilder descricaoDuplicatedConstraint(BiPredicate<DescricaoResumida, SafraId> constraint) {
        this.descricaoConstraint = constraint;
        return this;
    }

    public Safra build() {

        id = SafraId.generate();

        requireNonNull(descricao).applyDuplicateConstraint(descricaoConstraint, id);

        return new Safra(this);
    }
}
```

UpdateBuilder
```java
@Getter
public class SafraBuilderUpdate {

    private final Consumer<SafraBuilderUpdate> action;

    protected DescricaoResumida descricao;

    private BiPredicate<DescricaoResumida, SafraId> descricaoConstraint;

    public SafraBuilderUpdate descricao(DescricaoResumida descricao) {
        this.descricao = descricao;
        return this;
    }

    public SafraBuilderUpdate descricaoDuplicatedConstraint(BiPredicate<DescricaoResumida, SafraId> constraint) {
        this.descricaoConstraint = constraint;
        return this;
    }

    SafraBuilderUpdate(SafraId id, Consumer<SafraBuilderUpdate> action) {
        this.id = requireNonNull(id);
        this.action = requireNonNull(action);
    }

    public void apply() {

        requireNonNull(descricao)
            .applyDuplicateConstraint(descricaoConstraint, id);

        action.accept(this);
    }
}
```

## Exemplo de Teste Integrado:
```java
@IntegrationTest
class RegistrarSafraUseCaseIT {

    @Autowired
    private TestPublisher testPublisher;

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithAdministratorUser
    void caminhoFeliz() throws Exception {

        RegistrarSafra command = RegistrarSafra.builder().descricao(DescricaoResumida.from("2022")).build();

        MvcResult result = mock.perform(post("/api/v1/safra").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(command)))
            .andExpect(status().isCreated())
            .andExpect(header().exists(LOCATION))
            .andExpect(redirectedUrlPattern("**/**/api/v1/safra/*"))
            .andReturn();

        Assertions.assertNotNull(SafraId.from(extractIdFromLocation(result)));

        testPublisher.verify(RegistrarSafraUseCase.SafraRegistrada.class);
    }
}
```

## Exemplo de DDL de criação:
```sql  
CREATE TABLE IF NOT EXISTS safra (
	id uuid NOT NULL,
	row_version int2 DEFAULT 0 NOT NULL,
	row_created_at timestamp DEFAULT now() NOT NULL,
	row_updated_at timestamp DEFAULT now() NOT NULL,
	
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT safra_pk PRIMARY KEY (id)
);
```

## Exemplo de DDL de alteração:
```sql  
    ALTER TABLE safra ADD COLUMN IF NOT EXISTS produto_id UUID NULL;
);
```

## Exemplo de arquivo de migração:
```sql
[Prefix]=V2
[Version]=AAAAMMDDHHMM | Horário no formato UTC-3
[Description]=descricao_em_formato_snake_case
[Suffix]=.sql
-- V2.AAAAMMDDHHMM__create_table_safra.sql
-- V2.AAAAMMDDHHMM__alter_table_safra_add_column_descricao.sql
``` 

## Exemplo nome Primary Key:
```sql
[TableName].[ConstraintType]
-- safra_pk
```

## Exemplo nome Constraint:
```sql
[origem]_[destino]_[Suffix]
[origem] = nome da tabela de origem
[destino] = nome da tabela de destino
[suffix] = fk   
-- safra_produto_fk
```

## Exemplo nome Index:
```sql
[tabela]_[campo]_[suffix]
[tabela] = nome da tabela de origem
[campo] = nome do campo
[suffix] = index    

-- CREATE INDEX IF NOT EXISTS safra_descricao_index ON sefra(descricao);
```