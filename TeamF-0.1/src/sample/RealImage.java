package sample;

import javafx.embed.swing.SwingFXUtils;

import javafx.scene.image.Image;

public class RealImage implements IImage {
    private String fileName;
    public javafx.scene.image.Image map;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk(fileName);
    }

    public void display() {
        //System.out.println("Displaying: " + fileName);
        //Data.data.map.setImage(map);
    }

    private void loadFromDisk(String fileName) {
        this.map = new Image(getClass().getResourceAsStream("/sample/UI/Icons/" + fileName));
    }
}
