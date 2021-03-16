package model.statement;

import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.adt.stack.MyIStack;
import exceptions.MyException;
import exceptions.StatementExecutionException;
import model.PrgState;
import model.expression.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp expr, IStmt thenSt, IStmt elseSt) {
        exp=expr;
        thenS=thenSt;
        elseS=elseSt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        IHeapTable<Integer,Value> heaptbl=state.getHeapTable();
        Value val=exp.eval(symTbl, heaptbl);
        if (val instanceof BoolValue)
        {
            if (((BoolValue) val).getVal())
                stk.push(thenS);
            else
                stk.push(elseS);
        }
        else
            throw new StatementExecutionException("Statement needs boolean values");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepcopy());
            elseS.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public String toString() {
        return "IF "+ exp.toString()+" THEN " +thenS.toString() +" ELSE "+elseS.toString()+"";
    }
}
