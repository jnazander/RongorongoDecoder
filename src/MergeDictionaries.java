import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Class that merges 2 dictionary files together, omitting duplicates
 */
public class MergeDictionaries {

    public static void main(String[] args) throws IOException {

        Scanner s;
        s = new Scanner(new File("src/vocabulary.txt"));
        ArrayList<String> vocabulary = new ArrayList<>();
        while (s.hasNext()) {
            vocabulary.add(s.next());
        }
        s.close();

        s = new Scanner(new File("src/vocabulary-2.txt"));
        ArrayList<String> vocabulary2 = new ArrayList<>();
        while (s.hasNext()) {
            vocabulary.add(s.next());
        }
        s.close();

        Set<String> vocabulary3 = new HashSet();
        vocabulary3.addAll(vocabulary);
        vocabulary3.addAll(vocabulary2);

        vocabulary = new ArrayList<>(vocabulary3);
        vocabulary.sort(Comparator.naturalOrder());

        BufferedWriter outputWriter;
        outputWriter = new BufferedWriter(new FileWriter("vocabulary-3.txt"));
        for (String aVocabulary : vocabulary) {
            outputWriter.write(aVocabulary);
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();

    }
}
