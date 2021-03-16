package model.statement;

import model.adt.dictionary.MyIDictionary;
import model.adt.stack.MyIStack;
import exceptions.MyException;
import model.PrgState;
import model.type.Type;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt frst, IStmt second) {
        first=frst;
        snd=second;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return snd.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "("+first.toString() + ";" + snd.toString()+")";
    }
}
