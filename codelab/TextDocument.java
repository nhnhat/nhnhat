package model;

/**
 * Model: Represents the text document with raw content and normalized content.
 */
public class TextDocument {
    private String rawContent;
    private String normalizedContent;
    private String inputFilePath;
    private String outputFilePath;

    public TextDocument(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getNormalizedContent() {
        return normalizedContent;
    }

    public void setNormalizedContent(String normalizedContent) {
        this.normalizedContent = normalizedContent;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }
}
