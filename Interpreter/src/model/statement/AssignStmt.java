package model.statement;

import exceptions.StatementExecutionException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.MyIDictionary;
import exceptions.MyException;
import model.PrgState;
import model.adt.heapTable.IHeapTable;
import model.expression.Exp;
import model.type.Type;
import model.value.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String i, Exp ex) {
        id=i;
        exp=ex;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        IHeapTable<Integer,Value> heaptbl=state.getHeapTable();
        if (symTbl.containsKey(id)) {
            Value val = exp.eval(symTbl, heaptbl);
            Type typId = (symTbl.get(id)).getType();
            if (val.getType().equals(typId))
                symTbl.put(id, val);
            else
                throw new StatementExecutionException("Declared type of variable" + id + " and type of  the assigned expression do not match");
        }
        else throw new VariableNotDefinedException("The used variable" +id + " was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar=typeEnv.get(id);
        Type typeexp=exp.typecheck(typeEnv);
        if (typevar.equals(typeexp))
            return typeEnv;
        else
            throw new MyException("Assignment right and left have different types");
    }

    @Override
    public String toString(){
        return id+"="+ exp.toString();
    }
}
