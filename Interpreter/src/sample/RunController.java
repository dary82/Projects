package sample;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.PrgState;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.IHeapTable;
import model.statement.IStmt;
import model.value.Value;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunController implements Initializable {
    public RunController(){}
    private Controller controller;
    private PrgState selectedPrg;

    @FXML
    private TableView<HashMap.Entry<Integer, String>> HeapTableView=new TableView<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, Integer> HeapAdressColumn=new javafx.scene.control.TableColumn<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, String> HeapValueColumn=new javafx.scene.control.TableColumn<>();

    @FXML
    private ListView<String> OutputListView=new ListView<>();

    @FXML
    private ListView<String> FileTableListView= new ListView<>();

    @FXML
    private ListView<Integer> ProgramStateListView=new ListView<>();

    @FXML
    private TableView<Map.Entry<String, String>> symTableView=new javafx.scene.control.TableView<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symVarNameColumn=new TableColumn<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symVarValueColumn=new TableColumn<>();

    @FXML
    private ListView<String> ExeStackView=new ListView<>();

    @FXML
    private TextField nrProgramStatesField=new TextField("");


    public void setController(Controller ctr) {
        controller=ctr;

        selectedPrg=controller.getRepository().getPrgList().get(0);

        loadData();
    }

    @FXML
    public void setSelectedProgram(){
        if(ProgramStateListView.getSelectionModel().getSelectedIndex()>=0 && ProgramStateListView.getSelectionModel().getSelectedIndex()<=this.controller.getRepository().getPrgList().size()){
            selectedPrg=controller.getRepository().getPrgList().get(ProgramStateListView.getSelectionModel().getSelectedIndex());
            loadData();
        }
    }

    private void loadData(){
        this.ProgramStateListView.getItems().setAll( controller.getRepository().getPrgList().stream().map(PrgState::getId).collect(Collectors.toList()) );

        if(selectedPrg!=null){

            OutputListView.getItems().setAll( selectedPrg.getOut().toString());

            FileTableListView.getItems().setAll(String.valueOf(selectedPrg.getFileTable().getContent().keySet()));

            List<String> executionStackList=selectedPrg.getStk().getStack().stream().map(IStmt::toString).collect(Collectors.toList());
            //Collections.reverse(executionStackList);
            ExeStackView.getItems().setAll(executionStackList);

            IHeapTable<Integer, Value> heapTable=selectedPrg.getHeapTable();
            List<Map.Entry<Integer, String>> heapTableList=new ArrayList<>();
            for(Map.Entry<Integer, Value> element:heapTable.getContent().entrySet()){
                Map.Entry<Integer, String> el=new AbstractMap.SimpleEntry<Integer, String>(element.getKey(),element.getValue().toString());
                heapTableList.add(el);
            }
            HeapTableView.setItems(FXCollections.observableList(heapTableList));
            HeapTableView.refresh();

            HeapAdressColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            HeapValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            MyIDictionary<String, Value> symbolTable=this.selectedPrg.getSymTable();
            List<Map.Entry<String, String>> symbolTableList=new ArrayList<>();
            for(Map.Entry<String, Value> element:symbolTable.getContent().entrySet()){
                Map.Entry<String, String> el=new AbstractMap.SimpleEntry<String, String>(element.getKey(),element.getValue().toString());
                symbolTableList.add(el);
            }
            symTableView.setItems(FXCollections.observableList(symbolTableList));
            symTableView.refresh();

            symVarNameColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
            symVarValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            nrProgramStatesField.setText(Integer.toString(controller.getRepository().getSize()));

        }
    }


    @FXML
    public void onRunOneStepButtonPressed() throws IOException, InterruptedException {
        if(controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No program selected!");
            alert.setContentText("Please select a program");
            alert.showAndWait();
            return;
        }

        if(selectedPrg.getStk().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Done");
            alert.setHeaderText("Program has finished!");
            alert.setContentText("Close the program and select another one from the list");
            alert.showAndWait();
            return;
        }

        controller.executeOneStep();

        loadData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
