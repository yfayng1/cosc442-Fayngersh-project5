/*
 * Author: Brandon Wuest.
 */
import java.io.*;
import java.util.*;

public class TestingTreeNode{
  
  State currentState;
  boolean markedState;
  int vertexNum;
  int level;
  Vector branchVector;
  
  public TestingTreeNode(State s1){
    
    currentState = s1;
    branchVector = new Vector();
    markedState = false;
    vertexNum = 0;
    level = 0;
    
  }
  
  public TestingTreeNode(State s1, int vertexNumber, int lev){
    currentState = s1;
    branchVector = new Vector();
    markedState = false;
    vertexNum = 0;
    level = lev;
    
  }
  
  public TestingTreeNode(State s1, Vector bv, boolean ms, int vn){
    currentState = s1;
    branchVector = bv;
    markedState = ms;
    vertexNum = vn;
    
  }
  
  public TestingTreeNode(State s1, int vertexNumber)
  {
    
    currentState = s1;
    branchVector = new Vector();
    markedState = false;
    vertexNum = vertexNumber;
  }
  
  
  
  public void printNode(){
    System.out.println("PRINTING NODE");
    System.out.println("CurrentState: " + currentState.getID());
    
    if(branchVector.isEmpty()){
      System.out.println("BranchVector: " + "EMPTY");
    }else{
      Iterator branchIterator = branchVector.iterator();
      while(branchIterator.hasNext()){
        TestingTreeBranch current = (TestingTreeBranch) branchIterator.next();
        current.printBranch();
        
      }
    }
  }
  
  public TestingTreeNode returnCopy(){
    return new TestingTreeNode(currentState, branchVector, markedState, vertexNum);
    
  }
}