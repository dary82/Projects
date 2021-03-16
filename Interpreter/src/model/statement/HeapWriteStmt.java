package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.expression.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

import java.io.IOException;

public class HeapWriteStmt implements IStmt {
    private String var_name;
    private Exp exp;

    public HeapWriteStmt(String name, Exp ex) {
        var_name=name;
        exp=ex;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIDictionary<String, Value> symtbl=state.getSymTable();
        IHeapTable<Integer,Value> heaptbl=state.getHeapTable();
        if (symtbl.containsKey(var_name)) {
            Value value=symtbl.get(var_name);
            if (value.getType() instanceof RefType) {
                RefValue ref_value= (RefValue) value;
                int address=ref_value.getAddress();
                if (heaptbl.containsKey(address)) {
                    Value exp_value=exp.eval(symtbl,heaptbl);
                    if (exp_value.getType().equals(ref_value.getLocationType()))
                        heaptbl.put(address, exp_value);
                    else
                        throw new MyException("Type of variable and expression different");
                } else
                    throw new MyException("Address not found");
            } else
                throw new MyException("Variable type not a reference");
        } else
            throw new MyException("Variable not defined");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.get(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("Write stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "wH "+var_name+","+exp.toString();
    }
}
