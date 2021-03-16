package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.expression.Exp;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private Exp expression;
    private String varName;

    public ReadFileStmt(Exp expr, String varN) {
        expression=expr;
        varName=varN;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIDictionary<String, Value> symbolsTable=state.getSymTable();
        IHeapTable<Integer,Value> heaptbl=state.getHeapTable();
        if(!symbolsTable.containsKey(varName))
            throw new MyException("Variable "+varName+" is not defined");
        Value stringTester=expression.eval(symbolsTable, heaptbl);
        if(!(stringTester instanceof StringValue))
            throw new RuntimeException("Expression: " + expression.toString() + " cannot be evaluated to a string!");
        String fileName=((StringValue) stringTester).getVal();
        MyIDictionary<String,BufferedReader> fileTable=state.getFileTable();
        if(!fileTable.containsKey(fileName))
            throw new MyException("File not found");
        BufferedReader buff=fileTable.get(fileName);
        String line=buff.readLine();
        int newVal;
        if(line==null)
            newVal=0;
        else
            newVal=Integer.parseInt(line);
        symbolsTable.put(varName,new IntValue(newVal));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.get(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new IntType()))
            if (typexp.equals(new StringType()))
                return typeEnv;
            else
                throw new MyException("File is not a string");
        else
            throw new MyException("Variable not an integer");
    }

    @Override
    public String toString() {
        return "ReadFile "+expression.toString();
    }
}
