
import java.io.*;
import java.util.*;

/**
 * 
 Program to compute the W-set for a given FSM.
 Based on Chow's algorithm published in 1980
 This program is based on some code written by Aditya for the UIO algorithm.
 
 Note that the algorithm does NOT work for machines with self loops in states.
 An FSM without any self loops represents  the core behavior of an FSM.
 
 Originally coded by: Aditya Mathur (for the UIO algorothm. Some of those files are used here).
 W-method and test sets coded by a bright Purdue undergraduate students.
 
 Comments:Variables in the initial version are the same as given by the authors of the algorithm.
 Java naming norms may be violated.
 
 Updated: August 1, 2013
 Aditya Mathur
 
 Several classes and methods used here have been coded by Brandon Wuest while he
 was an undergraduate student at Purdue.

 */


public class WMethod{
  
  private static String debugMode1="";  // No debugging.
  private static String debugMode2="";
  public static final int maxStates=20;  // Max no of states in the FSM (=n).
  public static final int maxSequences=100;  // Max no of sequences of length (2xnxn).
  public static final int maxEdges=100;  // Max no of edges in the FSM.
  public static final int maxOutgoingEdges=20;  // Max no of outgoing edges/state.
  public static final int maxInputs=50;  // Max ize of the input alphabet.
  public static final int maxOutputs=50;  // Max size of the output  alphabet.
  public static final int maxTransitions=250;  // Max number of transitions.
  public static int maxLabels=50; // Max number of distinct edge labels.
  
  // An FSM state contains an ID, description, and a set of outgoing edges.
  public static State [] FSM =new State[maxStates]; // Area where the FSM will be stored.
  public static int numberOfStates; // Number of distinct states in the FSM.
  public static int startState; // Starting state of the FSM.
  
  
  Set inputAlphabet=new HashSet();// Contains input alphabet symbols.
  Set  outputAlphabet=new HashSet(); // Output alphabet symbols.
  public static int numberOfTransitions=0; // Total transitions in the FSM.
  public static String fsmFilename; // File containing the FSM description.
  

  public static int numberOfTransitions2 = 0;
  public static String fsmFilename2;
  
  private static String [] Opattern=new String[maxEdges];; // Holds all patterns being tried out.
  private static int [] Oend=new int[maxEdges]; // Holds end tail state corresponding to each pattern in Opattern.
  private static Set [] Oled=new HashSet[maxEdges]; // Holds sets of edges corresponding to a labels.
  
  
  private static String[] outputArray = new String[maxOutputs]; //Output alphabet
  private static String[] inputArray = new String[maxInputs]; // Input alphabet
  private static int countOutputs = 0; //Size of the output alphabet
  private static int countInputs = 0; // Size of the input alphabet
  
  private static Scanner fileSource; // Name of a Scanner object.
  
  
  /**
   * 
   Method to read the FSM description from a source file.
   startState is assumed to be 1.
   */
  
  public static void getFSM(){
    String aTransition; // One transition from input.
    int currentState=-1; // ID of state being input (-1 is an invalid ID)
    numberOfStates=0; // No states input so far.
    startState=1;
    try{
      BufferedReader fsmFile= new BufferedReader (new FileReader(fsmFilename));
      aTransition=fsmFile.readLine();
      boolean done=false; // Indicates loop to be terminated or not.
      if (aTransition==null) 
        done=true;
      
      while (!done){
        numberOfTransitions++;  // One more transition input.
        //   System.out.println("Next transition: "+numberOfTransitions+" "+aTransition);
        splitTransition(aTransition);  // Separate from, to, and edge label of the transition and add to FSM.
        aTransition=fsmFile.readLine(); // Get the next transition.
        if (aTransition==null || (numberOfTransitions==maxTransitions)) 
          done=true;
      }
    }catch (FileNotFoundException e){
      System.out.println("File: "+fsmFilename+" not found");
      System.exit(0);
    }catch (IOException e){
      Utilities.printException("wAlgoriithm", "getFSM","IO exception raised while reading: "+
                               fsmFilename+" not found");
      Utilities.printException("wAlgoriithm", "getFSM", "Transitions input: "+numberOfTransitions);
    }
  } //End getFSM()
  
  
  
  static void splitTransition(String transition){
    
    /*
     * Split a line of input from the FSM file.
     * Example:
     * Input line: 3 4 a/0  Note the three tokens 3, 4, and a/0.
     * Indicates: Source state: 3
     * Destination state:  4 
     * Take the transition on input: a
     * Generate output: 0.
     */
  
    String [] token =new String[3];
    String [] edgeToken =new String[2];
    String inputLabel, outputLabel, toState, edgeLabel; // Temporaries.
    int ID; // ID of the from state in a transition.
    int toID; //ID of the tdestination satte in a transition.
    
    StringTokenizer transitionTokens=new StringTokenizer(transition);
    int tokensFound=0;
    
    // Split transition into its three components: source, destination and edge label.
    while(transitionTokens.hasMoreTokens()){
      token[tokensFound]=transitionTokens.nextToken();
      Utilities.debugFSM("Next token: "+token[tokensFound]);
      tokensFound++;
    }// End of while
    
    ID=-1; // To avoid compiler error.
    try{
      ID=Integer.parseInt(token[0]);  // Convert from state ID to integer.
      if (newState(ID)){ // Check if this state ID appeared before.
      numberOfStates++;
      FSM[ID]=new State(ID);  // Create a new state entry in the FSM.
    }
    }catch(NumberFormatException e){
      Utilities.printException("WMethod", "splitTransition", "From state ID must be a number, found: "+token[0]);
    }
    
    inputLabel=""; // Initialized to avoid copmpiler error.
    outputLabel=""; // Initialized to avoid copmpiler error.
    toID=-1;
    try{
      toID=Integer.parseInt(token[1]);  // Destination state.
      edgeLabel=token[2]; // Edge label
      edgeToken=splitEdgeLabel(edgeLabel);  // Split edge label to input and output labels.
      inputLabel=edgeToken[0]; // Set input and 
      outputLabel=edgeToken[1]; // output labels.
    }catch(NumberFormatException e){
        Utilities.printException("WMethod", "splitTransition", "To state ID must be a number, found: "+token[1]);
      }
    
    // Check whether or not the output label has already appeared.
    int traverser = 0;
    boolean found = false;
    while(traverser < outputArray.length){
      if(outputArray[traverser].equals(outputLabel)){ // Check if symbol already in the output array.
        found = true;
        break;
      }
      traverser++; 
    }
    // Add output symbol to the array if it does not already exist.
    
    if(found == false && !outputLabel.equals("")){
      Utilities.debugFSM("Adding " + outputLabel + " to output array position " + countOutputs + ".");
      outputArray[countOutputs] = outputLabel;
      countOutputs++;
    }
    
  // Check whether or not the input label has already appeared.
    found = false;
    traverser = 0;
    int traverser2 = 0;
    boolean found2 = false;
    while(traverser2 < inputArray.length){
      if(inputArray[traverser2].equals(inputLabel)){ //already in the input array.
        found2 = true;
        break;
      }
      traverser2++;
    }
    
    // Add input symbol to the input array if it does not already exist.
    if(found2 == false && !inputLabel.equals("")){
      Utilities.debugFSM("Adding " + inputLabel + " to input array position " + countInputs + ".");
      inputArray[countInputs] = inputLabel;
      countInputs++;
    }
    
    found2 = false;
    traverser2 = 0;
    
    
    try{
      FSM[ID].addEdge(new Edge(ID, toID, inputLabel, outputLabel));
    }catch (InvalidEdgeException e){
      Utilities.debugFSM("Trying to add an invalid edge. State ID does not match head ID");
      Utilities.debugFSM("State ID:" + ID+ " Edge head ID" + ID); // Impossible here.
      System.exit(0);
    }
  } // End splitTransition()
  
  /**
   Method to split an edge label of the form x/y to x and y.
   */
  
  private static String []  splitEdgeLabel(String edgeToken){
    String []token=new String [2];
    Utilities.debugFSM("EDGE TOKEN:" + edgeToken); 
    StringTokenizer ioTokens=new StringTokenizer(edgeToken, "/");
    token[0]=ioTokens.nextToken();
    token[1]=ioTokens.nextToken();
    return token;
  }//End splitEdgeLabel()
  
  
  /**
   * 
   Find if a given state ID corresponds to a new state in the FSM.
   */
  private static boolean newState(int stateID){
    if (FSM[stateID]!=null){
      return(false);
    }
    else{
      return(true);
    }
  }//End newState()
  
  
  
  /**
   Method to print all states and transitions of the input FSM.
   */
  
  public static void printFSM(String [] inputAlphabet){
    // Temporary variables.
    
    Set edges=new HashSet();
    int stateID;
    
    System.out.println("States: "+numberOfStates);
    System.out.println("Edges "+numberOfTransitions);
    System.out.println("Input alphabet:");
    for (int i=0; i<inputAlphabet.length; i++)
      System.out.println(inputAlphabet[i]);
    
    System.out.println("\nOutput alphabet:");
    int count = 0;
    Arrays.sort(outputArray);
    while(count<maxOutputs){
      if(!outputArray[count].equals(""))
        System.out.println(outputArray[count]); 
      count++;
    }
    System.out.println("\nFrom \t Input/Output \t To");
    for (int i=0; i<maxStates; i++){
      if(FSM[i]!=null){
        stateID=FSM[i].getID();
        edges=FSM[i].getEdgeSet();
        Iterator E=edges.iterator();
        while(E.hasNext()){
          Edge e=(Edge)E.next();
          System.out.println(stateID+ "\t "+ e.input()+ "/" + e.output()+ "\t\t "+ e.tail());
        }// End of while over edges from a node
      }// End of if to check ith state.
    }// End of for to iterate over all states.
    count = 0;
   // Arrays.sort(inputArray);
    sortInputs();
    while(!inputArray[count].equals("")){
      Utilities.debugFSM("Possible input "+count +": " +inputArray[count]); 
      count++;
    }
  }// End printFSM()
  
  private static String getFilename(){
    System.out.print("Enter filename: "); // Prompt for file name containing FSM.
    String name=fileSource.nextLine();
    return (name);
  }//End getFileName()
  
 
  
  
   public static void sortInputs(){
    boolean Swapped = true;
    int csize = countInputs;
    
    while(Swapped){
      Swapped = false;
      for(int i = 0; i < csize-1; i++){
        if(inputArray[i].compareTo(inputArray[i+1]) > 0){
          String temp;
          temp = inputArray[i];
          inputArray[i] = inputArray[i+1];
          inputArray[i+1] = temp;
          Swapped = true;
        }
      }
      csize--;
    }
    
  }// End of sortInput()
   
    public static boolean existsInVector(String searchString, Vector searchVector){
      for(int i = 0; i < searchVector.size(); i++){
        if((searchVector.get(i)).toString().equals(searchString)){
          return true;
        }
      }
      return false;
    }// End of existsInVector()  
    
  
   public static Vector<String> generateTests(TestingTree tree, pTableManager tablemanager){
    Vector <String> pVector = new Vector<String>();
    Vector wVector = new Vector();
    Vector<String> testCases = new Vector<String>(); // Contains generated test cases (strings)
    pVector = tree.getPValues(); // Get the transition cover set (P) 
    wVector = tablemanager.getW(); // Get the W set
    pVector.add(new String("")); // The transition cover set contains the empty string.
    
    // Generate tests by computing P.W. 
    // We assume m=n, where m is the number of states in the FSM of the program under test
    // and n is the number of stastes in the design FSM.
    
    int repeatedEntries=0; // Some test cases might be repeated during the set multiplication process. 
    for(int i = 0; i < pVector.size(); i++){
      for(int j = 0; j < wVector.size(); j++){
        String pValue = (String) pVector.get(i);
        String wValue = (String) wVector.get(j);
        String testCase = pValue + wValue;
        if(!existsInVector(testCase, testCases)){ 
          testCases.add(testCase); 
        }else
          repeatedEntries++;
      }
    }
     return testCases;
   }// End generateTests.
   
   /* 
   Driver for the W-algorithm.
   */
   public static void main(String [] args){
     
     System.out.println("Test Generation Using the W-method. V2.0. August 1, 2013\n");
     fileSource=new Scanner(System.in);
     fsmFilename=getFilename(); // Get  from the user file name for FSM.
     for (int i = 0; i < outputArray.length; i++){
       outputArray[i] = ""; // Initialize array that will contain the output alphabet.
     }
     for (int j = 0; j < inputArray.length; j++){
       inputArray[j] = ""; // Initialize array that will contain the input alphabet.
     }
     getFSM(); // Get the FSM. Determine the input and output alphabets.
     
     String [] realInput = new String [countInputs];
     
     for(int z = 0; z < realInput.length; z++){
       realInput[z] = inputArray[z]; // Real Input contains only the elements of the input alphabet.
     }
     Arrays.sort(realInput);
     System.out.println("FSM input from:  "+fsmFilename);
     if(Utilities.fsmPrintSw)
       printFSM(realInput); // Print FSM.
     
     // Generate testingTree (Section 5.6.3 in the textbook).
     TestingTree transitionCover = new TestingTree(FSM, numberOfStates); 
     // Generate P-tables and the W set (Section 5.5 in the textbook).
     pTableManager w = new pTableManager(FSM, numberOfStates, realInput);
     Vector <String> tests=generateTests(transitionCover, w); // Generate tests.
     Utilities.printAllTestCases(tests); // Print tests.
     
     // TODO: 	Write the necessary code to iterate through all test cases and run them against
     // 		the FSM using the Utilities.runFSM() method. 
     //
     // Example use of the Utilities.runFSM() method
     // Utilities.runFSM(FSM, 1, "a a b a b", " ");
     
   }// End of main()
   
}//End of class WMethod




