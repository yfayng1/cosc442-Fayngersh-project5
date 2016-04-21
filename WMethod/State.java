
/*
 * Author: Brandon Wuest.
 */

import java.util.*;

/**
 * 
 This class defines a state of any finite state machine. 
 A state has a name, description, and a set of zero or more outgoing edges.
 Several useful methods allow the extraction of this information given a state.
 
 Written by (and copyright): Aditya P. Mathur
 Date started: March 12, 2004
 V1.0 completed:  March 12, 2004
 Last revision: March 22,  2004.
 
 */

public class State {
  
  
  private int  ID; // Unique ID given to each state. (>=0)
  String description; // State description
  private HashSet outGoingEdges; // Set of outgoing edges.
  private String uioSequence; // UIO sequence for this state.
  private boolean stateMark=false;  // State marker useful in some algorithms.
  private int numberOfVisits=0;  // Number of times this state is visited.
  int maxEdges=20; // Max number of outgoing edges from any state.
  
  
  // Define a state.
  
  public State(int stateID){
    if(ID<0){
      System.out.println("ID must be an integer>0. State not initialized.");
      System.exit(0);
    }
    ID=stateID;
    description="No description yet.";
    outGoingEdges=new HashSet(); // Initialize the set of outgoing  edges to empty.
    uioSequence=""; // UIO sequence not found yet.
    stateMark=false;
  }
  
  // Set (true), unset(false), and get mark.
  
  public void mark(){
    stateMark=true;
  }
  
  public void unMark(){
    stateMark=false;
  }
  
  
  public boolean getMark(){
    return(stateMark);
  }
  
  // Add to visits, initialize visits, return visits.
  
  public void stateVisited(){
    numberOfVisits++;
  }
  
  public void resetVisits(){
    numberOfVisits=0;
  }
  
  public void setVisits(int v){
    numberOfVisits=v;
  }
  
  
  public int getVisits(){
    return(numberOfVisits);
  }
  
  // Add an edge to this state.
  // Note that the start state of e must be the same as ID.
  
  public void addEdge(Edge e) throws InvalidEdgeException{
    if (!(e.head()==ID))throw new InvalidEdgeException();
    outGoingEdges.add(e);
  }
  
  
  public void removeEdge(Edge e){
    outGoingEdges.remove(e);
  }
  
  
  
  public HashSet  getEdgeSet(){
    return(outGoingEdges);
  }
  /**
   * 
   Return ID
   */
  
  public int getID(){
    return ID;
  }
  /**
   * 
   Return number of outgoing edges.
   */
  
  public int edgeCount(){
    return (outGoingEdges.size());
  }
  
  /**
   * 
   Return next state ID when an input symbol is received.
   */
  
  public  Edge getNextState(String inputSymbol) throws  NoNextStateException{
    Iterator E=outGoingEdges.iterator();
    while (E.hasNext())
    {
      Edge temp=(Edge)E.next();
      if (temp.input().equals(inputSymbol)) return(temp);
    }
    throw new NoNextStateException(inputSymbol);
  }
  
  
  /**
   * 
   Return the set of next states.
   */
  
  public  HashSet getNextStates() {
    HashSet nextStateSet=new HashSet();
    HashSet e=new HashSet();
    e=outGoingEdges;
    Iterator E=e.iterator();
    while (E.hasNext())
    {
      Edge temp=(Edge)E.next();
      nextStateSet.add(new Integer(temp.tail()));
    }
    return(nextStateSet);
  }
  
  /**
   * 
   Return the set of outgoing edge labels.
   */
  
  public  HashSet getLabels() {
    HashSet labels=new HashSet();
    HashSet e=new HashSet();
    e=outGoingEdges;
    Iterator E=e.iterator();
    while (E.hasNext())
    {
      Edge temp=(Edge)E.next();
      labels.add(temp.getLabel());
    }
    return(labels);
  }
  
}// End of class State
