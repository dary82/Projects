package view;

import controller.Controller;
import exceptions.MyException;
import model.adt.dictionary.MyDictionary;
import model.adt.dictionary.MyIDictionary;
import model.statement.IStmt;
import model.type.Type;

import java.io.IOException;

public class RunExample extends Command {
    private Controller controller;
    private IStmt stmt;

    public RunExample(String key, String desc, Controller ctr, IStmt state) {
        super(key, desc);
        this.controller = ctr;
        this.stmt=state;
    }

    @Override
    public void execute() {
        try {
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            stmt.typecheck(typeEnv);
        } catch (MyException  e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        try {
            controller.executeOneStep();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}