package resella;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ResellaTableTest extends Application{
	
	private static final String DEFAULT_IMG_URL = "https://ir.ebaystatic.com/cr/v/c1/ebay-logo-1-1200x630-margin.png";
	
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
		JFXTreeTableColumn<ProductListingObservable, String> deptColumn = new JFXTreeTableColumn<>("Department");
		deptColumn.setPrefWidth(150);
		deptColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductListingObservable, String> param) ->{
			if(deptColumn.validateValue(param)) return param.getValue().getValue().department;
			else return deptColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<ProductListingObservable, String> empColumn = new JFXTreeTableColumn<>("Employee");
		empColumn.setPrefWidth(150);
		empColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductListingObservable, String> param) ->{
			if(empColumn.validateValue(param)) return param.getValue().getValue().userName;
			else return empColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<ProductListingObservable, String> ageColumn = new JFXTreeTableColumn<>("Age");
		ageColumn.setPrefWidth(150);
		ageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductListingObservable, String> param) ->{
			if(ageColumn.validateValue(param)) return param.getValue().getValue().age;
			else return ageColumn.getComputedValue(param);
		});
		
		JFXTreeTableColumn<ProductListingObservable, String> imgColumn = new JFXTreeTableColumn<>("Product Image");
		imgColumn.setPrefWidth(150);
		imgColumn.setCellValueFactory(param -> param.getValue().getValue().imgURL);


		ageColumn.setCellFactory((TreeTableColumn<ProductListingObservable, String> param) -> new GenericEditableTreeTableCell<ProductListingObservable,
				String>(new TextFieldEditorBuilder()));
		ageColumn.setOnEditCommit((CellEditEvent<ProductListingObservable, String> t)->{
			((ProductListingObservable) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).age
			.set(t.getNewValue());


		});

		empColumn.setCellFactory((TreeTableColumn<ProductListingObservable, String> param) -> new GenericEditableTreeTableCell<ProductListingObservable,
				String>(new TextFieldEditorBuilder()));
		empColumn.setOnEditCommit((CellEditEvent<ProductListingObservable, String> t)->{
			((ProductListingObservable) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
			.userName.set(t.getNewValue());


		});

		deptColumn.setCellFactory((TreeTableColumn<ProductListingObservable, String> param) ->
		new GenericEditableTreeTableCell<ProductListingObservable, String>(new TextFieldEditorBuilder()));
		deptColumn.setOnEditCommit((CellEditEvent<ProductListingObservable, String> t)->{
			((ProductListingObservable) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).department.
			set(t.getNewValue()); 
		});
		
		imgColumn.setCellFactory(column -> new TreeTableCell<ProductListingObservable, String>() {
			    private final ImageView imageView;
	
			    {
			        imageView = new ImageView();
			        imageView.setFitWidth(50);
			        imageView.setFitHeight(50);
			        setGraphic(imageView);
			    }
	
			    @Override
			    protected void updateItem(String item, boolean empty) {
			    	super.updateItem(item, empty);
			    	if(item==null) {
			    	}
			    	else {
			    		imageView.setImage(new Image(item));
			    	}
			    }
		});

		ageColumn.setEditable(false);
		empColumn.setEditable(false);
		deptColumn.setEditable(false);
		imgColumn.setEditable(false);

//		Hyperlink hyperlink = new Hyperlink("Goooooogle");
//		hyperlink.setOnAction(event -> getHostServices().showDocument("https://www.google.co.in/"));
		
		// data
		ObservableList<ProductListingObservable> users = FXCollections.observableArrayList();
		users.add(new ProductListingObservable("Computer Department", "23","CD 1", DEFAULT_IMG_URL));
		users.add(new ProductListingObservable("Sales Department", "22","Employee 1", DEFAULT_IMG_URL));
		users.add(new ProductListingObservable("Sales Department", "22","Employee 2", DEFAULT_IMG_URL));
		users.add(new ProductListingObservable("Sales Department", "25","Employee 4", DEFAULT_IMG_URL));
		users.add(new ProductListingObservable("Sales Department", "25","Employee 5", DEFAULT_IMG_URL));
		users.add(new ProductListingObservable("IT Department", "42","ID 2", DEFAULT_IMG_URL));
		users.add(new ProductListingObservable("HR Department", "22","HR 1", DEFAULT_IMG_URL));
		users.add(new ProductListingObservable("HR Department", "22","HR 2", DEFAULT_IMG_URL));
		
		
		for(int i = 0 ; i< 100; i++){
			users.add(new ProductListingObservable("HR Department", i%10+"","HR 2" + i, DEFAULT_IMG_URL));
		}
		for(int i = 0 ; i< 100; i++){
			users.add(new ProductListingObservable("Computer Department", i%20+"","CD 2" + i, DEFAULT_IMG_URL));
		}

		for(int i = 0 ; i< 100; i++){
			users.add(new ProductListingObservable("IT Department", i%5+"","HR 2" + i, DEFAULT_IMG_URL));
		}

		// build tree
		final TreeItem<ProductListingObservable> treeItemRoot = new RecursiveTreeItem<ProductListingObservable>(users, RecursiveTreeObject::getChildren);

		JFXTreeTableView<ProductListingObservable> treeView = new JFXTreeTableView<ProductListingObservable>(treeItemRoot);

		treeView.setShowRoot(false);
		treeView.setEditable(true);
		treeView.getColumns().setAll(deptColumn, ageColumn, empColumn, imgColumn);

		JFXTextField filterField = new JFXTextField();
		filterField.textProperty().addListener((o,oldVal,newVal)->{
			treeView.setPredicate(user -> user.getValue().age.get().contains(newVal)
					|| user.getValue().department.get().contains(newVal)
					|| user.getValue().userName.get().contains(newVal));
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
}
