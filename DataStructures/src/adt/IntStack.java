/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

/**
 *
 * @author Мадияр
 */
public interface IntStack {
         /**
     * Puts the given element on the top of the stack.
     * 
     * @param value element to be added on the top of the stack
     */
    public void push(int value);
    
    /**
     * Removes and returns the top most element of the stack
     * 
     * @return the top most element of the stack
     * @throws Exception if the stack is empty
     */
    public int pop();
        
    /**
     * @return the size of the stack
     */
    public int getSize();
    
    /**
     * Removes all elements from the stack
     */
    public void clear();
       
    /**
     * @return a String representation of the stack
     */
    @Override
    public String toString();
 
}

