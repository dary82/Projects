package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
    private int val;

    public IntValue() {
        val=0;
    }

    public IntValue(int v) {
        val=v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Value another) {
        if(another instanceof IntValue)
            return val==((IntValue) another).getVal();
        return false;
    }

    public String toString(){
        return ""+val;
    }
}
