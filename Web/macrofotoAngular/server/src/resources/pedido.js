module.exports = function(app){
    app.get('/getPedidos', function (req, res) {+
        console.log(req);
        
        res.send('Hello,  Reporte works!');
    });

}