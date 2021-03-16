package controller;

import model.adt.heapTable.IHeapTable;
import model.adt.stack.MyIStack;
import exceptions.ADTEmptyException;
import exceptions.MyException;
import model.PrgState;
import model.statement.IStmt;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;
    private String name;

    public Controller(String fileName, String prgName) {
        repository= new Repository(fileName);
        name=prgName;
    }

    public Controller(IRepository repo) {
        repository=repo;
    }

    public void executeOneStep() {
        executor=Executors.newFixedThreadPool(8);
        List<PrgState> programList=removeCompletedPrg(repository.getPrgList());
        if(programList.size()>0){
            try{
                programList.get(0).getHeapTable().setContent((HashMap<Integer, Value>) conservativeGarbageCollector(programList, programList.get(0).getHeapTable().getContent()));
                oneStepForAllPrg(programList);
                programList=removeCompletedPrg(repository.getPrgList());
            } catch(Exception error){
                System.out.println(error.toString());
            }
            programList.forEach(e->{
                try{
                    repository.logPrgStateExec(e);
                }
                catch (Exception error){
                    System.out.println(error.toString());
                }
            });
        }
        this.executor.shutdownNow();
        repository.setPrgList(programList);
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException, IOException, InterruptedException {
        for(PrgState program: prgList){
            this.repository.logPrgStateExec(program);
        }

        List<Callable<PrgState>> callList=prgList.stream()
                .map((PrgState p)->(Callable<PrgState>)()->{return p.oneStep();})
                .collect(Collectors.toList());
        List<PrgState> newPrgList=executor.invokeAll(callList).stream()
                .map(future-> {
                    try {
                        return future.get();
                    }
                    catch (MyException | InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(p->p!=null)
                .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        for(PrgState program: prgList){
            this.repository.logPrgStateExec(program);
        }
        repository.setPrgList(prgList);
    }

    public void allStep() throws IOException, InterruptedException {
        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repository.getPrgList());
        while(prgList.size()>0) {
            IHeapTable<Integer,Value> heap=prgList.get(0).getHeapTable();
            heap.setContent(conservativeGarbageCollector(prgList,heap.getContent()));
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void addPrgState(PrgState state) {
        repository.addPrgState(state);
    }

    public Map<Integer, Value> conservativeGarbageCollector(List<PrgState> prgStateList, Map<Integer,Value> heap) {
        List<Integer> referencedAddress=Stream.concat(
                getAddrRefSymTbl(prgStateList
                .stream()
                .map(prgState -> prgState.getSymTable().getValues())),getAddrRefHeap(heap
                .values()
                .stream())
        ).collect(Collectors.toList());

        return heap
                .entrySet()
                .stream()
                .filter(e->referencedAddress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Stream<Integer> getAddrRefSymTbl(Stream<Collection<Value>> symTblVal) {
        return symTblVal
                .flatMap(Collection::stream)
                .filter(value->value instanceof RefValue)
                .map(value -> ((RefValue) value).getAddress());
    }

    public Stream<Integer> getAddrRefHeap(Stream<Value> heapVal) {
        return heapVal
                .filter(value -> value instanceof RefValue)
                .map(value -> ((RefValue) value).getAddress());
    }

    public String getName() {
        return name;
    }

    public IRepository getRepository() {
        return repository;
    }
}
