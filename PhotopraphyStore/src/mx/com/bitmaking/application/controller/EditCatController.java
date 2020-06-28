package mx.com.bitmaking.application.controller;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;

@Controller
public class EditCatController {
	
	@FXML private TreeView<String> treeCategoria;
	@FXML private JFXTextField inputBarcode;
	@FXML private JFXTextField inputCosto;
	@FXML private JFXTextField inputCategoria;
	
	@FXML private JFXButton btnAddCat;
	@FXML private JFXButton btnRemoveCat;
	@FXML private JFXButton btnAccept;
	@FXML private JFXButton btnCancel;
	
	TreeItem<String> root =null;
	TreeItem<String> actualNodo =null;
	
	
	
	
	public JFXButton getBtnAccept() {
		return btnAccept;
	}

	public void setBtnAccept(JFXButton btnAccept) {
		this.btnAccept = btnAccept;
	}

	public JFXButton getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(JFXButton btnCancel) {
		this.btnCancel = btnCancel;
	}

	public TreeView<String> getTreeCategoria() {
		return treeCategoria;
	}

	public void setTreeCategoria(TreeView<String> treeCategoria) {
		this.treeCategoria = treeCategoria;
	}

	public void initialize() {
		root = new TreeItem<>();
		treeCategoria.setRoot(root);
		treeCategoria.getSelectionModel().select(0);
		treeCategoria.getStyleClass().add("mainTree");
	}

	@FXML 
	private void addCategoria() {
		TreeItem<String> nodeSelected = treeCategoria.getSelectionModel().getSelectedItem();
		
		if(nodeSelected==null || "".equals(nodeSelected)) {
			GeneralMethods.modalMsg("", "Aviso", "Debe seleccionar primero un nodo para darlo de alta");
			return;
		}
		String cat =inputCategoria.getText();
		if(cat!=null && !"".equals(cat)) {
			boolean itemExists = false;
			
		}
		
	}
	@FXML
	private void removeNodeCat() {

		TreeItem<String> parent = treeCategoria.getSelectionModel().getSelectedItem().getParent();
		if(parent!=null) {
			
			treeCategoria.getSelectionModel().getSelectedItem().getParent().getChildren().removeAll(
					treeCategoria.getSelectionModel().getSelectedItem().getParent().getChildren());
			treeCategoria.getSelectionModel().select(parent);
			treeCategoria.refresh();
			if(parent.getValue()==null || "null".equals(parent.getValue()) ) {
			//	fillCbxCategoria(0);
				
			}else if(parent.getValue().contains("|")) {
				try {
					int id = (Integer.parseInt(parent.getValue().substring(1, parent.getValue().indexOf("|")).trim()));
					System.out.println("idPadre:"+id);
				//	fillCbxCategoria(id);
				}catch(NumberFormatException e) {
					System.out.println(e.getMessage());
				}
			}
			
		}else {
			//fillCbxCategoria(0);
		}
	}
}
