package com.project.textprocessingfx;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexTool {

    public static VBox createRegexPane() {
        VBox layout = new VBox(10); // Layout with vertical spacing

        // Input fields
        TextArea inputText = new TextArea();
        inputText.setPromptText("Enter text here...");
        TextField regexField = new TextField();
        regexField.setPromptText("Enter regex pattern...");
        TextField replaceField = new TextField();
        replaceField.setPromptText("Enter replacement text...");

        // Buttons
        Button searchButton = new Button("Search");
        Button matchButton = new Button("Match");
        Button replaceButton = new Button("Replace");

        // Results display
        Text resultText = new Text();

        // Button Actions
        searchButton.setOnAction(e -> {
            try {
                String text = inputText.getText();
                String pattern = regexField.getText();
                Pattern compiledPattern = Pattern.compile(pattern);
                Matcher matcher = compiledPattern.matcher(text);

                StringBuilder matches = new StringBuilder("Matches Found:\n");
                while (matcher.find()) {
                    matches.append("Match: ").append(matcher.group())
                            .append(" at ").append(matcher.start()).append("\n");
                }

                if (matches.toString().equals("Matches Found:\n")) {
                    matches.append("No matches found.");
                }

                resultText.setText(matches.toString());
            } catch (PatternSyntaxException ex) {
                resultText.setText("Invalid Regex Pattern: " + ex.getMessage());
            }
        });

        matchButton.setOnAction(e -> {
            try {
                String text = inputText.getText();
                String pattern = regexField.getText();
                boolean isMatch = Pattern.matches(pattern, text);
                resultText.setText("Exact Match: " + isMatch);
            } catch (PatternSyntaxException ex) {
                resultText.setText("Invalid Regex Pattern: " + ex.getMessage());
            }
        });

        replaceButton.setOnAction(e -> {
            try {
                String text = inputText.getText();
                String pattern = regexField.getText();
                String replacement = replaceField.getText();

                Pattern compiledPattern = Pattern.compile(pattern);
                Matcher matcher = compiledPattern.matcher(text);

                String replacedText = matcher.replaceAll(replacement);
                resultText.setText("Replaced Text:\n" + replacedText);
            } catch (PatternSyntaxException ex) {
                resultText.setText("Invalid Regex Pattern: " + ex.getMessage());
            }
        });

        // Layout configuration
        layout.getChildren().addAll(
                new Label("Text Input:"), inputText,
                new Label("Regex Pattern:"), regexField,
                new Label("Replacement Text:"), replaceField,
                searchButton, matchButton, replaceButton,
                new Label("Results:"), resultText
        );

        return layout;
    }
}
