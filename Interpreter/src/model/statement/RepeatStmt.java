package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.stack.MyIStack;
import model.expression.Exp;
import model.expression.NotExp;
import model.type.BoolType;
import model.type.Type;

import java.io.IOException;

public class RepeatStmt implements IStmt {
    private IStmt stmt1;
    private Exp exp2;

    public RepeatStmt(IStmt s, Exp e) {
        stmt1=s;
        exp2=e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIStack<IStmt> stack=state.getStk();
        //we need to negate the exp for the condition:  stmt1;(while(!exp2) stmt1)
        stack.push(new CompStmt(stmt1,new WhileStmt(new NotExp(exp2),stmt1)));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp2.typecheck(typeEnv);
        if(typexp.equals(new BoolType())) {
            stmt1.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new RuntimeException("Repeat: Cond exp not bool");
    }

    @Override
    public String toString() {
        return "repeat "+stmt1.toString()+" until !"+exp2.toString();
    }
}
