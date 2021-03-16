package model.adt.latchTable;

import java.util.HashMap;

public class LatchTable implements ILatchTable {
    private HashMap<Integer,Integer> latchTable;
    private int freeLocation=0;

    public LatchTable() {
        latchTable=new HashMap<Integer,Integer>();
    }

    public LatchTable(HashMap<Integer,Integer> table) {
        latchTable=table;
    }

    @Override
    public void put(int key, int value) {
        latchTable.put(key,value);
    }

    @Override
    public int get(int key) {
        return latchTable.get(key);
    }

    @Override
    public boolean containsKey(int key) {
        return latchTable.containsKey(key);
    }

    @Override
    public int getFreeAddress() {
        freeLocation++;
        return freeLocation;
    }

    @Override
    public void update(int key, int value) {
        latchTable.replace(key,value);
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        freeLocation=freeAddress;
    }

    @Override
    public HashMap<Integer, Integer> getLatchTable() {
        return latchTable;
    }

    @Override
    public void setLatchTable(HashMap<Integer, Integer> table) {
        latchTable=table;
    }

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();
        for(Integer key:latchTable.keySet())
            s.append(key.toString()+"->"+latchTable.get(key).toString()+" ");
        return s.toString();
    }
}
