Text Processing Application
A JavaFX-based application that provides powerful text processing capabilities using regular expressions. The application allows users to search, match, and replace text patterns while maintaining a history of operations.
Features

Regular Expression Operations

Pattern searching with position tracking
Full text pattern matching
Pattern-based text replacement
Support for complex regex patterns


History Management

Track all text processing operations
Save and reuse previous patterns
View detailed operation history
Delete individual history entries


Modern User Interface

Clean, intuitive design
Real-time results display
Pattern history dropdown
Operation history panel
Color-coded action buttons



Project Structure
com.project.textprocessingfx/
├── TextProcessingController.java  // Handles UI interactions and processing logic
├── TextEntry.java                 // Data model for text operations
├── DataManager.java              // Manages data and operations
└── text-processing-view.fxml     // JavaFX UI layout
Requirements

Java SE Development Kit 8 or higher
JavaFX (included in JDK 8-10, separate module for JDK 11+)

Installation

Clone the repository:

git clone https://github.com/EssilfieBernard/Text-Processing-Tool

Import the project into your IDE of choice (Eclipse, IntelliJ IDEA, etc.)
Ensure JavaFX is properly configured in your development environment
Build and run the application

Usage
Basic Operations

Search for Patterns

Enter your text in the input area
Type your regex pattern
Click the "Search" button
View matches in the results area


Replace Text

Enter your text in the input area
Type your regex pattern
Enter replacement text
Click the "Replace" button
View the transformed text in results


Pattern Matching

Enter your text in the input area
Type your regex pattern
Click the "Match" button
See if the entire text matches the pattern



Working with History

Use the pattern history dropdown to select previous patterns
View operation history in the right panel
Click on history entries to load previous operations
Delete unwanted history entries using the "Delete Selected" button

Components Overview
TextEntry
Represents a single text processing operation:
public class TextEntry {
private String id;                  // Unique identifier
private String originalText;        // Input text
private String processedText;       // Result text
private String regexPattern;        // Used pattern
private Date timestamp;            // Operation timestamp
}
DataManager
Handles data operations using multiple collection types:
public class DataManager {
private Map<String, TextEntry> entriesMap;     // Quick lookup
private Set<String> uniquePatterns;            // Pattern tracking
private List<TextEntry> entriesList;           // Ordered entries
}
TextProcessingController
Manages UI interactions and processing operations:

Regular expression operations
History management
Event handling
Results display

UI Components
Main Content Area

Text input area
Regular expression input
Replacement text input
Action buttons (Search, Match, Replace, Clear)
Results display area

History Panel

Operation history list
Pattern history dropdown
Delete functionality