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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.local.entity.Store_cat_prod;
import mx.com.bitmaking.application.local.service.IStoreCatProdService;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
// @Scope("prototype")
public class GestProdController {
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService catProdService;

	@FXML
	private JFXButton btnAddProd;
	@FXML
	private JFXButton btnEliminarProd;
	@FXML
	private JFXButton btnEdtProd;
	@FXML
	private JFXButton btnSalir;
	@FXML
	private JFXButton btnAcceptModif;
	@FXML
	private JFXButton btnCancenModif;
	@FXML
	private JFXTextField inputName;
	@FXML
	private JFXComboBox<String> cbxStts;

	@FXML
	private AnchorPane bodyCatProd;
	@FXML
	private TreeView<String> treeProd;
	private Stage stageProd = null;
	LinkedHashMap<Integer, Store_cat_prod> productsMap = null;
	Store_cat_prod catProdModif = null;

	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		responsiveGUI();
		getTblCatProducts();
		btnAddProd.addEventHandler(MouseEvent.MOUSE_CLICKED, editProd("A"));
		btnEdtProd.addEventHandler(MouseEvent.MOUSE_CLICKED, editProd("M"));
		treeProd.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetails());
		btnAcceptModif.addEventHandler(MouseEvent.MOUSE_CLICKED, acceptModifProd());
		btnEliminarProd.addEventHandler(MouseEvent.MOUSE_CLICKED, modalDelProd());

	}

	private EventHandler<MouseEvent> acceptDelProd(TreeItem<String> treeItem) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				try {
					deleteChildren(treeItem);
					String[] arrayStr = treeItem.getValue().split("\\|");

					String idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
					Store_cat_prod row = productsMap.get(Integer.parseInt(idProd));
					row.setEstatus(("ACTIVO".equals(row.getEstatus().toUpperCase())) ? "1" : "0");
					catProdService.deleteRow(row);
					
					getTblCatProducts();
					stageProd.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	/**
	 * Abre modal de confirmacion para la eliminacion
	 * 
	 * @return
	 */
	private EventHandler<MouseEvent> modalDelProd() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// System.out.println(event.getSource());
				try {
					if (productsMap == null || productsMap.size() == 0) {
						GeneralMethods.modalMsg("WARNING", "", "No hay registro a eliminar");
						return;
					}
					
					ObservableList<TreeItem<String>> objTree = treeProd.getSelectionModel().getSelectedItems();
					TreeItem<String> treeItem = objTree.get(0);
					if (treeItem == null) {
						GeneralMethods.modalMsg("WARNING", "", "No hay registro seleccionado");
						return;
					}
					int idx = treeProd.getSelectionModel().getSelectedIndex();
					System.out.println("idx:"+idx);
					if(idx<=0){
						GeneralMethods.modalMsg("Error", "", "Seleccione algún producto");
						return;
					}
					FXMLLoader fxmlLoader = new FXMLLoader(
							getClass().getResource("/mx/com/bitmaking/application/view/ModalConfirm.fxml"));

					Parent sceneEdit = fxmlLoader.load();
					Scene scene = new Scene(sceneEdit, 190, 550);
					scene.getStylesheets().add(getClass()
							.getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
					stageProd = new Stage();
					stageProd.setScene(scene);
					stageProd.setTitle("Eliminar Producto");
					stageProd.setMinHeight(190.0);
					stageProd.setMinWidth(550.0);
					stageProd.setMaxHeight(190.0);
					stageProd.setMaxWidth(550.0);
					stageProd.initModality(Modality.APPLICATION_MODAL);

					ModalConfirmController modalObj = fxmlLoader.getController(); 
					List<String> lstStts = new ArrayList<>();
					lstStts.add("Activo");
					lstStts.add("Inactivo");
					modalObj.getLblMsg().setText("¿Seguro que desea eliminar el producto?");

					modalObj.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeModalEditProd());
					modalObj.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED, acceptDelProd(treeItem));
					stageProd.show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	/**
	 * Consulta Tabla de productos
	 */
	private void getTblCatProducts() {
		List<String> lstStts = new ArrayList<>();
		lstStts.add("Activo");
		lstStts.add("Inactivo");
		cbxStts.getItems().removeAll(cbxStts.getItems());
		cbxStts.setItems(FXCollections.observableList(lstStts));

		productsMap = catProdService.getAllCatalogoProduct2();
		TreeItem<String> root = new TreeItem<>("Productos del cliente");
		root.setExpanded(true);

		generateTreeProd(productsMap, 0, root);
		treeProd.setRoot(root);
		treeProd.getSelectionModel().select(0);
	}

	//
	private void generateTreeProd(LinkedHashMap<Integer, Store_cat_prod> hashMap, int id_padre,
			TreeItem<String> nodoPadre) {

		LinkedHashMap<Integer, Store_cat_prod> auxMap = new LinkedHashMap<>();
		for (Map.Entry<Integer, Store_cat_prod> el : hashMap.entrySet()) {
			if (el.getValue().getId_padre_prod() == id_padre) {
				auxMap.put(el.getValue().getId_prod(), el.getValue());
			}
		}

		if (auxMap.size() <= 0) {
			return;
		}
		TreeItem<String> nodo = null;
		for (Map.Entry<Integer, Store_cat_prod> el : auxMap.entrySet()) {
			nodo = new TreeItem<>("p-" + el.getValue().getId_prod() + " | " + el.getValue().getProducto());
			nodo.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetails());
			nodoPadre.getChildren().add(nodo);
			generateTreeProd(hashMap, el.getValue().getId_prod(), nodo);

		}
	}

	private EventHandler<MouseEvent> showDetails() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				catProdModif = null;
				inputName.setText("");
				cbxStts.setValue("");
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
						System.out.println(strRow);
						String[] arrayStr = strRow.split("\\|");
						System.out.println(arrayStr[0]);
						String idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
						catProdModif = productsMap.get(Integer.parseInt(idProd));
						inputName.setText(catProdModif.getProducto());
						cbxStts.setValue(catProdModif.getEstatus());
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
						Store_cat_prod row = catProdModif;
						row.setProducto(inputName.getText());
						row.setEstatus(("ACTIVO".equals(cbxStts.getValue().toUpperCase())) ? "1" : "0");
						catProdService.insertRow(row);
						// if("0".equals(row.getEstatus())){
						inactiveChildren(treeItem, row.getEstatus());
						// }
						cancelModif();
						inputName.setText("");
						cbxStts.setValue("");
						getTblCatProducts();
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
		Store_cat_prod row = null;
		for (TreeItem<String> el : children) {

			arrayStr = el.getValue().split("\\|");

			idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
			row = productsMap.get(Integer.parseInt(idProd));
			row.setEstatus(stts);
			catProdService.insertRow(row);
			inactiveChildren(el, stts);

		}

	}

	private void deleteChildren(TreeItem<String> treeItem) {
		ObservableList<TreeItem<String>> children = treeItem.getChildren();
		// TreeItem<String> item = null;
		String[] arrayStr = null;
		String idProd = null;
		Store_cat_prod row = null;
		for (TreeItem<String> el : children) {

			arrayStr = el.getValue().split("\\|");

			idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
			row = productsMap.get(Integer.parseInt(idProd));
			catProdService.deleteRow(row);
			deleteChildren(el);

		}

	}
	/**
	 * 
	 * @param typeForm:
	 *            saber si es alta o edici�n
	 * @return
	 */
	private EventHandler<MouseEvent> editProd(String typeForm) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// System.out.println(event.getSource());
				try {
					
					switch (typeForm) {
					case "A":
						FXMLLoader fxmlLoader = new FXMLLoader(
								getClass().getResource("/mx/com/bitmaking/application/view/EditProducto.fxml"));

						Parent sceneEdit = fxmlLoader.load();
						Scene scene = new Scene(sceneEdit, 300, 350);
						scene.getStylesheets()
								.add(getClass()
										.getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css")
										.toExternalForm());
						stageProd = new Stage();
						stageProd.setScene(scene);
						stageProd.setTitle("Editar Producto");
						stageProd.setMinHeight(350.0);
						stageProd.setMinWidth(350.0);
						stageProd.setMaxHeight(350.0);
						stageProd.setMaxWidth(350.0);
						stageProd.initModality(Modality.APPLICATION_MODAL);

						EditaProdController edtProd = fxmlLoader.getController();
						List<String> lstStts = new ArrayList<>();
						lstStts.add("Activo");
						lstStts.add("Inactivo");
						edtProd.getCbxEstatusProd().getItems().removeAll(edtProd.getCbxEstatusProd().getItems());
						edtProd.getCbxEstatusProd().setItems(FXCollections.observableList(lstStts));
						edtProd.getCbxEstatusProd().setValue("Activo");
						edtProd.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED,
								acceptEditProd(edtProd, typeForm));
						edtProd.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeModalEditProd());

						stageProd.show();
						break;
					case "M":
						int idx = treeProd.getSelectionModel().getSelectedIndex();
						System.out.println("idx:"+idx);
						if(idx<=0){
							GeneralMethods.modalMsg("Error", "", "Seleccione algún producto");
							return;
						}
						treeProd.setDisable(true);
						btnAcceptModif.setVisible(true);
						btnCancenModif.setVisible(true);
						cbxStts.setDisable(false);
						inputName.setDisable(false);

						break;
					default:
						GeneralMethods.modalMsg("Error", "", "No fue posible identificar la operación");
						return;
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	@FXML
	private void cancelModif() {
		btnAcceptModif.setVisible(false);
		btnCancenModif.setVisible(false);
		cbxStts.setDisable(true);
		inputName.setDisable(true);
		treeProd.setDisable(false);

	}

	private EventHandler<MouseEvent> acceptEditProd(EditaProdController edtProd, String typeForm) {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// System.out.println(event.getSource());
				Store_cat_prod row = new Store_cat_prod();

				row.setProducto(edtProd.getInputProdName().getText());
				row.setEstatus(("ACTIVO".equals(edtProd.getCbxEstatusProd().getValue().toUpperCase())) ? "1" : "0");
				if (catProdModif == null) {
					row.setId_padre_prod(0);
				} else {
					row.setId_padre_prod(catProdModif.getId_prod());
				}
				try {
					if ("M".equals(typeForm)) {
						ObservableList<Store_cat_prod> lsRow = null;// tblProducts.getSelectionModel().getSelectedItems();
						if (lsRow == null || lsRow.size() == 0) {
							GeneralMethods.modalMsg("WARNING", "",
									"Para modificar un producto, debes seleccionar un registro");
							return;
						}
						row.setId_prod(lsRow.get(0).getId_prod());
						// catProdService.updateRow(row);

					}
					catProdService.insertRow(row);
					getTblCatProducts();
					stageProd.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	private EventHandler<MouseEvent> closeModalEditProd() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// System.out.println(event.getSource());
				try {
					stageProd.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre */
		// colProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.6));
		// colEstatus.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.4));

	}
}
