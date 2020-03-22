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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.iservice.IStoreClteProdCostService;
import mx.com.bitmaking.application.iservice.IStoreFotografoService;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class CostProdByClteController {
	
//	@Autowired
//	@Qualifier("CatProdDAO")
//	ICatProdDAO catProdDAO;
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
	
	@FXML private TreeView<String> treeProd;
	@FXML private JFXButton btnEdtProd;
	@FXML private JFXButton btnSalir;
	@FXML private JFXButton btnAcceptModif;
	@FXML private JFXButton btnCancenModif;
	@FXML private JFXTextField inputName;
	@FXML private JFXTextField inputBarcode;
	@FXML private JFXComboBox<String> cbxStts;
	@FXML private JFXComboBox<String> cbxCliente;
	@FXML private JFXTextField inputCosto;
	@FXML private Label lblBarcode;
	private List<Store_fotografo> lstClte = null;
	LinkedHashMap<Integer, CostProductsDTO> productsMap = null;
	CostProductsDTO catProdModif = null;
	
	/**
	 * @return the btnSalir
	 */
	public JFXButton getBtnSalir() {
		return btnSalir;
	}



	/**
	 * @param btnSalir the btnSalir to set
	 */
	public void setBtnSalir(JFXButton btnSalir) {
		this.btnSalir = btnSalir;
	}



	public void initialize() {
		iniSect();
		getTblCatProducts(0);
		treeProd.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetails());
		btnAcceptModif.addEventHandler(MouseEvent.MOUSE_CLICKED, acceptModifProd());
		
		inputCosto.textProperty().addListener(GeneralMethods.formatNumber(inputCosto));
		
		inputBarcode.setVisible(false);//Para la siguiente fase se agrega
		lblBarcode.setVisible(false);
	}
	
	
	private void iniSect() {
	//	inputBarcode.setVisible(false);
		List<String> lstStts = new ArrayList<>();
		lstStts.add("Activo");
		lstStts.add("Inactivo");
		cbxStts.getItems().removeAll(cbxStts.getItems());
		cbxStts.setItems(FXCollections.observableList(lstStts));
		
		lstClte= (Flags.remote_valid)?remoteClienteService.getActiveClients():clienteService.getActiveClients();
		
		cbxCliente.getItems().removeAll(cbxCliente.getItems());
		List<String> lstNameCltes = new ArrayList<>();
		for(Store_fotografo el: lstClte) {
			lstNameCltes.add(el.getFotografo());
		}
		
		cbxCliente.setItems(FXCollections.observableList(lstNameCltes));
		cbxCliente.getSelectionModel().select(0);
		
		getTblCatProducts(lstClte.get(0).getId_fotografo());
	}
	
	@FXML
	private void getCostProdByClte() {
		if(lstClte==null)return;
		
		int nRow= cbxCliente.getSelectionModel().getSelectedIndex();
		Store_fotografo objClte = lstClte.get(nRow);
		System.out.println("idClte"+objClte.getId_fotografo());
		getTblCatProducts(objClte.getId_fotografo());
	}
	@FXML
	private void cancelModif() {
		
		btnAcceptModif.setVisible(false);
		btnCancenModif.setVisible(false);
		cbxStts.setDisable(true);
		inputName.setDisable(true);
		treeProd.setDisable(false);
		
		inputCosto.setDisable(true);
		inputBarcode.setDisable(true);
		

	}
	@FXML
	private void editProd(){
		ObservableList<TreeItem<String>> objTree = treeProd.getSelectionModel().getSelectedItems();
		TreeItem<String> treeItem = objTree.get(0);
		if(treeItem.getChildren().size()>0){
			GeneralMethods.modalMsg("WARNING", "", "Solo es permitido modificar o asignar costos  a registros sin subgrupos");
			return;
		}
		treeProd.setDisable(true);
		btnAcceptModif.setVisible(true);
		btnCancenModif.setVisible(true);
		inputCosto.setDisable(false);
		inputBarcode.setDisable(true);
		
		//cbxStts.setDisable(false);
		//inputName.setDisable(false);
	}

	private EventHandler<MouseEvent> showDetails() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getDetails();
			}
		};
	}
	
	private void getDetails() {
		catProdModif = null;
		inputName.setText("");
		cbxStts.setValue("");
		inputCosto.setText("");
		inputBarcode.setText("");
		try {

			if (productsMap == null || productsMap.size() == 0) {
		//		 GeneralMethods.modalMsg("WARNING", "", "No hay registro a modificar");
				return;
			}

			ObservableList<TreeItem<String>> objTree = treeProd.getSelectionModel().getSelectedItems();
			TreeItem<String> treeItem = objTree.get(0);
			if (treeItem == null) {
		//		GeneralMethods.modalMsg("WARNING", "", "No hay registro seleccionado");
				return;
			}
			int idx = treeProd.getSelectionModel().getSelectedIndex();

			String strRow = treeItem.getValue();

			if (strRow == null || strRow.length() == 0 || idx <= 0) {
				// GeneralMethods.modalMsg("WARNING", "", "Para
				// modificar, debes seleccionar un registro");
				return;
			} else {
				System.out.println(strRow);
				String[] arrayStr = strRow.split("\\|");
				System.out.println(arrayStr[0]);
				String idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
				catProdModif = productsMap.get(Integer.parseInt(idProd));
				inputName.setText(catProdModif.getProducto());
				cbxStts.setValue(catProdModif.getEstatus());
				System.out.println("getCosto:select: "+catProdModif.getCosto());
				
				inputCosto.setText(catProdModif.getCosto()==null?"":String.valueOf(catProdModif.getCosto()));
				inputBarcode.setText(catProdModif.getBar_code()==null?"":catProdModif.getBar_code());
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private EventHandler<MouseEvent> acceptModifProd() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				try {

					if (productsMap == null || productsMap.size() == 0) {
						 GeneralMethods.modalMsg("WARNING", "", "No hay  registro a modificar");
						return;
					}

					ObservableList<TreeItem<String>> objTree = treeProd.getSelectionModel().getSelectedItems();
					TreeItem<String> treeItem = objTree.get(0);
					
					if (treeItem == null) {
						 GeneralMethods.modalMsg("WARNING", "", "No hay registro seleccionado");
						return;
					}
					int idx = treeProd.getSelectionModel().getSelectedIndex();

					String strRow = treeItem.getValue();

					if (strRow == null || strRow.length() == 0 || idx <= 0) {// ||
																				// row.length()==0
						GeneralMethods.modalMsg("WARNING", "", "Para modificar, debes seleccionar un registro");
						return;
					} 
					if(treeItem.getChildren().size()>0){
						GeneralMethods.modalMsg("WARNING", "", "Solo es permitido modificar costos y barcode de registros sin subgrupos");
						return;
					}
					else {
						cancelModif();
						CostProductsDTO row = catProdModif;
						int nRow= cbxCliente.getSelectionModel().getSelectedIndex();
						Store_fotografo objClte = lstClte.get(nRow);
						
						
						Store_cliente_prod_cost costProdObj = (Flags.remote_valid)?
								remoteClteProdCostService.getRowByIdProdAndClient(objClte.getId_fotografo(), row.getId_prod()):
								clteProdCostService.getRowByIdProdAndClient(objClte.getId_fotografo(), row.getId_prod());
						
						if(costProdObj ==null){
							costProdObj = new Store_cliente_prod_cost();
							costProdObj.setId_cliente(objClte.getId_fotografo());
							costProdObj.setId_prod(row.getId_prod());
						}
					//	costProdObj.setBar_code(inputBarcode.getText()==null?"":inputBarcode.getText());
						
						System.out.println("idClte:"+objClte.getId_fotografo());
						System.out.println("idProd:"+row.getId_prod());
						System.out.println("costProdObj:"+costProdObj.getId_clte_prod_cost());
						
						String costoProd = inputCosto.getText()==null?"":inputCosto.getText().replace(",", "");
						costProdObj.setCosto(inputCosto.getText().length()==0?
														null:new BigDecimal(costoProd.trim()));
						
						/*ACtualiza o inserta nuevo registro*/
						clteProdCostService.insertRow(costProdObj);
						if(Flags.remote_valid)remoteClteProdCostService.insertRow(costProdObj);
						inputName.setText("");
						cbxStts.setValue("");
						inputCosto.setText("");
						inputBarcode.setText("");
						getTblCatProducts(objClte.getId_fotografo());
					}
				} catch (Exception ex) {
					GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
					
					ex.printStackTrace();
				}
			}

		};
	}

	private void inactiveChildren(TreeItem<String> treeItem, String stts) {
		ObservableList<TreeItem<String>> children = treeItem.getChildren();
		// TreeItem<String> item = null;
		String[] arrayStr = null;
		String idProd = null;
		CostProductsDTO row = null;
		for (TreeItem<String> el : children) {

			arrayStr = el.getValue().split("\\|");

			idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
			row = productsMap.get(Integer.parseInt(idProd));
			row.setEstatus(stts);
		//	catProdDAO.insertRow(row);
			inactiveChildren(el, stts);

		}

	}

	
	private void getTblCatProducts(int cliente) {
		
		productsMap = (Flags.remote_valid)?remoteStoreCatProdService.getCostProdByClient(cliente):
											storeCatProdService.getCostProdByClient(cliente);
		TreeItem<String> root =new TreeItem<>("Productos del cliente");
		root.setExpanded(true);
		generateTreeProd(productsMap,0,root);
		treeProd.setRoot(root);
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
	
	@FXML 
	private void selectByArrow(KeyEvent e) {
		System.out.println("entra:"+e.getCode().toString());
		if("DOWN".equals(e.getCode().toString()) || "UP".equals(e.getCode().toString())){
			getDetails();
		}
	}
}
