package model.adt.dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface MyIDictionary<T1,T2> {
    T2 get(T1 k);
    T2 put(T1 k, T2 v);
    void remove(T1 k);
    int size();
    boolean containsKey(T1 k);
    Collection<T2> getValues();
    String toString();
    String toStringFile();
    void setContent(Map<T1,T2> map);
    HashMap<T1,T2> getContent();
    MyIDictionary<T1,T2> deepcopy();
}
