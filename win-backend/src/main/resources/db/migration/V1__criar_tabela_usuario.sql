CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE usuarios (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          nome VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          senha VARCHAR(255) NOT NULL,
                          telefone VARCHAR(20),
                          data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          ultimo_acesso TIMESTAMP WITH TIME ZONE
);

CREATE TABLE clientes (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          usuario_id UUID UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                          cpf VARCHAR(14) UNIQUE,
                          data_nascimento DATE
);

CREATE TABLE vendedores (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            usuario_id UUID UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                            nome_fantasia VARCHAR(100) NOT NULL,
                            razao_social VARCHAR(100),
                            cnpj VARCHAR(18) UNIQUE,
                            data_abertura DATE,
                            horario_funcionamento JSONB
);

CREATE TABLE motoristas (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            usuario_id UUID UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                            cnh VARCHAR(20) UNIQUE,
                            veiculo VARCHAR(100),
                            placa VARCHAR(10) UNIQUE,
                            status_disponibilidade VARCHAR(20) DEFAULT 'offline' CHECK (status_disponibilidade IN ('online', 'offline', 'em_corrida'))
);

CREATE TABLE enderecos (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                           tipo_endereco VARCHAR(50) CHECK (tipo_endereco IN ('residencial', 'comercial', 'entrega', 'retirada')),
                           rua VARCHAR(100),
                           numero VARCHAR(20),
                           bairro VARCHAR(50),
                           cidade VARCHAR(50),
                           estado VARCHAR(50),
                           cep VARCHAR(20),
                           complemento VARCHAR(100)
);

CREATE TABLE categorias (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            nome VARCHAR(100) NOT NULL,
                            categoria_pai_id UUID REFERENCES categorias(id) ON DELETE SET NULL,
                            descricao TEXT
);

CREATE TABLE produtos (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          vendedor_id UUID NOT NULL REFERENCES vendedores(id) ON DELETE CASCADE,
                          categoria_id UUID REFERENCES categorias(id) ON DELETE SET NULL,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          preco NUMERIC(10, 2) NOT NULL CHECK (preco > 0),
                          quantidade_estoque INTEGER NOT NULL DEFAULT 0 CHECK (quantidade_estoque >= 0),
                          ativo BOOLEAN DEFAULT TRUE,
                          data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE imagens_produto (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 produto_id UUID NOT NULL REFERENCES produtos(id) ON DELETE CASCADE,
                                 url TEXT NOT NULL,
                                 principal BOOLEAN DEFAULT FALSE
);

CREATE TABLE pedidos (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         cliente_id UUID REFERENCES clientes(id) ON DELETE SET NULL,
                         motorista_id UUID REFERENCES motoristas(id) ON DELETE SET NULL,
                         status VARCHAR(50) DEFAULT 'pendente' CHECK (status IN ('pendente', 'em_preparacao', 'em_transporte', 'entregue', 'cancelado', 'devolvido')),
                         valor_total NUMERIC(10, 2),
                         data_pedido TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                         data_atualizacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE itens_pedido (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              pedido_id UUID NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
                              produto_id UUID NOT NULL REFERENCES produtos(id) ON DELETE CASCADE,
                              quantidade INTEGER NOT NULL CHECK (quantidade > 0),
                              preco_unitario NUMERIC(10, 2) NOT NULL
);

CREATE TABLE entregas (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          pedido_id UUID UNIQUE NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
                          motorista_id UUID REFERENCES motoristas(id) ON DELETE SET NULL,
                          endereco_entrega_id UUID REFERENCES enderecos(id) ON DELETE SET NULL,
                          status VARCHAR(50) DEFAULT 'pendente' CHECK (status IN ('pendente', 'em_rota', 'entregue', 'falha_entrega')),
                          data_saida TIMESTAMP WITH TIME ZONE,
                          data_entrega_estimada TIMESTAMP WITH TIME ZONE,
                          data_entrega_real TIMESTAMP WITH TIME ZONE,
                          codigo_rastreamento VARCHAR(50)
);

CREATE TABLE pagamentos (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            pedido_id UUID UNIQUE NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
                            metodo_pagamento VARCHAR(50),
                            status VARCHAR(50) DEFAULT 'pendente' CHECK (status IN ('pendente', 'aprovado', 'rejeitado', 'estornado')),
                            valor_total NUMERIC(10,2),
                            data_pagamento TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            transacao_id VARCHAR(100) -- Para referência externa como Stripe ou PagSeguro
);
CREATE TABLE lojas (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                       nome_fantasia VARCHAR(100),
                       razao_social VARCHAR(100),
                       cnpj VARCHAR(20) UNIQUE,
                       telefone VARCHAR(20),
                       email VARCHAR(100),
                       horario_funcionamento VARCHAR(50)
);


CREATE TABLE avaliacoes_produto (
                                    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                    produto_id UUID NOT NULL REFERENCES produtos(id) ON DELETE CASCADE,
                                    cliente_id UUID NOT NULL REFERENCES clientes(id) ON DELETE SET NULL,
                                    nota INTEGER NOT NULL CHECK (nota >= 1 AND nota <= 5),
                                    comentario TEXT,
                                    data_avaliacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notificacoes (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                              tipo VARCHAR(50) NOT NULL, -- Ex: 'pedido_status', 'nova_mensagem'
                              mensagem TEXT NOT NULL,
                              lida BOOLEAN DEFAULT FALSE,
                              data_envio TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);