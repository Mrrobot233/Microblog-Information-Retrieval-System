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
removing all the stop words, numbers, links, and punctuations.

Once the tweets have been filtered, they are put into the filtered_tweets_list.txt so that they can be easily read later.
Each tweet is displayed on its own line which in turn represents a single document. After filtering the tweets, the program 
uses a hashmap to create a reference between a tweet (document) and its id. This is done so that we can reference a document 
given a specific tweet id.

Once all the tweet list preprocessing is complete, the program creates an inverted index. This inverted index uses a hashmap 
where the key is the term (or word) and the value is another hashmap where the key represents the document number and the value 
is the number of occurences for the term (or word) in that document. The below illustration should help visualize the inverted index.

{ "tweet": {0: 2, 3: 1}, "game": {4: 5, 900: 1, ...} ...}

Once the inverted index is created, the program then calculates the weights for each token. Since the number of document occurrences for each token is no longer needed, the program replaces the value of the embedded hashmap with the calculated weight for that particular token and document. To calculate this, we use the following formula: W_ij = tf_ij x idf_i.

Once indexing is completed, the program will then loop through each query from query.txt.  With each query, the program will find the list of relevant documents.  This is done by parsing the query into individual words and using each word to find relevant documents using the invereted index since the inverted index has a reference to the document number.  From the subset of documents we retreive, we apply the cosine method to calculate the similarities between each relevant document and the query.  An important thing to note is that we use the weights for each document tokens from the inverted index as well as calculate the weights for each query word before we calculate the cosine value.  The cosine values are stored into a list which ends up getting sorted to determine their corresponding rank.


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

- 1 Q0 29983478363717633 1 1.008 myRun
- 1 Q0 30198105513140224 2 0.892 myRun
- ...

## Explanation
### Data Structures
In this program we used a few arrays and hashmaps.

The array was used to temporarily store the unfiltered tweets so that the program can scan through the terms and remove any 
stop words, urls, numbers, hashtags, and special characters. The array was also used to allow the program to downcase any remaining terms before storing the contents of the array in a filtered_tweet_list.txt file. 

Additionally we used an arraylist to store a list of query objects that were created for each query in the query.txt file. 
This gives us fast access to the queries and allows us to efficiently process them.

Hashmaps were used in several areas. It was first used to create a reference between a tweet id and its document. 
This allowed the program to efficiently access a tweet or document given its id or vice versa. The key of this hashmap is the
document number, and the value is the tweet id. 

Lastly we used 2 hashmaps in the invertedIndex. The reason we decided to do this is because it allowed us to efficiently 
access information stored for a given term without having to loop through an array, or tarverse through a list. Access is 
thus constant. We decided to use another hashmap to store the number of documents and the number of term occurrences for a 
particular document, because it allowed us to (again) access the number of occurrences for a term in a document in constant 
time, and it also gave us the benefit of not having to use a large array. Since it's possible for a term to occur in document
1 and 10000, it would mean that we would need to have an array of length 10000 for only 2 values. This wastes a lot of 
memory. A hashmap on the other hand would only have a length of 2.

### Algorithms
Algorithms for Tokenizing
- createTweetIdReference(): Populates an hash map with with keys as document numbers and values as tweet ids.
- createFilteredListOfTweetFromFilteredDocument(): Creates list of filtered tweets from filtered documented in case a filtered document already exists.
- createFilteredListOfTweetFromNonFilteredDocument(): Creates a list of filtered tweets from an unfiltered tweet list.

Algorithms for Indexing
- createInvertedIndex(): Loop through the filtered Tweets list and create an inverted index.
- addTokenToInvertedIndex(String documentToken, int documentNumber): Add a token to the inverted index.
- calculateWeights(): Loop through the inverted index and replace the number of document occurrences with calculated term/document weights.
- calculateWeight(double freq, double maxF, int dI): Actually calculate the term/document weight.

Algorithrms for Ranking
- getCosineSimilarity(String[] listOfQueryWord, HashMap<String, Double> queryWordWeightList, int documentNumber, String document): Gets similiarity value between query and document.
- getListOfRelevantDocument(String[] listOfQueryWord): Gathers a list of relevent document that contain at least one word inside the query.
- getQueryWordWeight(String[] listOfQueryWord): Calculates the weight of each word inside the query.
- buildQueryList(): Creates a list of query objects from query.txt.

### Optimizations
- Removal of stop words, punctuation and numbers from our query (This improved our results roughly by 0.01~)
- Removing links from our documents (Very small improvement in our results, roughly by 0.005~)
- Adding weights to each query word (This improved our reuslts roughly by 0.05~)
- Instead of replacing dashes with empty string, we replaced dashes with spaces which often created 2 separate words (This improved our results roughly by 0.05~)
- Included size 1 words inside list of stop words (Very small improvement of our results by roughly 0.005~)

### Vocabulary
Our vocabulary size is: 65920.

100 token sample: 
undermining, cheneybribes, spilling, abrupt, salary, childs, artieka, bbctbq, clothingshoeshandbagsmpdvds, avant, blasts, 
liferichmond, remax, flfw, wingsuit, canqueer, timah, chiles, unitedkingdom, cribsies, ballestas, jayrosennyu, nonviolent, 
pnsky, recallcheck, ifyouletme, coliseo, matttbastard, glade, sisil, rahmemanuel, stoning, twodisc, circoncis, bschool, 
timao, panamericana, bindelwijk, soekarnohatta, revdlesley, flip, mssql, taylorswift, mpkirk, posture, eating, 
oracaopefabio, iwebslogcom, lickmyjs, glady, songsthatdropthepanties, dropsby, automating, sinuses, adrian, ofar, 
prebailout, estudiando, pourrastu, tnfisherman, swapsies, jobshirt, analytics, hotairblog, myriad, arrojado, glace, 
irrelevance, tambunan, windburn, shakespear, coastcoast, informative, pagewrite, nagwoworry, remek, categorically, scholars,
remet, wefestival, jozan, chapelle, tcells, wowzer, remed, offs, srry, benjetzrt, salama, motive, swishing, berlinspandau, 
shrinks, schubart, bruceleroy, cabbies, colouring, offc, glaad, offe. 


## First 10 Results From Queries 1 and 25
### Query 1
- 29983478363717633
- 30198105513140224
- 33823403328671744
- 29993695927336960
- 29386330014220288
- 34703780100448257
- 29621742011944960
- 29486393336008704
- 30317913206431744
- 30260724248870912

### Query 25
- 31499543946207232
- 31076061462659072
- 29220224423174145
- 31177737502724096
- 31915245379256320
- 30134314033217538
- 32172153587638272
- 31960639719079936
- 31396671703220224
- 30237111005220864

## Discussion of Results
Since there are over 20 sample results above, I will compare the best result for query 1 against query 1.  Query 1 text was "BBC World Service staff cuts" and the best ranked document contained the following text, "[BBC News] Major cuts to BBC World Service: BBC World Service is to close five of its language services, with th... http://bbc.in/e2vlpX".  After filtering the results of our best ranked document, we get the following text: "bbc news major cuts bbc service bbc service close language services".  After filtering query 1, we get the following text: "bbc service staff cuts". If we compare the text of these 2 results, every single word in the query is contained inside the best ranked document.  Furthermore, some of these words appear more than once and 7/11 of the words inside the document matches the query.  The reason these 2 string of text became so similar is due to link and stop word removal.  If we compare the original text, 9/21 of the words inside the document match the query which is quite a big difference percent wise compared to 7/11.  Furthermore, since the results are all weighted, this further increased similiarity value between query 1 and the best ranked document.

- num_q          	all	49
- num_ret        	all	35994
- num_rel        	all	2640
- num_rel_ret    	all	2141
- map            	all	0.1532
- gm_ap          	all	0.0940
- R-prec         	all	0.1748
- bpref          	all	0.1371
- recip_rank     	all	0.4012
- ircl_prn.0.00  	all	0.4581
- ircl_prn.0.10  	all	0.2870
- ircl_prn.0.20  	all	0.2383
- ircl_prn.0.30  	all	0.2071
- ircl_prn.0.40  	all	0.1946
- ircl_prn.0.50  	all	0.1765
- ircl_prn.0.60  	all	0.1312
- ircl_prn.0.70  	all	0.1028
- ircl_prn.0.80  	all	0.0726
- ircl_prn.0.90  	all	0.0359
- ircl_prn.1.00  	all	0.0048
- P5             	all	0.2490
- P10            	all	0.2265
- P15            	all	0.2068
- P20            	all	0.1939
- P30            	all	0.1748
- P100           	all	0.1314
- P200           	all	0.1116
- P500           	all	0.0747
- P1000          	all	0.0437





