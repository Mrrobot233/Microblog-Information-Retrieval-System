# CSI 4107 Assignment 1

## Student Names and Numbers
- Ryan O'Connor: 6852747
- Andy Tang: 6815495

## Task Division
For task division we simply divided out each step and assigned each other to them.
- Andy Tang
  - Step 1
  - Step 3
  - Step 4
- Ryan O'Connor
  - Step 2
  - Step 5
  - Report

## Program Description
This program is an information retrieval system that is implemented for a collection of Twitter messages.
The system expects a list of tweets as input. The system then parses this list of tweets and filters it,
removing all the stop words, numbers, links, and hashtags.

Once the tweets have been filtered, they are put into the filtered_tweets_list.txt so that they can be easily read later.
Each tweet is displayed on its own line which in turn represents a single document. After filtering the tweets, the program 
uses a hashmap to create a reference between a tweet (document) and its id. This is done so that we can reference a document 
given a specific tweet id.

Once all the tweet list preprocessing is complete, the program creates an inverted index. This inverted index uses a hashmap 
where the key is the term (or word) and the value is another hashmap where the key represents the document number and the value 
is the number of occurences for the term (or word) in that document. The below illustration should help visualize the inverted index.

{ "tweet": {0: 2, 3: 1}, "game": {4: 5, 900: 1, ...} ...}

Once the inverted index is created, the program then calculates the weights for each token. Since the number of document occurrences for each token is no longer needed, the program replaces the value of the embedded hashmap with the calculated weight for that particular token and document. To calculate this, we use the following formula: W_ij = tf_ij x idf_i.

## finish talking about ranking part of the program

## How to run the program
To run the program, simply take the Java project and import it direclty into Eclipse. You will need to include the list of 
tweets in the root folder of your program in a file called non_filtered_tweet_list.txt and the list of queries under a file named query.txt. We included the stop_words_list.txt since we have added a few more stop words to improve our results. So your project in Eclipse should look something like the following:

- CSI4107_Assignment01
  - src
    - indexing
    - preprocessing
    - ranking
    - test
  - JRE System Library
  - non_filtered_tweet_list.txt
  - query.txt
  - stop_word_list.txt

Once you've imported the project and included all the necessary files, you are ready to run the program.
To run the program under the src -> test directory select the Test_Tokenizer.java file and run it. This will build a list of filtered tweets as mentioned in the program description. Next under the same folder, run the Test_Ranker.java file and it will build a list of results and print them into a file called evaluation_result.txt. The contents of the file should look like the following:

1 Q0 29983478363717633 1 1.008 myRun
1 Q0 30198105513140224 2 0.892 myRun
...

## Explanation




