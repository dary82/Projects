package repository;

import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    PrgState getPrgAt(int index);
    List<PrgState> getPrgList();
    Integer getSize();
    void setPrgList(List<PrgState> prgList);
    void addPrgState(PrgState state);
    void logPrgStateExec() throws IOException;
    void logPrgStateExec(PrgState state) throws IOException;
}
