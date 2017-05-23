/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import adt.Node;
import adt.Queue;

/**
 *
 * @author Мадияр
 * @param <T>
 */
public class LinkedListQueue<T> implements Queue<T> {

    private Node<T> front;
    private Node<T> back;
    private int size;


    public LinkedListQueue() {

        front = back = null;
        size = 0;
    }

    @Override
    public void enqueue(T value) {

        if(size == 0) {
            Node<T> temp = new Node<>(value);
            temp.setLink(null);
            front = temp;
            back = temp;
        }
        else {
            Node<T> temp = new Node<> (value);
            temp.setLink(null);
            back.setLink(temp);
            back = temp;
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
        front = back = null;
    }

    @Override
    public String toString() {
        String result = "";

        Node<T> temp = front;
        while(temp != null) {

            result += temp.getValue() + " ";
            temp = temp.getLink();
        }

        return result;
    }
    
}

