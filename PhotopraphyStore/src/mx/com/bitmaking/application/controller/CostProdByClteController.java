package mx.com.bitmaking.application.controller;

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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.repository.ICatProdDAO;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class CostProdByClteController {
	
	@Autowired
	ICatProdDAO catProdDAO;
	@Autowired
	IStoreCatProdService storeCatProdService;
	
	@FXML private TreeView<String> treeProd;
	@FXML private JFXButton btnEdtProd;
	@FXML private JFXButton btnSalir;
	@FXML private JFXButton btnAcceptModif;
	@FXML private JFXButton btnCancenModif;
	@FXML private JFXTextField inputName;
	@FXML private JFXTextField inputBarcode;
	@FXML private JFXComboBox<String> cbxStts;
	@FXML private JFXTextField inputCosto;
	
	LinkedHashMap<Integer, CostProductsDTO> productsMap = null;
	CostProductsDTO catProdModif = null;
	
	public void initialize() {
		
		getTblCatProducts(0);
		treeProd.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetails());
		btnAcceptModif.addEventHandler(MouseEvent.MOUSE_CLICKED, acceptModifProd());

	}

	@FXML
	private void cancelModif() {
		btnAcceptModif.setVisible(false);
		btnCancenModif.setVisible(false);
		cbxStts.setDisable(true);
		inputName.setDisable(true);
		treeProd.setDisable(false);

	}
	@FXML
	private void editProd(){
		treeProd.setDisable(true);
		btnAcceptModif.setVisible(true);
		btnCancenModif.setVisible(true);
		cbxStts.setDisable(false);
		inputName.setDisable(false);
	}

	private EventHandler<MouseEvent> showDetails() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
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
						inputCosto.setText(catProdModif.getCosto()==null?"":String.valueOf(catProdModif.getCosto()));
						inputBarcode.setText(catProdModif.getBar_code()==null?"":catProdModif.getBar_code());
						
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	private EventHandler<MouseEvent> acceptModifProd() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				try {

					if (productsMap == null || productsMap.size() == 0) {
						// GeneralMethods.modalMsg("WARNING", "", "No hay
						// registro a modificar");
						return;
					}

					ObservableList<TreeItem<String>> objTree = treeProd.getSelectionModel().getSelectedItems();
					TreeItem<String> treeItem = objTree.get(0);
					if (treeItem == null) {
						// GeneralMethods.modalMsg("WARNING", "", "No hay
						// registro seleccionado");
						return;
					}
					int idx = treeProd.getSelectionModel().getSelectedIndex();

					String strRow = treeItem.getValue();

					if (strRow == null || strRow.length() == 0 || idx <= 0) {// ||
																				// row.length()==0
						// GeneralMethods.modalMsg("WARNING", "", "Para
						// modificar, debes seleccionar un registro");
						return;
					} else {
						cancelModif();
						CostProductsDTO row = catProdModif;
						row.setProducto(inputName.getText());
						row.setEstatus(("ACTIVO".equals(cbxStts.getValue().toUpperCase())) ? "1" : "0");
						//catProdDAO.insertRow(row);
						
						// if("0".equals(row.getEstatus())){
						inactiveChildren(treeItem, row.getEstatus());
						// }
						cancelModif();
						inputName.setText("");
						cbxStts.setValue("");
						getTblCatProducts(0);//cambiar por el combo dinamico
					}
				} catch (Exception ex) {
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
		List<String> lstStts = new ArrayList<>();
		lstStts.add("Activo");
		lstStts.add("Inactivo");
		cbxStts.getItems().removeAll(cbxStts.getItems());
		cbxStts.setItems(FXCollections.observableList(lstStts));

		productsMap = storeCatProdService.getCostProdByClient(cliente);
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
}
