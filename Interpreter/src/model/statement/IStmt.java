package model.statement;
import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.type.Type;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, IOException;
    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    String toString();
}
