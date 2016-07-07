package com.google.engedu.wordladder;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static final int MIN_WORD_LENGTH = 2;
    private static final int MAX_PATH_LENGTH = 7;

    private static HashSet<String> words                           = new HashSet<>();
    private static ArrayList<String> wordList                      = new ArrayList<>();
    private static HashMap<Integer, ArrayList<String>> wordLengths = new HashMap<>();
    private static ArrayList<LinkedList<String>> adjDictionary     = new ArrayList<>();

    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        Log.i("TAGGER", "Loading dict");

        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH && word.length() < MIN_WORD_LENGTH) {
                continue;
            }

            words.add(word);
            wordList.add(word);

            int size = word.length();

            if(wordLengths.containsKey(size) == false){
                ArrayList<String> newTemp = new ArrayList<>();
                newTemp.add(word);
                wordLengths.put(size, newTemp);
            } else {
                ArrayList<String> addToTemp = wordLengths.get(size);
                addToTemp.add(word);
                wordLengths.put(size, addToTemp);
            }

        }

        for(int i = 0; i < wordList.size(); i++){
            // add into path
            String input = wordList.get(i);

            LinkedList<String> neighbours = neighbours(input);
            adjDictionary.add(new LinkedList<String>());
            adjDictionary.get(i).add(input); // adds root node

            String display = "";
            for(int j = 0; j < neighbours.size(); j++){
                adjDictionary.get(i).add(neighbours.get(j)); // adds neighbors to the root node

                display += " " + neighbours.get(j);
            }
          //  Log.d("TAGGER", "Vertice: " + adjDictionary.get(i).get(0) + " Edges: " + adjDictionary.get(i));


        }


    }

    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    private LinkedList<String> neighbours(String word) {

        int size = word.length();
        LinkedList<String> neighbours = new LinkedList<>();
        ArrayList<String> possibleNeighbours  = wordLengths.get(size);

        for(String validWord: possibleNeighbours){
            int count = 0;

            // the root
            if(validWord.equals(word)) continue;

            for(int i = 0; i < size; i++){

                if(!validWord.contains("" + word.charAt(i))){
                    count++;
                }
            }

            if(!(count > 1)) neighbours.add(validWord);
        }


        return neighbours;
    }

    public String[] findPath(String start, String end) {
        ArrayDeque pathsExplored = new ArrayDeque();
        ArrayList<String> path = new ArrayList<String>();
        int pathSize = 0;


        path.add(start);
        pathsExplored.add(path);

        while(!pathsExplored.isEmpty()){

            //  TODO: Implement if(pathSize > MAX_PATH_LENGTH) return null;
            //While the queue is not empty, get the path from the head of the queue and consider all of its neighbours.
            ArrayList<String> tempPath = (ArrayList) pathsExplored.remove();
            int lastElementInPath = tempPath.size();
            String currentWord = tempPath.get(lastElementInPath - 1);

            int root = wordList.indexOf(currentWord);
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
