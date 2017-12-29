import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Decoder based on the assumption that Rongorongo is an Abugida/Alphasyllabic
 */
public class AbugidaDictionaryDecoder {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Starting abugida brute force");

        Scanner s;
        s = new Scanner(new File("src/consonants.txt"));
        ArrayList<String> consonants = new ArrayList<>();
        while (s.hasNext()) {
            consonants.add(s.next());
        }
        s.close();

        s = new Scanner(new File("src/vowels.txt"));
        ArrayList<String> vowels = new ArrayList<>();
        while (s.hasNext()) {
            vowels.add(s.next());
        }
        s.close();

        s = new Scanner(new File("src/vocabulary.txt"));
        ArrayList<String> vocabulary = new ArrayList<>();
        while (s.hasNext()) {
            vocabulary.add(s.next());
        }
        s.close();

        String inputGlyphs = "A1B1 maBamaBa";
        int minDictionaryWords = 2;

        int numberConsonantsInPhrase = countUppercaseLetters(inputGlyphs);
        int numberVowelsInPhrase = countIntegers(inputGlyphs);

        List<List<String>> consonantCombinations = generateCombinations(numberConsonantsInPhrase, consonants);
        List<List<String>> vowelCombinations = generateCombinations(numberVowelsInPhrase, vowels);

        System.out.println("consonantCombinations: " + consonantCombinations.size());
        System.out.println("vowelCombinations: " + vowelCombinations.size());

        int counter = 0;
        Map<String, Integer> sentenceDictionaryWordCounts = new HashMap<>();
        for (List<String> consonantCombination : consonantCombinations) {
            for (List<String> vowelCombination : vowelCombinations) {

                String sentence = inputGlyphs;
                for (int i = 0; i < numberConsonantsInPhrase; i++) {
                    sentence = sentence.replace(getCharForNumber(i + 1), consonantCombination.get(i));
                }
                for (int j = 0; j < numberVowelsInPhrase; j++) {
                    sentence = sentence.replace(String.valueOf(j + 1), vowelCombination.get(j));
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

    private static int countUppercaseLetters(String input) {
        List<Character> foundLetters = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char o = input.charAt(i);
            if (Character.isLetter(o) && Character.isUpperCase(o) && !foundLetters.contains(o)) {
                count++;
                foundLetters.add(o);
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

    private static String getCharForNumber(int i) {
        return String.valueOf((char)(i + 64));
    }
}
