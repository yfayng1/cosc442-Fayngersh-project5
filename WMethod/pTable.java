
/*
 * Author: Brandon Wuest.
 */

import java.io.*;

public class pTable{
  public pTableEntry[] entries;
  public int numGroups;
  public pTableEntry[] backupEntries;
  public int backupNumGroups;
  public int numberOfStates;
  
  public pTable(pTableEntry[] entryArray, int numberOfGroups){
    
    entries = entryArray;
    numGroups = numberOfGroups;
    backupEntries = entries;
    backupNumGroups = numberOfGroups;
    
  }
  
  
  public pTable(State [] stateArray, int numStates, String [] inputArray){
    numGroups = 1;
    entries = new pTableEntry[numStates];
    backupEntries = new pTableEntry[numStates];
    numberOfStates = numStates;
    
    Utilities.debugPtable("pTable construction");
    
    for(int i = 0; i < numStates; i++){
      Utilities.debugPtable("I:" + i);
      Utilities.debugPtable("BEFORE CONSTRUCTOR");
      entries[i] = new pTableEntry(stateArray[i+1], inputArray);
      Utilities.debugPtable("One entry was correctly generated");
      
    }
    
    
    for(int j = 0; j < entries.length; j++){
      if(Utilities.pTableDebugSw)
        entries[j].printEntry();
    }
    backupEntries = entries;
    backupNumGroups = numGroups;
    Utilities.debugPtable("DONE.");
  }
  
  public pTable getPOne(){
    
    //for all entries, check i and i+1 and see if they're the same. (output-wise)
    //if they are, do nothing...if they aren't, update i+1's group number and the number of total groups.
    
    //then, relabel by checking each entry's group number and the nextState array.
    //for each nextState in each entry, find its group in the table.
    
    
    for(int i = 0; i < entries.length - 1; i++){
      
      String [] firstOutputs = entries[i].outputs;
      String [] secondOutputs = entries[i+1].outputs;
      int sameCount = 0;
      
      Utilities.debugPtable("I is currently: " + i);
      
      for(int j = 0; j < firstOutputs.length; j++){
        if(firstOutputs.length != secondOutputs.length){
          Utilities.debugPtable("ERROR!  SOMETHING IS WRONG WITH OUTPUTS, LENGTHS DO NOT MATCH");
        }else{
          Utilities.debugPtable("Comparing " + firstOutputs[j] + " and " + secondOutputs[j]); 
          if(firstOutputs[j].equals(secondOutputs[j])){
            Utilities.debugPtable("Compared " + firstOutputs[j] + " and " + secondOutputs[j]); 
            sameCount++;
            Utilities.debugPtable("Same count: " + sameCount);
          } //if
        }//if
      }//for
      
      if(sameCount != firstOutputs.length){ //we need to separate these. 
        Utilities.debugPtable("Adding a split between " + entries[i].currentState + " and " + entries[i+1].currentState);
        entries[i+1].currentGroup = entries[i].currentGroup + 1;
        Utilities.debugPtable("Updated group for " + entries[i+1].currentState + " to " + entries[i+1].currentGroup);
        //++numGroups;
      }else{  //they are the same, so their groups should be the same too
        Utilities.debugPtable("Group for entries[i]: " + entries[i].currentGroup);
        Utilities.debugPtable("Group for entries[i+1]: " + entries[i+1].currentGroup);
        entries[i+1].currentGroup = entries[i].currentGroup;
      }
      sameCount = 0;
    }//for
    
    //now relabel
    //for all entries, for all nextStates in each entry, return the group number corresponding to that entry and put it 
    //in the nextGroups array.
    
    
    for(int k = 0; k < entries.length; k++){
      
      for(int l = 0; l < entries[k].nextStates.length; l++){
        
        if(!entries[k].nextStates[l].equals("") && !entries[k].nextStates[l].equals("-1")){
          int g = findGroup(entries[k].nextStates[l]);
          Integer group = new Integer(g);
          entries[k].nextGroups[l] = group.toString();
        }
      } 
    }
    
    numGroups = entries[numberOfStates-1].currentGroup;
    Utilities.debugPtable("NUMBER OF GROUPS: " + numGroups);
    return this;
    
  }// End of getPOne()
  
  
  public int findGroup(String nextState){
    //find out which entry it is, and then get the group number of that entry.
    int stateToFind = 0;
    try{
      stateToFind = Integer.parseInt(nextState);
    }catch(Exception e) {
      Utilities.debugPtable("Next State (parse error): " + nextState);
      Utilities.debugPtable("FATAL ERROR. INTEGER CANNOT BE PARSED");
      //stateToFind = -1;
    }
    
    for(int i = 0; i < entries.length; i++){
      if(entries[i].currentState == stateToFind){
        return entries[i].currentGroup;
      }
      
    }//for
    
    return -1;
    
  }// Ened of fineGroup()
  
  
  public pTable getPNext(){
    
    //for all entries, check i and i+1 and see if they're the same. (nextGroup-wise)
    //if they are, do nothing...if they aren't, update i+1's group number and the number of total groups.
    
    //then, relabel by checking each entry's group number and the nextState array.
    //for each nextState in each entry, find its group in the table.
    
    
    for(int i = 0; i < entries.length - 1; i++){
      String [] firstGroups = entries[i].nextGroups;
      String [] secondGroups = entries[i+1].nextGroups;
      int sameCount = 0;
      
      Utilities.debugPtable("I is currently: " + i);
      
      for(int j = 0; j < firstGroups.length; j++){
        if(firstGroups.length != secondGroups.length){
        Utilities.debugPtable("ERROR!  SOMETHING IS WRONG WITH GROUPS, LENGTHS DO NOT MATCH");
      }else{
        Utilities.debugPtable("Comparing " + firstGroups[j] + " and " + secondGroups[j]); 
        
        if(firstGroups[j].equals(secondGroups[j])){
          Utilities.debugPtable("Compared " + firstGroups[j] + " and " + secondGroups[j]); 
        sameCount++;
        Utilities.debugPtable("Same count: " + sameCount);
      } //if
    }//if
  }//for
  
      if(sameCount != firstGroups.length){ //we need to separate these.
        Utilities.debugPtable("Adding a split between " + entries[i].currentState + " and " + entries[i+1].currentState);
        entries[i+1].currentGroup = entries[i].currentGroup + 1;
        Utilities.debugPtable("Updated group for " + entries[i+1].currentState + " to " + entries[i+1].currentGroup);
        Utilities.debugPtable("NUM GROUPS BEFORE: " + numGroups);
        //++numGroups;
        Utilities.debugPtable("NUM GROUPS AFTER: " + numGroups);
        
      }
      else{  //they are the same, so their groups should be the same too
        
        Utilities.debugPtable("Group for entries[i]: " + entries[i].currentGroup);
        Utilities.debugPtable("Group for entries[i+1]: " + entries[i+1].currentGroup);
        
        entries[i+1].currentGroup = entries[i].currentGroup;
      }
      sameCount = 0;
    }//for
    
    //now relabel
    //for all entries, for all nextStates in each entry, return the group number corresponding to that entry and put it 
    //in the nextGroups array.
    
    
    for(int k = 0; k < entries.length; k++){
      for(int l = 0; l < entries[k].nextStates.length; l++){
        if(!entries[k].nextStates[l].equals("") && !entries[k].nextStates[l].equals("-1")){
          int g = findGroup(entries[k].nextStates[l]);
          Integer group = new Integer(g);
          entries[k].nextGroups[l] = group.toString();
        }// End of if
        
      }// End of for
      
    }// End of for
    
    numGroups = entries[numberOfStates-1].currentGroup;
    Utilities.debugPtable("NUMBER OF GROUPS: " + numGroups);
    
    return this;
    
  } // End of getPNext()
  
  
  public void printTable(){
    for(int m = 0; m < entries.length; m++){
      entries[m].printEntry();
    }  
  }// End of printTable()
  
  
  public pTable returnCopy(){
    
    pTableEntry [] copyEntries = new pTableEntry[numberOfStates];
    int copyGroups = numGroups;
    
    for(int i = 0; i < copyEntries.length; i++)
    {
      copyEntries[i] = entries[i].returnCopy();
    }
    return new pTable(copyEntries, copyGroups);
    
  }// End of returnCopy

  
  public String GCompare(int state1, int state2){
    for(int i = 0; i < entries[state1-1].nextGroups.length; i++){
      if(!entries[state1-1].nextGroups[i].equals(entries[state2-1].nextGroups[i])){
        Utilities.debugPtable("GCompare: " + state1 + ", " + state2 + " = " + entries[state1-1].possibleInputs[i]);
        return entries[state1-1].possibleInputs[i];
      } 
    }
    return "";
  }// End of GCompare()
  
  public String O (int state, String symbol){
    
    int inputPos = -1;
    for(int i = 0; i < entries[state-1].possibleInputs.length; i++){
      if(entries[state-1].possibleInputs[i].equals(symbol)){
        inputPos = i;
        break;
      }// End of if
    }//for
    
    Utilities.debugPtable("O: " + state + ", " + symbol + " = " + entries[state-1].nextStates[inputPos]);
    return entries[state-1].nextStates[inputPos];
    
  }// End of O()
  
  
  public String OCompare(int state1, int state2){
    for(int i = 0; i < entries[state1-1].outputs.length; i++){
      if(!entries[state1-1].outputs[i].equals(entries[state2-1].outputs[i])){
        Utilities.debugPtable("OCompare: " + state1 + ", " + state2 + " = " + entries[state1-1].possibleInputs[i]);
        return entries[state1-1].possibleInputs[i];
      }
    }
    return "";
  }// End of OCompare()
  
}// End of class pTable