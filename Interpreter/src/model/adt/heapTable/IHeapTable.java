package model.adt.heapTable;

import java.util.HashMap;
import java.util.Map;

public interface IHeapTable<T1,T2> {
    T2 get(T1 k);
    T2 put(T1 k, T2 v);
    void remove(T1 k);
    int size();
    boolean containsKey(T1 k);
    int getAddress();
    String toString();
    String toStringFile();
    void setContent(Map<T1,T2> map);
    HashMap<T1,T2> getContent();
}
