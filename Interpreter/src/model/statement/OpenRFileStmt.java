package model.statement;

import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.expression.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt {
    private Exp expression;

    public OpenRFileStmt(Exp expr) {
        expression=expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String,BufferedReader> fileTable =state.getFileTable();
        MyIDictionary<String,Value> symTable=state.getSymTable();
        IHeapTable<Integer,Value> heaptbl=state.getHeapTable();
        Value value=expression.eval(symTable, heaptbl);
        if(value.getType() instanceof StringType)
        {
            String filePath=((StringValue) value).getVal();
            if (!fileTable.containsKey(filePath)) {
                try {
                    FileReader fileReader = new FileReader(filePath);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    fileTable.put(filePath, bufferedReader);
                } catch (FileNotFoundException e) {
                    throw new MyException("File not found!");
                }
            } else {
                throw new MyException("This file is already in the file-table");
            }
        }else
        {
            throw new MyException("the expression is not a sttring type");
        }
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
        return "OpenRFile "+expression.toString();
    }
}
