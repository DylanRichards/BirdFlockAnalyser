package ca1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Dylan Richards
 *
 */
public class ImageProcessingEngine {

	private Set<Integer> birds = new HashSet<>();
	private int[] arrImage;
	private int imgWidth, imgHeight;

	private Rectangle[] birdBoxes;

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
		removeNoise();
		createBirdBoxes();
	}

	private void addArrtoSet() {
		for (int id = 0; id < arrImage.length; id++) {
			int parent = find(arrImage, id);
			if (parent != -1)
				birds.add(parent);
		}
	}

	private void removeNoise() {
		System.out.println(birds.size() + " birds before noise reduction");

		// Remove small sets of birds
		birds.removeIf(b->((double) occurrences(b) / arrImage.length) * 100.0<0.055);
	}

	private void createBirdBoxes() {
		birdBoxes = new Rectangle[birds.size()];

		int index = 0;
		for (Iterator<Integer> iterator = birds.iterator(); iterator.hasNext();) {
			Integer element = iterator.next();

			int x = element % getImgWidth();
			int y = element / getImgWidth();

			int minX = x, maxX = x, minY = y, maxY = y;
			for (int i = 0; i < arrImage.length; i++) {
				if (arrImage[i] == -1)
					continue;

				if (element == find(arrImage, i)) {
					// It is apart of the current bird check pixel
					int xLoc = i % getImgWidth();
					int yLoc = i / getImgWidth();

					minX = (minX > xLoc) ? xLoc : minX;
					maxX = (maxX < xLoc) ? xLoc : maxX;

					minY = (minY > yLoc) ? yLoc : minY;
					maxY = (maxY < yLoc) ? yLoc : maxY;
				}

			}

			birdBoxes[index++] = new Rectangle(minX, minY, maxX - minX, maxY - minY);
		}
	}
	
	public int getFurthestRemovedBird() {
		double maxDistance = 0;
		int furthestBird = -1;
		
		int birdNum = 1;
		for (Iterator<Integer> iterator = birds.iterator(); iterator.hasNext();) {
			Integer bird = iterator.next();

			int x1 = bird % getImgWidth();
			int y1 = bird / getImgWidth();

			double localDistance = 0;
			for (Iterator<Integer> iterator2 = birds.iterator(); iterator2.hasNext();) {
				Integer bird2 = iterator2.next();

				int x2 = bird2 % getImgWidth();
				int y2 = bird2 / getImgWidth();

				localDistance += Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
			}
			
			if (maxDistance < localDistance) {
				maxDistance = localDistance;
				furthestBird = birdNum;
			}
			
			birdNum++;			
		}
		
		return furthestBird;
	}

	private int occurrences(int parent) {
		int count = 0;
		for (int i = 0; i < arrImage.length; i++) {

			if (parent == find(arrImage, i)) {
				count++;
			}
		}
		return count;
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

	public Rectangle[] getBirdBoxes() {
		return birdBoxes;
	}
}
