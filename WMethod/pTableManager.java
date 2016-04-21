
import java.io.*;


/*
 * Author: Brandon Wuest.
 */

import java.util.*;

public class pTableManager{
  
  
  public pTable[] tableArray;
  public State[] FSMArray;
  public int currentNumberOfGroups;
  public static int maxTables = 50;
  public int numberOfStates;
  public Vector  W;
  public int tableCount;
  public pTable firstTable;
  
  public pTableManager(State [] stateArray, int numStates, String [] inputArray){
    tableArray = new pTable[maxTables]; // Create array to hold P-tables
    FSMArray = stateArray;
    numberOfStates = numStates;
    firstTable = new pTable(stateArray, numStates, inputArray);// First P-table.
    
    W = new Vector();// Initialize the W-set.
    
    tableArray[0] = firstTable.getPOne().returnCopy();
    
    if(tableArray[0].numGroups == numberOfStates){
      Utilities.debugPtable("Special case: only 1 P-table");
      tableCount = 1;
      currentNumberOfGroups = tableArray[0].numGroups;
      calculateW(FSMArray);
      return;
    }
    
    
    tableArray[1] = firstTable.getPNext().returnCopy();
    
    
    if(tableArray[1].numGroups == numberOfStates){
      Utilities.debugPtable("Special case: 2 P-tables");
      tableCount = 2;
      currentNumberOfGroups = tableArray[1].numGroups;
      calculateW(FSMArray);
      return;
    }
    
    int count = 2;
    Utilities.debugPtable("Normal case: more than two tables");
    Utilities.debugPtable("Number of States:" + numberOfStates);
    
    
    while(currentNumberOfGroups < numberOfStates){
      tableArray[count] = firstTable.getPNext().returnCopy();
      currentNumberOfGroups = tableArray[count].numGroups;
      Utilities.debugPtable("Current Number Of Groups:" + currentNumberOfGroups);
      Utilities.debugPtable("Number of States:" + numberOfStates);
      Utilities.debugPtable("Added a table.");
      Utilities.debugPtable("Length = " + (count+1));
      count++;
    }
    
    tableCount = count;
    calculateW(FSMArray);
    
  }//End of pTableManager()
  
  
  public void calculateW(State[] stateArray){
    String z = "";
    
    //if there's only one table, get the heck out of here!
    if(tableArray[0].numGroups == numberOfStates){
      String last = "";
      for(int a = 1; a <= numberOfStates; a++){
        for(int b = 1; b <= numberOfStates; b++){
          if(a != b){
            int currentA = a;
            int currentB = b;
            Utilities.debugPtable("A:" + a);
            Utilities.debugPtable("B:" + b);
            last = tableArray[0].OCompare(currentA, currentB);
            if(!W.contains((Object) last) && !last.equals(""))
              W.add((Object) last);
          }// End if
        }// End inner for
      }// End outer for
      
      // Print the W set.
      if (Utilities.WSetDebugSw)
        printW(W);
      return;
    }
   
    for(int i = 1; i <= numberOfStates; i++){
      for(int j = 1; j <= numberOfStates; j++){
        if(i != j){
          for(int k = 0; k < tableCount-1; k++){ // k = 0; k < tablecount-1
            
            State iState = FSMArray[i];
            State jState = FSMArray[j];
            Integer iValue = new Integer(iState.getID());
            Integer jValue = new Integer(jState.getID());
 
            if((tableArray[k].findGroup(iValue.toString()) == tableArray[k].findGroup(jValue.toString())) &&
               (tableArray[k+1].findGroup(iValue.toString()) != tableArray[k+1].findGroup(jValue.toString()))){

              //we found our r... it's k
              //states are iValue, jValue
              int currentI = iState.getID();
              int currentJ = jState.getID();
              
              z = "";
              int r = k;
              
              while( r >= 0 ){
                Utilities.debugPtable("Current r: " + r);
                //Utilities.debugPtable("Before GCompare: " + currentI + " , " + currentJ);
                String nextSymbol = tableArray[r].GCompare(currentI, currentJ);
                //Utilities.debugPtable("After GCompare");
                z = z.concat(nextSymbol);
                Utilities.debugPtable("Z:" + z);
                
                try{
                  currentI = Integer.parseInt(tableArray[r].O(currentI, nextSymbol));
                  currentJ = Integer.parseInt(tableArray[r].O(currentJ, nextSymbol));
                } catch(Exception e) { 
                  Utilities.printException("pTableManager", "CalculateW", "THIS SHOULD NOT HAPPEN"); 
                }
                //Utilities.debugPtable("Next I and J: " + r + ", " + currentI + ", " + currentJ );
                r--;
              }//while
              
              String lastSymbol = tableArray[0].OCompare(currentI, currentJ);
              Utilities.debugPtable("Last Symbol: " + lastSymbol);
              z = z.concat(lastSymbol);
              Utilities.debugPtable("Z Adding to W:" + z);
              
              
              if(!W.contains((Object) z)){
                W.addElement((Object) z);
              }
            }else{
              Utilities.debugPtable("No r was found.");
              String lastSymbol = tableArray[0].OCompare(iValue.intValue(), jValue.intValue());
              if(!W.contains((Object) lastSymbol) && (!lastSymbol.equals(""))){
                if((iValue.intValue() == 3) && (jValue.intValue() == 4))
                  Utilities.debugPtable("HELLO");
                W.addElement((Object) lastSymbol);
              }              
            }
          }//for
        }//if 
      }//for
    }//for

   // Print the W set.
    if (Utilities.WSetDebugSw)
      printW(W);
   
  }// End of calculateW()
  
  
  public void printW(Vector EW){
      System.out.println("\nW Set. "+ W.size()+" entries.");
      Collections.sort(W);
      Iterator wIterator = W.iterator();
      
      while(wIterator.hasNext()){
        String wElement = (String) wIterator.next();
        System.out.print(wElement+" "); 
      }
      System.out.println();
  }// End of printW()
  
  public Vector getW(){
    return W;
    
  }// End of getW()
  
}// End of class pTableManager