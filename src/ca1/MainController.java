package ca1;

import java.util.Set;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

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
			birdNum = 1;

			ImageProcessingEngine iPE = new ImageProcessingEngine(model.getImage(), bwThreshold);

			iPE.findBirds();

			Set<Integer> birds = iPE.getBirds();
			int numOfBirds = birds.size();
			System.out.println("There are " + numOfBirds + " birds in the picture!");

			birds.forEach(i -> view.createRectangle(i % iPE.getImgWidth(), i / iPE.getImgWidth(), 100, 100, birdNum++));
		}
	}

}
