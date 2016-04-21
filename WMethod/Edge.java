
import java.util.*;

/**
 * 
 Defines an edge in a finite state machine.
 
 An edge is a pair  (source, target) where is the source node and j is the destination node.
 An edge also has a label a/b where a is an input symbol, and b is an output symbol.
 
 Programmer: Aditya Mathur
 First version: March 16, 2004
 Last revision: March 22, 2004
 */

public class Edge {
  
  private int sourceState;
  private int targetState;
  private String label;
  private String inputSymbol, outputSymbol;
  private int cost; // Cost of traversing this edge.
  
  public Edge(int sourceID, int targetID, String inputS, String outputS)
  {
    sourceState=sourceID;
    targetState=targetID;
    inputSymbol=inputS;
    outputSymbol=outputS;
    label=inputSymbol+"/"+outputSymbol;
    cost=1;  // Default cost of traversal.
  }
  
  public Edge(int sourceID, int targetID)
  {
    sourceState=sourceID;
    targetState=targetID;
    inputSymbol="Input  Unknown";
    outputSymbol="Output Unknown";
    label=inputSymbol+"/"+outputSymbol;
    cost=1;  // Default cost of traversal.
  }
  
  public Edge(int sourceID, int targetID, int costOfTraversal)
  {
    sourceState=sourceID;
    targetState=targetID;
    inputSymbol="Input  Unknown";
    outputSymbol="Output Unknown";
    label=inputSymbol+"/"+outputSymbol;
    cost=costOfTraversal;  // Given cost of traversal.
  }
  
  public Edge()
    
  {
    label="No label";
    cost=1; // Default cost.
  }
  public String displayEdge()
  {
    
    return("("+Integer.toString(sourceState)+","+Integer.toString(targetState)+")"+" Label: " +label);
  }
  
  public int head()
  {
    return(sourceState);
  }
  
  public int tail()
  {
    return(targetState);
  }
  
  public String getLabel()
  {
    return(label);
  }
  
  // Return input symbol for this edge.
  public String input()
  {
    return(inputSymbol);
    
  }
  
  // Return output symbol for this edge.
  public String output()
  {
    return(outputSymbol);
    
  }
  
  public int getCost()
  {
    return(cost);
  }
}
