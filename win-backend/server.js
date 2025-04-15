require('dotenv').config();
const express = require('express');
const cors = require('cors');
const app = require('./src/app');

const PORT = process.env.PORT || 3000;
const server = express();

server.use(cors());
server.use(express.json());
server.use(app);

server.listen(PORT, () => {
    console.log(`Servidor rodando na porta ${PORT}`);
});
