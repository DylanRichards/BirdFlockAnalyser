package ca1;

/**
 * 
 * @author Dylan Richards
 *
 */
public class BlackWhiteController {

	public BlackWhiteController(BlackWhiteView view, BlackWhiteModel model) {
		view.setImageView(model.getBlackWhiteImage());
	}

}
