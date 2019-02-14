package ca1;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ImageProcessingEngine {

	private Set<Integer> birds;
	private int[] arrImage;
	private int imgWidth, imgHeight;

	public ImageProcessingEngine(Image orgImg, double threshold) {
		createPixelArr(new BlackWhiteModel(orgImg, threshold).getBlackWhiteImage());
	}

	private void createPixelArr(Image bwImg) {
		imgWidth = (int) bwImg.getWidth();
		imgHeight = (int) bwImg.getHeight();

		arrImage = new int[imgWidth * imgHeight];

		PixelReader reader = bwImg.getPixelReader();

		int index = 0;
		for (int row = 0; row < imgHeight; row++) {
			for (int col = 0; col < imgWidth; col++) {
				Color color = reader.getColor(col, row);

				arrImage[index] = (color.equals(Color.BLACK)) ? index : -1;
				index++;
			}
		}
	}

	public void findBirds() {
		if (arrImage == null)
			return;

		for (int i = 0; i < arrImage.length; i++) {
			if (arrImage[i] == -1)
				continue;

			try {
				if (arrImage[i + 1] != -1) {
					// Union with the pixel to the right
					union(arrImage, i, i + 1);
				}

				if (arrImage[i + imgWidth] != -1) {
					// Union with the pixel below
					union(arrImage, i, i + imgWidth);
				}
			} catch (ArrayIndexOutOfBoundsException boundsException) {
			}

		}

		addArrtoSet();
	}

	private void addArrtoSet() {
		birds = new HashSet<>();

		for (int id = 0; id < arrImage.length; id++) {
			int parent = find(arrImage, id);
			if (parent != -1)
				birds.add(parent);
		}
	}

	private void printRoot() {
		for (int id = 0; id < arrImage.length; id++) {
			int parent = find(arrImage, id);
			if (parent != -1)
				System.out.println("The root of " + id + " is " + parent);
		}
	}

	private void union(int[] a, int p, int q) {
		a[find(a, q)] = find(a, p);
	}

	private int find(int[] a, int id) {
		if (a[id] == -1)
			return -1;

		while (a[id] != id)
			id = a[id];
		return id;
	}

	public Set<Integer> getBirds() {
		return birds;
	}

	public int getImgWidth() {
		return imgWidth;
	}

}
