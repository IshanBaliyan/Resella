
package resella;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

class ProductListingProperties extends RecursiveTreeObject<ProductListingProperties> {
	protected StringProperty price;
	protected StringProperty title;
	protected StringProperty subtitle;
	protected ObjectProperty<ProductLink> listingURL;
	
	
	public ProductListingProperties(String title, String listingURL, String price) {
		this.title = new SimpleStringProperty(title);
		this.listingURL = new SimpleObjectProperty<ProductLink>(new ProductLink(title, listingURL));
		this.price = new SimpleStringProperty(price);
	}
	
public class ProductLink{
	
	private String linkTitle;
	private String linkURL;
	private Hyperlink hyperlink;
	
	ProductLink(String linkTitle,String linkURL){
		
		 hyperlink = new Hyperlink(linkTitle);
		 this.linkTitle = linkTitle;
		 this.linkURL = linkURL;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public Hyperlink getHyperlink() {
		return hyperlink;
	}

	
}
	
}