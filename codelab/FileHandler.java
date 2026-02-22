package model;

import exception.FileReadException;
import exception.FileWriteException;
import exception.EmptyFileException;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Model: Responsible for reading from and writing to files.
 */
public class FileHandler {

    /**
     * Read all content from the given file path.
     *
     * @param filePath path to the input file
     * @return content of the file as a String
     * @throws FileReadException  if file not found or cannot be read
     * @throws EmptyFileException if file exists but is empty
     */
    public String readFile(String filePath) throws FileReadException, EmptyFileException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileReadException("File not found: " + filePath);
        }
        if (!file.isFile()) {
            throw new FileReadException("Path is not a regular file: " + filePath);
        }
        if (!file.canRead()) {
            throw new FileReadException("Cannot read file (permission denied): " + filePath);
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (FileNotFoundException e) {
            throw new FileReadException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new FileReadException("Error reading file: " + filePath + " - " + e.getMessage(), e);
        }

        String result = content.toString().trim();
        if (result.isEmpty()) {
            throw new EmptyFileException("File is empty: " + filePath);
        }

        return result;
    }

    /**
     * Write text content to the given file path.
     *
     * @param filePath path to the output file
     * @param content  content to write
     * @throws FileWriteException if cannot write to file
     */
    public void writeFile(String filePath, String content) throws FileWriteException {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new FileWriteException("Cannot create directory: " + parentDir.getAbsolutePath());
            }
        }

        // Check write permission if file already exists
        if (file.exists() && !file.canWrite()) {
            throw new FileWriteException("Cannot write to file (permission denied): " + filePath);
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            writer.write(content);
            writer.flush();

        } catch (IOException e) {
            throw new FileWriteException("Error writing to file: " + filePath + " - " + e.getMessage(), e);
        }
    }
}
