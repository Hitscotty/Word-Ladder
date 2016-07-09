package com.google.engedu.wordladder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static final int MIN_WORD_LENGTH = 2;
    private static final int MAX_PATH_LENGTH = 7;

    /**
     * The words hashset is used for searching if a word is in our dictionary in constant time
     * the wordsList is used to insert into adjDictionary
     */
    private static HashSet<String> words                           = new HashSet<>();
    private static ArrayList<String> wordList                      = new ArrayList<>();
    private static ArrayList<LinkedList<String>> adjDictionary     = new ArrayList<>();


    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();

            if (word.length() > MAX_WORD_LENGTH || word.length() <= MIN_WORD_LENGTH) {
                continue;
            }

            words.add(word);
            wordList.add(word);

        }


        int index = 0;
        for(String input: wordList){

            LinkedList<String> neighbours = neighbours(input);
            LinkedList<String> root       = new LinkedList<>();
            root.add(input);

            for(int j = 0; j < neighbours.size(); j++) {
                root.add(neighbours.get(j));
            }

            adjDictionary.add(index, root);
            index++;
        }

    }

    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    private LinkedList<String> neighbours(String word) {

        LinkedList<String> neighbours = new LinkedList<>();

        for(String validWord: wordList){

            int discrepancy = 0;
            if(validWord.equals(word)) continue;

            for(int i = 0; i < word.length(); i++){
                if(word.charAt(i) != validWord.charAt(i) && (discrepancy++ > 1)) {
                    break;
                }

            }

            if(discrepancy == 1) neighbours.add(validWord);

        }

        return neighbours;
    }

    public String[] findPath(String start, String end) {
        ArrayDeque<ArrayList<String>> pathsExplored = new ArrayDeque<>();
        ArrayList<String> path   = new ArrayList<>();

        path.add(start);
        pathsExplored.add(path);

        while(!pathsExplored.isEmpty()){

            //  TODO: Implement if(pathSize > MAX_PATH_LENGTH) return null;
            //While the queue is not empty, get the path from the head of the queue and consider all of its neighbours.
            ArrayList<String> tempPath = pathsExplored.remove();
            int lastElementInPath      = tempPath.size();
            String currentWord         = tempPath.get(lastElementInPath - 1);

            int root                     = wordList.indexOf(currentWord);
            LinkedList<String> neighbors = adjDictionary.get(root);

            //If the neighbour is the desired end, return the corresponding path.
            if(neighbors.contains(end)){
                tempPath.add(end);
                String [] pathFound = new String [path.size()];
                return tempPath.toArray(pathFound);
            }

            //Otherwise, create new paths representing the current path plus each of the neighbours (avoiding loops) and add them to the queue.
            for(String word: neighbors){
                //stops loops last word in should not be considered
                if(word.equals(tempPath.get(tempPath.size() - 1))){
                    continue;
                }
                //create new paths with all neighbors using old path
                ArrayList<String> newPath = new ArrayList<>(tempPath);
                newPath.add(word);
                pathsExplored.add(newPath);

            }



        }

        return null;
    }
}
