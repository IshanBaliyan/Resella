package resella;

import java.beans.EventHandler;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Resella extends Application{

	// Global static constants for use throughout the program
	private static final int GAP = 15;
	private static final int SMALL_MEDIUM_GAP = 30;
	private static final int MEDIUM_GAP = 45;
	private static final int MEDIUM_LARGE_GAP = 60;
	private static final int LARGE_GAP = 75;
	private static final int VERY_LARGE_GAP = 150; 

	private static double screenWidth;
	private static double screenHeight;

	// The root is a StackPane, so dialogs can be used
	private StackPane dialogRoot = new StackPane();

	// The root of the scene
	private VBox root = new VBox(GAP);

	private HBox headerMenu = new HBox(GAP);

	private HBox tableFooter = new HBox(GAP);

	@Override
	public void start(Stage myStage) throws Exception {
		JFXTreeTableColumn<ProductListingProperties, String> titleColumn = new JFXTreeTableColumn<>("Product Name");
		titleColumn.setPrefWidth(150);
		titleColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductListingProperties, String> param) ->{
			if(titleColumn.validateValue(param)) return param.getValue().getValue().title;
			else return titleColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<ProductListingProperties, Hyperlink> listingURLColumn = new JFXTreeTableColumn<>("Listing URL");
		listingURLColumn.setPrefWidth(150);
		listingURLColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductListingProperties, Hyperlink> param) ->{
			if(listingURLColumn.validateValue(param)) return param.getValue().getValue().listingURL;
			else return listingURLColumn.getComputedValue(param);
		});
//		
//		listingURLColumn.setCellValueFactory(new PropertyValueFactory<ProductListingURL, String>)("listingURL");
//		
//		listingURLColumn.setCellValueFactory(new TreeItemPropertyValueFactory<ProductListingProperties, Hyperlink>)("listingURL");

		JFXTreeTableColumn<ProductListingProperties, String> priceColumn = new JFXTreeTableColumn<>("Price");
		priceColumn.setPrefWidth(150);
		priceColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductListingProperties, String> param) ->{
			if(priceColumn.validateValue(param)) return param.getValue().getValue().price;
			else return priceColumn.getComputedValue(param);
		});


		priceColumn.setCellFactory((TreeTableColumn<ProductListingProperties, String> param) -> new GenericEditableTreeTableCell<ProductListingProperties,
				String>(new TextFieldEditorBuilder()));
		priceColumn.setOnEditCommit((CellEditEvent<ProductListingProperties, String> t)->{
			((ProductListingProperties) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).price
			.set(t.getNewValue());


		});

		listingURLColumn.setCellFactory((TreeTableColumn<ProductListingProperties, Hyperlink> param) -> new GenericEditableTreeTableCell<ProductListingProperties,
				Hyperlink>(new TextFieldEditorBuilder()));

		titleColumn.setCellFactory((TreeTableColumn<ProductListingProperties, String> param) ->
		new GenericEditableTreeTableCell<ProductListingProperties, String>(new TextFieldEditorBuilder()));
		titleColumn.setOnEditCommit((CellEditEvent<ProductListingProperties, String> t)->{
			((ProductListingProperties) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).title.
			set(t.getNewValue()); 
		});

		priceColumn.setEditable(false);
		listingURLColumn.setEditable(false);
		titleColumn.setEditable(false);

		ImageView testGalaxy = new ImageView("https://i.ebayimg.com/images/g/7JcAAOSwU9RaeefH/s-l300.jpg");

//		Hyperlink hyperlink = new Hyperlink("google.com");
//		hyperlink.setOnAction(event -> getHostServices().showDocument("https://www.google.co.in/"));

//		Hyperlink hyperlink = new Hyperlink("Google");
//		hyperlink.setOnAction(event -> getHostServices().showDocument("https://www.google.co.in/"));
		
		String hyperlink = "google.com";
		
		// data
		ObservableList<ProductListingProperties> productListings = FXCollections.observableArrayList();
		productListings.add(new ProductListingProperties("Ishan Product", hyperlink,"CD 1"));
		productListings.add(new ProductListingProperties("Sales Department", hyperlink,"Employee 1"));
		productListings.add(new ProductListingProperties("Sales Department", hyperlink,"Employee 4"));
		productListings.add(new ProductListingProperties("Sales Department",hyperlink,"Employee 5"));
		productListings.add(new ProductListingProperties("IT Department", hyperlink,"ID 2"));
		productListings.add(new ProductListingProperties("HR Department", hyperlink,"HR 1"));
		productListings.add(new ProductListingProperties("HR Department", hyperlink,"HR 2"));
		//productListings..add(new TeamsInnerClass(testGalaxy));
		
		
//		for(int i = 0 ; i< 100; i++){
//			productListings.add(new ProductListingProperties("HR Department", i%10+"","HR 2" + i));
//		}
//		for(int i = 0 ; i< 100; i++){
//			productListings.add(new ProductListingProperties("Computer Department", i%20+"","CD 2" + i));
//		}
//
//		for(int i = 0 ; i< 100; i++){
//			productListings.add(new ProductListingProperties("IT Department", i%5+"","HR 2" + i));
//		}

		// build tree
		final TreeItem<ProductListingProperties> treeItemRoot = new RecursiveTreeItem<ProductListingProperties>(productListings, RecursiveTreeObject::getChildren);

		JFXTreeTableView<ProductListingProperties> treeView = new JFXTreeTableView<ProductListingProperties>(treeItemRoot);

		treeView.setShowRoot(false);
		treeView.setEditable(true);
		treeView.getColumns().setAll(titleColumn, priceColumn, listingURLColumn);

		JFXTextField filterField = new JFXTextField();
		filterField.textProperty().addListener((o,oldVal,newVal)->{
			treeView.setPredicate(productListing -> productListing.getValue().price.get().contains(newVal)
					|| productListing.getValue().title.get().contains(newVal)
					|| productListing.getValue().listingURL.getBean().toString().contains(newVal));
		});

		Label size = new Label();
		size.textProperty().bind(Bindings.createStringBinding(()->treeView.getCurrentItemsCount()+"",
				treeView.currentItemsCountProperty()));

		// Get screen dimensions
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		screenWidth = primaryScreenBounds.getWidth();
		screenHeight = primaryScreenBounds.getHeight();

		/********* ROOT ********/
		// Set the root's formatting options
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(treeView, filterField);


		/********* GENERATE SCENE AND ADD IT TO THE STAGE ********/
		// Add root to the dialog StackPane
		dialogRoot.getChildren().add(root);
		Scene scene = new Scene(dialogRoot, screenWidth, screenHeight);

		scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto:400,500,700&display=swap");

		// Load the application's CSS
		scene.getStylesheets().add("/CSS/Resella.css");


		myStage.setTitle("Resella");
		myStage.setScene(scene);
		myStage.setMaximized(true);
		// myStage.getIcons().add(new Image("/images/TTT_logo.png"));
		myStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Hyperlink createHyper(Hyperlink hyperlink) {
		hyperlink.setOnAction(event -> getHostServices().showDocument("https://www.google.co.in/"));
		return hyperlink;
		
	}
}