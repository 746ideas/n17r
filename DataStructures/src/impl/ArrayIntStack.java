/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import adt.IntStack;

/**
 *
 * @author Мадияр
 */
public class ArrayIntStack implements IntStack{
    
    private int[] values;
    private int size;
    
    
    public ArrayIntStack(){
        values = new int[10];
        size = 0;
    }

    @Override
    public void push(int value) {
        if(size<values.length){
            values[size]=value;
            size++;
        }else {
            int[] doubled=new int[2*values.length];
            for(int i=0; i<values.length ;i++){
                doubled[i]=values[i];
            }
            values=doubled;
            values[size]=value;
            size++;
        }
    }

    @Override
    public int pop() {
        if(size==0){
            System.out.println("no values in stack");
        }
        int result=values[size-1];
        size--;
        return result;        
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        values=new int[10];
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
