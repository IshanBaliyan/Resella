package resella;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class ProductListingObservable extends RecursiveTreeObject<ProductListingObservable> {
	protected StringProperty userName;
	protected StringProperty age;
	protected StringProperty department;
	protected StringProperty imgURL;
 
    /**
	 * @return the imgURL
	 */
	public StringProperty getImgURL() {
		return imgURL;
	}

	public ProductListingObservable(String department, String age, String userName, String imgURL) {
    	this.department = new SimpleStringProperty(department);
        this.userName = new SimpleStringProperty(userName);
        this.age = new SimpleStringProperty(age);
        this.imgURL = new SimpleStringProperty(imgURL);
    }
}
