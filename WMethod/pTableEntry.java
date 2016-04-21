/*
 * Author: Brandon Wuest.
 */

import java.io.*;
import java.util.*;

public class pTableEntry{

  
  public int currentGroup;
  public int currentState;
  public String[] outputs;
  public String[] nextStates;
  public String[] possibleInputs;
  public String[] nextGroups;
  
  public pTableEntry(int cGroup, int cState,  String [] outpt, String [] nxtst, String [] posInp, String [] ng){
    currentGroup = cGroup;
    currentState = cState;
    outputs = outpt;
    nextStates = nxtst;
    possibleInputs = posInp;
    nextGroups = ng;
    
  }// End of pTableEntry()
  
  
  public pTableEntry(State s, String[] inputArray){
    
    Utilities.debugPtable("HELLO");
    HashSet edgeSet;
    possibleInputs = inputArray;
    nextStates = new String [possibleInputs.length];
    outputs = new String [possibleInputs.length];
    edgeSet = new HashSet(s.getEdgeSet());
    nextGroups = new String [possibleInputs.length];
    
    initializeArrays();
    Utilities.debugPtable("Initialization complete.");
    currentGroup = 1;
    currentState = s.getID();
    Iterator edgeIterator = edgeSet.iterator();
    
    while(edgeIterator.hasNext()){
      
      Edge e = (Edge) edgeIterator.next();
      String input = e.input();
      String output = e.output();
      int nState = e.tail();
      Integer nextState = new Integer(nState);
      
      Utilities.debugPtable("Processing Edge:" + input + "/" + output);
      
      
      for(int i = 0; i < outputs.length; i++){
        if(possibleInputs[i].equals(input)){
          Utilities.debugPtable("Adding " + output + " to " + input);
          outputs[i] = output;
        }
      }
      
      for (int j = 0; j < nextStates.length; j++){
        if(possibleInputs[j].equals(input)){
          Utilities.debugPtable("Adding next state " + nState + " to " + input);
          nextStates[j] = nextState.toString(); 
        }
      }
    }//while
  }// End of another pTableEntry()
  
  
  
  
  public void initializeArrays(){
    for(int i = 0; i < nextStates.length; i++){
      nextStates[i] = "";
    }
    
    for(int j = 0; j < outputs.length; j++){
      outputs[j] = "";
    }
    
    for (int k = 0; k < nextGroups.length; k++){
      nextGroups[k] = "";
    }
  }// End of initializeArrays()
  
  
  
  public void printEntry(){
    System.out.println("TABLE ENTRY");
    System.out.println("STATE: " + currentState);
    System.out.println("GROUP: " + currentGroup);
    System.out.println("ARRAY DATA");
    
    System.out.print("Possible Inputs: ");
    for(int k = 0; k < possibleInputs.length; k++){
      if(possibleInputs[k] != null)
        System.out.print(possibleInputs[k] + " ");
      
    }
    System.out.println("");
    
    System.out.print("Next States: ");
    for(int i = 0; i < nextStates.length; i++){
      if(nextStates[i] != null)
        System.out.print(nextStates[i] + " ");
      
    }
    System.out.println("");
    
    System.out.print ("Outputs: ");
    for(int j = 0; j < outputs.length; j++){
      if(outputs[j] != null)
        System.out.print(outputs[j] + " ");
    }
    System.out.println("");
    System.out.print ("Next Groups: ");
    for(int l = 0; l < nextGroups.length; l++){
      if(nextGroups[l] != null)
        System.out.print(nextGroups[l] + " ");
      
    }
    System.out.println("");
    
  }// End of printEntry()
  
  
  
  public pTableEntry returnCopy(){

    String [] newOutputs = new String[outputs.length];
    String [] newNextStates = new String[nextStates.length];
    String [] newNextGroups = new String[nextGroups.length];
    
    int newCurrentGroup = currentGroup;
    int newCurrentState = currentState;
    
    for(int i = 0; i < outputs.length; i++){
      newOutputs[i] = outputs[i];
    }
    
    for(int j = 0; j < nextGroups.length; j++){
      newNextGroups[j] = nextGroups[j];
    }
    
    for(int k = 0; k < nextStates.length; k++){
      newNextStates[k] = nextStates[k];
      
    }// End of returnCopy()
    
    return new pTableEntry(newCurrentGroup, newCurrentState, newOutputs, newNextStates, possibleInputs, newNextGroups);
    
  }
  
  
}
