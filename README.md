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


