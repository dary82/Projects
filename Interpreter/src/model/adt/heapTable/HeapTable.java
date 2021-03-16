package model.adt.heapTable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class HeapTable<T1,T2> implements IHeapTable<T1,T2> {
    private HashMap<T1,T2> heapTable;
    private int freeAddress;
    public HeapTable() {
        heapTable=new HashMap<T1,T2>();
        freeAddress=1;
    }

    @Override
    public void setContent(Map<T1,T2> map) {
        heapTable.clear();
        heapTable= (HashMap<T1, T2>) map;
    }

    @Override
    public HashMap<T1,T2> getContent() {
        return heapTable;
    }

    @Override
    public T2 get(T1 k) {
        return heapTable.get(k);
    }

    @Override
    public T2 put(T1 k, T2 v) {
        freeAddress++;
        return heapTable.put(k,v);
    }

    @Override
    public void remove(T1 k) {
        heapTable.remove(k);
    }

    @Override
    public int size() {
        return heapTable.size();
    }

    @Override
    public boolean containsKey(T1 id) {
        return heapTable.containsKey(id);
    }

    @Override
    public int getAddress() {
        return freeAddress;
    }

    @Override
    public String toString(){
        return heapTable.toString();
    }

    @Override
    public String toStringFile() {
        StringBuilder result =new StringBuilder();
        for(T1 key:heapTable.keySet())
        {
            result.append(key.toString()).append(" -> ").append(heapTable.get(key).toString()).append('\n');
        }
        return result.toString();
    }
}
