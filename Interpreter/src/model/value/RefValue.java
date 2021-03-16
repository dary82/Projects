package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue(int addr, Type type) {
        address=addr;
        locationType=type;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Value another) {
        if(another instanceof RefValue)
            return address==((RefValue) another).getAddress() && locationType.equals(another.getType());
        return false;
    }

    public String toString() {
        return ""+address+","+locationType.toString();
    }
}
