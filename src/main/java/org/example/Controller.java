package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;

import java.util.Locale;
import java.util.Random;

public class Controller {
    private Logic logic;
    @FXML private Button decodeButton;
    @FXML private TextField encodedTextInputField;
    @FXML private ComboBox<String> decipherAlgorithmComboBox;
    @FXML private ComboBox<Integer> keyComboBox;
    @FXML private TextArea decodedTextOutput;

    @FXML private TextField plainTextInputField;
    @FXML private Button encodeButton;
    @FXML private TextArea encryptedTextOutput;


    public Controller(Logic logic) {
        this.logic=logic;
    }

    @FXML
    private void initialize() {
        Locale.setDefault(Locale.ENGLISH);
        decipherAlgorithmComboBox.getItems().addAll("Chi-Square Comparison  (reliability)", "Frequency Analysis  (efficiency)");
        for (int i=1; i<26; i++){
            keyComboBox.getItems().addAll(i);
        }


    }


    @FXML
    private void handleDecode(){
        decodedTextOutput.clear();
        String encodedText = encodedTextInputField.getText();
        String algorithm = decipherAlgorithmComboBox.getValue();

        if (algorithm == null || encodedText == null || encodedText.isEmpty()) {
            decodedTextOutput.setText("Please provide input and select an algorithm.");
            return;
        }
        try{
        String decodedText = switch (algorithm) {
            case "Chi-Square Comparison  (reliability)" -> logic.decipherAlgorithmChiComparison(encodedText);
            case "Frequency Analysis  (efficiency)" -> logic.decipherAlgorithmFrequencyAnalysis(encodedText);
            default -> "Invalid algorithm selected.";
        };
        decodedTextOutput.setText(decodedText);}
        catch (Exception e){
            decodedTextOutput.setText("ERROR!!! "+e.getMessage()+"\nPossibly the message is too short");
        }

    }

    @FXML
    private void handleEncode() {
        String plainText = plainTextInputField.getText();
        Integer key = keyComboBox.getValue();

        // If no key is selected, generate a random key between 1 and 25
        if (key == null) {
            Random random = new Random();
            key = random.nextInt(25) + 1; // Random key between 1 and 25
        }

        if (plainText == null || plainText.isEmpty()) {
            encryptedTextOutput.setText("Please provide plain text.");
            return;
        }

        String encryptedText = logic.shift(plainText, key);

        encryptedTextOutput.setText(encryptedText + "\nKey Used: " + key);
    }


}
