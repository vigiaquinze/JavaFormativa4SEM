# Sistema de aluguéis de campos de futebol

## Código Fonte

## Banco de Dados
    -- Criar a tabela de clientes
    CREATE TABLE cliente (
        id SERIAL PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        telefone VARCHAR(15),
        tipo VARCHAR(10) CHECK (tipo IN ('ADMIN', 'USER')) NOT NULL
    );

    -- Criar a tabela de campos
    CREATE TABLE campo (
        id SERIAL PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        disponibilidade BOOLEAN NOT NULL DEFAULT TRUE
    );

    -- Criar a tabela de reservas
    CREATE TABLE reserva (
        id SERIAL PRIMARY KEY,
        cliente_id INT REFERENCES cliente(id) ON DELETE CASCADE,
        campo_id INT REFERENCES campo(id) ON DELETE CASCADE,
        data DATE NOT NULL,
        hora_inicio TIME NOT NULL,
        hora_fim TIME NOT NULL,
        CONSTRAINT horario_unico UNIQUE (campo_id, data, hora_inicio, hora_fim)
    );

    -- Criar a tabela de relatórios
    CREATE TABLE relatorio (
        id SERIAL PRIMARY KEY,
        cliente_id INT REFERENCES cliente(id) ON DELETE CASCADE,
        campo_id INT REFERENCES campo(id) ON DELETE CASCADE,
        data DATE NOT NULL,
        hora_inicio TIME NOT NULL,
        hora_fim TIME NOT NULL
    );