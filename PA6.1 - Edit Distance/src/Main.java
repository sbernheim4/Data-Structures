/**
 * @author Samuel Bernheim
 * This program works by taking in a .txt file of words and computes for each word a list of all of that words'
 * intermediate neighbors. This is stored in a map where the key is the word and the values is the list of its neighbors.
 * Then the user is prompted for two words, whose edit distance and path will be calculated and printed.
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // initiate the two words the user wants to compare up here so that wordOne var can be used to also determine
        // when the user wants to quit the program
        String wordOne;
        String wordTwo;

        // scanner for the console
        Scanner console = new Scanner(System.in);

        // load the file with the data
        File dataFile = loadFile(console);

        // create the HashSet and populate it with the words from the input file
        Set<String> dict = createDict(dataFile);

        // creates a map for all words which have a edit distance of 1
        Map<String, List<String>> distOfOne = populate(dict);

        System.out.println("Dictionary has " + dict.size() + " words.");

        do {
            // get the two words the user wants to compare
            System.out.println("Enter the two words with a space in between them. To exit the program type 'QUIT' ");
            wordOne = console.next();

            if (wordOne.equals("QUIT")){
                System.exit(0);
            }

            wordOne = wordOne.toLowerCase();
            wordTwo = console.next().toLowerCase();

            // if the words are of unequal length print No Solution
            // else if either word is not contained in the dictionary specify which word or both
            // and if either error is encountered print No Solution
            // otherwise continue with the algorithm to find the path and edit Distance

            if (wordOne.length() != wordTwo.length()) {
                System.out.println("No Solution");
            } else if (!dict.contains(wordOne) && !dict.contains(wordTwo)) {
                System.out.println("Neither word you entered is in the dictionary");
            } else if (!dict.contains(wordOne)) {
                System.out.println("The word " + wordOne + " is not in the dictionary");
            } else if (!dict.contains(wordTwo)) {
                System.out.println("The word " + wordTwo + " is not in the dictionary");
            } else {
                // compare the two words
                Map<String, String> pathDeterminer = compare(distOfOne, wordOne, wordTwo);
                // print the path
                if (pathDeterminer != null) {
                    printPath(pathDeterminer, wordOne, wordTwo);
                }
            }
        } while (true);
    }

    /**
     * asks the user for the file name and returns the file with that name
     * */
    public static File loadFile(Scanner console) {
        // get the file name from the user and return it
        System.out.println("Enter the name of the data file");
        String fileName = console.next();
        return new File(fileName);
    }

    /**
     * creates a local dictionary as a set based on the dictionary file the user has entered
     * */
    public static HashSet<String> createDict(File input) throws FileNotFoundException {
        // scanner for the inputFile
        Scanner inputScanner = new Scanner(input);
        // new TreeSet for the input data so that the input is organized
        HashSet<String> dict = new HashSet<String>();
        // populate the TreeSet with the words in the input file
        while (inputScanner.hasNextLine()) {
            dict.add(inputScanner.nextLine());
        }
        return dict;
    }

    /**
     * creates a map where each key is a word in the dictionary and the value is a list of that word's neighbors
     * */
    public static Map<String, List<String>> populate (Set<String> dict){
        char[] alphabet = {'a', 'b','c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Map<String, List<String>> neighbors = new TreeMap<String, List<String>>();

        Iterator<String> itr = dict.iterator();
        String testWord;
        while (itr.hasNext()) {
            List<String> values = new LinkedList<String>();
            String tempWord = itr.next(); // word that represents a key for the map
            for (int j = 0; j<tempWord.length(); j++){
                for (char temp : alphabet) {
                    // changing the first, last or any middle character of the word
                    if (j == 0) {
                        testWord = temp + tempWord.substring(1);
                    } else if (j == tempWord.length() - 1) {
                        testWord = tempWord.substring(0, tempWord.length() - 1) + temp;
                    } else {
                        testWord = tempWord.substring(0, j) + temp + tempWord.substring(j + 1);
                    }
                    // if the resulting word is in the dictionary file, and the new word is of the same length as the original word add it in
                    if (dict.contains(testWord) && tempWord.length() == testWord.length() && !testWord.equals(tempWord)) {
                        values.add(testWord);
                    }
                }
            }
            neighbors.put(tempWord, values);
        }
        return neighbors;
    }

    /**
     * Compares the two words to each other. A map is created for each word that is analyzed where the key is the word
     * and the value is the word's parent aka the value is the key's parent
     * */
    public static Map<String, String> compare(Map<String, List<String>> distOfOne, String wordOne, String wordTwo) {

        // map which is used to keep track of each word and its parent
        Map<String, String> wordsDerivation = new HashMap<String, String>();

        // queue which keeps track of words which need to be analyzed
        Queue<String> wordsToCheck = new LinkedList<String>();

        //set which contains all the words that were added into the wordsToCheck so far
        Set<String> doubleCheck = new HashSet<String>();

        // set the currentWord to wordOne to begin
        String currentWord = wordOne;

        // add the current word to the wordsToCheck and the set to begin
        wordsToCheck.add(currentWord);
        doubleCheck.add(currentWord);

        // while the currentWord doesn't equal the first word...
        while (!currentWord.equals(wordTwo)) {

            // iterator for the list of all the neighbors of the current word
            Iterator<String> neighbors = distOfOne.get(currentWord).iterator();

            // iterate over the neighbors of the currentWord and if any neighbor is in the doubleCheck set, remove
            // the word from the distOfOne list
            while (neighbors.hasNext()){
                String temp = neighbors.next();
                if (doubleCheck.contains(temp)) {
                    neighbors.remove();
                }
            }

            // add all the remaining neighbors to the wordsToCheck queue and the doubleCheck set
            for (String temp : distOfOne.get(currentWord)){
                wordsDerivation.put(temp, currentWord);
                wordsToCheck.add(temp);
                doubleCheck.add(temp);
            }

            // if the wordsToCheck queue is empty a NoSuchElementException is thrown signifying that there are no more
            // words left to check and thus no path exists from wordOne to wordTwo
            try {
                currentWord = wordsToCheck.remove();
            } catch (NoSuchElementException e){
                System.out.println("No path exists from " + wordOne + " to " + wordTwo);
                return null;
            }
        }
        return wordsDerivation;
    }

    /**
     * The map is passed in as a parameter and the path is determined by going from wordTwo to its parent until wordOne
     * is reached. Each of those words is added to an array list which is then printed in reverse order to go from
     * wordOne to wordTwo. The Edit Distance is then printed as a function of the size of the arrayList.
     * */
    public static void printPath(Map<String, String> finalMap, String wordOne, String wordTwo){
        // ArrayList which keeps track of the path in reverse order
        ArrayList<String> path = new ArrayList<String>();

        // add to the path while wordOne doesn't equal wordTwo using wordTwo and temp to go up from wordTwo to its
        // parent and repeating this until wordOne = wordTwo
        String temp = wordTwo;
        while (!wordTwo.equals(wordOne)){
            path.add(temp);
            temp = finalMap.get(wordTwo);
            wordTwo = temp;
        }
        path.add(wordOne);

        // print out the path in the right order
        System.out.print("Path = ");
        for (int i = path.size()-1; i> 0; i--) {
            System.out.print(path.get(i) + ", ");
        }
        System.out.print(path.get(0));

        // get and print the edit distance
        int size = path.size()-1;
        System.out.println("\nEdit Distance = " + size);
    }
}
