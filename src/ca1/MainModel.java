package ca1;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 * @author Dylan Richards
 *
 */
public class MainModel {

	private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("*.jpg", "*.png", "*.gif");

	private Image image;

	private File chooseImageFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Eclipse\\Workspace\\BirdFlockAnalyser\\res"));
		fileChooser.setTitle("Open Image File");

		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Image Files", IMAGE_EXTENSIONS);
		fileChooser.getExtensionFilters().add(fileExtensions);

		return fileChooser.showOpenDialog(BirdFlockApplication.mainStage);
	}

	public Image getImage() {
		return image;
	}

	public void setImageByChooser() {
		File imageFile = chooseImageFile();
		Image image = new Image(imageFile.toURI().toString());
		this.image = image;
	}

}
