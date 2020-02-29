package mx.com.bitmaking.application.local.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractClteProdCostDAO;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IClteProdCostDAO;

@Repository("ClteProdCostDAO")
public class ClteProdCostDAO extends AbstractClteProdCostDAO{//implements IClteProdCostDAO{
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional(value="transactionManager")
	public List<PedidosReporteDTO> consultaPedido(String qry) {
		return super.consultaPedido(qry);
	}


	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
