package view;

import controller.Controller;
import exceptions.MyException;
import model.PrgState;
import model.adt.dictionary.MyDictionary;
import model.adt.dictionary.MyIDictionary;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {
    public static void main(String[] args) {
        IRepository repository=new Repository("src/log.txt");
        Controller c1=new Controller(new Repository("src/log.txt"));
        Controller c2=new Controller(new Repository("src/log.txt"));
        Controller c3=new Controller(new Repository("src/log.txt"));
        Controller c4=new Controller(new Repository("src/log.txt"));
        Controller c5=new Controller(new Repository("src/log.txt"));
        Controller c6=new Controller(new Repository("src/log.txt"));
        Controller c7=new Controller(new Repository("src/log.txt"));
        Controller c8=new Controller(new Repository("src/log.txt"));
        Controller c9=new Controller(new Repository("src/log.txt"));
        Controller c10=new Controller(new Repository("src/log.txt"));

        PrgState state1=new PrgState(Ex1());
        PrgState state2=new PrgState(Ex2());
        PrgState state3=new PrgState(Ex3());
        PrgState state4=new PrgState(Ex4());
        PrgState state5=new PrgState(Ex5());
        PrgState state6=new PrgState(Ex6());
        PrgState state7=new PrgState(Ex7());
        PrgState state8=new PrgState(Ex8());
        PrgState state9=new PrgState(Ex9());
        PrgState state10=new PrgState(Ex10());

        c1.addPrgState(state1);
        c2.addPrgState(state2);
        c3.addPrgState(state3);
        c4.addPrgState(state4);
        c5.addPrgState(state5);
        c6.addPrgState(state6);
        c7.addPrgState(state7);
        c8.addPrgState(state8);
        c9.addPrgState(state9);
        c10.addPrgState(state10);

        TextMenu menu=new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExample("1",state1.description(),c1,Ex1()));
        menu.addCommand(new RunExample("2",state2.description(),c2,Ex2()));
        menu.addCommand(new RunExample("3",state3.description(),c3,Ex3()));
        menu.addCommand(new RunExample("4",state4.description(),c4,Ex4()));
        menu.addCommand(new RunExample("5",state5.description(),c5,Ex5()));
        menu.addCommand(new RunExample("6",state6.description(),c6,Ex6()));
        menu.addCommand(new RunExample("7",state7.description(),c7,Ex7()));
        menu.addCommand(new RunExample("8",state8.description(),c8,Ex8()));
        menu.addCommand(new RunExample("9",state9.description(),c9,Ex9()));
        menu.addCommand(new RunExample("10",state10.description(),c10,Ex10()));
        menu.show();
    }

    public static IStmt Ex1() {
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(5))),
                        new PrintStmt(new VarExp("v"))
                )
        );
    }

    public static IStmt Ex2() {
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*' ), '+' )),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), '+' )),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
    }

    public static IStmt Ex3() {
        return new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
    }

    public static IStmt Ex4() {
        return new CompStmt(
                new VarDeclStmt("fileName", new StringType()),
                new CompStmt(
                        new AssignStmt("fileName", new ValueExp(new StringValue("src/test.in"))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("fileName")),
                                new CompStmt(
                                        new VarDeclStmt("v", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("fileName"), "v"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("fileName"), "v"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("v")),
                                                                        new CloseRFileStmt(new VarExp("fileName"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }
    public static IStmt Ex5() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HeapAllocStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a"))
                                        )
                                )
                        )
                )
        );
    }

    public static IStmt Ex6() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HeapAllocStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))),new ValueExp(new IntValue(5)),'+'))
                                        )
                                )
                        )
                )
        );
    }

    public static IStmt Ex7() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                new CompStmt(
                                        new HeapWriteStmt("v",new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(new HeapReadingExp(new VarExp("v")),new ValueExp(new IntValue(5)),'+'))
                                )
                        )
                )
        );
    }

    public static IStmt Ex8() {
        return new CompStmt(
                new VarDeclStmt("v",new IntType()),
                new CompStmt(
                        new AssignStmt("v",new ValueExp(new IntValue(5))),
                        new CompStmt(
                                new WhileStmt(new RelExp(new VarExp("v"),new ValueExp(new IntValue(0)),">"),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v",new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),'-'))
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
    }

    public static IStmt Ex9() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HeapAllocStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new HeapAllocStmt("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );
    }

    public static IStmt Ex10() {
        return new CompStmt(
                new VarDeclStmt("v",new IntType()),
                new CompStmt(
                        new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v",new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new HeapAllocStmt("a",new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new HeapWriteStmt("a",new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v",new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
