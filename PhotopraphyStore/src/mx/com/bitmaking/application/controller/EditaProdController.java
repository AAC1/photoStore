package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.idao.ICatProdDAO;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;

@Controller
//@Scope("prototype")
public class EditaProdController {
	
	@FXML private JFXButton btnAccept;
	@FXML private JFXButton btnCancel;
	@FXML private JFXComboBox<String>  cbxEstatusProd;
	@FXML private JFXTextField inputProdName;
	

//	@FXML private TreeView<String> treeCategoria;
//	@FXML private JFXComboBox<String>  cbxCategoria;
	@FXML private JFXTextField inputBarcode;
	@FXML private JFXTextField inputCosto;
	
//	@FXML private JFXButton btnAddCat;
//	@FXML private JFXButton btnRemoveCat;
	
	@FXML private TableView<CostProductsDTO> tblProds;
	@FXML private JFXButton btnAddProd;
	@FXML private JFXButton btnRemoveProd;
	
	@FXML private TableColumn<CostProductsDTO, String>colProd;
	@FXML private TableColumn<CostProductsDTO, String>colBarcode;
	@FXML private TableColumn<CostProductsDTO, BigDecimal>colCosto;
	
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService storeCatProdService;
	
	@Autowired
	@Qualifier("remoteStoreCatProdService")
	IStoreCatProdService remoteStoreCatProdService;
	
	TreeItem<String> root =null;
	TreeItem<String> actualNodo =null;
	JFXAutoCompletePopup<String> autoCompletePopup =null;
	
	
	
	/**
	 * @return the tblProds
	 */
	public TableView<CostProductsDTO> getTblProds() {
		return tblProds;
	}
	/**
	 * @param tblProds the tblProds to set
	 */
	public void setTblProds(TableView<CostProductsDTO> tblProds) {
		this.tblProds = tblProds;
	}
	/*public TreeView<String> getTreeCategoria() {
		return treeCategoria;
	}
	public void setTreeCategoria(TreeView<String> treeCategoria) {
		this.treeCategoria = treeCategoria;
	}*/
	public JFXTextField getInputBarcode() {
		return inputBarcode;
	}
	public void setInputBarcode(JFXTextField inputBarcode) {
		this.inputBarcode = inputBarcode;
	}
	public JFXTextField getInputCosto() {
		return inputCosto;
	}
	public void setInputCosto(JFXTextField inputCosto) {
		this.inputCosto = inputCosto;
	}
	/**
	 * @return the btnAccept
	 */
	public JFXButton getBtnAccept() {
		return btnAccept;
	}
	/**
	 * @param btnAccept the btnAccept to set
	 */
	public void setBtnAccept(JFXButton btnAccept) {
		this.btnAccept = btnAccept;
	}
	/**
	 * @return the btnCancel
	 */
	public JFXButton getBtnCancel() {
		return btnCancel;
	}
	/**
	 * @param btnCancel the btnCancel to set
	 */
	public void setBtnCancel(JFXButton btnCancel) {
		this.btnCancel = btnCancel;
	}
	
	/**
	 * @return the cbxEstatusProd
	 */
	public JFXComboBox<String> getCbxEstatusProd() {
		return cbxEstatusProd;
	}
	/**
	 * @param cbxEstatusProd the cbxEstatusProd to set
	 */
	public void setCbxEstatusProd(JFXComboBox<String> cbxEstatusProd) {
		this.cbxEstatusProd = cbxEstatusProd;
	}
	/**
	 * @return the inputProdName
	 */
	public JFXTextField getInputProdName() {
		return inputProdName;
	}
	/**
	 * @param inputProdName the inputProdName to set
	 */
	public void setInputProdName(JFXTextField inputProdName) {
		this.inputProdName = inputProdName;
	}
	

	public void initialize() {
		
		root = new TreeItem<>();
	//	treeCategoria.setRoot(root);
	//	treeCategoria.getSelectionModel().select(0);
	//	treeCategoria.getStyleClass().add("mainTree");
		root.setExpanded(true);
		
	//	setFilterPopup();
		fillCbxCategoria(0);//categoria incial
		responsiveGUI();

		inputCosto.textProperty().addListener(GeneralMethods.formatNumber(inputCosto));
	}
	/*
	private void setFilterPopup() {
		autoCompletePopup = new JFXAutoCompletePopup<>();
		autoCompletePopup.setSelectionHandler(event -> {
			cbxCategoria.setValue(event.getObject());
		});
		
		TextField editor = cbxCategoria.getEditor();
		editor.textProperty().addListener(observable -> {
		    //The filter method uses the Predicate to filter the Suggestions defined above
		    //I choose to use the contains method while ignoring cases
		    autoCompletePopup.filter(item -> item.toLowerCase().contains(editor.getText().toLowerCase()));
		    //Hide the autocomplete popup if the filtered suggestions is empty or when the box's original popup is open
		    if (autoCompletePopup.getFilteredSuggestions().isEmpty() 
		    	|| editor.getText().trim().length()==0) {
		        autoCompletePopup.hide();
		    } 
		    else {
		    	System.out.println("show popup");
		        autoCompletePopup.show(editor);
		    }
		});
	}
	*/
	private void fillCbxCategoria(int idPadre) {
		

		
	//	cbxCategoria.getItems().removeAll(cbxCategoria.getItems());
		
		
		List<Store_cat_prod> lstCat = (Flags.remote_valid)?
									remoteStoreCatProdService.getCatByPadre(idPadre):
									storeCatProdService.getCatByPadre(idPadre);
		List<String> lsStrCat = new ArrayList<>(); 	
	
		for(Store_cat_prod el:lstCat) {
			
			lsStrCat.add("p"+el.getId_prod()+" | "+el.getProducto());
		}
		
	/*	if(autoCompletePopup.getSuggestions() !=null)
			autoCompletePopup.getSuggestions().removeAll(autoCompletePopup.getSuggestions());
		autoCompletePopup.getSuggestions().addAll(cbxCategoria.getItems());
		
		*/
	}
	
	@FXML
	private void selectCategoria() {
	/*	System.out.println("seleccion");
		String cat = cbxCategoria.getSelectionModel().getSelectedItem();
		if(cat!=null && !"".equals(cat)) {
			boolean itemExists = false;
			
			if(Constantes.TXT_NUEVA_CATEGORIA.toUpperCase().equals(cat.toUpperCase())) {
				
			}else {
				for(String el: cbxCategoria.getItems() ) {
					if(el.toLowerCase().equals(cat.toLowerCase())) {
						itemExists = true; break;
					}
				}
				TreeItem<String> hijo = null;
				if(itemExists) {
				
					String[] arrayCat = cat.split("\\|");
					if(arrayCat.length>=2) {
						if(arrayCat[0].contains("p")) {
							fillCbxCategoria(Integer.parseInt(arrayCat[0].substring(1, arrayCat[0].length()).trim()));
						}
					}
					
					
				}else {

					cbxCategoria.getItems().removeAll(cbxCategoria.getItems());
					autoCompletePopup.getSuggestions().removeAll(autoCompletePopup.getSuggestions());
					cbxCategoria.setValue("");cbxCategoria.getEditor().setText("");
				}
				hijo = new TreeItem<>(cat);
				treeCategoria.getSelectionModel().getSelectedItem().getChildren().add(hijo);
				treeCategoria.getSelectionModel().select(hijo);
				treeCategoria.refresh();
				treeCategoria.scrollTo(treeCategoria.getRow(hijo));
				
			}
		}*/
	}
	
	@FXML
	private void filterCbxCat(KeyEvent event) {
//		System.out.println("code:"+event.getCode());
//		System.out.println("text:"+event.getText());
//		System.out.println("character:"+event.getCharacter());
//		System.out.println("event:"+event);
	/*	if(event.getCode() == KeyCode.ENTER) {
			System.out.println("item:"+cbxCategoria.getSelectionModel().getSelectedItem());
			System.out.println("item_editor:"+cbxCategoria.getEditor().getText());
			cbxCategoria.setValue(cbxCategoria.getEditor().getText());
			selectCategoria();
		}*/
		
	}
	@FXML
	private void removeNodeCat() {
/*
		TreeItem<String> parent = treeCategoria.getSelectionModel().getSelectedItem().getParent();
		if(parent!=null) {
			
			treeCategoria.getSelectionModel().getSelectedItem().getParent().getChildren().removeAll(
					treeCategoria.getSelectionModel().getSelectedItem().getParent().getChildren());
			treeCategoria.getSelectionModel().select(parent);
			treeCategoria.refresh();
			if(parent.getValue()==null || "null".equals(parent.getValue()) ) {
				fillCbxCategoria(0);
				
			}else if(parent.getValue().contains("|")) {
				try {
					int id = (Integer.parseInt(parent.getValue().substring(1, parent.getValue().indexOf("|")).trim()));
					System.out.println("idPadre:"+id);
					fillCbxCategoria(id);
				}catch(NumberFormatException e) {
					System.out.println(e.getMessage());
				}
			}
			
		}else {
			fillCbxCategoria(0);
		}*/
	}
	
	@FXML
	private void removeRowProd() {
		CostProductsDTO row = tblProds.getSelectionModel().getSelectedItem();
		if(row !=null)
			tblProds.getItems().remove(row);
		
	}
	
	@FXML
	private void addRowProd() {
		if(inputProdName.getText()==null || "".equals(inputProdName.getText().trim())){
			GeneralMethods.modalMsg("WARNING", "", "Ingrese el nombre de producto");
			return;
		}
		if(inputBarcode.getText()==null || "".equals(inputBarcode.getText().trim())){
			GeneralMethods.modalMsg("WARNING", "", "Ingrese c\u00F3digo de barras");
			return;
		}
		if(inputCosto.getText()==null || "".equals(inputCosto.getText().trim())){
			GeneralMethods.modalMsg("WARNING", "", "Asigne un valor para el producto");
			return;
		}
		CostProductsDTO row = new CostProductsDTO();
		row.setBar_code(inputBarcode.getText());
		String costo = inputCosto.getText().replace(",", "");
		
		row.setCosto(new BigDecimal(costo));
		row.setProducto(inputProdName.getText());
		row.setEstatus(("ACTIVO".equals(cbxEstatusProd.getValue().toUpperCase())) ? "1" : "0");
		
		tblProds.getItems().add(row);
		
		inputBarcode.setText("");
		inputCosto.setText("");
		cbxEstatusProd.setValue("Value");
		inputProdName.setText("");
		
	}
	
	private void responsiveGUI(){
		/* resize de acuerdo al tamaño del Pane padre */
		colProd.prefWidthProperty().bind(tblProds.widthProperty().multiply(0.4));
		colBarcode.prefWidthProperty().bind(tblProds.widthProperty().multiply(0.4));
		colCosto.prefWidthProperty().bind(tblProds.widthProperty().multiply(0.2));
		//COLUMNAS DE PRODUCTOS DE PEDIDO
		colProd.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("producto"));
		colBarcode.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("bar_code"));
		colCosto.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, BigDecimal>("costo"));
	}
	
	
}
