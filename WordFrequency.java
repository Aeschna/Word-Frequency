//Mehmet Furkan Erdem 
package Assignment4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordFrequency {
    private HashMap<String, Integer> wordCountMap = new HashMap<>(); //wordCountMap which is a HashMap that will store words as keys and their counts as values.

    public void readFile(String filename) {
        try (BufferedReader bReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] words = line.toLowerCase().split("[\\W&&[^']]+");//converts the line to lowercase and excepts non-words
                for (String word : words) {// checks for all elements
                    if (!word.isEmpty()) {
                        int count = wordCountMap.getOrDefault(word, 0);
                        wordCountMap.put(word, count + 1);
                    }
                }
            }
            System.out.println("File \"" + filename + "\" has been successfully read.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public int getWordCount(String word) {//get the count of a specific word
        return wordCountMap.getOrDefault(word.toLowerCase(), 0);
    }

    public String[] getMostFrequentWords(int k) {
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
                (a, b) -> b.getValue().compareTo(a.getValue())); //priority queue store the map entries based on their values
        maxHeap.addAll(wordCountMap.entrySet());

        String[] result = new String[k];
        for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
            result[i] = maxHeap.poll().getKey();//extracts the bottom k entries from the max heap and stores the keys
        }
        return result;
    }

    public String[] getLeastFrequentWords(int k) {
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue));
        minHeap.addAll(wordCountMap.entrySet());

        String[] result = new String[k];
        for (int i = 0; i < k && !minHeap.isEmpty(); i++) {
            result[i] = minHeap.poll().getKey();//extracts the bottom k entries from the min heap and stores the keys
        }
        return result;
    }

    public static void main(String[] args) {
        WordFrequency wordFreqCount = new WordFrequency();
        String filename = "sentences.txt";
        wordFreqCount.readFile(filename);

        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println(" 1. Get the count of a specific word\r\n"
            		+ " 2. Get the most frequent words\r\n"
            		+ " 3. Get the least frequent words\r\n"
            		+ " 4. Exit");
            
            System.out.print("Enter your choice: ");
            if (!scn.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1, 2, 3, or 4).");
                scn.next(); 
                continue;
            }
            int option = scn.nextInt();
            scn.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("Enter the word: ");
                    String word = scn.nextLine();
                    System.out.println("'" + word + "' appears " + wordFreqCount.getWordCount(word) + " times.");
                    break;
                case 2:
                    System.out.print("Enter the number of words: ");
                    int k1 = scn.nextInt();
                    String[] frequentWords = wordFreqCount.getMostFrequentWords(k1);
                    System.out.print("The " + k1 + " most frequent words are: ");
                    System.out.println(Arrays.toString(frequentWords));
                    break;
                case 3:
                    System.out.print("Enter the number of words: ");
                    int k2 = scn.nextInt();
                    String[] leastFrequentWords = wordFreqCount.getLeastFrequentWords(k2);
                    System.out.print("The " + k2 + " least frequent words are: ");
                    System.out.println(Arrays.toString(leastFrequentWords));
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scn.close();
                    return;
                default:
                    System.out.println("Invalid option. Enter a number (1, 2, 3, or 4).");
            }
        }
    }
}
