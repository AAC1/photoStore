package mx.com.bitmaking.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
//@Scope("prototype")
public class GestProdController {
	@FXML private JFXButton btnAddProd;
	@FXML private JFXButton btnEliminarProd;
	@FXML private JFXButton btnEdtProd;
	@FXML private JFXButton btnSalir;
	@FXML private TableView<Store_cat_prod> tblProducts;
	@FXML private TableColumn colProd;
	@FXML private TableColumn colEstatus;
	@FXML private AnchorPane bodyCatProd;
	
	Stage stageProd = null;
	
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService catProdService;
	
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		responsiveGUI();
		getTblCatProducts();
		btnAddProd.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditProd("A"));
		btnEdtProd.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditProd("M"));

		btnEliminarProd.addEventHandler(MouseEvent.MOUSE_CLICKED,modalDelProd());
		
	}
	
	private EventHandler<MouseEvent> acceptDelProd() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					ObservableList<Store_cat_prod> lsRow =tblProducts.getSelectionModel().getSelectedItems();
					catProdService.deleteRow(lsRow.get(0));
					getTblCatProducts();
					stageProd.close();
					
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	
	/**
	 * Abre modal de confirmacion para la eliminacion
	 * @return
	 */
	private EventHandler<MouseEvent> modalDelProd() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					ObservableList<Store_cat_prod> row =tblProducts.getSelectionModel().getSelectedItems();
					if(row==null || row.size()==0){
						GeneralMethods.modalMsg("WARNING", "", "Para eliminar un producto, debes seleccionar un registro");
						return;
					}
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/ModalConfirm.fxml"));
						
						Parent sceneEdit= fxmlLoader.load();
						Scene scene = new Scene(sceneEdit,190,550);
						scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
						stageProd = new Stage();
						stageProd.setScene(scene);
						stageProd.setTitle("Eliminar Producto");
						stageProd.setMinHeight(190.0);
						stageProd.setMinWidth(550.0);
						stageProd.setMaxHeight(190.0);
						stageProd.setMaxWidth(550.0);
						stageProd.initModality(Modality.APPLICATION_MODAL); 
						
						ModalConfirmController modalObj = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
						List<String>lstStts =new ArrayList<>();
						lstStts.add("Activo");
						lstStts.add("Inactivo");
						modalObj.getLblMsg().setText("¿Seguro que desea eliminar el producto?");
						
						modalObj.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeModalEditProd());
						modalObj.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED, acceptDelProd());
						stageProd.show();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	
	/**
	 * Consulta Tabla de productos
	 */
	private void getTblCatProducts() {
		List<Store_cat_prod> lstProd = catProdService.getAllCatalogoProduct();
		
		tblProducts.setItems(FXCollections.observableList(lstProd));
		colProd.setCellValueFactory(new PropertyValueFactory("producto"));
		colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
	}

	/**
	 * 
	 * @param typeForm: saber si es alta o edici�n
	 * @return
	 */
	private EventHandler<MouseEvent> modalEditProd(String typeForm) {
		return new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			//System.out.println(event.getSource());
			try {
				
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/EditProducto.fxml"));
					
					Parent sceneEdit= fxmlLoader.load();
					Scene scene = new Scene(sceneEdit,300,350);
					scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css").toExternalForm());
					stageProd = new Stage();
					stageProd.setScene(scene);
					stageProd.setTitle("Editar Producto");
					stageProd.setMinHeight(350.0);
					stageProd.setMinWidth(350.0);
					stageProd.setMaxHeight(350.0);
					stageProd.setMaxWidth(350.0);
					stageProd.initModality(Modality.APPLICATION_MODAL); 
					
					EditaProdController edtProd = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
					List<String>lstStts =new ArrayList<>();
					lstStts.add("Activo");
					lstStts.add("Inactivo");
					edtProd.getCbxEstatusProd().getItems().removeAll(edtProd.getCbxEstatusProd().getItems());
					edtProd.getCbxEstatusProd().setItems(FXCollections.observableList(lstStts));
					switch(typeForm){
					case "A":
						edtProd.getCbxEstatusProd().setValue("Activo");
						break;
					case "M":
						ObservableList<Store_cat_prod> row =tblProducts.getSelectionModel().getSelectedItems();
						if(row==null || row.size()==0){
							GeneralMethods.modalMsg("WARNING", "", "Para modificar un producto, debes seleccionar un registro");
							return;
						}else{
							edtProd.getInputProdName().setText(row.get(0).getProducto());
							edtProd.getCbxEstatusProd().setValue(row.get(0).getEstatus());
						}
						break;
					default:
						GeneralMethods.modalMsg("Error", "", "No fue posible identificar la operación");
						return;
					}
					
					edtProd.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeModalEditProd());
					edtProd.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED, acceptEditProd(edtProd,typeForm));
					stageProd.show();
	        } catch(Exception ex) {
				ex.printStackTrace();
			}
		}};
	}
	private EventHandler<MouseEvent> acceptEditProd(EditaProdController edtProd,String typeForm) {
		return new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				Store_cat_prod row = new Store_cat_prod();
				
				row.setProducto(edtProd.getInputProdName().getText());
				row.setEstatus(("ACTIVO".equals(edtProd.getCbxEstatusProd().getValue().toUpperCase()) )?"1":"0");
				try {
					if("M".equals(typeForm)) {
						ObservableList<Store_cat_prod> lsRow =tblProducts.getSelectionModel().getSelectedItems();
						if(lsRow==null || lsRow.size()==0){
							GeneralMethods.modalMsg("WARNING", "", "Para modificar un producto, debes seleccionar un registro");
							return;
						}
						row.setId_prod(lsRow.get(0).getId_prod());
						//catProdService.updateRow(row);
						
					}
					catProdService.insertRow(row);
					getTblCatProducts();
					stageProd.close();
					
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	
	private EventHandler<MouseEvent> closeModalEditProd() {
		return new EventHandler<MouseEvent>() {
	
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					stageProd.close();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	
	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre*/
		colProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.6));
		colEstatus.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.4));
		
	}
}
