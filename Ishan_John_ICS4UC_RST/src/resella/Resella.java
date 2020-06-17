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
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import simpleIO.Console;

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
	
	private WebScraper scraper;

	private AdListingTable listingTable;
	
	@Override
	public void start(Stage myStage) throws Exception {
		
		scraper = new WebScraper("iphone x");
		scraper.scrapeListings();
		
		listingTable = new AdListingTable(scraper.getSoldAdListings());
		listingTable.calculateAverageSellPrice();
		
		ProductListing.setSellPrice(listingTable.getAverageSellPrice());
		
		JFXTreeTableColumn<ProductListing, String> imgColumn = new JFXTreeTableColumn<>("Product Image");
		imgColumn.setPrefWidth(150);
		imgColumn.setCellValueFactory(param -> param.getValue().getValue().getImgURL());

		JFXTreeTableColumn<ProductListing, ProductListing.ProductLink> listingURLColumn = new JFXTreeTableColumn<>("Listing URL");
		listingURLColumn.setPrefWidth(200);
		listingURLColumn.setCellValueFactory(param -> param.getValue().getValue().getListingURL());
		
		JFXTreeTableColumn<ProductListing, Double> profitColumn = new JFXTreeTableColumn<>("Potential Profit (Shipping Not Included)");
		profitColumn.setPrefWidth(150);
		profitColumn.setCellValueFactory(param -> param.getValue().getValue().getProfit().asObject());
		
		JFXTreeTableColumn<ProductListing, String> shippingPriceColumn = new JFXTreeTableColumn<>("Shipping Price + Availability");
		shippingPriceColumn.setPrefWidth(150);
		shippingPriceColumn.setCellValueFactory(param -> param.getValue().getValue().getOrderMethod());
		
		JFXTreeTableColumn<ProductListing, String> listingTypeColumn = new JFXTreeTableColumn<>("Listing Type");
		listingTypeColumn.setPrefWidth(150);
		listingTypeColumn.setCellValueFactory(param -> param.getValue().getValue().getListingType());
		
		JFXTreeTableColumn<ProductListing, String> marketplaceColumn = new JFXTreeTableColumn<>("Marketplace");
		marketplaceColumn.setPrefWidth(150);
		marketplaceColumn.setCellValueFactory(param -> param.getValue().getValue().getMarketplace());
		
		imgColumn.setCellFactory(column -> new TreeTableCell<ProductListing, String>() {
			private final ImageView imageView;
			
			{
			    imageView = new ImageView();
			    setGraphic(imageView);
			}
			
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(item != null) {
					imageView.setImage(new Image(item, 150, 150, true, true));
				}
			}
		});

		listingURLColumn.setCellFactory(column -> new TreeTableCell<ProductListing, ProductListing.ProductLink>(){
			@Override
			protected void updateItem(ProductListing.ProductLink item, boolean empty) {

				super.updateItem(item, empty);
				if(item == null || empty) {
					setGraphic(null);
                    setText(null);

				}else {
					Hyperlink hyperlink = new Hyperlink(item.getLinkTitle());
					hyperlink.setOnAction(event -> getHostServices().showDocument(item.getLinkURL()));
					setGraphic(hyperlink);
					setText(null);
				}
			}
		});
		
		profitColumn.setCellFactory(column -> new TreeTableCell<ProductListing, Double>(){
			@Override
			protected void updateItem(Double item, boolean empty) {

				super.updateItem(item, empty);
				if(item == null || empty) {
                    setText(null);

				}else {
					setText(item.doubleValue() + "");
				}
			}
		});
		
		shippingPriceColumn.setCellFactory(column -> new TreeTableCell<ProductListing, String>(){
			@Override
			protected void updateItem(String item, boolean empty) {

				super.updateItem(item, empty);
				if(item == null || empty) {
                    setText(null);

				}else {
					setText(item);
				}
			}
		});

		listingTypeColumn.setCellFactory(column -> new TreeTableCell<ProductListing, String>(){
			@Override
			protected void updateItem(String item, boolean empty) {

				super.updateItem(item, empty);
				if(item == null || empty) {
                    setText(null);

				}else {
					setText(item);
				}
			}
		});
		
		marketplaceColumn.setCellFactory(column -> new TreeTableCell<ProductListing, String>(){
			@Override
			protected void updateItem(String item, boolean empty) {

				super.updateItem(item, empty);
				if(item == null || empty) {
                    setText(null);

				}else {
					setText(item);
				}
			}
		});

		imgColumn.setEditable(false);
		listingURLColumn.setEditable(false);
		profitColumn.setEditable(false);
		shippingPriceColumn.setEditable(false);
		listingTypeColumn.setEditable(false);
		marketplaceColumn.setEditable(false);

		// data
		ObservableList<ProductListing> productListings = FXCollections.observableArrayList(scraper.getActiveAdListings());

		// build tree
		final TreeItem<ProductListing> treeItemRoot = new RecursiveTreeItem<ProductListing>(productListings, RecursiveTreeObject::getChildren);

		JFXTreeTableView<ProductListing> treeView = new JFXTreeTableView<ProductListing>(treeItemRoot);

		treeView.setShowRoot(false);
		treeView.setEditable(true);
		treeView.getColumns().setAll(imgColumn, listingURLColumn, profitColumn, shippingPriceColumn, listingTypeColumn, marketplaceColumn);

		JFXTextField filterField = new JFXTextField();
		filterField.textProperty().addListener((o,oldVal,newVal)->{
			treeView.setPredicate(listing -> listing.getValue().getTitle().getValue().toLowerCase().contains(newVal.toLowerCase()) || listing.getValue().getLocation().getValue().toLowerCase().contains(newVal.toLowerCase()) ||
					listing.getValue().getOrderMethod().getValue().toLowerCase().contains(newVal.toLowerCase()) || listing.getValue().getTags().contains(newVal.toLowerCase()) || 
					listing.getValue().getMarketplace().getValue().toLowerCase().contains(newVal.toLowerCase()) || listing.getValue().getListingType().getValue().toLowerCase().contains(newVal.toLowerCase()));
			
			if (newVal.toLowerCase().equals(ProductListing.AUCTION_LISTING.toLowerCase()) || newVal.toLowerCase().equals(ProductListing.BUY_IT_NOW_LISTING.toLowerCase())) {
				// Don't filter, reset filteredListings
				listingTable.resetFilteredListings();
			}
			else if (newVal.toLowerCase().equals(ProductListing.KIJIJI.toLowerCase())) {
				// Don't filter, reset filteredListings
				listingTable.resetFilteredListings();
			}
			else {
				listingTable.filterSoldListings(newVal.toLowerCase());
				listingTable.calculateAverageSellPrice();
				ProductListing.setSellPrice(listingTable.getAverageSellPrice());
			}
		});

		Label size = new Label();
		size.textProperty().bind(Bindings.createStringBinding(()->treeView.getCurrentItemsCount()+"",
				treeView.currentItemsCountProperty()));
		
		Label sellPrice = new Label();
		sellPrice.textProperty().bind(Bindings.createStringBinding(()-> listingTable.getAverageSellPrice() + "", listingTable.averageSellPriceProperty()));

		// Get screen dimensions
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		screenWidth = primaryScreenBounds.getWidth();
		screenHeight = primaryScreenBounds.getHeight();

		/********* TABLEFOOTER ********/
		// Set the tableFooter's formatting options
		tableFooter.getChildren().addAll(filterField, size, sellPrice);
		
		/********* ROOT ********/
		// Set the root's formatting options
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(headerMenu, treeView, tableFooter);


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
}