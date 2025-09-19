CREATE TABLE IF NOT EXISTS pessoa_endereco (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    pessoa_id uuid NOT NULL,
    logradouro varchar(256) NULL,
    numero varchar(32) NULL,
    complemento varchar(128) NULL,
    bairro varchar(128) NULL,
    cidade varchar(128) NULL,
    estado varchar(2) NULL,
    cep varchar(10) NULL,
    pais varchar(64) NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT pessoa_endereco_pk PRIMARY KEY (id),
    CONSTRAINT fk_pessoa_endereco_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);

CREATE TABLE IF NOT EXISTS pessoa_contato (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    pessoa_id uuid NOT NULL,
    tipo varchar(64) NOT NULL,
    contato varchar(256) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT pessoa_contato_pk PRIMARY KEY (id),
    CONSTRAINT fk_pessoa_contato_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
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
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    categoria_id uuid NOT NULL,
    descricao varchar(128) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT objeto_pk PRIMARY KEY (id),
    CONSTRAINT fk_objeto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);


CREATE TABLE IF NOT EXISTS leilao (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    objeto_id uuid NOT NULL,
    descricao varchar(128) NOT NULL,
    observacao varchar(128) NULL,
    localizacao geometry NULL,
    lance_final decimal(9,2) NULL,
    lote varchar(64) NOT NULL,
    inicio timestamp NOT NULL,
    fim timestamp NOT NULL,
    condicao_venda jsonb NULL,
    tipo_lance varchar(64) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT leilao_pk PRIMARY KEY (id),
    CONSTRAINT fk_leilao_objeto FOREIGN KEY (objeto_id) REFERENCES objeto(id)
);

CREATE TABLE IF NOT EXISTS lance (
    id uuid NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,

    leilao_id uuid NOT NULL,
    pessoa_id uuid NOT NULL,
    valor numeric(10,4) NOT NULL,
    moeda varchar(3) NOT NULL,

    CONSTRAINT lance_pk PRIMARY KEY (id),
    CONSTRAINT fk_lance_leilao FOREIGN KEY (leilao_id) REFERENCES leilao(id),
    CONSTRAINT fk_lance_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    CONSTRAINT ck_lance_moeda CHECK (moeda IN ('USD', 'BRL'))
);

CREATE TABLE IF NOT EXISTS pessoa (
    id uuid NOT NULL,
    row_version int2 DEFAULT 0 NOT NULL,
    row_created_at timestamp DEFAULT now() NOT NULL,
    row_updated_at timestamp DEFAULT now() NOT NULL,

    nome varchar(256) NOT NULL,
    documento varchar(128) NOT NULL,
    tipo varchar(64) NOT NULL,
    deleted bool DEFAULT false NOT NULL,

    CONSTRAINT pessoa_pk PRIMARY KEY (id)
);
