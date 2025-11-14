CREATE TABLE IF NOT EXISTS usuario (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    nome varchar(256) NOT NULL,
    documento varchar(32) NOT NULL,
    tipo_usuario varchar(32) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT usuario_pk PRIMARY KEY (id),
    CONSTRAINT uk_usuario_documento UNIQUE (documento)
);

CREATE TABLE IF NOT EXISTS usuario_endereco (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    usuario_id uuid NOT NULL,
    logradouro varchar(256) NULL,
    numero varchar(32) NULL,
    complemento varchar(128) NULL,
    bairro varchar(128) NULL,
    cidade varchar(128) NULL,
    estado varchar(2) NULL,
    cep varchar(10) NULL,
    pais varchar(64) NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT usuario_endereco_pk PRIMARY KEY (id),
    CONSTRAINT fk_usuario_endereco_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS usuario_contato (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    usuario_id uuid NOT NULL,
    tipo varchar(64) NOT NULL,
    contato varchar(256) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT usuario_contato_pk PRIMARY KEY (id),
    CONSTRAINT fk_usuario_contato_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);


CREATE TABLE IF NOT EXISTS categoria (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    descricao varchar(128) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT categoria_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS objeto (
    id uuid NOT NULL,
    leilao_id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    categoria_id uuid NOT NULL,
    descricao varchar(128) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT objeto_pk PRIMARY KEY (id),
    CONSTRAINT fk_objeto_leilao FOREIGN KEY (leilao_id) REFERENCES leilao(id),
    CONSTRAINT fk_objeto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);


CREATE TABLE IF NOT EXISTS leilao (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    descricao varchar(128) NOT NULL,
    observacao varchar(128) NULL,
    localizacao varchar(256) NULL,
    lance_inicial decimal(9,2) NULL,
    lance_final decimal(9,2) NULL,
    lote varchar(64) NOT NULL,
    inicio timestamp NOT NULL,
    fim timestamp NOT NULL,
    condicao_venda jsonb NULL,
    tipo_lance varchar(64) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT leilao_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS lance (
    id uuid NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,

    leilao_id uuid NOT NULL,
    usuario_id uuid NOT NULL,
    valor numeric(10,4) NOT NULL,
    moeda varchar(3) NOT NULL,

    CONSTRAINT lance_pk PRIMARY KEY (id),
    CONSTRAINT fk_lance_leilao FOREIGN KEY (leilao_id) REFERENCES leilao(id),
    CONSTRAINT fk_lance_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT ck_lance_moeda CHECK (moeda IN ('USD', 'BRL'))
);
