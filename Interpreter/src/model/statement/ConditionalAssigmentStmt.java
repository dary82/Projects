package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.stack.MyIStack;
import model.expression.Exp;
import model.expression.VarExp;
import model.type.BoolType;
import model.type.Type;
import model.value.Value;
import org.hamcrest.core.Is;

import java.io.IOException;

public class ConditionalAssigmentStmt implements IStmt {
    private String v;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;

    public ConditionalAssigmentStmt(String val, Exp e1, Exp e2, Exp e3) {
        v=val;
        exp1=e1;
        exp2=e2;
        exp3=e3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIStack<IStmt> stack=state.getStk();
        IStmt ifStmt=new IfStmt(exp1, new AssignStmt(v,exp2),new AssignStmt(v,exp3));
        stack.push(ifStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp1=exp1.typecheck(typeEnv);
        Type typexp2=exp2.typecheck(typeEnv);
        Type typexp3=exp3.typecheck(typeEnv);
        Type typeV=new VarExp(v).typecheck(typeEnv);

        if(typexp1.equals(new BoolType()) && typexp2.equals(typeV) && typexp3.equals(typeV))
            return typeEnv;
        else
            throw new RuntimeException("CondAss: Conditional Assignment is invalid");
    }

    @Override
    public String toString() {
        return v+"="+exp1.toString()+"?"+exp2.toString()+":"+exp3.toString();
    }
}
