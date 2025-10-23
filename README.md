# Caesar Cipher Decipher Application

This project is a JavaFX application designed to encode and decode text using the Caesar cipher. It provides two deciphering algorithms: Chi-Square Comparison for reliability and Frequency Analysis for efficiency. The application features a graphical user interface (GUI) for user interaction, allowing encoding with a specified or random key and decoding of encrypted text.

## Project Structure

```
Caesar-Decipher/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── org/example/
│   │   │   │   ├── Main.java              # Entry point for the JavaFX application
│   │   │   │   ├── Controller.java        # Handles UI interactions and logic
│   │   │   │   ├── Logic.java            # Core logic for encoding/decoding
│   │   │   │   └── Distribution.java      # Data model for letter frequency
│   │   ├── resources/
│   │   │   ├── distributions.json         # Letter frequency data for English
│   │   │   └── org/example/ui.fxml        # JavaFX UI layout
```

## Prerequisites

- Java 17 or higher (with JavaFX SDK)
- Required libraries: `javafx-controls`, `javafx-fxml`, `jackson-databind`
- Maven or Gradle for dependency management (optional)

Install dependencies using Maven (example `pom.xml` snippet):
```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
</dependencies>
```

## Usage

1. **Prepare the Environment**:
   - Ensure the `distributions.json` file is placed in `src/main/resources/` for letter frequency data.
   - Configure your IDE or build tool to include JavaFX modules (e.g., `--module-path` and `--add-modules` for JavaFX).

2. **Run the Application**:
   - Run `Main.java` to launch the JavaFX application.
   - The GUI window will open with options for encoding and decoding text.

3. **Encoding**:
   - Enter plain text in the "Input Plain Text" field.
   - Select a key (1-25) from the dropdown or skip for a random key.
   - Click the "Encode" button to generate encrypted text, displayed in the "Encrypted Text" area along with the key used.

4. **Decoding**:
   - Enter encrypted text in the "Input Encoded Text" field.
   - Select an algorithm: "Chi-Square Comparison (reliability)" or "Frequency Analysis (efficiency)".
   - Click the "Decode" button to display the decoded text and inferred key in the "Decoded Text" area.
   - Note: Short messages may cause errors due to insufficient data for analysis.

## Features

- **Encoding**:
  - Shifts letters in the input text by a specified or random key (1-25).
  - Preserves case (uppercase/lowercase) and non-letter characters.

- **Decoding**:
  - **Chi-Square Comparison**: Uses statistical comparison of letter frequencies to expected English frequencies to find the most likely key.
  - **Frequency Analysis**: Identifies the key by matching the top 6 frequent letters in the encoded text to common English letters (e.g., 'e', 't', 'a').

- **GUI**:
  - Built with JavaFX, defined in `ui.fxml`.
  - Includes text fields for input, dropdowns for algorithm and key selection, and text areas for output.
  - User-friendly design with clear labels and error messages.

## Implementation Details

- **Main.java**: Initializes the JavaFX application, loads the FXML UI, and sets up the controller.
- **Controller.java**: Manages user interactions, handles encoding/decoding logic, and updates the UI.
- **Logic.java**: Contains the core logic for Caesar cipher operations, including:
  - Encoding via character shifting.
  - Decoding using Chi-Square or Frequency Analysis.
  - Loading letter frequency data from `distributions.json`.
- **Distribution.java**: Defines the data model for letter frequency JSON.
- **distributions.json**: Contains English letter frequencies used for decoding.
- **ui.fxml**: Defines the JavaFX layout with input fields, buttons, and output areas.

## Notes

- The application assumes input text contains English letters for accurate decoding.
- The `distributions.json` file is critical for decoding algorithms and must be present in the resources directory.
- Error handling is implemented for invalid inputs (e.g., empty text, short messages).
- The Chi-Square method is more reliable for longer texts, while Frequency Analysis is faster but may be less accurate for short texts.