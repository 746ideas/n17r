/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import adt.Node;
import adt.SortedQueue;

/**
 *
 * @author Мадияр
 * @param <T>
 */
public class LinkedListSortedQueue<T extends Comparable> implements SortedQueue<T>{
    
    private Node<T> front;

    private int size;


    public LinkedListSortedQueue() {

        front = null;
        size = 0;
    }

    @Override
    public void insert(T value) {
    	Node<T> newNode = new Node(value);
        // Node<T> curr = front;
         Node<T> prev;
         if(size == 0)
             front = newNode;
         else {
             //newNode goes before front
            
             if(value.compareTo(front.getValue()) <= 0) {
                 newNode.setLink(front);
                 front = newNode;
             }
             else {
                 prev = front;
                 while(true) {
                     //newNode is added at the end
                     if(prev.getLink() == null) {
                         prev.setLink(newNode);
                         break;
                     }
                     //newNode goes somewhere in the middle
                     if(value.compareTo(prev.getLink().getValue()) >= 0) {
                         prev = prev.getLink();
                     }
                     else {
                         newNode.setLink(prev.getLink());
                         prev.setLink(newNode);
                         break;
                     }
                 }
             }
         }
         size++;
    }


    @Override
    public T dequeue() throws Exception {
        if(size == 0){
            throw new Exception("Empty queue");
        }
        Node<T> temp = front;
        front = front.getLink();
        size--;
        return temp.getValue();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        front = null;
    }

    @Override
    public String toString() {
        String result = "";

        Node<T> temp = front;
        while(temp != null) {

            result += temp.getValue() + "\n";
            temp = temp.getLink();
        }

        return result;
    }
    
}
