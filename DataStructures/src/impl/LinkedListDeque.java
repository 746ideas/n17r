/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import adt.Deque;

/**
 *
 * @author Мадияр
 * @param <T>
 */
public class LinkedListDeque<T> implements Deque<T> {
    
    private DoublyLinkedNode<T> front;
    private DoublyLinkedNode<T> back;
    private int size=0;
    
    public LinkedListDeque(){
        front=null;
        back=null;
        size=0;
    }

    @Override
    public void pushToFront(T value) {
        if(size == 0) {
            DoublyLinkedNode<T> temp = new DoublyLinkedNode<>(value);
            temp.setNext(null);
            temp.setPrevious(null);
            front = temp;
            back = temp;
        }
        else {
            DoublyLinkedNode<T> temp = new DoublyLinkedNode<> (value);
            temp.setNext(front);
            front.setPrevious(temp);
            temp.setPrevious(null);
            front = temp;
        }
        size++;
    }

    @Override
    public void pushToBack(T value) {
        if(size == 0) {
            DoublyLinkedNode<T> temp = new DoublyLinkedNode<>(value);
            temp.setNext(null);
            temp.setPrevious(null);
            front = temp;
            back = temp;
        }
        else {
            DoublyLinkedNode<T> temp = new DoublyLinkedNode<> (value);
            temp.setNext(null);
            back.setNext(temp);
            temp.setPrevious(back);
            back = temp;
        }
        size++;
    }

    @Override
    public T popFromFront() throws Exception {
        if(size == 0){
            throw new Exception("Empty queue");
        }
        if(size == 1){
            size=0;
            back=null;
            front=null;
            return null;
        }else{
            DoublyLinkedNode<T> temp = front;
            front = front.getNext();
            front.setPrevious(null);
            size--;
            return temp.getValue();
    }
    }

    @Override
    public T popFromBack() throws Exception {
        if(size == 0){
            throw new Exception("Empty queue");
        }
        if(size == 1){
            size=0;
            back=null;
            front=null;
            return null;
        }else{
            DoublyLinkedNode<T> temp = back;
            back = back.getPrevious();
            back.setNext(null);
            size--;
            return temp.getValue();
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        front = back = null;
    }
    
    @Override
    public String toString(){
        String result = "front [";

        DoublyLinkedNode<T> temp = front;
        while(temp != null) {

            result += temp.getValue() + " ";
            temp = temp.getNext();
        }
        result +="] back";
        return result;
    }
}
