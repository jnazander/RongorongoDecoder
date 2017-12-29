import java.io.*;
import java.util.*;

/**
 * Decoder based on the assumption that Rongorongo is a Syllabic writing system
 */
public class SyllabicDictionaryDecoder {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Starting brute force");

        Scanner s;
        s = new Scanner(new File("src/syllables.txt"));
        ArrayList<String> syllables = new ArrayList<>();
        while (s.hasNext()) {
            syllables.add(s.next());
        }
        s.close();

        s = new Scanner(new File("src/vocabulary.txt"));
        ArrayList<String> vocabulary = new ArrayList<>();
        while (s.hasNext()) {
            vocabulary.add(s.next());
        }
        s.close();

        String inputGlyphs = "k123";
        int minDictionaryWords = 1;

        int numberGlyphsInPhrase = countIntegers(inputGlyphs);
        List<List<String>> syllableCombinations = generateCombinations(numberGlyphsInPhrase, syllables);

        System.out.println("syllableCombinations: " + syllableCombinations.size());

        int counter = 0;
        Map<String, Integer> sentenceDictionaryWordCounts = new HashMap<>();
        for (List<String> combination : syllableCombinations) {
            String sentence = inputGlyphs;
            for (int i = 0; i < numberGlyphsInPhrase; i++) {
                sentence = sentence.replace(String.valueOf(i + 1), combination.get(i));
            }
            if (counter % 20000 == 0) {
                System.out.println("Sentence created: " + sentence + ", ");
            }

            String[] words = sentence.split(" ");
            int dictionaryWords = 0;
            for (String word : words) {
                if (vocabulary.contains(word)) {
                    dictionaryWords++;
                }
            }

            if (dictionaryWords >= minDictionaryWords) {
                sentenceDictionaryWordCounts.put(sentence, dictionaryWords);
            }

            counter++;
        }

        System.out.println("Finished. Found " + sentenceDictionaryWordCounts.size() + " matches: " + sentenceDictionaryWordCounts);
    }

    private static int countIntegers(String input) {
        List<Character> foundDigits = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char o = input.charAt(i);
            if (Character.isDigit(o) && !foundDigits.contains(o)) {
                count++;
                foundDigits.add(o);
            }
        }
        return count;
    }

    private static List<List<String>> generateCombinations(int arraySize, ArrayList<String> possibleValues) {
        List<List<String>> output = new ArrayList<>();
        int carry;
        int[] indices = new int[arraySize];
        do {
            List<String> permutation = new ArrayList<>();
            for (int index : indices) {
                String value = possibleValues.get(index);
                permutation.add(value);
            }
            //Make sure the permutations don't contain duplicates
            if (permutation.size() == new HashSet<>(permutation).size()) {
                output.add(permutation);
            }

            carry = 1;
            for (int i = indices.length - 1; i >= 0; i--) {
                if (carry == 0)
                    break;

                indices[i] += carry;
                carry = 0;

                if (indices[i] == possibleValues.size()) {
                    carry = 1;
                    indices[i] = 0;
                }
            }
        } while (carry != 1); // Call this method iteratively until a carry is left over
        return output;
    }
}
