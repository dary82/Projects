package model.adt.list;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList(){
        list=new ArrayList<T>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean add(T v) {
        return list.add(v);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public String toString(){
        return list.toString();
    }

    @Override
    public String toStringFile() {
        StringBuilder result =new StringBuilder();
        for(T item : list)
        {
            result.append(item.toString()).append("\n");
        }
        return result.toString();
    }
}
