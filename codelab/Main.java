import controller.TextNormalizerController;

/**
 * Main class - entry point of the Text Normalizer program.
 *
 * Usage:
 *   java Main                         -> uses default input.txt and output.txt
 *   java Main input.txt output.txt    -> uses specified file paths
 */
public class Main {

    private static final String DEFAULT_INPUT  = "input.txt";
    private static final String DEFAULT_OUTPUT = "output.txt";

    public static void main(String[] args) {
        String inputPath  = DEFAULT_INPUT;
        String outputPath = DEFAULT_OUTPUT;

        if (args.length >= 2) {
            inputPath  = args[0];
            outputPath = args[1];
        } else if (args.length == 1) {
            inputPath = args[0];
        }

        TextNormalizerController controller = new TextNormalizerController();
        controller.run(inputPath, outputPath);
    }
}
