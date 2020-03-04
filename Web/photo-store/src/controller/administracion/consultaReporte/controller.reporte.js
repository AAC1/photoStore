app.controller('reporteController', ['$rootScope', '$routeParams', '$scope', '$http', '$location',
    '$timeout', '$mdDialog',
    function($rootScope, $routeParams, $scope, $http, $location, $timeout, $mdDialog) {
        console.log("reporte inicia");
        $rootScope.showBackgroundImg = false;
        $scope.listPedidos = [];
        $scope.filterPedidos = {
            folio: '',
            cliente: '',
            estatus: '',
            fecha_fec_pedidoIni: '',
            fec_pedidoFin: ''
        }

        $scope.getPedidos = function(jsonIn) {
            $http({
                method: 'POST',
                url: $rootScope.baseUrl + '/ConsultaPedidos',
                data: jsonIn
            }).then(function(response) {

                var resp = response.data;
                $scope.listPedidos = resp.data;
                console.log(resp);

            }, function(err) {
                console.log(err);
            });
        }
        $scope.getPedidos($scope.filterPedidos);
    }
]);