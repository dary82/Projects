package model.expression;

import exceptions.DivisionByZeroException;
import exceptions.OperandNotIntegerException;
import model.adt.dictionary.MyIDictionary;
import exceptions.MyException;
import model.adt.heapTable.IHeapTable;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private char op;

    public ArithExp(Exp ex1, Exp ex2, char o) {
        e1=ex1;
        e2=ex2;
        op=o;
    }

    public ArithExp(char o, Exp ex1, Exp ex2) {
        e1=ex1;
        e2=ex2;
        op=o;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeapTable<Integer, Value> heaptbl) throws MyException {
        Value v1,v2;
        v1= e1.eval(tbl, heaptbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heaptbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op=='+')
                    return new IntValue(n1+n2);
                if (op =='-')
                    return new IntValue(n1-n2);
                if(op=='*')
                    return new IntValue(n1*n2);
                if(op=='/')
                    if(n2==0)
                        throw new DivisionByZeroException("division by zero");
                    else
                        return new IntValue(n1/n2);
            }
            else
                throw new OperandNotIntegerException("second operand is not an integer");
        }
        else
            throw new OperandNotIntegerException("first operand is not an integer");
        return new IntValue();
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }

    public String toString() {
        return e1.toString()+op+e2.toString();
    }
}
