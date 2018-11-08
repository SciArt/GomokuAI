package view;

import java.io.FileInputStream;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
	private Group tmp;
	private Image image;
	public ImageView imageView;
	private int xPosition;
	private int yPosition;
	
	
	public Sprite(int x, int y, String path, Group hook) {
		
		xPosition = x;
		yPosition = y;
		image = new Image(path);
		imageView = new ImageView(image);
		tmp = new Group(imageView); 
		hook.getChildren().add(tmp);
		//imageView.relocate(400, 400);
		
	}
	
	public ImageView getImageView () {
		return imageView;
	}

}
