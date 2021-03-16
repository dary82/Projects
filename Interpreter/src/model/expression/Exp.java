package model.expression;

import model.adt.dictionary.MyIDictionary;
import exceptions.MyException;
import model.adt.heapTable.IHeapTable;
import model.type.Type;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, IHeapTable<Integer, Value> heaptbl) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
