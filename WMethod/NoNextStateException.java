

/**
 * 
 Coded by: Aditya Mathur
 March 17, 2004.
 
 For use in class State when no next state is found for an input symbol.
 
 */
public class NoNextStateException extends Exception{
  

  private String m;
  
  
  public NoNextStateException()
  {
    super("No next state found");
    
  }
  
  public NoNextStateException(String message)
  {
    super(message);
    m=message;
  }
  
  public String message()
  {
    return(m);
  }
  
}
