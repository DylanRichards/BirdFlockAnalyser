package ca1;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Dylan Richards
 *
 */
public class BlackWhiteView extends Stage {

	private ImageView imageView = new ImageView();

	public BlackWhiteView() {
		super.setTitle("Black and White View");

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1000, 600);

		imageView.setFitHeight(scene.getHeight() * 0.9);
		imageView.setPreserveRatio(true);
		root.setCenter(imageView);

		super.setScene(scene);
		super.show();
	}

	public void setImageView(Image image) {
		this.imageView.setImage(image);
	}
}
