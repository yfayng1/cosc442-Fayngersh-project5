import java.util.*;

/**
 * 
 An EdgeSet is a collection of edges. An edge is an Edge.
 
 Aditya Mathur
 Last update: March 17, 2004.
 
 */

public class EdgeSet {
  
  
  private  HashSet edges; // Contains a set of outgoing edges from a state (node).
  private int numberOfEdges; // Actual number of elements in the set.
  
  public EdgeSet(){
    edges=new HashSet();  // Create a new empty set of edges.
    numberOfEdges=0;  // Empty set.
  }
  
  public EdgeSet(Edge e){
    super();
    addEdge(e);
  }
  
  // Add an edge.
  
  public void addEdge(Edge e){
    edges.add(e);
    numberOfEdges++; // One element added.
  }
  
  public void removeEdge(Edge e){
    edges.remove(e);
    numberOfEdges--; // One element deleted.
  }
  
  public HashSet getEdgeSet(){
    return(edges);
  }
  public int getEdgeCount(){
    return (edges.size());
  }
  
  
  public static void printEdgeSet(EdgeSet es){
    HashSet e=new HashSet();
    e=es.getEdgeSet();
    Iterator E=e.iterator();
    while(E.hasNext())
    {
      Edge nextEdge=(Edge)E.next();
      System.out.println(nextEdge.head()+" "+ nextEdge.input()+" "+ nextEdge.output()+" "+ nextEdge.tail());
    }
  }
  
}// End of class EdgeSet.
