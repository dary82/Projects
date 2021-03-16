package model.adt.stack;

import java.util.Deque;

public interface MyIStack<T> {
    T pop();
    void push(T v);
    Deque<T> getStack();
    boolean isEmpty();
    String toString();
    String toStringFile();
}
