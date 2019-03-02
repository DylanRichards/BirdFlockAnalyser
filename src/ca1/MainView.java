package ca1;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @author Dylan Richards
 *
 */
public class MainView extends Stage {

	private MenuItem menuItemOpen, menuItemExit, menuItemBlackWhite, menuItemFindBirds;

	private Pane pane = new Pane();
	private ImageView imageView = new ImageView();
	private TextArea imageDetails = new TextArea();

	public MainView() {
		super.setTitle("Bird Flock Analyser");

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1200, 675);

		VBox menuBox = new VBox(getMenuBar());
		root.setTop(menuBox);

		pane.getChildren().add(imageView);
		root.setCenter(pane);
		
		imageDetails.setEditable(false);
		imageDetails.setMaxHeight(50);
		imageDetails.setMaxWidth(300);
		root.setRight(imageDetails);

		super.setScene(scene);
		super.show();
	}

	public void createRectangle(Rectangle rectangle, int birdId) {
		Text birdTag = new Text(String.valueOf(birdId));
		birdTag.setLayoutX(rectangle.getX());
		birdTag.setLayoutY(rectangle.getY());

		Rectangle innerRec = new Rectangle(rectangle.getX() + 3, rectangle.getY() + 3, rectangle.getWidth() - 6, rectangle.getHeight() - 6);

		Shape box = Shape.subtract(rectangle, innerRec);

		pane.getChildren().addAll(box, birdTag);
	}

	public void removeImageOverlays() {
		pane.getChildren().removeIf(node -> node instanceof Shape || node instanceof Text);
	}

	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(createFileMenu(), createImageMenu());
		return menuBar;
	}

	private Menu createFileMenu() {
		Menu menuFile = new Menu("File");

		menuItemOpen = new MenuItem("Open Image");
		menuItemExit = new MenuItem("Exit");

		menuFile.getItems().addAll(menuItemOpen, menuItemExit);
		return menuFile;
	}

	private Menu createImageMenu() {
		Menu menuImage = new Menu("Image");

		menuItemBlackWhite = new MenuItem("View Black and White");
		menuItemFindBirds = new MenuItem("Find Birds");

		menuImage.getItems().addAll(menuItemBlackWhite, menuItemFindBirds);
		return menuImage;
	}

	public void setImageView(Image image) {
		this.imageView.setImage(image);
	}
	
	public void setImageDetails(String details) {
		this.imageDetails.setText(details);
	}

	public MenuItem[] getAllMenuItems() {
		MenuItem[] menuItems = { menuItemOpen, menuItemExit, menuItemBlackWhite, menuItemFindBirds };
		return menuItems;
	}

	public MenuItem getMenuItemOpen() {
		return menuItemOpen;
	}

	public MenuItem getMenuItemExit() {
		return menuItemExit;
	}

	public MenuItem getMenuItemBlackWhite() {
		return menuItemBlackWhite;
	}

	public MenuItem getMenuItemFindBirds() {
		return menuItemFindBirds;
	}

}
