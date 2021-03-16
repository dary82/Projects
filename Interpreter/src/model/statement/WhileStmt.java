package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.adt.stack.MyIStack;
import model.expression.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

import java.io.IOException;
import java.io.InterruptedIOException;

public class WhileStmt implements IStmt {
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp ex, IStmt st) {
        exp=ex;
        stmt=st;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIStack<IStmt> stack=state.getStk();
        MyIDictionary<String, Value> symtbl=state.getSymTable();
        IHeapTable<Integer,Value> heaptbl= state.getHeapTable();
        Value result=exp.eval(symtbl,heaptbl);
        if (result.getType() instanceof BoolType) {
            if (((BoolValue)result).getVal())
                stack.push(new CompStmt(stmt,new WhileStmt(exp,stmt)));
        } else
            throw new InterruptedIOException();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new MyException("While: Cond exp not bool");
    }

    @Override
    public String toString() {
        return "while "+exp.toString()+"{"+stmt.toString()+"}";
    }
}
