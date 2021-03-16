package model.adt.list;

public interface MyIList<T> {
    int size();
    boolean isEmpty();
    boolean add(T v);
    void clear();
    T get(int index);
    String toString();
    String toStringFile();
}
