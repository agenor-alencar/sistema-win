const express = require('express');
const app = express();

// Importação de rotas
// const userRoutes = require('./routes/userRoutes');

// app.use('/api/usuarios', userRoutes);

app.get('/', (req, res) => {
    res.send('API WIN está online');
});

module.exports = app;
