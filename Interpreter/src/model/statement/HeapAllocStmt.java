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

public class HeapAllocStmt implements IStmt {
    private String var_name;
    private Exp exp;

    public HeapAllocStmt(String name, Exp ex) {
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
                RefValue var_ref= (RefValue) value;
                Type var_locType=var_ref.getLocationType();
                Value expVal=exp.eval(symtbl,heaptbl);
                if (expVal.getType().equals(var_locType)) {
                    int address=heaptbl.getAddress();
                    heaptbl.put(address,expVal);
                    symtbl.put(var_name,new RefValue(address,var_locType));
                } else
                    throw new MyException("Expression and variable type differ");
            } else
                throw new MyException("Variable is not a reference");
        } else
            throw new MyException("Variable name not in symbol table");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.get(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new "+var_name+","+exp.toString();
    }
}
