

/**
 * 
 Coded by: Aditya Mathur
 March 17, 2004.
 
 For use in class State when no next state is found for an input symbol.
 
 */
public class InvalidEdgeException extends Exception {
  

  private String m;
  
  
  public InvalidEdgeException()
  {
    super("Edge head state does not match with state ID");
    
  }
  
  public InvalidEdgeException(String message)
  {
    super(message);
    m=message;
  }
  
  public String message()
  {
    return(m);
  }
  
}
