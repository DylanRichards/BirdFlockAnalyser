package ca1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Rectangle;

/**
 * @author Dylan Richards
 *
 */
public class MainController implements EventHandler<ActionEvent> {

	private MainView view;
	private MainModel model;

	static int birdNum = 1;

	public MainController(MainView view, MainModel model) {
		this.view = view;
		this.model = model;

		for (MenuItem item : view.getAllMenuItems()) {
			item.setOnAction(this);
		}

	}

	@Override
	public void handle(ActionEvent event) {
		double bwThreshold = 0.6;

		if (event.getSource().equals(view.getMenuItemOpen())) {
			model.setImageByChooser();
			view.setImageView(model.getImage());
			view.removeImageOverlays();
		} else if (event.getSource().equals(view.getMenuItemExit())) {
			Platform.exit();
		} else if (event.getSource().equals(view.getMenuItemBlackWhite())) {
			BlackWhiteView blackWhiteView = new BlackWhiteView();
			BlackWhiteModel blackWhiteModel = new BlackWhiteModel(model.getImage(), bwThreshold);

			new BlackWhiteController(blackWhiteView, blackWhiteModel);
		} else if (event.getSource().equals(view.getMenuItemFindBirds())) {
			view.removeImageOverlays();

			ImageProcessingEngine iPE = new ImageProcessingEngine(model.getImage(), bwThreshold);

			iPE.findBirds();

			int numOfBirds = iPE.getBirds().size();
			view.setImageDetails("There are " + numOfBirds + " birds in the picture!" + '\n'
			+ "The furthest removed bird is: " + iPE.getFurthestRemovedBird());

			birdNum = 1;
			for(Rectangle rect : iPE.getBirdBoxes()) {
				view.createRectangle(rect, birdNum++);
			}
			
		}
	}

}
