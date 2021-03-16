package model.statement;

import model.adt.dictionary.MyIDictionary;
import model.adt.stack.MyIStack;
import exceptions.MyException;
import exceptions.StatementExecutionException;
import model.PrgState;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

public class VarDeclStmt implements IStmt{
    private String name;
    private Type typ;

    public VarDeclStmt(String n,Type type) {
        name=n;
        typ=type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        if (symTbl.containsKey(name))
            throw new StatementExecutionException("Variable already defined");
        if (typ instanceof IntType) {
            symTbl.put(name, new IntValue(0));
        } else if (typ instanceof BoolType)
            symTbl.put(name, new BoolValue(false));
         else if (typ instanceof StringType)
            symTbl.put(name, new StringValue(""));
         else if (typ instanceof RefType)
            symTbl.put(name, typ.defaultValue());
        else
            throw new StatementExecutionException("Incorrect type");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name,typ);
        return typeEnv;
    }

    @Override
    public String toString() {
        return typ.toString()+" "+name;
    }
}
