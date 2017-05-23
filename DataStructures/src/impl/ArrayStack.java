/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;


import adt.Stack;

/**
 *
 * @author Мадияр
 * @param <T>
 */
public class ArrayStack<T> implements Stack<T>{
    
    private T[] values;
    private int size;
    
    
    public ArrayStack(){
        values = (T[])new Object[10];
        size = 0;
    }

    @Override
    public void push(T value) {
        if(size<values.length){
            values[size]=value;
            size++;
        }else {
            T[] doubled=(T[])new Object[2*values.length];
            for(int i=0; i<values.length ;i++){
                doubled[i]=values[i];
            }
            values=doubled;
            values[size]=value;
            size++;
        }
    }

    @Override
    public T pop() {
        if(size==0){
            System.out.println("no values in stack");
        }
        T result=values[size-1];
        size--;
        return result;        
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        values=(T[])new Object[10];
        size=0;
    }
    
    public String toString(){
        String valuesToString="";
        for(int i=0; i<size;i++){
            valuesToString+=" "+values[i];
        }
        return "bottom["+valuesToString+"]top";
    }
    
}
