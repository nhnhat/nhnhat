package model;

/**
 * Model: Contains all business logic for normalizing text.
 *
 * Normalization rules:
 *  1. No blank lines between lines - merge into one paragraph.
 *  2. Only one space between words.
 *  3. Only one space after comma (,), dot (.) and colon (:).
 *  4. First character of word after dot is Uppercase; other words are lowercase.
 *  5. No spaces before/after content inside quotes ("...").
 *  6. First character of the first word of the whole text is Uppercase.
 *  7. No space between comma/dot and the word before it.
 *  8. Must have dot at the end of text.
 */
public class TextNormalizer {

    /**
     * Normalize the given raw text and return the normalized version.
     *
     * @param rawText the original, un-normalized text
     * @return normalized text
     */
    public String normalize(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return "";
        }

        // Step 1: Merge all lines into one, removing blank lines
        String merged = mergeLines(rawText);

        // Step 2: Collapse multiple spaces into one
        merged = merged.replaceAll("[ \\t]+", " ").trim();

        // Step 3: Remove spaces before , . : and ensure exactly one space after them
        merged = normalizePunctuationSpacing(merged);

        // Step 4: Lowercase entire text (content inside quotes preserved)
        merged = lowercaseText(merged);

        // Step 5: Capitalize first letter after each dot
        merged = capitalizeAfterDot(merged);

        // Step 6: Remove spaces inside quotes (trim just inside the quotes)
        merged = normalizeQuotes(merged);

        // Step 7: Capitalize the very first character
        merged = capitalizeFirstChar(merged);

        // Step 8: Ensure text ends with a dot
        merged = ensureEndWithDot(merged);

        return merged;
    }

    /**
     * Step 1: Merge multiple lines into a single paragraph, stripping blank lines.
     * Non-empty lines are joined with a single space.
     */
    private String mergeLines(String text) {
        String[] lines = text.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(trimmed);
            }
        }
        return sb.toString();
    }

    /**
     * Step 3: Remove spaces before comma, dot, colon;
     * ensure exactly one space after them when followed by non-space.
     */
    private String normalizePunctuationSpacing(String text) {
        // Remove space(s) before , . :
        text = text.replaceAll("\\s+([,\\.:])", "$1");

        // Ensure exactly one space after , and : when followed by non-space
        text = text.replaceAll("([,:])(\\S)", "$1 $2");

        // Ensure exactly one space after . when followed by a letter or digit
        text = text.replaceAll("(\\.)(\\S)", "$1 $2");

        return text;
    }

    /**
     * Step 4: Lowercase everything except content inside quotes (preserve quoted text).
     */
    private String lowercaseText(String text) {
        StringBuilder sb = new StringBuilder();
        boolean inQuote = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '"') {
                inQuote = !inQuote;
                sb.append(c);
            } else if (inQuote) {
                // Preserve original case inside quotes
                sb.append(c);
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    /**
     * Step 5: Capitalize the first letter of the word following each dot (outside quotes).
     */
    private String capitalizeAfterDot(String text) {
        char[] chars = text.toCharArray();
        boolean inQuote = false;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '"') {
                inQuote = !inQuote;
            }
            if (!inQuote && chars[i] == '.') {
                // Find the next letter after this dot
                int j = i + 1;
                while (j < chars.length && chars[j] == ' ') {
                    j++;
                }
                if (j < chars.length && Character.isLetter(chars[j])) {
                    chars[j] = Character.toUpperCase(chars[j]);
                }
            }
        }
        return new String(chars);
    }

    /**
     * Step 6: Remove spaces immediately inside opening and closing quotes.
     * e.g.  the " second row " -> the "second row"
     */
    private String normalizeQuotes(String text) {
        StringBuilder sb = new StringBuilder(text);
        boolean inQuote = false;
        int i = 0;
        while (i < sb.length()) {
            char c = sb.charAt(i);
            if (c == '"') {
                if (!inQuote) {
                    // Opening quote: delete spaces immediately after it
                    inQuote = true;
                    i++;
                    while (i < sb.length() && sb.charAt(i) == ' ') {
                        sb.deleteCharAt(i);
                        // do not increment i since chars shifted left
                    }
                } else {
                    // Closing quote: delete spaces immediately before it
                    inQuote = false;
                    int j = i - 1;
                    while (j >= 0 && sb.charAt(j) == ' ') {
                        sb.deleteCharAt(j);
                        i--;
                        j--;
                    }
                    i++;
                }
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * Step 7: Capitalize the very first character of the text.
     */
    private String capitalizeFirstChar(String text) {
        if (text.isEmpty()) return text;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                return text.substring(0, i)
                        + Character.toUpperCase(text.charAt(i))
                        + text.substring(i + 1);
            }
        }
        return text;
    }

    /**
     * Step 8: Ensure text ends with a dot. If the last non-space char is not a dot, append one.
     */
    private String ensureEndWithDot(String text) {
        String trimmed = text.stripTrailing();
        if (!trimmed.endsWith(".")) {
            return trimmed + ".";
        }
        return trimmed;
    }
}
