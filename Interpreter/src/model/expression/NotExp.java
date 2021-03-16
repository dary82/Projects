package model.expression;

import exceptions.MyException;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class NotExp implements Exp {
    public Exp exp;

    public NotExp(Exp e) {
        exp=e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeapTable<Integer, Value> heaptbl) throws MyException {
        BoolValue v=(BoolValue) exp.eval(tbl,heaptbl);
        if (v.getVal())
            return new BoolValue(false);
        else
            return new BoolValue(true);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return exp.typecheck(typeEnv);
    }

    @Override
    public String toString() {
        return "!"+exp.toString();
    }
}
