/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;


import adt.Queue;

/**
 *
 * @author Мадияр
 * @param <T>
 */
public class ArrayQueue<T> implements Queue<T>{
    
    private T[] values;
    private int size;
    private int front;
    private int back;
    
    public ArrayQueue(){
        values = (T[])new Object[5];
        size=0;
        front=0;
        back=0;
    }
    @Override
    public void enqueue(T value) {
        if(size<values.length){
            back = back % values.length;
            values[back]=value; 
        }else{
            T[] doubled=(T[])new Object[2*values.length];
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
    public T dequeue() throws Exception{
        if(size==0){
            throw new Exception("No elements in queue");
        }
        T result = values[front];
        values[front]=null;
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
        values=(T[])new Object[5];
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
