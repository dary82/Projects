package model.adt.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private Deque<T> stack;

    public MyStack(){
        stack = new ArrayDeque<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public Deque<T> getStack() {
        return stack;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        return stack.toString();
    }

    @Override
    public String toStringFile() {
        StringBuilder result =new StringBuilder();
        for(T item : stack)
        {
            String str =item.toString();
            String[] split=str.split(";");
            for(String s: split)
            {
                s=s.trim();
                s=s.replaceAll("\\(","");
                s=s.replaceAll("\\)","");
                result.append(s).append('\n');
            }
        }
        return result.toString();
    }
}
