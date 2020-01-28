package mx.com.bitmaking.application.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;

@Repository
public class ClteProdCostDAO implements IClteProdCostDAO{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	@Override
	public List<PedidosReporteDTO> consultaPedido(String qry) {
		List<PedidosReporteDTO> results = new ArrayList<>();
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry);
			
			query.setResultTransformer(Transformers.aliasToBean(PedidosReporteDTO.class));
			
			results =query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		return results;
	}

}
