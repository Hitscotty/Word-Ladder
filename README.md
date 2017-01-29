# Code-To-Work: Word Ladder

<link href="/modules/core_tags/_static/css/markdown.css" rel="stylesheet"></link>

This workshop’s app is a game called [Word Ladder]. This game was invented by Lewis Carroll (the author of Alice in Wonderland) and appears in some newspapers along with the crossword puzzles. It looks like this:

<center>
![Screenshot]
</center>

The game gives the players two starting words of the same length (gain and fire in this example) and a number of blank spaces in between. The objective of the game is to fill each blank space with a word that differs from the word above and the word below it by a single letter. For example, one solution (there may be several) to the above puzzle is:

    gain
    gait
    wait
    wart
    ware
    wire
    fire

### Tour of code

The [starter code] is made up of two Java classes:

-   `WordSelectionActivity`: This is the activity that is shown when the application is launched:
-   `onCreate`: boilerplate plus code to load the word list
-   `onStart`: handler for the “Start” button. You will have to add code to launch another activity here.
-   `onCreateOptionsMenu` and `onOptionsItemSelected`: boilerplate
-   `PathDictionary`: This is a custom class that stores the list of words and provides functionality to find paths between words:
-   `PathDictionary`: Constructor that reads the words list. You’ll need to store the words in some convenient data structures.
-   `isWord`: Implementation is provided.
-   `neighbours`: Private helper to find all the words that differ from the given word by one letter.
-   `findPath`: Returns an array of strings that form a path between two strings. For example, given `start = "cold"` and `end = "warm"`, it will return `["cold", "cord", "card", "ward", "warm"]`.

### Implementation

The given implementation of `WordSelectionActivity` is almost complete so we’ll start by implementing `PathDictionary`. The given code stores each word in a `HashSet` to allow fast checking of word validity but we will need to also store the words in another data structure. Conceptually, we want to store the words in a graph where each node is a word and each edge connects two words that differ by only one letter (we’ll call such words ‘neighbours’).&lt;

  [Word Ladder]: https://en.wikipedia.org/wiki/Word_ladder
  [Screenshot]: https://cswithandroid.withgoogle.com/content/assets/img/word_ladder.png
  [starter code]: https://cswithandroid.withgoogle.com/content/assets/img/WordLadder_starter.zip


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

Using a hashmap (wordLengths)to store words of only needed lengths

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


[More about this Project Here][android]

[android]:https://cswithandroid.withgoogle.com/content/unit?unit=39&lesson=41


