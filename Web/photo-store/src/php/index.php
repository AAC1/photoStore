
<html >
<head>
    <link href="src/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="src/assets/css/angular-material.min.css" rel="stylesheet" type="text/css"/>
    
    <link href="src/assets/css/style.css" rel="stylesheet" type="text/css"/>
    
    <script src="src/assets/js/jquery-4.2.1.min.js"></script>
    <script src="src/assets/js/bootstrap.min.js" type="text/javascript"></script>

    <script src="src/assets/js/angular.min.js"></script>
    <script src="src/assets/js/angular-route.min.js"></script>
    <script src="src/assets/js/angular-animate.min.js"></script>
    <script src="src/assets/js/angular-messages.min.js"></script>
    <script src="src/assets/js/angular-aria.min.js"></script>
    <script src="src/assets/js/angular-touch.min.js"></script>
    <script src="src/assets/js/angular-material.min.js"></script>
    <script src="src/assets/js/moveBackground.js"></script>

    <script src="src/controller/controller.js" type="text/javascript"></script>


</head>
<body ng-app="PhotoStore">
<div  ng-controller="mainController" >

    <div>
    
    <!-- Vertical navbar -->
    <div class="vertical-nav bg-white fixed-bottom" id="sidebar">
        <div class="py-4 px-3 mb-4 bg-light">
            <div class="media d-flex align-items-center row">
                <div class="col-md-12" style="text-align:center">
                    <img src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNTAgMjUwIj4KICAgIDxwYXRoIGZpbGw9IiNERDAwMzEiIGQ9Ik0xMjUgMzBMMzEuOSA2My4ybDE0LjIgMTIzLjFMMTI1IDIzMGw3OC45LTQzLjcgMTQuMi0xMjMuMXoiIC8+CiAgICA8cGF0aCBmaWxsPSIjQzMwMDJGIiBkPSJNMTI1IDMwdjIyLjItLjFWMjMwbDc4LjktNDMuNyAxNC4yLTEyMy4xTDEyNSAzMHoiIC8+CiAgICA8cGF0aCAgZmlsbD0iI0ZGRkZGRiIgZD0iTTEyNSA1Mi4xTDY2LjggMTgyLjZoMjEuN2wxMS43LTI5LjJoNDkuNGwxMS43IDI5LjJIMTgzTDEyNSA1Mi4xem0xNyA4My4zaC0zNGwxNy00MC45IDE3IDQwLjl6IiAvPgogIDwvc3ZnPg=="
                    alt="..." width="65" class="mr-3 rounded-circle img-thumbnail shadow-sm">
                </div>
                <div class="media-body col-md-12" style="text-align:center">
                    <h4 class="m-0 font-weight-bold">Amairani Garcia S.</h4>
                    <p class="font-weight-light text-muted mb-0">Web developer</p>
                </div>
            </div>
        </div>
<!--
        <p class="text-gray font-weight-bold text-uppercase px-3 small pb-4 mb-0">Principal</p>

        <ul class="nav flex-column bg-white mb-0">
            <li class="nav-item">
                <a href="#!/venta" class="nav-link text-dark  bg-light">
                    <i class="fa fa-th-large mr-3 text-primary fa-fw"></i> Venta
                </a>
            </li>

        </ul>
-->
        <p class="text-gray font-weight-bold text-uppercase px-3 small py-4 mb-0">Administraci&oacute;n</p>

        <ul class="nav flex-column bg-white mb-0">
            <li class="nav-item">
                <a href="#!/gestion/producto" class="nav-link text-dark ">
                    <i class="fa fa-area-chart mr-3 text-primary fa-fw"></i>Gesti&oacute;n de Productos
                </a>
            </li>
            <li class="nav-item">
                <a href="#!/gestion/costoProducto" class="nav-link text-dark ">
                    <i class="fa fa-area-chart mr-3 text-primary fa-fw"></i>Gesti&oacute;n Costo Productos
                </a>
            </li>
            <li class="nav-item">
                <a href="#!/reporte" class="nav-link text-dark ">
                    <i class="fa fa-area-chart mr-3 text-primary fa-fw"></i>Consulta Reportes
                </a>
            </li>
            
        </ul>
        <p class="text-gray font-weight-bold text-uppercase px-3 small py-4 mb-0">Usuario</p>
        <ul class="nav flex-column bg-white mb-0">
            <li class="nav-item">
                <a href="#!/" class="nav-link text-dark ">
                    <i class="fa fa-area-chart mr-3 text-primary fa-fw"></i>Gesti&oacute;n Usuarios
                </a>
            </li>
            <li class="nav-item">
                <a href="#!/" class="nav-link text-dark ">
                    <i class="fa fa-area-chart mr-3 text-primary fa-fw"></i>Gesti&oacute;n Perfiles
                </a>
            </li>
            <li class="nav-item">
                <a href="#!/logout" class="nav-link text-dark ">
                    <i class="fa fa-line-chart mr-3 text-primary fa-fw"></i> Cerrar sesión
                </a>
            </li>
        </ul>
    </div>
    <!-- End vertical navbar -->
    <div  class="backgroundImg header" ng-if="showBackgroundImg"></div>
    <div class="page-content " id="content" style=""><!-- p-5 -->
        
        <!-- Toggle button -->
        <button id="btnSidebarCollapse" type="button" 
            class="btn btn-light bg-white shadow-sm btnHambuger" style=""
            ng-click="toggleMenu()">
        &#x2630;
        </button>
        <div ng-view="ng-view" class="" container >&nbsp;</div>
        
    </div>
    
</div>
    <footer class="page-footer font-small blue fixed-bottom" >

        
            <!-- Copyright -->
            <div class="footer-copyright text-center py-3">© 2019 Copyright:
                <a href="https://mdbootstrap.com/education/bootstrap/"> macrofotostudio.com.mx</a>
            </div>
            <!-- Copyright -->
    </footer>
</div>

</body>
</html>
