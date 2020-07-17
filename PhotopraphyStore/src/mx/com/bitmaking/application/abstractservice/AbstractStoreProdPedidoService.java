package mx.com.bitmaking.application.abstractservice;

import java.util.ArrayList;
import java.util.List;

//import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.iservice.IStoreProdPedidoService;

//@Service("StoreProdPedidoService")
public abstract class AbstractStoreProdPedidoService implements IStoreProdPedidoService{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("StoreProdPedidoDAO")
	private IStoreProdPedidoDAO prodPedidoRepo;
	//private IStoreProdPedidoRepo prodPedidoRepo;
	
	@Autowired
	@Qualifier("PedidoDAO")
	private IPedidoDAO pedidoRepo;
	*/
	
	public abstract IStoreProdPedidoDAO getProdPedidoRepo();
	public abstract IPedidoDAO getPedidoDao();
	public abstract SessionFactory getSessionFactory();
	

	@Override
	public List<Store_prod_pedido> getListProdPedidos(String pedidos) {
		return getProdPedidoRepo().getListProdPedidos(pedidos);
	}

	@Override
	public boolean guardaProdsByPedido(String folio, Store_prod_pedido producto) {
	//	int id_pedido = getPedidoDao().getIdByFolio(folio);
	//	System.out.println("Id_pedido_saved:"+id_pedido);
		
	//	producto.setId_pedido(id_pedido);
		getProdPedidoRepo().save(producto);
		
		return false;
	}
	@Override
	public void deleteByFolio(String folio){
		getProdPedidoRepo().deleteByIdPedido(folio);
	}
	
	@Override
	public void editProd(Store_prod_pedido obj){
		getProdPedidoRepo().editProd(obj);
	}

}
