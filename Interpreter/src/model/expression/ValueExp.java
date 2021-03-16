package model.expression;

import model.adt.dictionary.MyIDictionary;
import exceptions.MyException;
import model.adt.heapTable.IHeapTable;
import model.type.Type;
import model.value.Value;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e1) {
        e=e1;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeapTable<Integer, Value> heaptbl) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    public String toString() {
        return e.toString();
    }
}
