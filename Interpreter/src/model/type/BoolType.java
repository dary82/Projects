package model.type;

import model.value.IntValue;
import model.value.Value;

public class BoolType implements Type {
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "boolean";
    }

    @Override
    public Value defaultValue() {
        return new IntValue();
    }
}
