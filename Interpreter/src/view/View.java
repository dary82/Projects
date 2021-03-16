package view;

import controller.Controller;
import exceptions.InvalidInputException;
import exceptions.MyException;
import model.PrgState;
import model.expression.ArithExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.Scanner;

public class View {
    private Controller controller;
    private Scanner scanner;

    public View(Controller contr){
        controller=contr;
        scanner=new Scanner(System.in);
    }

    public static void main(String[] args){
        IRepository repository=new Repository("view.txt");
        Controller controller = new Controller(repository);
        View ui=new View(controller);
        ui.start();
    }

    public IStmt Ex1() {
        return new CompStmt(
                new VarDeclStmt("v",new IntType()),
                new CompStmt(
                        new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
    }

    public IStmt Ex2() {
        return new CompStmt(
                new VarDeclStmt("a",new IntType()),
                new CompStmt(
                        new VarDeclStmt("b",new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)),'*'), '+')),
                                new CompStmt(
                                        new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)),'+')),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
    }

    public IStmt Ex3() {
        return new CompStmt(
                new VarDeclStmt("a",new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v",new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
    }

    public void run_all_steps(IStmt stmt) throws IOException, InterruptedException {
        PrgState prgState = new PrgState(stmt);
        controller.addPrgState(prgState);
        controller.allStep();
        System.out.println("=============Finished=============");
    }

    public void one_step(IStmt stmt) {
        PrgState prgState=new PrgState(stmt);
        controller.addPrgState(prgState);
        while (controller.getRepository().getPrgList().size()>0) {
            try {
                controller.executeOneStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        while (true) {
            try {
                System.out.println("1. int v; v=2; Print(v)");
                System.out.println("2. int a; int b; a=2+3*5; b=a+1; Print(b)");
                System.out.println("3. bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)");
                System.out.println("4. One step for 1");
                System.out.println("0. Exit");

                System.out.print(">>");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "0" -> {
                        System.exit(0);
                    }
                    case "1" -> {
                        run_all_steps(Ex1());
                    }
                    case "2" -> {
                        run_all_steps(Ex2());
                    }
                    case "3" -> {
                        run_all_steps(Ex3());
                    }
                    case "4" -> {
                        one_step(Ex1());
                    }
                    default -> {
                        throw new InvalidInputException("Bad input");
                    }

                }
            } catch (MyException | IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
