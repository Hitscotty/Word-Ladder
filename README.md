# Code-To-Work: Word Ladder

The game gives the players two starting words of the same length (gain and fire in this example) and a number of blank spaces in between. The objective of the game is to fill each blank space with a word that differs from the word above and the word below it by a single letter. For example, one solution (there may be several) to the above puzzle is:

+ **gain**
+ gait
+ wait
+ wart
+ ware
+ wire
+ **fire**

## Implementation

Used an adjacency list with linked-list to store words from dictionary where the root is the word and nodes are valid words that can be made from the root. 

```  java       
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
        }
```

Using a hashmap implementation to store words of only needed lengths

``` java
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
```


[More about this Project Here][link]

[link](https://cswithandroid.withgoogle.com/content/unit?unit=39&lesson=41)


