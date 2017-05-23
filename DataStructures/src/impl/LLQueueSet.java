/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import adt.Set;

/**
 *
 * @author Мадияр
 * @param <T>
 */
public class LLQueueSet<T> implements Set<T> {

    private LinkedListQueue<T> hold;
    private int size;
    
    public LLQueueSet(){
        hold=new LinkedListQueue();
        size=0;
    }
    
    @Override
    public void add(T value) {
        int counter=0;
        for(int i=0; i<size; i++){
            T temp;
            try {
                temp=hold.dequeue();
                if(temp.equals(value)){
                    counter++;
                }
                hold.enqueue(temp);
            } catch (Exception ex) {
               System.out.println(ex.getMessage());
            }
        }
        if(counter==0){
            hold.enqueue(value);
            size++;
        }
        
    }

    @Override
    public boolean contains(T value) {
        int counter=0;

        for(int i=0; i<size; i++){
            T temp;
            try {
                temp=hold.dequeue();
                if(temp.equals(value)){
                    counter++;   
                }
                hold.enqueue(temp);
            } catch (Exception ex) {
               System.out.println(ex.getMessage());
            }
        }
        if(counter==0){
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(T value) {
        int counter=0;

        for(int i=0; i<size; i++){
            T temp;
            try {
                temp = hold.dequeue();
                if(temp.equals(value)){
                    counter++;
                    temp=hold.dequeue();
                }
                hold.enqueue(temp);
            } catch (Exception ex) {
               System.out.println(ex.getMessage());
            }
        }
        if(counter==0){
            return false;
        }
        size--;
        return true;
    }

    @Override
    public T removeAny() throws Exception {
        if(size==0){
            throw new Exception("Set is empty");
        }
        T temp = null;
        try{
            temp = hold.dequeue();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        size--;
        return temp;
    }

    @Override
    public int getSize() {
        return hold.getSize();
    }

    @Override
    public void clear() {        
        hold=new LinkedListQueue();
        size=0;
    }  
    @Override
    public String toString(){
        String result="{";
        for(int i=0; i<size; i++){
            T temp = null;
            try{
                temp = hold.dequeue();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            result+=temp+" ";
            hold.enqueue(temp);
        }
        result+="}";
        return result;
    }
}
