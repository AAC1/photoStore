package mx.com.bitmaking.application.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;

public class CostProdByClteController {
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService catProdService;
	@FXML private TreeView treeProd;
	
	private void getTblCatProducts() {
		//List<Store_cat_prod> lstProd = catProdService.getAllCatalogoProduct();
		LinkedHashMap<Integer,CostProductsDTO> productsMap = catProdService.getCostProdByClient(0);
		TreeItem<String> root =new TreeItem<>("Productos del cliente");
		root.setExpanded(true);
		//obtiene productos y costos por cliente
	//	productsMap =  catProdService.getCostProdByClient(0);
		generateTreeProd(productsMap,0,root);
		treeProd.setRoot(root);
		//tblProducts.setItems(FXCollections.observableList(lstProd));
		//colProd.setCellValueFactory(new PropertyValueFactory("producto"));
	//	colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
	}
	private void generateTreeProd(LinkedHashMap<Integer,CostProductsDTO> hashMap,int id_padre,TreeItem<String> nodoPadre) {
		
		LinkedHashMap<Integer,CostProductsDTO> auxMap = new LinkedHashMap<>();
		for (Map.Entry<Integer,CostProductsDTO> el : hashMap.entrySet()) {
			if(el.getValue().getId_padre_prod() == id_padre) {
				auxMap.put(el.getValue().getId_prod(), el.getValue());
			}
		}
		
		if(auxMap.size()<=0) {
			return;
		}
		TreeItem<String> nodo = null;
		for (Map.Entry<Integer,CostProductsDTO> el : auxMap.entrySet()) {
			nodo = new TreeItem<>("p-"+el.getValue().getId_prod()+" | "+el.getValue().getProducto());
			
			nodoPadre.getChildren().add(nodo);
			generateTreeProd(hashMap, el.getValue().getId_prod(),nodo);
			
		}
	}
}
