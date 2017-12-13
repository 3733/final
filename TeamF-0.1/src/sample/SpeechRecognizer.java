package sample;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import com.jfoenix.controls.JFXTextField;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;



public class SpeechRecognizer {

    @FXML
    private JFXTextField destination;

    // Necessary
    private LiveSpeechRecognizer recognizer;

    // Logger
    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * This String contains the Result that is coming back from SpeechRecognizer
     */
    public String speechRecognitionResult;

    public String prevSpeechRecognitionResult;

    /**
     * A simple property to bind the current SpeechRecognitionResult
     */
    private StringProperty speechRecognitionResultProperty = new SimpleStringProperty("");


    private SimpleBooleanProperty ignoreSpeechRecognitionResults = new SimpleBooleanProperty(false);

    /**
     * Checks if the speech recognise is already running
     */
    private SimpleBooleanProperty speechRecognizerThreadRunning = new SimpleBooleanProperty(false);

    /**
     * Checks if the resources Thread is already running
     */
    private boolean resourcesThreadRunning;

    //---

    /**
     * This executor service is used in order the playerState events to be executed in an order
     */
    private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);

    //------------------------------------------------------------------------------------

    /**
     * Constructor
     */
    public SpeechRecognizer() {

        // Loading Message
        logger.log(Level.INFO, "Loading Speech Recognizer...\n");

        // Configuration
        Configuration configuration = new Configuration();

        // Load model from the jar
        configuration.setAcousticModelPath("TeamF-0.1/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("TeamF-0.1/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

        //this can detect what you have said
        //        configuration.setLanguageModelPath("TeamF-0.1/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        //        Grammar
        configuration.setGrammarPath("TeamF-0.1/grammars");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);

        try {
            recognizer = new LiveSpeechRecognizer(configuration);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        // Start recognition process pruning previously cached data.
        // recognizer.startRecognition(true);

        //Check if resources available
        startResourcesThread();
    }


    /**
     * Starts the Speech Recognition Thread
     */
    public synchronized void startSpeechRecognition() {

        //Check lock
        if (speechRecognizerThreadRunning.get())
            logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
        else
            //Submit to ExecutorService
            eventsExecutorService.submit(() -> {

                //locks
                Platform.runLater(() -> {
                    speechRecognizerThreadRunning.set(true);
                    ignoreSpeechRecognitionResults.set(false);
                });

                //Start Recognition
                recognizer.startRecognition(true);

                //Information
                logger.log(Level.INFO, "You can start to speak...\n");

                try {
                    while (speechRecognizerThreadRunning.get()) {
						/*
						 * This method will return when the end of speech is reached. Note that the end pointer will determine the end of speech.
						 */

                        SpeechResult speechResult = recognizer.getResult();


                        //Check if we ignore the speech recognition results
                        if (!ignoreSpeechRecognitionResults.get()) {

                            //Check the result
                            if (speechResult == null)
                                logger.log(Level.INFO, "I can't understand what you said.\n");
                            else {

                                //Get the hypothesis
                                //if(speechResult.getHypothesis() != prevSpeechRecognitionResult) {
                                    speechRecognitionResult = speechResult.getHypothesis();
                                    //prevSpeechRecognitionResult = speechRecognitionResult;
                                /*} else {
                                    speechRecognitionResult = null;
                                }*/


                                //You said?
                                System.out.println("You said: [" + speechRecognitionResult + "]\n");

                                Platform.runLater(() -> speechRecognitionResultProperty.set(speechRecognitionResult));

                                //Call the appropriate method

                                recognizer.stopRecognition();

                                //break;


                                //makeDecision(speechRecognitionResult, speechResult.getWords());

                            }
                        } else
                            logger.log(Level.INFO, "Ingoring Speech Recognition Results...");

                    }
                }  catch (IllegalStateException ey) {
                    //ey.printStackTrace();
                    //System.out.println("Warning from speech rec");
                    //Because that is the lack of the api..

                } catch (Exception ex) {
                    logger.log(Level.WARNING, null, ex);
                    Platform.runLater(() -> speechRecognizerThreadRunning.set(false));
                }


                speechRecognizerThreadRunning.set(false);
//                destination.textProperty().unbind();

                logger.log(Level.INFO, "SpeechThread has exited...");

            });
    }

    /**
     * Stops ignoring the results of SpeechRecognition
     */
    public synchronized void stopIgnoreSpeechRecognitionResults() {

        //Stop ignoring speech recognition results
        Platform.runLater(() -> ignoreSpeechRecognitionResults.set(false));
    }

    /**
     * Ignores the results of SpeechRecognition
     */
    public synchronized void ignoreSpeechRecognitionResults() {

        //Instead of stopping the speech recognition we are ignoring it's results
        Platform.runLater(() -> ignoreSpeechRecognitionResults.set(true));

    }



    /**
     * Starting a Thread that checks if the resources needed to the SpeechRecognition library are available
     */
    public void startResourcesThread() {

        //Check lock
        if (resourcesThreadRunning)
            logger.log(Level.INFO, "Resources Thread already running...\n");
        else
            //Submit to ExecutorService
            eventsExecutorService.submit(() -> {
                try {

                    //Lock
                    resourcesThreadRunning = true;

                    // Detect if the microphone is available
                    while (true) {

                        //Is the Microphone Available
                        if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
                            logger.log(Level.INFO, "Microphone is not available.\n");

                        // Sleep some period
                        Thread.sleep(350);
                    }

                } catch (InterruptedException ex) {
                    logger.log(Level.WARNING, null, ex);
                    resourcesThreadRunning = false;
                }
            });
    }

//    /**
//     * Takes a decision based on the given result
//     *
//     * @param speechWords
//     */
//    public void makeDecision(String speech , List<WordResult> speechWords) {
//
//        System.out.println(speech);
//
//    }

    public SimpleBooleanProperty ignoreSpeechRecognitionResultsProperty() {
        return ignoreSpeechRecognitionResults;
    }

    public SimpleBooleanProperty speechRecognizerThreadRunningProperty() {
        return speechRecognizerThreadRunning;
    }

    /**
     * @return the speechRecognitionResultProperty
     */
    public StringProperty getSpeechRecognitionResultProperty() {
        return speechRecognitionResultProperty;
    }
}