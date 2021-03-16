package model.adt.fileTable;

import model.adt.dictionary.MyDictionary;
import model.adt.dictionary.MyIDictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileTable<T1,T2> implements MyIDictionary<T1,T2> {
    private HashMap<T1,T2> dictionary;

    public FileTable(){
        dictionary=new HashMap<T1,T2>();
    }

    @Override
    public T2 get(T1 k) {
        return dictionary.get(k);
    }

    @Override
    public T2 put(T1 k, T2 v) {
        return dictionary.put(k,v);
    }

    @Override
    public void remove(T1 k) {
        dictionary.remove(k);
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public boolean containsKey(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public Collection<T2> getValues() {
        return dictionary.values();
    }

    @Override
    public String toString(){
        return dictionary.toString();
    }

    @Override
    public String toStringFile() {
        StringBuilder result =new StringBuilder();
        for(T1 key:dictionary.keySet())
        {
            result.append(key.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public void setContent(Map<T1, T2> map) {
        return;
    }

    @Override
    public HashMap<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public MyIDictionary<T1, T2> deepcopy() {
        MyIDictionary<T1,T2> dict=new MyDictionary<>();
        for (T1 key:dictionary.keySet())
            dict.put(key,dictionary.get(key));
        return dict;
    }
}
