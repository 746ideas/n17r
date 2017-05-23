/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import adt.IntQueue;

/**
 *
 * @author Мадияр
 */
public class ArrayIntQueue implements IntQueue{
    
    private int[] values;
    private int size;
    private int front;
    private int back;
    
    public ArrayIntQueue(){
        values = new int[5];
        size=0;
        front=0;
        back=0;
    }
    @Override
    public void enqueue(int value) {
        if(size<values.length){
            back = back % values.length;
            values[back]=value; 
        }else{
            int[] doubled=new int[2*values.length];
            for(int i=0; i<values.length ;i++){
                doubled[i]=values[i];
            }
            values=doubled;
            back = back % values.length;
            values[back]=value;
        }
        size++;
        back++;
    }

    @Override
    public int dequeue() throws Exception{
        if(size==0){
            throw new Exception("No elements in queue");
        }
        int result = values[front];
        front=(front+1)%values.length;
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
        front=0;
        back=0;
    }
    
    public String toString(){
        String valuesToString="";
        if(front < back){
            for(int i=front; i<back;i++){
                valuesToString+=values[i]+" ";
            }
        }else{
            for(int i=front; i<values.length; i++){
                valuesToString+=values[i]+" ";
            }
            for(int i=0; i<back; i++){
                valuesToString+=values[i]+" ";
            }
        }
        return valuesToString;
    }
}
