const express = require('express');
var app = express();

require('./src/resources/pedido')(app);

app.get('/', function (req, res) {
  res.send('Hello World!');
});


app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});