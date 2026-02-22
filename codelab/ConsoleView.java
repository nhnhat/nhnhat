package view;

/**
 * View: Responsible for all console output (messages, errors, results).
 */
public class ConsoleView {

    public void showWelcome() {
        System.out.println("==============================================");
        System.out.println("        TEXT NORMALIZER APPLICATION          ");
        System.out.println("==============================================");
    }

    public void showInfo(String message) {
        System.out.println("[INFO]  " + message);
    }

    public void showSuccess(String message) {
        System.out.println("[OK]    " + message);
    }

    public void showError(String message) {
        System.err.println("[ERROR] " + message);
    }

    public void showWarning(String message) {
        System.out.println("[WARN]  " + message);
    }

    public void showSeparator() {
        System.out.println("----------------------------------------------");
    }

    public void showOriginalText(String text) {
        System.out.println("\n--- Original Text ---");
        System.out.println(text);
    }

    public void showNormalizedText(String text) {
        System.out.println("\n--- Normalized Text ---");
        System.out.println(text);
    }

    public void showDone() {
        System.out.println("\n==============================================");
        System.out.println("              PROCESS COMPLETED              ");
        System.out.println("==============================================");
    }
}
