package model.value;

import model.type.BoolType;
import model.type.IntType;
import model.type.Type;

public class BoolValue implements Value{
    private boolean val;

    public BoolValue() {
        val=false;
    }

    public BoolValue(boolean v) {
        val=v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Value another) {
        if(another instanceof BoolValue)
            return val==((BoolValue) another).getVal();
        return false;
    }

    public String toString(){
        return ""+val;
    }
}
