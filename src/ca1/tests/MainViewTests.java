package ca1.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca1.ImageProcessingEngine;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

/**
 * @author Dylan Richards
 *
 */
class MainViewTests {

	ImageProcessingEngine imageProcessingEngine;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {	
		new JFXPanel();
		File imageFile = new File("C:\\Eclipse\\Workspace\\BirdFlockAnalyser\\res\\simple512x512.png");
		Image image = new Image(imageFile.toURI().toString());
		imageProcessingEngine = new ImageProcessingEngine(image, 0.6);
		imageProcessingEngine.findBirds();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindBirds() {
		int numOfBirds = imageProcessingEngine.getBirds().size();
		
		assertEquals(6, numOfBirds);
	}
	
	@Test
	void testFurthestRemovedBird() {
		int furthestBird = imageProcessingEngine.getFurthestRemovedBird();
		
		assertEquals(3, furthestBird);
	}

}
