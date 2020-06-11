package resella;

import java.beans.EventHandler;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
		JFXTreeTableColumn<User, String> deptColumn = new JFXTreeTableColumn<>("Department");
		deptColumn.setPrefWidth(150);
		deptColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) ->{
			if(deptColumn.validateValue(param)) return param.getValue().getValue().department;
			else return deptColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<User, String> empColumn = new JFXTreeTableColumn<>("Employee");
		empColumn.setPrefWidth(150);
		empColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) ->{
			if(empColumn.validateValue(param)) return param.getValue().getValue().userName;
			else return empColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<User, String> ageColumn = new JFXTreeTableColumn<>("Age");
		ageColumn.setPrefWidth(150);
		ageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) ->{
			if(ageColumn.validateValue(param)) return param.getValue().getValue().age;
			else return ageColumn.getComputedValue(param);
		});


		ageColumn.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<User,
				String>(new TextFieldEditorBuilder()));
		ageColumn.setOnEditCommit((CellEditEvent<User, String> t)->{
			((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).age
			.set(t.getNewValue());


		});

		empColumn.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<User,
				String>(new TextFieldEditorBuilder()));
		empColumn.setOnEditCommit((CellEditEvent<User, String> t)->{
			((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
			.userName.set(t.getNewValue());


		});

		deptColumn.setCellFactory((TreeTableColumn<User, String> param) ->
		new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
		deptColumn.setOnEditCommit((CellEditEvent<User, String> t)->{
			((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).department.
			set(t.getNewValue()); 
		});

		ageColumn.setEditable(false);
		empColumn.setEditable(false);
		deptColumn.setEditable(false);

		ImageView testGalaxy = new ImageView("https://i.ebayimg.com/images/g/7JcAAOSwU9RaeefH/s-l300.jpg");

//		Hyperlink hyperlink = new Hyperlink("Goooooogle");
//		hyperlink.setOnAction(event -> getHostServices().showDocument("https://www.google.co.in/"));

		
		
		
		// data
		ObservableList<User> users = FXCollections.observableArrayList();
		users.add(new User("Computer Department", "23","CD 1"));
		users.add(new User("Sales Department", "22","Employee 1"));
		users.add(new User("Sales Department", "22","Employee 2"));
		users.add(new User("Sales Department", "25","Employee 4"));
		users.add(new User("Sales Department", "25","Employee 5"));
		users.add(new User("IT Department", "42","ID 2"));
		users.add(new User("HR Department", "22","HR 1"));
		users.add(new User("HR Department", "22","HR 2"));
		//users..add(new TeamsInnerClass(testGalaxy));
		
		
		for(int i = 0 ; i< 100; i++){
			users.add(new User("HR Department", i%10+"","HR 2" + i));
		}
		for(int i = 0 ; i< 100; i++){
			users.add(new User("Computer Department", i%20+"","CD 2" + i));
		}

		for(int i = 0 ; i< 100; i++){
			users.add(new User("IT Department", i%5+"","HR 2" + i));
		}

		// build tree
		final TreeItem<User> treeItemRoot = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);

		JFXTreeTableView<User> treeView = new JFXTreeTableView<User>(treeItemRoot);

		treeView.setShowRoot(false);
		treeView.setEditable(true);
		treeView.getColumns().setAll(deptColumn, ageColumn, empColumn);

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