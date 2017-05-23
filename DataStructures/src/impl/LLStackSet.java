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
public class LLStackSet<T> implements Set<T> {
    
    private LinkedListStack<T> hold;
    private int size;
    
    public LLStackSet(){
        hold=new LinkedListStack();
        size=0;
    }
    @Override
    public void add(T value) {
        LinkedListStack<T> temp=new LinkedListStack();
        int counter=0;
        for(int i=0; i<size; i++){
            T temp2;
            try {
                temp2=hold.pop();
                if(temp2.equals(value)){
                    counter++;
                }
                temp.push(temp2);
            } catch (Exception ex) {
               System.out.println(ex.getMessage());
            }
        }
        if(counter==0){
            temp.push(value);
            size++;
        }
        while(temp.getSize()!=0){
            T temp2 = null;
            try {
                temp2= temp.pop();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            hold.push(temp2);
        }
    }

    @Override
    public boolean contains(T value) {
        LinkedListStack<T> temp=new LinkedListStack();
        int counter=0;
        for(int i=0; i<size; i++){
            T temp2;
            try {
                temp2=hold.pop();
                if(temp2.equals(value)){
                    counter++;
                }
                temp.push(temp2);
            } catch (Exception ex) {
               System.out.println(ex.getMessage());
            }
        }
        while(temp.getSize()!=0){
            T temp2 = null;
            try {
                temp2= temp.pop();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            hold.push(temp2);
        }
        if(counter==0){
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(T value) {
        LinkedListStack<T> temp=new LinkedListStack();
        int counter=0;
        for(int i=0; i<size; i++){
            T temp2;
            try {
                temp2=hold.pop();
                if(temp2.equals(value)){
                    counter++;
                    continue;
                }
                temp.push(temp2);
            } catch (Exception ex) {
               System.out.println(ex.getMessage());
            }
        }
        while(temp.getSize()!=0){
            T temp2 = null;
            try {
                temp2= temp.pop();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            hold.push(temp2);
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
            temp = hold.pop();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        size--;
        return temp;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        hold=new LinkedListStack();
        size=0;
    }
    
    @Override
    public String toString(){
        String result="{";
        LinkedListStack<T> temp=new LinkedListStack();
        for(int i=0; i<size; i++){
            T temp2 = null;
            try{
                temp2 = hold.pop();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            result+=temp2+" ";
            temp.push(temp2);
        }
        result+="}";
        while(temp.getSize()!=0){
            T temp2 = null;
            try {
                temp2= temp.pop();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            hold.push(temp2);
        }
        return result;
    }
}
