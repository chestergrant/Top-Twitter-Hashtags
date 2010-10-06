/**
 * @(#)Hashtags.java
 *
 * Hashtags application
 *
 * @author 
 * @version 1.00 2010/10/5
 */
import java.io.*;
import java.util.*;

public class Hashtags {
	String filename; //Filename with directory of the corpus
	int breaking; //The number of top tweets and retweet hashtags to print
	HashMap<String, Integer> tweetHash;  //Store Hashtags along with
	HashMap<String, Integer> retweetHash;
	
	public Hashtags(String fn,int br){
		//Initialization 
		this.filename = fn;
		this.breaking = br;
		tweetHash = new HashMap<String, Integer>();
		retweetHash = new HashMap<String, Integer>();
		
	}
	
	//Stores hashtags in a tweet and increment counters as required
	public void processLine(String line){
		String strArr[] = line.split("[\\s]|[,]");
		boolean retweeted = false;
		for(int i = 0; i < strArr.length;i++){
			if(strArr[i].compareTo("RT") == 0){
				retweeted = true;
			}
		}
		for(int i = 0; i < strArr.length;i++){
			strArr[i] = strArr[i].toLowerCase();
		   if(strArr[i].length()>0){
			
			if(strArr[i].charAt(0) == '#'){
				if(!tweetHash.containsKey(strArr[i])){
					tweetHash.put(strArr[i],0);
					retweetHash.put(strArr[i],0);
				}
				  int newCount=  ((Integer)tweetHash.get(strArr[i])).intValue() + 1;
				  int newRTCount=  ((Integer)retweetHash.get(strArr[i])).intValue() + 1;
				  tweetHash.put(strArr[i],newCount);
				  if(retweeted){
				  	retweetHash.put(strArr[i],newRTCount);
				  }
				  
			}
		  }
		}
	}
	
//Sorts a hash and returns an iterator to the sorted hash
public Iterator getSortedIterator(HashMap hmap)
	{
		ArrayList items = new ArrayList();
		Iterator iter = hmap.entrySet().iterator();
		int count  =0;
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			items.add(entry);
			count++;
			//if(count % 100000 == 0)System.out.println(count);
			//System.out.println(entry.getKey()+ " -- "+ entry.getValue());
		}
		Collections.sort(items,(Comparator)(new MapCompare()));
		return items.iterator();
	
	}

    //Print the top hashtag given in a hash
	public void printAHash(HashMap aHash){
		//aHash = getSortedMap(aHash);
		Iterator iter = getSortedIterator(aHash);
		int count = 0;
		
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			System.out.println(entry.getKey()+ " -- "+ entry.getValue());
			count++;
			if(count>breaking) break;
		}
	}
	//Print the Top  Hashtags to for tweets and retweets
	public void printHash(){
		printAHash(tweetHash);
		System.out.println("-----------------------------------------------------");
		System.out.println("Reweets \n");
		printAHash(retweetHash);
		
	}
	//Prints the first use the first x number of tweets to determine the top tweets and retweet hashtag
	public void printNLines(int x){
		try{
			BufferedReader in = new BufferedReader(new FileReader(filename));
			int count = 0;
			String input ="";
				while((x>count)&&((input =in.readLine())!= null)){
					processLine(input);
					count++;
					//if(count==1)System.out.println(input);
					//if(count %100000 == 0) System.out.println(count);
					//System.out.println(input);	
				}
				//System.out.println(input);
				//System.out.println("Now Printing");
			printHash();	
			in.close();
		}catch(IOException ex){
			System.out.println("Unable to open the file");
			System.exit(1);
		}	
	}
    
    public static void main(String[] args) {
    	Hashtags ht = new Hashtags("C:\\Users\\chester\\Documents\\twitter.anon",100);
    	ht.printNLines(50000);
    	// TODO, add your application code
    	//System.out.println("Hello World!");
    }
}
