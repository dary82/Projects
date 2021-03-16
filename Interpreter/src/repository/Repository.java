package repository;

import exceptions.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> prgStateList;
    private String logFilePath;

    public Repository() {
        prgStateList=new ArrayList<PrgState>();
        logFilePath="";
    }

    public Repository(String filePath) {
        prgStateList=new ArrayList<PrgState>();
        logFilePath=filePath;
    }

    public Repository(PrgState prgState, String filePath) {
        prgStateList=new ArrayList<PrgState>();
        prgStateList.add(prgState);
        logFilePath=filePath;
    }

    public Repository(List<PrgState> programStateList, String filePath){
        prgStateList = programStateList;
        logFilePath=filePath;
    }

    @Override
    public PrgState getPrgAt(int index) {
        return prgStateList.get(index-1);
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStateList;
    }

    @Override
    public Integer getSize() {
        return prgStateList.size();
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        prgStateList.clear();
        prgStateList=prgList;
    }

    @Override
    public void addPrgState(PrgState state) {
        prgStateList.add(state);
    }

    @Override
    public void logPrgStateExec() throws IOException {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(prgStateList.get(prgStateList.size()-1).toStringFile());
        logFile.close();
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException, IOException {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(state.toStringFile());
        logFile.close();
    }
}
