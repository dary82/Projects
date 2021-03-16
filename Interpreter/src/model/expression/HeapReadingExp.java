package model.expression;

import exceptions.MyException;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class HeapReadingExp implements Exp {
    private Exp e;

    public HeapReadingExp(Exp expression) {
        e=expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeapTable<Integer, Value> heaptbl) throws MyException {
        Value v=e.eval(tbl,heaptbl);
        if(v.getType() instanceof RefType) {
            RefValue ref_v= (RefValue) v;
            if (heaptbl.containsKey(ref_v.getAddress()))
                return heaptbl.get(ref_v.getAddress());
            else
                throw new MyException("Address not found");
        } else
            throw new MyException("Expression type is not a reference");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=e.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        }
        else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "rH "+e.toString();
    }
}
