package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.sound.sampled.Line;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.jfoenix.svg.SVGGlyphLoader.clear;

public class AboutPageController { //implements Initializable, Data{

    volatile boolean isshowing;

    Timer atime;

    // AboutPageController()     {       isshowing = false;    }

    public void setShowing(boolean in)
    {
        isshowing = in;
    }

    public boolean getShowing()
    {
        return isshowing;
    }

    @FXML
    private Main mainController;

    @FXML
    public BorderPane aboutPane;

    @FXML
    public void someAction() throws IOException, InterruptedException
    {
        doTimer();
    }

    public void doTimer()
    {
        System.out.println("   1   ");
        atime.cancel();
        System.out.println("   2   ");
        startTimer();
    }

    public void startTimer()
    {
        System.out.println("    I suck a lot.    ");
        atime = new Timer();
        atime.schedule(AndrewTimer.restoreNavScreen(atime, true), AndrewTimer.getDelay() * 1000);
    }

    public void setMainController(Main in){
        mainController = in;
    }

}