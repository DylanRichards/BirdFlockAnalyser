package ca1;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @author Dylan Richards
 *
 */
public class BlackWhiteModel {

	private Image orgImage, blackWhiteImage;

	public BlackWhiteModel(Image image, double threshold) {
		this.orgImage = image;

		this.blackWhiteImage = generateBWImage(orgImage, threshold);
	}

	private Image generateBWImage(Image orgImage, double threshold) {
		int orgImgWidth = (int) orgImage.getWidth();
		int orgImgHeight = (int) orgImage.getHeight();

		PixelReader reader = orgImage.getPixelReader();

		WritableImage img = new WritableImage(orgImgWidth, orgImgHeight);
		PixelWriter writer = img.getPixelWriter();

		for (int row = 0; row < orgImgHeight; row++) {
			for (int col = 0; col < orgImgWidth; col++) {
				Color color = reader.getColor(col, row);
				double brightness = color.getBrightness();

				Color newColor = (brightness > threshold) ? Color.WHITE : Color.BLACK;
				writer.setColor(col, row, newColor);
			}
		}

		return img;
	}

	public Image getBlackWhiteImage() {
		return blackWhiteImage;
	}

}
