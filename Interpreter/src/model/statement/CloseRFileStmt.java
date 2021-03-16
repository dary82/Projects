package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.expression.Exp;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private Exp expression;

    public CloseRFileStmt (Exp expr) {
        expression=expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIDictionary<String, Value> systemTable=state.getSymTable();
        IHeapTable<Integer,Value> heaptbl=state.getHeapTable();
        Value stringTester=expression.eval(systemTable, heaptbl);
        if(!(stringTester instanceof StringValue))
        {
            throw new MyException("Expression: "+expression.toString()+" cannot be eval to a string");
        }
        String fileName=((StringValue) stringTester).getVal();
        MyIDictionary<String, BufferedReader> fileTable=state.getFileTable();
        if(!fileTable.containsKey(fileName))
            throw new MyException("File not found!");
        BufferedReader reader =fileTable.get(fileName);
        reader.close();
        fileTable.remove(fileName);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("Variable not an integer");
    }

    @Override
    public String toString() {
        return "CloseRFile "+expression.toString();
    }
}
