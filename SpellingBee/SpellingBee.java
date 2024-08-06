import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellingBee {
    private static boolean isWord(String input) throws FileNotFoundException {
        Scanner dictionary = new Scanner(new File("dictionary.txt"));
        while (dictionary.hasNextLine()) {
            if (dictionary.nextLine().equalsIgnoreCase(input)) {
                dictionary.close();
                return true;
            }
        }
        dictionary.close();
        return false;
    }

    private static ArrayList<String> getPotentialWords(String[][] letters) throws FileNotFoundException {
        ArrayList<String> potentialWords = new ArrayList<>();
        Scanner dictionary = new Scanner(new File("dictionary.txt"));
        while (dictionary.hasNextLine()) {
            String word = dictionary.nextLine().toUpperCase();
            if (wordWorks(word, letters)) {
                potentialWords.add(word);
            }
        }
        dictionary.close();
        return potentialWords;
    }

    private static boolean wordWorks(String word, String[][] letters) {
        if (word.length() < 3) {
            return false;
        }

        ArrayList<String> allLetters = new ArrayList<>(List.of(Arrays.stream(letters).flatMap(Arrays::stream).toArray(String[]::new)));
        for (char letter : word.toCharArray()) {
            if (!allLetters.contains(String.valueOf(letter))) {
                return false;
            }
        }
        for (int i = 0; i < word.length() - 1; i++) {
            if (groupOf(letters, word.substring(i, i + 1)) == groupOf(letters, word.substring(i + 1, i + 2))) {
                return false;
            }
        }
        return true;
    }

    private static int groupOf(String[][] letters, String letter) {
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters[0].length; j++) {
                if (letters[i][j].equals(letter)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static ArrayList<String> filter(ArrayList<String> potentialWords, String startChar) {
        ArrayList<String> output = new ArrayList<>();
        for (String word : potentialWords) {
            if (word.substring(0, 1).equals(startChar)) {
                output.add(word);
            }
        }
        return output;
    }

    private static ArrayList<ArrayList<String>> getAllAnswers(ArrayList<String> potentialWords) {
        ArrayList<ArrayList<String>> allAnswers = new ArrayList<>();
        for (String word : potentialWords) {
            ArrayList<String> filtered = filter(potentialWords, word.substring(word.length() - 1));
            for (String word2 : filtered) {
                ArrayList<String> filtered2 = filter(filtered, word2.substring(word2.length() - 1));
                for (String word3 : filtered2) {
                    ArrayList<String> filtered3 = filter(filtered2, word3.substring(word3.length() - 1));
                    if (filtered3.size() > 0) {
                        for (String word4 : filtered3) {
                            ArrayList<String> filtered4 = filter(filtered3, word4.substring(word4.length() - 1));
                            if (filtered.size() > 0) {
                                for (String word5 : filtered4) {
                                    ArrayList<String> filtered5 = filter(filtered4, word5.substring(word5.length() - 1));
                                    if (filtered5.size() > 0) {
                                        for (String word6 : filtered5) {
                                            allAnswers.add(new ArrayList<>(List.of(word, word2, word3, word4, word5, word6)));
                                        }
                                    } else {
                                        allAnswers.add(new ArrayList<>(List.of(word, word2, word3, word4, word5)));
                                    }
                                }
                            } else {
                                allAnswers.add(new ArrayList<>(List.of(word, word2, word3, word4)));
                            }
                        }
                    } else {
                        allAnswers.add(new ArrayList<>(List.of(word, word2, word3)));
                    }
                }
            }
        }
        return allAnswers;
    }

    public static void bot(String... game) throws FileNotFoundException {
        if (game.length != 12) {
            throw new RuntimeException("Input must be exactly 12 letters");
        }

        String[][] letters = new String[4][];
        for (int i = 0; i < 4; i++) {
            letters[i] = new String[3];
            for (int j = 0; j < 3; j++) {
                letters[i][j] = game[3 * i + j];
            }
        }

        ArrayList<String> potentialWords = getPotentialWords(letters);
        TreeSet<String> used = new TreeSet<>();
        ArrayList<String> answer = new ArrayList<>(10);

        ArrayList<ArrayList<String>> allAnswers = getAllAnswers(potentialWords);
        System.out.println(allAnswers);
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(bot("C", "Z", "I", "Y", "N", "H", "S", "L", "W", "O", "F", "A").size());
        bot("C", "Z", "I", "Y", "N", "H", "S", "L", "W", "O", "F", "A");

        /*String[] game = new String[]{"C", "Z", "I", "Y", "N", "H", "S", "L", "W", "O", "F", "A"};
        String[][] letters = new String[4][];
        for (int i = 0; i < 4; i++) {
            letters[i] = new String[3];
            for (int j = 0; j < 3; j++) {
                letters[i][j] = game[3 * i + j];
            }
        }
        System.out.println(groupOf(letters, "A"));*/
    }
}
