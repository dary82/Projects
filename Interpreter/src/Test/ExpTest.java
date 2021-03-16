package Test;

import model.adt.dictionary.MyDictionary;
import model.adt.dictionary.MyIDictionary;
import model.adt.heapTable.HeapTable;
import model.adt.heapTable.IHeapTable;
import model.expression.ArithExp;
import model.expression.LogicExp;
import model.expression.RelExp;
import model.expression.ValueExp;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExpTest {
    @Test
    public void testArithExp() {
        MyIDictionary<String, Value> symTbl=new MyDictionary<>();
        IHeapTable<Integer,Value> heaptbl=new HeapTable<>();
        ValueExp v1=new ValueExp(new IntValue(1));
        ValueExp v2=new ValueExp(new IntValue(2));
        ArithExp exp=new ArithExp(v1,v2,'+');

        assertEquals(exp.eval(symTbl, heaptbl).toString(),"3");
    }

    @Test
    public void testLogicExp() {
        MyIDictionary<String, Value> symTbl=new MyDictionary<>();
        IHeapTable<Integer,Value> heaptbl=new HeapTable<>();
        ValueExp v1=new ValueExp(new BoolValue(true));
        ValueExp v2=new ValueExp(new BoolValue(false));
        LogicExp exp1=new LogicExp(v1,v2,"AND");
        LogicExp exp2=new LogicExp(v1,v2,"OR");

        assertEquals(exp1.eval(symTbl, heaptbl).toString(),"false");
        assertEquals(exp2.eval(symTbl, heaptbl).toString(),"true");
    }

    @Test
    public void testRelExp() {
        MyIDictionary<String, Value> symTbl=new MyDictionary<>();
        IHeapTable<Integer,Value> heaptbl=new HeapTable<>();
        ValueExp v1=new ValueExp(new IntValue(5));
        ValueExp v2=new ValueExp(new IntValue(10));
        ValueExp v3=new ValueExp(new IntValue(20));
        ValueExp v4=new ValueExp(new IntValue(1));
        ValueExp v5=new ValueExp(new IntValue(10));
        RelExp exp1=new RelExp(v1,v2,">");
        RelExp exp2=new RelExp(v2,v5,"==");
        RelExp exp3=new RelExp(v2,v3,"<");
        RelExp exp4=new RelExp(v1,v5,">=");
        RelExp exp5=new RelExp(v3,v5,"!=");
        RelExp exp6=new RelExp(v1,v4,"<=");

        assertEquals(exp1.eval(symTbl, heaptbl).toString(),"false");
        assertEquals(exp2.eval(symTbl, heaptbl).toString(),"true");
        assertEquals(exp3.eval(symTbl, heaptbl).toString(),"true");
        assertEquals(exp4.eval(symTbl, heaptbl).toString(),"false");
        assertEquals(exp5.eval(symTbl, heaptbl).toString(),"true");
        assertEquals(exp6.eval(symTbl, heaptbl).toString(),"false");
    }
}
