package model;

import exceptions.MyException;
import model.adt.dictionary.MyDictionary;
import model.adt.dictionary.MyIDictionary;
import model.adt.fileTable.FileTable;
import model.adt.heapTable.HeapTable;
import model.adt.heapTable.IHeapTable;
import model.adt.latchTable.ILatchTable;
import model.adt.latchTable.LatchTable;
import model.adt.list.MyIList;
import model.adt.list.MyList;
import model.adt.stack.MyIStack;
import model.adt.stack.MyStack;
import model.statement.IStmt;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<String, BufferedReader> fileTable;
    private IHeapTable<Integer,Value> heapTable;
    private IStmt originalProgram;
    private Integer id;
    static int count=1;
    private ILatchTable latchTable;

    public PrgState(IStmt prg) {
        exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        fileTable=new FileTable<>();
        heapTable=new HeapTable<>();
        out=new MyList<>();
        id=1;
        latchTable=new LatchTable();
        originalProgram=prg;
        exeStack.push(prg);
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> filetbl, IHeapTable<Integer,Value> heaptbl, Integer idThread, IStmt prg, ILatchTable lt){
        exeStack=stk;
        symTable=symtbl;
        fileTable=filetbl;
        heapTable=heaptbl;
        out = ot;
        id=idThread;
        latchTable=lt;
        originalProgram=prg;
        exeStack.push(prg);
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, IOException {
        if (exeStack.isEmpty())
            throw new MyException("Stack is empty");
        IStmt crtStmt=exeStack.pop();
        return crtStmt.execute(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idThread) {
        id=idThread;
    }

    public MyIStack<IStmt> getStk() {
        return exeStack;
    }

    public MyIDictionary<String,Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value>getOut() {
        return out;
    }

    public ILatchTable getLatchTable() {
        return latchTable;
    }

    public IStmt getStatement() {
        return originalProgram;
    }

    public String toString() {
        return "Stack: "+ exeStack.toString()+"\n"+"Symbol Table: "+symTable.toString()+"\n"+"Output: "+out.toString()+"\n"+"===== Next step =====";
    }

    public MyIDictionary<String,BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeapTable<Integer,Value> getHeapTable() {
        return heapTable;
    }

    public String toStringFile() {
        return "Thread id: "+id+"\n"+"Stack:\n"+ exeStack.toStringFile()+"\n"+"Symbol Table:\n"+symTable.toStringFile()+"\n"+"File Table:\n"+fileTable.toStringFile()+"\n"+"Heap Table:\n"+heapTable.toStringFile()+"\n"+"Output:\n"+out.toStringFile()+"\n"+"---\n";
    }

    public String description() {
        return exeStack.toString();
    }
}
