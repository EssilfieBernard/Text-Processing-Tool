package com.project.textprocessingfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextProcessingController implements Initializable {
    private DataManager dataManager;
    private ObservableList<TextEntry> historyEntries;

    @FXML private TextField regexTextField;
    @FXML private TextField replacementTextField;
    @FXML private TextArea inputTextField;
    @FXML private TextArea resultTextArea;

    @FXML private ListView<TextEntry> historyListView;
    @FXML private ComboBox<String> patternHistoryComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize data manager and observable lists
        dataManager = new DataManager();
        historyEntries = FXCollections.observableArrayList();

        // Set up history list view
        historyListView.setItems(historyEntries);
        historyListView.setCellFactory(lv -> new ListCell<TextEntry>() {
            @Override
            protected void updateItem(TextEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText("Pattern: " + entry.getRegexPattern() +
                            "\nOriginal: " + truncateText(entry.getOriginalText(), 50) +
                            "\nProcessed: " + truncateText(entry.getProcessedText(), 50));
                }
            }
        });

        // Set up pattern history combo box
        updatePatternHistory();

        // Add selection listener to history list view
        historyListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadHistoryEntry(newVal);
            }
        });

        // Add selection listener to pattern history combo box
        patternHistoryComboBox.setOnAction(e -> {
            String selectedPattern = patternHistoryComboBox.getSelectionModel().getSelectedItem();
            if (selectedPattern != null) {
                regexTextField.setText(selectedPattern);
            }
        });
    }

    private void updatePatternHistory() {
        patternHistoryComboBox.setItems(FXCollections.observableArrayList(dataManager.getUniquePatterns()));
    }

    private void loadHistoryEntry(TextEntry entry) {
        inputTextField.setText(entry.getOriginalText());
        regexTextField.setText(entry.getRegexPattern());
        resultTextArea.setText(entry.getProcessedText());
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    private void saveOperation(String operationType, String result) {
        TextEntry entry = dataManager.createEntry(
                inputTextField.getText(),
                result,
                regexTextField.getText()
        );
        historyEntries.add(0, entry); // Add to start of list
        updatePatternHistory();
    }

    @FXML
    public void handleReplaceButton(ActionEvent actionEvent) {
        String input = inputTextField.getText();
        String regexPattern = regexTextField.getText();
        String replacement = replacementTextField.getText();

        if (regexPattern.isEmpty() || input.isEmpty()) {
            resultTextArea.setText("Please enter both regex pattern and input text.");
            return;
        }

        try {
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(input);
            String replacedText = matcher.replaceAll(replacement);

            resultTextArea.setText("Replaced Text: " + replacedText);
            saveOperation("REPLACE", replacedText);

        } catch (PatternSyntaxException exception) {
            resultTextArea.setText("Invalid regex pattern: " + exception.getDescription());
        }
    }

    @FXML
    public void handleSearchButton(ActionEvent actionEvent) {
        String input = inputTextField.getText();
        String regexPattern = regexTextField.getText();

        if (regexPattern.isEmpty() || input.isEmpty()) {
            resultTextArea.setText("Please enter both regex pattern and input text.");
            return;
        }

        try {
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(input);

            StringBuilder matches = new StringBuilder("Matches found:\n");
            boolean found = false;
            while (matcher.find()) {
                matches.append("Match: ").append(matcher.group())
                        .append(" at position ").append(matcher.start()).append("\n");
                found = true;
            }

            String result = found ? matches.toString() : "No matches found";
            resultTextArea.setText(result);
            saveOperation("SEARCH", result);

        } catch (PatternSyntaxException exception) {
            resultTextArea.setText("Invalid regex pattern: " + exception.getDescription());
        }
    }

    @FXML
    public void handleMatchButton(ActionEvent actionEvent) {
        String input = inputTextField.getText();
        String regexPattern = regexTextField.getText();

        if (regexPattern.isEmpty() || input.isEmpty()) {
            resultTextArea.setText("Please enter both regex pattern and input text.");
            return;
        }

        try {
            boolean exactMatch = Pattern.matches(regexPattern, input);
            String result = "Exact match: " + exactMatch;
            resultTextArea.setText(result);
            saveOperation("MATCH", result);

        } catch (PatternSyntaxException exception) {
            resultTextArea.setText("Invalid regex pattern: " + exception.getDescription());
        }
    }

    @FXML
    public void handleClearButton(ActionEvent actionEvent) {
        inputTextField.clear();
        regexTextField.clear();
        replacementTextField.clear();
        resultTextArea.clear();
        historyListView.getSelectionModel().clearSelection();
    }

    @FXML
    public void handleDeleteHistoryEntry(ActionEvent actionEvent) {
        TextEntry selectedEntry = historyListView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            dataManager.deleteEntry(selectedEntry.getId());
            historyEntries.remove(selectedEntry);
            updatePatternHistory();
        }
    }
}