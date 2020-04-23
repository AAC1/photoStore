
<template>

<div id="reporte" >
  <h1>Consulta Pedido</h1>
<article class="fluid-container ">
    <div class="row col-md-12">
        <div class=" col-md-3">
                <button v-on:click="getReporte()">Consultar</button>
        </div>
        <div class=" col-md-9">
            <h4>Pedidos</h4>
            <div class=" col-md-12 overflowDiv">

                <table class="table ">
                    <thead class="thead-dark">
                        <tr>
                            <th>Folio</th>
                            <th>Cliente</th>
                            <th>Contacto </th>
                            <th>Descripci&oacute;n</th>
                            <th>Estatus</th>
                            <th>Fec. del pedido</th>
                            <th>Fec. entregado</th>
                            <th>Monto de anticipo</th>
                            <th>Costo total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="item of listPedidos" v-bind:key="item">
                            <td>{{item.folio}}</td>
                            <td>{{item.cliente}}</td>
                            <td>{{item.telefono}}</td>
                            <td>{{item.descripcion}}</td>
                            <td>{{item.id_estatus}}</td>
                            <td>{{item.fec_pedido}}</td>
                            <td>{{item.fec_entregado}}</td>
                            <td>{{item.monto_ant}}</td>
                            <td>{{item.monto_total}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <pre>listPedidos={{listPedidos}}</pre>
    </div>

</article>
</div>

</template>

<script>
import axios from 'axios'

export default {

  name: 'reporte',
  props: {
  //    showBackgroundImg:Boolean
   // msg: String
  },
  data(){
    return {
        listPedidos: [],
        url:'http://localhost:3000'
    }  
  },
  methods: {
    getReporte: ()=>{
        axios
        .post(this.url+'/reporte',
            {
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data:{
                    val1:'val1'
                }
            })
        .then(response => {
            this.listPedidos = response.data
        })
        .catch(error => {
            console.log(error)
            //this.errored = true
        })
        .finally(() => this.loading = false)
    }
  }

}
</script>