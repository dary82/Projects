package model.expression;

import exceptions.MyException;
import exceptions.OperandNotIntegerException;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelExp implements Exp{
    private Exp e1;
    private Exp e2;
    private String op;

    public RelExp(Exp ex1, Exp ex2, String o) {
        e1=ex1;
        e2=ex2;
        op=o;
    }

    public RelExp(String o, Exp ex1, Exp ex2) {
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
                switch (op) {
                    case "<" -> { return new BoolValue(n1<n2); }
                    case "<=" -> { return new BoolValue(n1<=n2); }
                    case "==" -> { return new BoolValue(n1==n2); }
                    case "!=" -> { return new BoolValue(n1!=n2); }
                    case ">" -> { return new BoolValue(n1>n2); }
                    case ">=" -> { return new BoolValue(n1>=n2); }
                }
            }
            else
                throw new OperandNotIntegerException("second operand is not an integer");
        }
        else
            throw new OperandNotIntegerException("first operand is not an integer");
        return new BoolValue();
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
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
