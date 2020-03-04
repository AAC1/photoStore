var app = angular.module('PhotoStore', [
    'ngRoute',
    'ngMaterial',
    'ngMessages'
    /*,
        'material.svgAssetsCache',
        'ui.bootstrap',
        'ngSanitize'
        ,
            'ngIdle', 'ui.utils.masks'*/
]);


app.config(['$mdDateLocaleProvider', '$routeProvider', '$httpProvider',
    function($mdDateLocaleProvider, $routeProvider, $httpProvider) {
        //IdleProvider.timeout(10);
        //KeepaliveProvider.interval(10);

        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }

        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

        $routeProvider.
        when('/home', { templateUrl: 'src/view/home/home.php', controller: 'homeController' }).
            //   when('/login', { templateUrl: '../view/login/login.php', controller: 'loginController' }).
        when('/gestion/producto', { templateUrl: 'src/view/administracion/gestionProducto/gestProducto.php', controller: 'gestProdController' }).
        when('/gestion/costoProducto', { templateUrl: 'src/view/administracion/gestionCostoProducto/gestCostoProducto.php', controller: 'gestCostProdController' }).
        when('/reporte', { templateUrl: 'src/view/administracion/consultaReporte/consultaPedido.html', controller: 'reporteController' }).
        otherwise({ redirectTo: '/home' });

        $mdDateLocaleProvider.formatDate = function(date) {
            return moment(date).format('DD-MM-YYYY');
        };
    }
]);

app.controller('mainController', ['$rootScope', '$routeParams', '$scope', '$http', '$location',
    '$timeout', '$mdDialog',
    function($rootScope, $routeParams, $scope, $http,
        $location, $timeout, $mdDialog) {
        $rootScope.showBackgroundImg = true;
        $rootScope.baseUrl = 'http://localhost/photo-store/src/php';
        console.log("entra mainController");
        $scope.toggleMenu = function(idElement) {

            angular.element('#sidebar, #content').toggleClass('active');


        }
    }
]);