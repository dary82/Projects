package model.statement;

import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.adt.list.MyIList;
import exceptions.MyException;
import model.PrgState;
import model.expression.Exp;
import model.type.Type;
import model.value.Value;

public class PrintStmt implements IStmt{
    private Exp exp;

    public PrintStmt(Exp expr) {
        exp=expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> out=state.getOut();
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        IHeapTable<Integer,Value> heaptbl=state.getHeapTable();
        out.add(exp.eval(symTbl, heaptbl));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print " +exp.toString();
    }
}
