package model.expression;

import exceptions.OperandNotBooleanException;
import model.adt.dictionary.MyIDictionary;
import exceptions.MyException;
import model.adt.heapTable.IHeapTable;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp{
    private Exp e1;
    private Exp e2;
    private String op;

    public LogicExp(Exp ex1,Exp ex2, String o) {
        e1=ex1;
        e2=ex2;
        op=o;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeapTable<Integer, Value> heaptbl) throws MyException {
        Value v1,v2;
        v1=e1.eval(tbl, heaptbl);
        if(v1.getType().equals(new BoolType()))
        {
            v2=e2.eval(tbl, heaptbl);
            if(v2.getType().equals(new BoolType()))
            {
                BoolValue b1=(BoolValue) v1;
                BoolValue b2=(BoolValue) v2;
                boolean op1,op2;
                op1=b1.getVal();
                op2=b2.getVal();
                switch(op)
                {
                    case "AND" -> {
                        return new BoolValue(op1&&op2);
                    }
                    case "OR" -> {
                        return new BoolValue(op1||op2);
                    }
                }
            } else throw new OperandNotBooleanException("Operator is not a bool");
        } else throw new OperandNotBooleanException("Operator is not a bool");
        return new BoolValue();
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new MyException("second operand is not a bool");
        }
        else
            throw new MyException("first operand is not a bool");
    }

    public String toString() {
        return e1.toString()+" "+op+" "+e2.toString();
    }
}

