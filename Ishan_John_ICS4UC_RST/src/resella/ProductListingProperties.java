
package resella;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

	public class ProductLink {

		private String linkTitle;
		private String linkURL;

		ProductLink(String linkTitle, String linkURL) {
			this.linkTitle = linkTitle;
			this.linkURL = linkURL;
		}

		public String getLinkTitle() {
			return linkTitle;
		}

		public String getLinkURL() {
			return linkURL;
		}
	}
}