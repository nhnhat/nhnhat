package controller;

import exception.EmptyFileException;
import exception.FileReadException;
import exception.FileWriteException;
import model.FileHandler;
import model.TextDocument;
import model.TextNormalizer;
import view.ConsoleView;

/**
 * Controller: Coordinates the flow between Model and View.
 * Handles all exceptions and delegates display to ConsoleView.
 */
public class TextNormalizerController {

    private final ConsoleView view;
    private final FileHandler fileHandler;
    private final TextNormalizer normalizer;

    public TextNormalizerController() {
        this.view = new ConsoleView();
        this.fileHandler = new FileHandler();
        this.normalizer = new TextNormalizer();
    }

    /**
     * Entry point for running the text normalization process.
     *
     * @param inputFilePath  path to input.txt
     * @param outputFilePath path to output.txt
     */
    public void run(String inputFilePath, String outputFilePath) {
        view.showWelcome();

        TextDocument document = new TextDocument(inputFilePath, outputFilePath);

        // --- Step 1: Read the file ---
        view.showInfo("Reading file: " + inputFilePath);
        try {
            String rawContent = fileHandler.readFile(inputFilePath);
            document.setRawContent(rawContent);
            view.showSuccess("File read successfully.");
            view.showOriginalText(rawContent);

        } catch (FileReadException e) {
            view.showError("Failed to read input file.");
            view.showError("Reason: " + e.getMessage());
            return; // Cannot proceed without input
        } catch (EmptyFileException e) {
            view.showWarning("Input file is empty: " + e.getMessage());
            view.showWarning("Nothing to normalize. Exiting.");
            return;
        }

        // --- Step 2: Normalize the text ---
        view.showSeparator();
        view.showInfo("Normalizing text...");
        try {
            String normalized = normalizer.normalize(document.getRawContent());
            document.setNormalizedContent(normalized);
            view.showSuccess("Text normalized successfully.");
            view.showNormalizedText(normalized);

        } catch (Exception e) {
            // Catch unexpected runtime errors during normalization
            view.showError("An unexpected error occurred during normalization.");
            view.showError("Reason: " + e.getMessage());
            return;
        }

        // --- Step 3: Write the result ---
        view.showSeparator();
        view.showInfo("Writing normalized text to: " + outputFilePath);
        try {
            fileHandler.writeFile(outputFilePath, document.getNormalizedContent());
            view.showSuccess("Output written to: " + outputFilePath);

        } catch (FileWriteException e) {
            view.showError("Failed to write output file.");
            view.showError("Reason: " + e.getMessage());
            return;
        }

        view.showDone();
    }
}
