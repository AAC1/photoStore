package mx.com.bitmaking.application.abstractdao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BinaryType;
import org.hibernate.type.BlobType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.ByteArrayTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties.Hibernate;
import org.springframework.stereotype.Repository;

import com.itextpdf.text.pdf.qrcode.ByteArray;

import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IClteProdCostDAO;


public abstract class AbstractClteProdCostDAO implements IClteProdCostDAO{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	*/
	
	public abstract SessionFactory getSessionFactory();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PedidosReporteDTO> consultaPedido(String sqlQry) {
		List<PedidosReporteDTO> results = new ArrayList<>();
		try{
 
			SQLQuery qry= getSessionFactory().getCurrentSession().createSQLQuery(sqlQry);
			
			
			qry.addScalar("id_pedido",new IntegerType());
			qry.addScalar("folio",new StringType());
			qry.addScalar("cliente",new StringType());
			qry.addScalar("telefono",new StringType());
			qry.addScalar("descripcion",new StringType());
			qry.addScalar("fec_pedido",new DateType());
			qry.addScalar("fec_entregado",new DateType());
			qry.addScalar("monto_ant",new BigDecimalType());
			qry.addScalar("monto_total",new BigDecimalType());
			qry.addScalar("monto_pendiente",new BigDecimalType());
			qry.addScalar("estatus",new StringType());
			qry.addScalar("ticket",new BinaryType());
			qry.setResultTransformer(Transformers.aliasToBean(PedidosReporteDTO.class));
			
			results =qry.list();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		return results;
	}

}
