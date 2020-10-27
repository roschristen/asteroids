import java.io.*;
import java.util.ArrayList;



public class score {
	 static ArrayList<String> nameList = new ArrayList<String>();
	 static ArrayList<Integer> scoreList = new ArrayList<Integer>();
	 static ArrayList<Integer> nameSortScoreList = new ArrayList<Integer>();
	 static ArrayList<String> sortedNameList = new ArrayList<String>();
	public static void writeHighScores(String name, int score, String filename) throws FileNotFoundException, IOException {
		
		//function that reads high scores and writes to namelist2 and scorelist2
		 inventory inv = readScores(filename);
		 ArrayList<Integer> scoreList2 = inv.scores;
		 ArrayList<String> nameList2 = inv.names;
		/*
		 * The following code was modified from an example by @author imssbora
		 * Retrieved from https://www.boraji.com/java-printstream-example
		 */
		//file and stream declarations
		File file=new File(filename);
		FileOutputStream fileOutputStream=null;
		PrintStream printStream=null;
		
		try {
			fileOutputStream=new FileOutputStream(file);
			printStream=new PrintStream(fileOutputStream);
			
			//Loop to reprint names and scores already inside the file(retrieved using the earlier readScores() call)
			for(int i = 0; i <= scoreList2.size()-1; i ++) {
			printStream.println(nameList2.get(i) + " " + scoreList2.get(i));
			
			}
			
			//check to see if name was entered blank, and replaces it with the name "Anon"
			if(name == "") {
				name = "Anon";
			}
			//add new name and score to the file
			printStream.print(name + " " + score);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(fileOutputStream!=null){
					fileOutputStream.close();
				}
				if(printStream!=null){
					printStream.close();
				}
				//clears lists that were filled by the earlier readScore() call-- I realize this is inefficient as well
				nameList2.clear();
				scoreList2.clear();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	 public static inventory readScores(String filename) throws FileNotFoundException, IOException{
	 /*
	  * Code modified from example from ThinkJava
	  * Retrieved from http://greenteapress.com/thinkapjava/html/thinkjava020.html
	  */
		//arraylists to store unsorted score and name lists
		ArrayList<String> nameList2 = new ArrayList<String>();
		ArrayList<Integer> scoreList2 = new ArrayList<Integer>();
		        FileReader fileReader = new FileReader(filename);
		        BufferedReader in = new BufferedReader(fileReader);

		        while (true) {
		 
		        	 String s = in.readLine();
		             if (s == null) break;
		             
		             //splits name and scores apart
		             String[] highScores = s.split(" ");
		             
		             //sets each equal to their respective variables
		             String name = highScores[0];
		             int score = Integer.parseInt(highScores[1]);
		             
		             /*adds names and scores to lists
		             this is where I would have returned both lists if it were python, 
		             but I didn't have time to figure out how to export them both in java. (maybe by making a special inventory object?)*/
		             nameList2.add(name);
		             scoreList2.add(score);
		        }
		        in.close();
		        inventory inv = new inventory(nameList2, scoreList2);
		      return inv;
		    }
	
	 
	 
	 
	 public static stringInventory exportScores(String filename) throws FileNotFoundException, IOException {
		 
		 inventory inv = readScores(filename);
		 ArrayList<Integer> scoreList2 = inv.scores;
		 ArrayList<String> nameList2 = inv.names;
		 ArrayList<String> nameList=new ArrayList<String>();
		 ArrayList<Integer> scoreList =new ArrayList<Integer>();
		 ArrayList<String> sortedNameList=new ArrayList<String>();
		 ArrayList<Integer> nameSortedScoreList= new ArrayList<Integer>();
		 ArrayList<String> highScoreList = new ArrayList<String>();
		 ArrayList<String> nameHighScoreList = new ArrayList<String>();
		 
		 for(int i = 0; i <= scoreList2.size() -1; i ++) {
			 //passes unsorted name and score values into sort function one at a time
			 largeInventory linv = sortScore(nameList2.get(i), scoreList2.get(i));
			 nameList = linv.names1;
			 scoreList = linv.scores1;
			 sortedNameList = linv.names2;
			 nameSortedScoreList = linv.scores2;
			
		 }

		 for(int i = 0; i <= scoreList2.size()-1; i ++) {
			 //stores new, sorted values to a final formatted highscorelist
			 highScoreList.add(nameList.get(i) + " " + scoreList.get(i));
		 }
		 for(int i = 0; i <= sortedNameList.size()-1; i ++) {
			 nameHighScoreList.add(sortedNameList.get(i) + " " + nameSortedScoreList.get(i));
		 }
		 stringInventory strinv = new stringInventory(highScoreList, nameHighScoreList);
		 return strinv;
		 
	 }
	 
	 public static largeInventory sortScore(String name, int score) {
		
		 
		 //adds score if the list is empty
			if((nameList.size() - 1 <= 0) && (scoreList.size() <= 0)) {
				nameList.add(name);
				sortedNameList.add(name);
				scoreList.add(score);
				nameSortScoreList.add(score);
			}
			//checks to see if score is greater than other values in list, then inserts it when that is no longer true (insertion sort?)
			else {
				int i = scoreList.size() - 1;
				
				for(;i >= 0 && score > scoreList.get(i); i --); 	//empty
				
				//adds sorted values to lists
				///this is also where I'd love to return both lists
				scoreList.add(i + 1 , score);
				nameList.add(i + 1,name);
				
				}
			int i = sortedNameList.size() -1;
			for(; i >=0 && name.toUpperCase().compareTo((sortedNameList.get(i)).toUpperCase()) < 0; i -- );
			nameSortScoreList.add(i + 1, score);
			sortedNameList.add(i + 1, name);
		
			largeInventory linv = new largeInventory(nameList, scoreList, sortedNameList, nameSortScoreList);
			return linv;
			}
//list clear function because I couldn't figure out how to do this optimally :D
	 public static void clearLists() {
		 nameList.clear();
		 sortedNameList.clear();
		 scoreList.clear();
		 nameSortScoreList.clear();

	 }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
