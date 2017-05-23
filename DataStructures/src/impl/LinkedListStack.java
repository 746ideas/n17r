/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import adt.Node;
import adt.Stack;


/**
 *
 * @author Мадияр
 */
public class LinkedListStack<T> implements Stack<T>{
    private Node<T> top;
    private int size;

    public LinkedListStack() {
        top = null;
        size = 0;
    }

    @Override
    public void push(T value) {

        size++;
        if(top == null) {
            top = new Node<T>(value);
            top.setLink(null);
        }
        else {
            Node<T> tmp = new Node<T>(value);
            tmp.setLink(top);
            top = tmp;
        }
    }


    @Override
    public T pop() throws Exception {
        if(size == 0){
            throw new Exception("Empty Stack");
        }
        size--;
        Node<T> tmp = top;
        top = top.getLink();
        return tmp.getValue();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        top = null;
    }

    public String toString() {
        String result = "top [";
        Node<T> temp = top;
        while(temp != null) {
            result += temp.getValue() + " ";
            temp = temp.getLink();
        }
        result += "] bottom";
        return result;
    }
}
