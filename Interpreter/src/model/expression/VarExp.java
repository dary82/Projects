package model.expression;

import model.adt.dictionary.MyIDictionary;
import exceptions.MyException;
import model.adt.heapTable.IHeapTable;
import model.type.Type;
import model.value.Value;

public class VarExp implements Exp{
    private String id;

    public VarExp(String i) {
        id=i;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeapTable<Integer, Value> heaptbl) throws MyException {
        return tbl.get(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.get(id);
    }

    public String toString() {
        return id;
    }
}
