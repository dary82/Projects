package sample;

import controller.Controller;
import model.PrgState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.adt.dictionary.MyDictionary;
import model.adt.dictionary.MyIDictionary;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProgramStartController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public ProgramStartController(){}
    private List<Controller> programList=new ArrayList<>();
    @FXML
    private ListView<String> ProgramListView=new ListView<>();
    public void setProgramListView(){
        IStmt ex1=new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(5))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex1.typecheck(typeEnv);
            PrgState program1=new PrgState(ex1);
            Controller controller1=new Controller("example1.txt","1."+ex1.toString());
            controller1.addPrgState(program1);
            programList.add(controller1);
        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 1: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex2=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex2.typecheck(typeEnv);
            PrgState program2=new PrgState(ex2);
            Controller controller2=new Controller("example2.txt","2."+ex2.toString());
            controller2.addPrgState(program2);
            programList.add(controller2);
        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 2: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex3=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex3.typecheck(typeEnv);
            PrgState program3=new PrgState(ex3);
            Controller controller3=new Controller("example3.txt","3."+ex3.toString());
            controller3.addPrgState(program3);
            programList.add(controller3);
        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 3: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex4=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex4.typecheck(typeEnv);
            PrgState program4=new PrgState(ex4);
            Controller controller4=new Controller("example4.txt","4."+ex4.toString());
            controller4.addPrgState(program4);
            programList.add(controller4);
        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 4: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex5=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex5.typecheck(typeEnv);
            PrgState program5=new PrgState(ex5);
            Controller controller5=new Controller("example5.txt","5."+ex5.toString());
            controller5.addPrgState(program5);
            programList.add(controller5);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 5: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex6=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex6.typecheck(typeEnv);
            PrgState program6=new PrgState(ex6);
            Controller controller6=new Controller("example6.txt","6."+ex6.toString());
            controller6.addPrgState(program6);
            programList.add(controller6);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 6: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex7=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex7.typecheck(typeEnv);
            PrgState program7=new PrgState(ex7);
            Controller controller7=new Controller("example7.txt","7."+ex7.toString());
            controller7.addPrgState(program7);
            programList.add(controller7);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 7: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex8=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex8.typecheck(typeEnv);
            PrgState program8=new PrgState(ex8);
            Controller controller8=new Controller("example8.txt","8."+ex8.toString());
            controller8.addPrgState(program8);
            programList.add(controller8);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 8: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex9=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex9.typecheck(typeEnv);
            PrgState program9=new PrgState(ex9);
            Controller controller9=new Controller("example9.txt","9."+ex9.toString());
            controller9.addPrgState(program9);
            programList.add(controller9);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 9: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex10=new CompStmt(
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
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex10.typecheck(typeEnv);
            PrgState program10=new PrgState(ex10);
            Controller controller10=new Controller("example10.txt","10."+ex10.toString());
            controller10.addPrgState(program10);
            programList.add(controller10);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 10: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex11=new CompStmt(
                new VarDeclStmt("v",new IntType()),
                new CompStmt(
                        new VarDeclStmt("x",new IntType()),
                        new CompStmt(
                                new VarDeclStmt("y",new IntType()),
                                new CompStmt(
                                            new AssignStmt("v",new ValueExp(new IntValue(0))),
                                            new CompStmt(
                                                    new RepeatStmt(
                                                            new CompStmt(
                                                                    new ForkStmt(
                                                                            new CompStmt(
                                                                                    new PrintStmt(new VarExp("v")),
                                                                                    new AssignStmt("v",new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),'-'))
                                                                            )
                                                                    ),
                                                                    new AssignStmt("v",new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),'+'))
                                                            ),
                                                            new RelExp(new VarExp("v"),new ValueExp(new IntValue(3)),"==")
                                                    ),
                                                    new CompStmt(
                                                            new AssignStmt("x",new ValueExp(new IntValue(1))),
                                                            new CompStmt(
                                                                    new NopStmt(),
                                                                    new CompStmt(
                                                                            new AssignStmt("y", new ValueExp(new IntValue(3))),
                                                                            new CompStmt(
                                                                                    new NopStmt(),
                                                                                    new PrintStmt(new ArithExp(new VarExp("v"),new ValueExp(new IntValue(10)),'*'))
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                        )
                )
        );
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex11.typecheck(typeEnv);
            PrgState program11=new PrgState(ex11);
            Controller controller11=new Controller("example11.txt","11."+ex11.toString());
            controller11.addPrgState(program11);
            programList.add(controller11);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 11: "+e.getMessage());
            alert.showAndWait();
        }
        IStmt ex12=new CompStmt(
                new VarDeclStmt("a",new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("b",new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v",new IntType()),
                                new CompStmt(
                                        new HeapAllocStmt("a",new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new HeapAllocStmt("b",new ValueExp(new IntValue(0))),
                                                new CompStmt(
                                                        new HeapWriteStmt("a",new ValueExp(new IntValue(1))),
                                                        new CompStmt(
                                                                new HeapWriteStmt("b",new ValueExp(new IntValue(2))),
                                                                new CompStmt(
                                                                        new ConditionalAssigmentStmt("v", new RelExp(new HeapReadingExp(new VarExp("a")), new HeapReadingExp(new VarExp("b")),"<"),new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new CompStmt(
                                                                                        new ConditionalAssigmentStmt("v", new RelExp(new ArithExp(new HeapReadingExp(new VarExp("b")),new ValueExp(new IntValue(2)),'-'), new HeapReadingExp(new VarExp("a")),">"),new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                                        new PrintStmt(new VarExp("v"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<String, Type>();
            ex12.typecheck(typeEnv);
            PrgState program11=new PrgState(ex12);
            Controller controller12=new Controller("example12.txt","12."+ex11.toString());
            PrintWriter pw = new PrintWriter("example12.txt");
            pw.close();
            controller12.addPrgState(program11);
            programList.add(controller12);

        }catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type check error");
            alert.setContentText("Example 12: "+e.getMessage());
            alert.showAndWait();
        }

        ProgramListView.setItems(FXCollections.observableArrayList(
                programList.stream().map(Controller::getName).collect(Collectors.toList())
        ));


    }
    @FXML
    private void RunButtonPressed()
    {
        if(ProgramListView.getSelectionModel().getSelectedItem()!=null)
        {
            try{
                FXMLLoader loader =new FXMLLoader(getClass().getResource("RunLayout.fxml"));
                Parent interpreterView=loader.load();
                RunController controller=loader.getController();
                controller.setController(programList.get(ProgramListView.getSelectionModel().getSelectedIndex()));
                Stage stage=new Stage();
                stage.setTitle("Main Window");
                stage.setScene(new Scene(interpreterView));
                stage.showAndWait();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
