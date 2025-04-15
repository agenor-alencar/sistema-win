# WIN – Marketplace Local

O **WIN** é uma plataforma desenvolvida para conectar **usuários** a **lojas locais** de ferragens, materiais elétricos, limpeza e embalagens. A proposta é facilitar a compra de itens do dia a dia, com **pagamento instantâneo via PIX ou cartão**, e entrega rápida por serviços como **Uber Flash**, sem que o cliente precise sair de casa.

---

## 📁 Estrutura do Projeto

```
sistema-win/
├── win-backend/     # API em Node.js
└── win-frontend/    # Aplicação Web em React
```

---

## Funcionalidades

- Catálogo de produtos por loja
- Carrinho de compras
- Pagamento via PIX (com QR Code) ou Cartão de Crédito
- Agendamento de retirada por serviço de entrega
- Cadastro de usuários e lojas
- Horário de funcionamento de cada loja
- Controle de pedidos e entregas

---

## Tecnologias Utilizadas

### Backend (Node.js)
- Node.js
- Express.js
- CORS
- Nodemon (para desenvolvimento)
- Dotenv

### Frontend (React)
- React.js
- Vite
- Tailwind CSS
- React Router DOM

---

## Como Rodar o Projeto

### Pré-requisitos

- Node.js e npm instalados
- Git instalado

### Clone o repositório

```bash
git clone https://github.com/agenor-alencar/sistema-win.git
cd sistema-win
```

### Backend

```bash
cd win-backend
npm install
npm run dev
```

A API estará rodando normalmente em: `http://localhost:3000`

### Frontend

```bash
cd win-frontend
npm install
npm run dev
```

A aplicação web estará acessível em: `http://localhost:5173`

---

## Planejamento

Este projeto está sendo desenvolvido passo a passo:

1. Levantamento de Requisitos
2. Casos de Uso e Modelos UML
3. Modelagem de Dados
4. Estrutura inicial dos projetos
5. Implementação Backend e Frontend
6. Integrações (pagamento, entrega)
7. Testes e Validações
8. Deploy e entrega final

---

Contribuições

Em breve, este projeto poderá receber contribuições externas. Caso queira acompanhar o desenvolvimento ou contribuir com sugestões, fique à vontade para abrir issues!

---

Desenvolvido por

- [@agenor-alencar](https://github.com/agenor-alencar)

---