
package resella;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

class ProductListingProperties extends RecursiveTreeObject<ProductListingProperties> {
	StringProperty price;
	StringProperty title;
	StringProperty subtitle;
	protected final ObjectProperty<Hyperlink> listingURL;
	
	public ProductListingProperties(String title, String listingURL, String price) {
		this.title = new SimpleStringProperty(title);
		Resella sampleLink = new Resella();
		Hyperlink hyperlink = sampleLink.createHyper(new Hyperlink(listingURL));
//		hyperlink.setOnAction(new EventHandler<ActionEvent>() {
//          
////			@Override
////            public void handle(ActionEvent t) {
////                getHostServices().showDocument(hyperlink.getText());
////            }
//        });
		this.listingURL = new SimpleObjectProperty<Hyperlink>(hyperlink);
		this.price = new SimpleStringProperty(price);
	}

}