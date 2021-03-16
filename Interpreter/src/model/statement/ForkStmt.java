package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.stack.MyIStack;
import model.adt.stack.MyStack;
import model.type.Type;

import java.io.IOException;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt stmt) {
        statement=stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        return new PrgState(new MyStack<>(),state.getSymTable().deepcopy(),state.getOut(),state.getFileTable(),state.getHeapTable(),state.getId()*10,this.statement);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString(){
        return "fork "+ statement.toString();
    }
}
