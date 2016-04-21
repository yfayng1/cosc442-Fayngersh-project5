
import java.util.*;

/**
 * 
 LabelAndEdgeSet consists of a string a/b indicating the label of an edge.
 a is the input symbol and b the corresponding output symbol.
 
 A label is also associated with a set of edges for which this is the label.
 
 Coded by: Aditya Mathur
 March 17, 2004.
 
 */
public class LabelAndEdgeSet {
  
  
  private String label;  // The actual label of kind a/b.
  private Set edgesWithLabel; // Set of edges for which "label" is the label.
  
  
  public LabelAndEdgeSet(){
    label=""; // No label assigned yet.
    edgesWithLabel=new HashSet(); // No correspnding edges.
  }
  
  public LabelAndEdgeSet(String l){
    label="l"; // No label assigned yet.
    edgesWithLabel=new HashSet(); // No correspnding edges.
  }
  
  public String getLabel(){
    return(label);
  }
  
  public Set getLabelEdges(){
    return(edgesWithLabel);
  }
  
  public void addEdge(Edge e){
    edgesWithLabel.add(e);
  }
  public void addLabel(String l){
    label=l;
  }
}// End of class LabelAndEdgeSet
