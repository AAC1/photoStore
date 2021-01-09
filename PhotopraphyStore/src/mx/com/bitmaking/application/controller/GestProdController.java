package mx.com.bitmaking.application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.iservice.IStoreClteProdCostService;
import mx.com.bitmaking.application.iservice.IStoreFotografoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
// @Scope("prototype")
public class GestProdController {
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService catProdService;
	@Autowired
	@Qualifier("remoteStoreCatProdService")
	IStoreCatProdService remoteCatProdService;
	
	@Autowired
	@Qualifier("StoreFotografoService")
	IStoreFotografoService clienteService;
	@Autowired
	@Qualifier("remoteStoreFotografoService")
	IStoreFotografoService remoteClienteService;
	@Autowired 
	@Qualifier("StoreClteProdCostService")
	IStoreClteProdCostService clteProdCostService;
	@Autowired 
	@Qualifier("remoteStoreClteProdCostService")
	IStoreClteProdCostService remoteClteProdCostService;
	
	
	
	@FXML
	private JFXButton btnAddProd;
	@FXML
	private JFXButton btnEliminarProd;
	@FXML
	private JFXButton btnEdtProd;
	@FXML
	private JFXButton btnSalir;
	@FXML
	private JFXButton btnDescarga;
	@FXML
	private JFXButton btnAcceptModif;
	@FXML
	private JFXButton btnCancenModif;
	@FXML
	private JFXButton btnAddCatProd;
	
	@FXML
	private JFXTextField inputName;
	@FXML
	private JFXTextField inputBarcode;
	@FXML 
	private JFXTextField inputCosto;
	@FXML 
	private JFXTextField inputCostoCte;
	@FXML
	private JFXComboBox<String> cbxStts;

	@FXML
	private JFXComboBox<String> cbxTipoCosto;
	
	@FXML
	private AnchorPane bodyCatProd;
	@FXML
	private TreeView<String> treeProd;
	
	@FXML
	private JFXRadioButton radCategoria;
	@FXML
	private JFXRadioButton radProducto;
	@FXML
	private Label lblCosto;
	@FXML
	private Label lblCostoCte;
	@FXML
	private Label lblTipoCosto;
	
	//@Autowired
	//private ApplicationContext context ;
	@Autowired
	private Environment env;
	
	
	Stage stage = null;
	private Stage stageProd = null;
	LinkedHashMap<Integer, Store_cat_prod> productsMap = null;
	Store_cat_prod catProdModif = null;
	TreeItem<String> root = null;
	private boolean deleted=false;
	private String globalTypeForm = "";
	private FileChooser fileChooser = null;
	
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		fileChooser = new FileChooser();
		responsiveGUI();
		getTblCatProducts();
		btnAddProd.addEventHandler(MouseEvent.MOUSE_CLICKED, editCatProd("A"));
		btnEdtProd.addEventHandler(MouseEvent.MOUSE_CLICKED, editCatProd("M"));
		treeProd.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetails());
		btnAcceptModif.addEventHandler(MouseEvent.MOUSE_CLICKED, acceptModifProd());
		btnEliminarProd.addEventHandler(MouseEvent.MOUSE_CLICKED, modalDelProd());
		inputCosto.textProperty().addListener(GeneralMethods.formatNumber(inputCosto));
		inputCostoCte.textProperty().addListener(GeneralMethods.formatNumber(inputCostoCte));
		
	}
	
	@FXML
	private void changeCosto() {
		System.out.println("changeCosto");
		if(1 == (cbxTipoCosto.getSelectionModel().getSelectedIndex())) {
			inputCostoCte.setVisible(true);
			inputCostoCte.setDisable(false);
			lblCostoCte.setVisible(true);
			
		}else {
			inputCostoCte.setVisible(false);
			inputCostoCte.setDisable(true);
			lblCostoCte.setVisible(false);
			
			
		}
	//	inputCosto.setDisable(false);
	//	lblCosto.setVisible(true);
		inputCosto.setText("");
		inputCostoCte.setText("");
	}
	
	@FXML
	private void importFile() {
		Stage stageFile = new Stage();
		stageFile.setTitle("Seleccione inventario a importar");
		File inv = fileChooser.showOpenDialog(stage);
		DataFormatter dataFormatter = new DataFormatter();

		try {
			if(inv!=null) {
				Workbook workbook = WorkbookFactory.create(inv);
				StringBuilder rowNotFounded = new StringBuilder();
				Sheet sheet = workbook.getSheetAt(0);
				Cell cell =null;
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#.##"));
				
				Store_cat_prod catProd = null;
				
				globalTypeForm ="A";
				
				for (Row row: sheet) {
					catProd = new Store_cat_prod();
		            catProd.setProducto(dataFormatter.formatCellValue(row.getCell(0))); //Producto
		            
		            if(catProd.getProducto() ==null || catProd.getProducto().trim().length()==0 ||
		            		dataFormatter.formatCellValue(row.getCell(3)) ==null || dataFormatter.formatCellValue(row.getCell(3)).trim().length()==0 ||
		            		dataFormatter.formatCellValue(row.getCell(2)) ==null || dataFormatter.formatCellValue(row.getCell(2)).trim().length()==0) {
		            	continue;
		            }
		            
		            String padre = dataFormatter.formatCellValue(row.getCell(1)); //Padre
		            
		            if(padre.trim().length()>0) {
		            	//get IdPadre
		            	
		            	Store_cat_prod catProdObj = Flags.remote_valid?remoteCatProdService.getCatByPadre(padre):
		            					catProdService.getCatByPadre(padre);
		            	if(catProdObj!=null) {
		            		System.out.println("Producto:"+catProdObj.getProducto()+" / Cat:"+catProdObj.getCategoria());
		            	}
		            	 //Valida que se el padre exista y que sea tipo categoria (categoria=1)
		            	if(catProdObj!=null && catProdObj.getCategoria()==1) {
		            		catProd.setId_padre_prod(catProdObj.getId_prod());
		            	}else {
		            		rowNotFounded.append(catProd.getProducto()+"\n");
		            		continue;
		            	}
		            	 
		            }else {
		            	catProd.setId_padre_prod(0);
		            }
		           
		            catProd.setEstatus(dataFormatter.formatCellValue(row.getCell(2)));
		            
		            catProd.setCategoria(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(3))));
		            catProd.setBarcode(dataFormatter.formatCellValue(row.getCell(4)));
		            
		            //Vaida que se tenga barCode y sea producto (categoria=0)
		            if(catProd.getBarcode() != null && catProd.getBarcode().trim().length()>0 && catProd.getCategoria()==0) {
		            	
		            	catProd.setImg_barcode(GeneralMethods.generateImgBarcode(catProd.getBarcode().trim()));
		            }else {
		            	catProd.setBarcode(null);
		            }
		            
		            try {
		            	Store_cat_prod newProd = Flags.remote_valid?remoteCatProdService.getCatByPadre(catProd.getProducto()):
        					catProdService.getCatByPadre(catProd.getProducto());
		            	int idProd=0;
		            	
		            	//Costo aficionado
			            row.getCell(5).setCellStyle(cellStyle);
			            String costoGral =dataFormatter.formatCellValue(row.getCell(5));
			            costoGral = (costoGral ==null || "".equals(costoGral.trim()))? "0.0": costoGral.replaceAll(",", ".");
			            
			            //Costo fotografo
			            row.getCell(6).setCellStyle(cellStyle);
			            String costoCte =dataFormatter.formatCellValue(row.getCell(6));
			            costoCte = (costoCte ==null || "".equals(costoCte.trim()))? "0.0": costoCte.replaceAll(",", ".");
			            
			            catProd.setCosto_aficionado(new BigDecimal(costoGral));
			            catProd.setCosto_fotografo(new BigDecimal(costoCte));
			            
		            	//Valida si existe
		            	if(newProd==null) {
		            		idProd = Flags.remote_valid?remoteCatProdService.insertRow(catProd):
				            catProdService.insertRow(catProd);
		            		
		            	}else {
		            		idProd = newProd.getId_prod();
		            	}
			            
		            	
			            //Valida que sea producto, para actualizar costo
			            if(catProd.getCategoria()==0 ) {//
			            	
			            //	double cost = Double.parseDouble(costo.trim());
			            	saveCostosByCliente(idProd,costoCte,costoGral);
			            }
		            }catch(Exception ex) {
		            	rowNotFounded.append(catProd.getProducto()+"\n");
		            	ex.printStackTrace();
	            		continue;
		    		}
		        }
				if(rowNotFounded.length()>0) {

					GeneralMethods.modalMsg("", "", "Los siguientes registros no se pudieron agregar correctamente: \n"+rowNotFounded);
				}else {
					GeneralMethods.modalMsg("", "", "Inventario agregado correctamente");
						
				}
				
			}
		}catch(IOException ex) {
			GeneralMethods.modalMsg("", "", "No fue posible cargar todo el inventario, valide el contenido");
			ex.printStackTrace();
		}catch(Exception ex) {
			GeneralMethods.modalMsg("", "", "No fue posible cargar todo el inventario, valide el contenido");
			ex.printStackTrace();
		}finally {
			globalTypeForm ="";
			getTblCatProducts();
		}
	}
	
	@FXML 
	private void selectCat() {
		radProducto.setSelected(false);
		inputBarcode.setDisable(true);
		inputCosto.setDisable(true);
		lblCosto.setVisible(false);

		inputCostoCte.setDisable(true);
		lblCostoCte.setVisible(false);
		inputCostoCte.setText("");
		
		inputBarcode.setText("");
		inputCosto.setText("");
		inputCosto.setVisible(false);
		
	
		cbxTipoCosto.setVisible(false);
		cbxTipoCosto.setDisable(true);
		cbxTipoCosto.setValue("General");
		lblTipoCosto.setVisible(false);
	}
	
	@FXML 
	private void selectProd() {
		
			inputBarcode.setDisable(false);
			inputCosto.setDisable(false);
			inputCosto.setVisible(true);
			inputCosto.setText("");
			radCategoria.setSelected(false);
			lblCosto.setVisible(true);
			
			inputCostoCte.setVisible(false);
			inputCostoCte.setDisable(false);
			inputCostoCte.setText("");
			lblCostoCte.setVisible(false);
			
			cbxTipoCosto.setDisable(false);
			cbxTipoCosto.setVisible(true);
			cbxTipoCosto.setValue("General");
			lblTipoCosto.setVisible(true);
		
	}
	@FXML
	private void addCatProd() {
		
	}
	private EventHandler<MouseEvent> acceptDelProd(TreeItem<String> treeItem) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				deleted=true;
				try {
					deleteChildren(treeItem);
					String[] arrayStr = treeItem.getValue().split("\\|");

					String idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
					Store_cat_prod row = productsMap.get(Integer.parseInt(idProd));
					row.setEstatus(("ACTIVO".equals(row.getEstatus().toUpperCase())) ? "1" : "0");
					if(deleted){//valida si algun hijo no se pudo eliminar
						catProdService.deleteRow(row);
						if(Flags.remote_valid)remoteCatProdService.deleteRow(row);
					}else{
						GeneralMethods.modalMsg("", "", "Algunos productos no fueron eliminados.");
					}
					getTblCatProducts();
					catProdModif = null;
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
					stageProd.setMinWidth(500.0);
					stageProd.setMaxHeight(190.0);
					stageProd.setMaxWidth(500.0);
					stageProd.initModality(Modality.APPLICATION_MODAL);

					ModalConfirmController modalObj = fxmlLoader.getController(); 
					List<String> lstStts = new ArrayList<>();
					lstStts.add("Activo");
					lstStts.add("Inactivo");
					modalObj.getLblMsg().setText("¿Seguro que desea eliminar el registro?\n"
							+ "Se eliminar\u00E1n los costos de los productos relacionados");

					modalObj.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeModalEditProd(stageProd));
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
		inputName.setText("");
		List<String> lstStts = new ArrayList<>();
		lstStts.add("Activo");
		lstStts.add("Inactivo");
		cbxStts.getItems().removeAll(cbxStts.getItems());
		cbxStts.setItems(FXCollections.observableList(lstStts));
		
		List<String> lstTipoCost = new ArrayList<>();
		lstTipoCost.add("General");
		lstTipoCost.add("Espec\u00EDfico");
		cbxTipoCosto.getItems().removeAll(cbxTipoCosto.getItems());
		cbxTipoCosto.setItems(FXCollections.observableList(lstTipoCost));
		
		
		
		productsMap = (Flags.remote_valid)?remoteCatProdService.getAllCatalogoProduct2():
											catProdService.getAllCatalogoProduct2();
		root = new TreeItem<>("Productos del cliente");
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
			nodoPadre.setExpanded(true);
			generateTreeProd(hashMap, el.getValue().getId_prod(), nodo);

		}
	}

	private EventHandler<MouseEvent> showDetails() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getDetails();
			}
		};
	}

	private EventHandler<MouseEvent> acceptModifProd() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				try {

					if (productsMap == null || productsMap.size() == 0  ) {
						if("A".equals(globalTypeForm)){
							altaProd();
							btnAddProd.setDisable(false);
							btnEdtProd.setDisable(false);
							btnEliminarProd.setDisable(false);
							btnDescarga.setDisable(false);
							inputName.setText("");
							cbxStts.setValue("");
							inputBarcode.setText("");
							getTblCatProducts();
							catProdModif = null;
							//saveCostosByCliente(row.getId_prod(),inputCosto.getText());
						}else {
							GeneralMethods.modalMsg("WARNING", "", "No hay registro a modificar");
						}
						 
						return;
					}

					ObservableList<TreeItem<String>> objTree = treeProd.getSelectionModel().getSelectedItems();
					TreeItem<String> treeItem = objTree.get(0);
					if (treeItem == null && "M".equals(globalTypeForm)) {
						// GeneralMethods.modalMsg("WARNING", "", "No hay
						// registro seleccionado");
						return;
					}
					int idx = treeProd.getSelectionModel().getSelectedIndex();

					String strRow = treeItem.getValue();

					
					if (strRow != null && strRow.length() > 0){
						if ( idx <= 0) {// ||
							if("M".equals(globalTypeForm)){
							GeneralMethods.modalMsg("WARNING", "", "Para modificar, debes seleccionar un registro");
							return;
							}
							
						} 
						
						if(radProducto.isSelected()) {
							
							if(inputBarcode.getText()==null || "".equals(inputBarcode.getText().trim())){
								GeneralMethods.modalMsg("WARNING", "", "Ingrese c\u00F3digo de barras");
								return;
							}
							if(inputCosto.getText()==null || "".equals(inputCosto.getText().trim())){
								GeneralMethods.modalMsg("WARNING", "", "Asigne un valor para el producto");
								return;
							}
							
						}
						
						Store_cat_prod row=null;
						if("M".equals(globalTypeForm)){
							row = catProdModif;
							row.setProducto(inputName.getText());
							row.setEstatus(("ACTIVO".equals(cbxStts.getValue().toUpperCase())) ? "1" : "0");
							if(inputBarcode.getText()!=null && !"".equals(inputBarcode.getText().trim())) {
								row.setBarcode(inputBarcode.getText());
								row.setImg_barcode(GeneralMethods.generateImgBarcode(inputBarcode.getText()));
								
							}
							else {
								inputBarcode.setText("");
							}
							String cost = inputCosto.getText();
							String costoCte = inputCostoCte.getText();
							if (cost==null || cost.length()==0 ) {
								cost="0.0";
							}
							if (costoCte==null || costoCte.length()==0 ) {
								costoCte="0.0";
							}
							
							cost=cost.replaceAll(",", "");
							costoCte=costoCte.replaceAll(",", "");

							row.setCosto_aficionado(new BigDecimal(cost));
							row.setCosto_fotografo(new BigDecimal(costoCte));
							
							catProdService.updateRow(row);
							if(Flags.remote_valid)remoteCatProdService.updateRow(row);
							// if("0".equals(row.getEstatus())){
							inactiveChildren(treeItem, row.getEstatus());

							if (catProdModif != null && inputBarcode.getText().length()>0 
									&& inputBarcode.getText().length()>0  ) {
								
								
								if(cbxTipoCosto.getSelectionModel().getSelectedIndex() == 1) {
										saveCostosByCliente(row.getId_prod(),costoCte,cost);
								}else {
										saveCostosByCliente(row.getId_prod(),cost);
								}
							//	cost=cost.replaceAll(",", "");
							//	saveCostosByCliente(row.getId_prod(),cost);
							}
							cancelModif();
							
						}
						else if("A".equals(globalTypeForm)){
							altaProd();
							//saveCostosByCliente(row.getId_prod(),inputCosto.getText());
						}
						
						// }
						
						btnAddProd.setDisable(false);
						btnEdtProd.setDisable(false);
						btnEliminarProd.setDisable(false);
						btnDescarga.setDisable(false);
						inputName.setText("");
						cbxStts.setValue("");
						inputBarcode.setText("");
						getTblCatProducts();
						catProdModif = null;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		};
	}
	
	private void altaProd() {
		Store_cat_prod row = new Store_cat_prod();
		if (catProdModif == null) {
			row.setId_padre_prod(0);
		} else {
			row.setId_padre_prod(catProdModif.getId_prod());
		}
		/*if(strRow.contains("|")) {
			String[] arrayStr = strRow.split("\\|");
			
			idProd = arrayStr[0].substring(2, arrayStr[0].length()).trim();
		}*/
		row.setProducto(inputName.getText());
		row.setEstatus(("ACTIVO".equals(cbxStts.getValue().toUpperCase())) ? "1" : "0");
	
		if(inputBarcode.getText()!=null && !"".equals(inputBarcode.getText().trim())) {
			row.setBarcode(inputBarcode.getText());
			row.setImg_barcode(GeneralMethods.generateImgBarcode(inputBarcode.getText()));
			
		}
		if(radCategoria.isSelected()) {
			row.setCategoria(1);
		}
		else{
			row.setCategoria(0);
		}
		
		String cost = inputCosto.getText();
		String costoCte = inputCostoCte.getText();
		if (cost==null || cost.length()==0 ) {
			cost="0.0";
		}
		if (costoCte==null || costoCte.length()==0 ) {
			costoCte="0.0";
		}

		cost=cost.replaceAll(",", "");
		costoCte=costoCte.replaceAll(",", "");
		
		row.setCosto_aficionado(new BigDecimal(cost));
		row.setCosto_fotografo(new BigDecimal(costoCte));
		
		catProdService.insertRow(row);
		if(Flags.remote_valid)remoteCatProdService.insertRow(row);
		
		//if (cost!=null && cost.length()>0 ) {
			if(cbxTipoCosto.getSelectionModel().getSelectedIndex() == 1) {
				saveCostosByCliente(row.getId_prod(),costoCte,cost);
			}else {
				saveCostosByCliente(row.getId_prod(),cost);
			}
	//	}
			
		
		
		cancelModif();
	}
	
	private void getDetails() {
		System.out.println("entra: en getDetails");
		catProdModif = null;
		inputName.setText("");
		cbxStts.setValue("");
		inputBarcode.setText("");
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
				inputBarcode.setText(catProdModif.getBarcode());

				System.out.println("Categoria: "+catProdModif.getCategoria());
				if(catProdModif.getCategoria()==1) {
					radCategoria.setDisable(true);
					radCategoria.setSelected(true);
					
					radProducto.setDisable(true);
					radProducto.setSelected(false);
				}
				else {
					radCategoria.setDisable(true);
					radCategoria.setSelected(false);
					
					radProducto.setDisable(true);
					radProducto.setSelected(true);
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
			try {
				catProdService.updateRow(row);//catProdService.insertRow(row);
				if(Flags.remote_valid)remoteCatProdService.updateRow(row);//remoteCatProdService.insertRow(row);
				inactiveChildren(el, stts);
			}catch(Exception e) {
				GeneralMethods.modalMsg("ERROR", "", e.getMessage());
			}

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
			try{
				catProdService.deleteRow(row);
				if(Flags.remote_valid)remoteCatProdService.deleteRow(row);
			}catch(Exception e){
				deleted=false;
				System.out.println(e.getMessage());
			}
			deleteChildren(el);

		}

	}

	
	private EventHandler<MouseEvent> editCatProd(String typeForm) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// System.out.println(event.getSource());
				try {
					globalTypeForm = typeForm;
					int idx = treeProd.getSelectionModel().getSelectedIndex();
					System.out.println("idx:"+idx);
					if(idx<0){
						GeneralMethods.modalMsg("Error", "", "Seleccione algún producto o categoria");
						return;
					}
					
					ObservableList<TreeItem<String>> lstTree = treeProd.getSelectionModel().getSelectedItem().getChildren();
					System.out.println("treeItems:"+lstTree.size());
					
					inputBarcode.setDisable(true);
					inputCosto.setDisable(true);
					
					switch (typeForm) {
					case "A":
						
						if(lstTree.isEmpty() )//Valida que no tenga hijos
						{
							if(catProdModif !=null && catProdModif.getBarcode()!=null &&
									catProdModif.getBarcode().trim().length()>0) {
								GeneralMethods.modalMsg("Error", "", "No puede agregar un producto en un producto.\n Favor de seleccionar una categoria.");
								return;
							}
						}
						radCategoria.setDisable(false);
						radCategoria.setSelected(true);
						
						radProducto.setDisable(false);
						radProducto.setSelected(false);

						inputName.setText("");
						inputBarcode.setText("");
						inputCosto.setText("");
						cbxStts.setValue("Activo");
						/*FXMLLoader fxmlLoader = new FXMLLoader(
								getClass().getResource("/mx/com/bitmaking/application/view/ChooseCatProd.fxml"));
						fxmlLoader.setControllerFactory(context::getBean);
						Parent sceneEdit = fxmlLoader.load();
						Scene scene = new Scene(sceneEdit, 300, 350);
						scene.getStylesheets()
								.add(getClass()
										.getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css")
										.toExternalForm());
						stageProd = new Stage();
						stageProd.setScene(scene);
						stageProd.setTitle("Alta de productos");
						stageProd.setMinHeight(211.0);
						stageProd.setMinWidth(415.0);
						stageProd.setMaxHeight(211.0);
						stageProd.setMaxWidth(415.0);
						stageProd.initModality(Modality.APPLICATION_MODAL);

						ChooseCatProdController edtProd = fxmlLoader.getController();
					
						edtProd.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,openEditCatProd(edtProd,stageProd, typeForm));
						edtProd.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeModalEditProd(stageProd));
						
						stageProd.show();*/

				
						break;
					case "M":
						/*int idx = treeProd.getSelectionModel().getSelectedIndex();
						System.out.println("idx:"+idx);
						if(idx<=0){
							GeneralMethods.modalMsg("Error", "", "Seleccione algún producto o categoria");
							return;
						}
						treeProd.setDisable(true);
						btnAcceptModif.setVisible(true);
						btnCancenModif.setVisible(true);
						cbxStts.setDisable(false);
						inputName.setDisable(false);
						ObservableList<TreeItem<String>> lstTree = treeProd.getSelectionModel().getSelectedItem().getChildren();
						System.out.println("treeItems:"+lstTree.size());
						*/
						if(idx<=0){
							GeneralMethods.modalMsg("Error", "", "Seleccione algún producto o categoria");
							inputBarcode.setDisable(false);
							inputCosto.setDisable(false);
							return;
						}
						if(lstTree.isEmpty())//Valida que no tenga hijos
						{System.out.println("Es hijo");	inputBarcode.setDisable(false);}
						
						if(catProdModif !=null) {
							if(catProdModif.getCategoria() == 0) { //Valida si es producto
								
								inputCosto.setDisable(false);
								inputCosto.setVisible(true);
								lblCosto.setVisible(true);
								
								inputCostoCte.setDisable(false);
								inputCostoCte.setVisible(false);
								lblCostoCte.setVisible(false);
								
								cbxTipoCosto.setVisible(true);
								cbxTipoCosto.setDisable(false);
								cbxTipoCosto.setValue("General");
								lblTipoCosto.setVisible(true);
								
								radProducto.setDisable(true);
								radProducto.setSelected(true);
								
								radCategoria.setDisable(true);
								radCategoria.setSelected(false);
								System.out.println("Cat catProdModif:"+catProdModif.getCategoria());
								
							}
							else {
								radProducto.setDisable(true);
								radProducto.setSelected(false);
								
								radCategoria.setDisable(true);
								radCategoria.setSelected(true);
								
								/*Invalida estos valores por ser categoria*/
								inputBarcode.setDisable(true);
								inputBarcode.setText("");
								

								inputCosto.setDisable(true);
								inputCosto.setVisible(false);
								lblCosto.setVisible(false);
								
								inputCostoCte.setDisable(true);
								inputCostoCte.setVisible(false);
								lblCostoCte.setVisible(false);
								
								cbxTipoCosto.setVisible(false);
								cbxTipoCosto.setDisable(true);
								cbxTipoCosto.setValue("General");
								lblTipoCosto.setVisible(false);
								
							}
						}
						
						/*
						if(catProdModif !=null && !"".equals(catProdModif.getBarcode())) {
							inputCosto.setDisable(false);
							inputCosto.setVisible(true);
							lblCosto.setVisible(true);
						}
						treeProd.setDisable(true);
						*/
						break;
					default:
						GeneralMethods.modalMsg("Error", "", "No fue posible identificar la operación");
						
						return;
					}
				//	treeProd.setDisable(true);
					btnAcceptModif.setVisible(true);
					btnCancenModif.setVisible(true);
					cbxStts.setDisable(false);
					inputName.setDisable(false);

					btnAddProd.setDisable(true);
					btnEdtProd.setDisable(true);
					btnEliminarProd.setDisable(true);
					btnDescarga.setDisable(true);
					treeProd.setDisable(true);
					btnAddCatProd.setDisable(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			
		};
	}
	


	@FXML
	private void cancelModif() {
		cbxTipoCosto.setVisible(false);
		//cbxTipoCosto.setValue("General");
		lblTipoCosto.setVisible(false);
		
		btnAcceptModif.setVisible(false);
		btnCancenModif.setVisible(false);
		cbxStts.setDisable(true);
		inputName.setDisable(true);
		treeProd.setDisable(false);
		inputBarcode.setDisable(true);
		
		radCategoria.setDisable(true);
		radProducto.setDisable(true);
		globalTypeForm ="";
		
		btnAddProd.setDisable(false);
		btnEdtProd.setDisable(false);
		btnEliminarProd.setDisable(false);
		btnDescarga.setDisable(false);
		treeProd.setDisable(false);

		inputCosto.setVisible(false);
		inputCosto.setDisable(true);
		lblCosto.setVisible(false);
		inputCosto.setText("");
		
		inputCostoCte.setVisible(false);
		inputCostoCte.setDisable(true);
		lblCostoCte.setVisible(false);
		inputCostoCte.setText("");
		
		btnAddCatProd.setDisable(false);
		
	}

	private void saveCostosByCliente(int idProd, String costo) {
		List<Store_fotografo>  lstClte = (Flags.remote_valid)?remoteClienteService.getActiveClients():clienteService.getActiveClients();
		Store_cliente_prod_cost clteProdCost = null;
		clteProdCostService.deleteRowByIdCostProd(idProd);
		System.out.println("Costo: "+costo);
		for(Store_fotografo fo: lstClte) {
			clteProdCost = new Store_cliente_prod_cost();
			clteProdCost.setCosto(new BigDecimal(costo));//.replaceAll(",", "")
			clteProdCost.setId_cliente(fo.getId_fotografo());
			clteProdCost.setId_prod(idProd);
			if("A".equals(globalTypeForm)) {
				clteProdCostService.insertRow(clteProdCost);
				if(Flags.remote_valid)remoteClteProdCostService.insertRow(clteProdCost);
			}
			else if("M".equals(globalTypeForm)) {
				clteProdCostService.updateRow(clteProdCost);
				if(Flags.remote_valid)remoteClteProdCostService.updateRow(clteProdCost);
			}
		}
		
	}

	private void saveCostosByCliente(int idProd, String costoCte,String costoGral) {
		List<Store_fotografo>  lstClte = (Flags.remote_valid)?remoteClienteService.getActiveClients():clienteService.getActiveClients();
		Store_cliente_prod_cost clteProdCost = null;
		clteProdCostService.deleteRowByIdCostProd(idProd);
	//	System.out.println("Costo Gral: "+costoGral);
	//	System.out.println("Costo Fotografo: "+costoCte);
		List<Store_fotografo>  lstClteGral = lstClte.stream().filter(e -> "A".equals(e.getTipo())).collect(Collectors.toList());
		List<Store_fotografo>  lstClteFot = lstClte.stream().filter(e -> "F".equals(e.getTipo())).collect(Collectors.toList());
		
		for(Store_fotografo fo: lstClteGral) {
			clteProdCost = new Store_cliente_prod_cost();
			
			clteProdCost.setCosto(new BigDecimal(costoGral));//.replaceAll(",", "")
			
			clteProdCost.setId_cliente(fo.getId_fotografo());
			clteProdCost.setId_prod(idProd);
			if("A".equals(globalTypeForm)) {
				clteProdCostService.insertRow(clteProdCost);
				if(Flags.remote_valid)remoteClteProdCostService.insertRow(clteProdCost);
			}
			else if("M".equals(globalTypeForm)) {
				clteProdCostService.updateRow(clteProdCost);
				if(Flags.remote_valid)remoteClteProdCostService.updateRow(clteProdCost);
			}
		}
		
		for(Store_fotografo fo: lstClteFot) {
			clteProdCost = new Store_cliente_prod_cost();
			
			clteProdCost.setCosto(new BigDecimal(costoCte));//.replaceAll(",", "")
			
			clteProdCost.setId_cliente(fo.getId_fotografo());
			clteProdCost.setId_prod(idProd);
			if("A".equals(globalTypeForm)) {
				clteProdCostService.insertRow(clteProdCost);
				if(Flags.remote_valid)remoteClteProdCostService.insertRow(clteProdCost);
			}
			else if("M".equals(globalTypeForm)) {
				clteProdCostService.updateRow(clteProdCost);
				if(Flags.remote_valid)remoteClteProdCostService.updateRow(clteProdCost);
			}
		}
		
	}

	private EventHandler<MouseEvent> closeModalEditProd(Stage stageProd) {
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
	
	@FXML 
	private void selectByArrow(KeyEvent e) {
		System.out.println("entra:"+e.getCode().toString());
		if("DOWN".equals(e.getCode().toString()) || "UP".equals(e.getCode().toString())){
			getDetails();
		}
	}
	
	@FXML
	private void downloadBarCodeProds() {
		File file=null;
		FileInputStream fileInputStream = null;
	//	ClassLoader classLoader = GestProdController.class.getClassLoader();
	//	URL loader = GestProdController.class.getClassLoader().getSystemResource("TblBarCode.jasper");
	
		try {
			
			//if(loader==null){
		//		GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar directorio de la plantilla para el reporte");
		//		return;
		//	}

			file = new File(env.getProperty("exportFile.pathBarCodeJasper"));//loader.getFile());
			System.out.println("ABS_PATH: "+file.getAbsolutePath());
			System.out.println("PARENT: "+file.getParent());
			System.out.println("JUST_PATH: "+file.getPath());
			
		//	String pathPlantilla = file.getAbsolutePath();
			
			//File fileToDownload = new File(pathPlantilla);
			SimpleDateFormat formatoD = new SimpleDateFormat("ddMMyyyy_hhmmss");
		
			if (file.exists() && file.isFile()) {
				fileInputStream = new FileInputStream(file);
			} else {
				GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar plantilla de reporte-> "+file.getAbsolutePath());
				return;
			}
			String pathReport=env.getProperty("exportFile.path")+"/barcode_"+formatoD.format(new Date())+".pdf";
			String logoPath = "src/mx/com/bitmaking/application/assets/img/macrofoto_logo.jpg";
			File logoFile = new File("src/mx/com/bitmaking/application/assets/img/macrofoto_logo.jpg");
			
			if(logoFile!=null) {
				logoPath = logoFile.getAbsolutePath();
			}
			String titulo=Constantes.COMPANY_NAME;
			boolean export = (Flags.remote_valid)?
					remoteCatProdService.createBarcodePDF(fileInputStream,titulo,pathReport,logoPath):
						catProdService.createBarcodePDF(fileInputStream,titulo,pathReport,logoPath);
			
			if(export) {
				File fataExported = new File(pathReport);
			//	GeneralMethods.modalMsg("", "Exportación Terminada.", " Vaya a la ruta: "+pathReport);
				stage = new Stage();
				GeneralMethods.saveFile(stage, fataExported.toPath(),"PDF files (*.pdf)", "*.pdf");
			}else
				GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
		}
		catch (Exception e) {
			GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
			e.printStackTrace();
		}finally{
			if(fileInputStream!=null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
