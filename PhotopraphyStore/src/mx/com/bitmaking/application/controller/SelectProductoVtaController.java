package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.service.IStoreCatProdService;
import mx.com.bitmaking.application.service.IStoreClteProdCostService;
import mx.com.bitmaking.application.service.IStoreFotografoService;

@Component
public class SelectProductoVtaController {
	//@Autowired
	//ICatProdDAO catProdDAO;
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService storeCatProdService;
	@Autowired
	@Qualifier("StoreFotografoService")
	IStoreFotografoService clienteService;
	@Autowired 
	@Qualifier("StoreClteProdCostService")
	IStoreClteProdCostService clteProdCostService;
	
	@Autowired
	@Qualifier("remoteStoreCatProdService")
	IStoreCatProdService remoteStoreCatProdService;
	@Autowired
	@Qualifier("remoteStoreFotografoService")
	IStoreFotografoService remoteClienteService;
	@Autowired 
	@Qualifier("remoteStoreClteProdCostService")
	IStoreClteProdCostService remoteClteProdCostService;
	
	//@FXML private TreeView<String> treeProd;
	@FXML private JFXButton btnSalir;
	@FXML private JFXButton btnAcceptModif;
	@FXML private JFXButton btnCleanModif;
	@FXML private JFXTextField inputBarcodeSearch;
	@FXML private TableView<CostProductsDTO> tblProducto;
	@FXML private TableColumn<CostProductsDTO,String> colProducto;
	@FXML private TableColumn<CostProductsDTO,String> colBarCode;
	@FXML private TableColumn<CostProductsDTO,BigDecimal> colCosto;
	
	private LinkedHashMap<Integer, CostProductsDTO> productsMap = null;
	private List<CostProductsDTO> lstProd = new ArrayList<>();
	private CostProductsDTO rowSelected = null;
	private int idCliente=0;
	
	
	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public CostProductsDTO getRowSelected() {
		return rowSelected;
	}

	public void setRowSelected(CostProductsDTO rowSelected) {
		this.rowSelected = rowSelected;
	}

	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JFXButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public JFXButton getBtnAcceptModif() {
		return btnAcceptModif;
	}

	public void setBtnAcceptModif(JFXButton btnAcceptModif) {
		this.btnAcceptModif = btnAcceptModif;
	}

	public LinkedHashMap<Integer, CostProductsDTO> getProductsMap() {
		return productsMap;
	}

	public void setProductsMap(LinkedHashMap<Integer, CostProductsDTO> productsMap) {
		this.productsMap = productsMap;
	}
	
	
	public List<CostProductsDTO> getLstProd() {
		return lstProd;
	}

	public void setLstProd(List<CostProductsDTO> lstProd) {
		this.lstProd = lstProd;
	}

	public TableView<CostProductsDTO> getTblProducto() {
		return tblProducto;
	}

	public void setTblProducto(TableView<CostProductsDTO> tblProducto) {
		this.tblProducto = tblProducto;
	}

	public void initialize() {
		iniSect();
		//getTblCatProducts(0);
		//treeProd.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetails());
	//	btnAcceptModif.addEventHandler(MouseEvent.MOUSE_CLICKED, acceptModifProd());

	}
	
	
	
	private void iniSect() {
		inputBarcodeSearch.setText("");
		tblProducto.getItems().removeAll(tblProducto.getItems());
		colBarCode.prefWidthProperty().bind(tblProducto.widthProperty().multiply(0.3));
		colProducto.prefWidthProperty().bind(tblProducto.widthProperty().multiply(0.5));
		colCosto.prefWidthProperty().bind(tblProducto.widthProperty().multiply(0.2));
		
		colBarCode.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("bar_code"));
		colProducto.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("producto"));
		colCosto.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, BigDecimal>("costo"));
		
	}
	
	@FXML 
	private void searchByBarcode(KeyEvent e) {
		System.out.println("entra:"+e.getCode().toString());
		if("ENTER".equals(e.getCode().toString()) ){
			getTblCatProductsByBarCode(inputBarcodeSearch.getText().trim());
		}
	}
	
	
	@FXML
	private void cleanForm() {
		
		inputBarcodeSearch.setText("");
		tblProducto.getItems().removeAll(tblProducto.getItems());
		getTblCatProducts();
	}
	
	private void getTblCatProductsByBarCode(String valueToSearch) {
		tblProducto.getItems().removeAll(tblProducto.getItems());
		lstProd = new ArrayList<>();
		if(valueToSearch.trim().length()==0) {
			getDescProducts(productsMap, 0, lstProd, new CostProductsDTO(), new StringBuilder(),"");
		//	tblProducto.getItems().removeAll(tblProducto.getItems());
			tblProducto.setItems(FXCollections.observableList(lstProd));
			return;
		}
		for (Map.Entry<Integer,CostProductsDTO> el : productsMap.entrySet()) {
			if(el.getValue().getBar_code()!=null && 
					el.getValue().getBar_code().contains(valueToSearch)) {
				lstProd.add(el.getValue());
			}
		}
	//	tblProducto.getItems().removeAll(tblProducto.getItems());
		tblProducto.setItems(FXCollections.observableList(lstProd));
	}
	
	protected void getTblCatProducts() {
		tblProducto.getItems().removeAll(tblProducto.getItems());
		productsMap = storeCatProdService.getCostProdByClient(idCliente);
	/*	TreeItem<String> root =new TreeItem<>("Productos del cliente");
		root.setExpanded(true);
		generateTreeProd(productsMap,0,root);
		treeProd.setRoot(root);
		*/
		
		getDescProducts(productsMap, 0, lstProd, new CostProductsDTO(), new StringBuilder(),"");
		tblProducto.setItems(FXCollections.observableList(lstProd));
		
	}
	
	private void getDescProducts(LinkedHashMap<Integer, CostProductsDTO> hashMap,
								int id_padre,List<CostProductsDTO> lstProd,
									CostProductsDTO obj,StringBuilder prod,String currentProd) {
		
		LinkedHashMap<Integer,CostProductsDTO> auxMap = new LinkedHashMap<>();
		
		for (Map.Entry<Integer,CostProductsDTO> el : hashMap.entrySet()) {
			if(el.getValue().getId_padre_prod() == id_padre) {
				auxMap.put(el.getValue().getId_prod(), el.getValue());
			}
		}
		
		if(auxMap.size()<=0) {
			obj.setProducto(prod.toString().substring(0,prod.length()-1));
			lstProd.add(obj);
			obj = new CostProductsDTO();
			
			return;
		}
		
		for (Map.Entry<Integer,CostProductsDTO> el : auxMap.entrySet()) {
			obj =el.getValue();
			currentProd =el.getValue().getProducto()+"_";
			prod.append(el.getValue().getProducto()+"_");
			getDescProducts(hashMap, el.getValue().getId_prod(),lstProd,obj,prod,currentProd);
			int tam =prod.length() - (currentProd).length();
			
			if(tam>=0) {
				
				prod.replace(tam, prod.length(),"");
			}
		}
		
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
