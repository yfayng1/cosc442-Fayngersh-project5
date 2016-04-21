
import java.io.*;
import java.util.*;

public class TestingTreeBranch{
  
  String inputValue;
  TestingTreeNode nextState;
  
  public TestingTreeBranch(String input, TestingTreeNode next){
    
    inputValue = input;
    nextState = next;
    
  }
  
  public void printBranch(){
    
    System.out.println("PRINTING BRANCH");
    System.out.println("Input Value: " + inputValue);
    System.out.println("Next State (Node):");
    nextState.printNode(); 
  }
}