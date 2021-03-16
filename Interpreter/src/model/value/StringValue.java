package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    private String val;

    public StringValue() {
        val="";
    }

    public StringValue(String v) {
        val=v;
    }

    public String getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Value another) {
        if(another instanceof StringValue)
            return val.equals(((StringValue) another).getVal());
        return false;
    }

    public String toString(){
        return val;
    }
}
