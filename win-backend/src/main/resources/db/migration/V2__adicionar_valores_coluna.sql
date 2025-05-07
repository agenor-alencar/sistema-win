INSERT INTO usuarios (nome, email, senha, telefone, tipo_usuario)
VALUES
('Ana Souza', 'ana.souza@email.com', 'senha123', '11999990001', 'cliente'),
('Carlos Lima', 'carlos.lima@email.com', 'senha456', '21988887777', 'motorista'),
('Loja Central', 'contato@lojacentral.com', 'senha789', '11333334444', 'loja'),
('Beatriz Mendes', 'beatriz.mendes@email.com', 'senha321', '31999998888', 'cliente'),
('Admin Master', 'admin@empresa.com', 'adminsenha', '11911112222', 'admin');
INSERT INTO enderecos (usuario_id, rua, numero, bairro, cidade, estado, cep, complemento)
VALUES
(1, 'Rua das Flores', '123', 'Centro', 'São Paulo', 'SP', '01000-000', 'Apto 101'),
(2, 'Avenida Brasil', '456', 'Jardim', 'Rio de Janeiro', 'RJ', '20000-000', NULL),
(3, 'Rua da Paz', '789', 'Bela Vista', 'Belo Horizonte', 'MG', '30000-000', NULL),
(4, 'Rua do Comércio', '1011', 'Centro', 'Salvador', 'BA', '40000-000', NULL),
(5, 'Avenida Paulista', '1213', 'Jardins', 'São Paulo', 'SP', NULL, NULL);
