package ru.skillsmart.ooap2.task13;

import com.google.common.collect.Streams;
import ru.skillsmart.ooap2.task11_12.Any;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

enum OperationResult {
    SUCCESS,
    FAILURE
}

abstract class Summable extends Any {
    abstract void sum(Summable number);
    abstract int getLength();
    abstract OperationResult getSumOperationResult();

}

public class Vector<T extends Summable> extends Summable {
    private List<Summable> values;
    private OperationResult addOperationResult;

    public Vector() {
        this.values = new LinkedList<>();
    }

    public void add(Summable number) {
        values.add(number);
    }

    /*
    предусловие: кол-во элементов в векторе совадает с кол-вом в элементов в векторе
    */
    @Override
    public void sum(Summable v2) {
        if (v2 == null) {
            throw new IllegalArgumentException();
        }

        if (v2.getLength() != this.getLength()) {
            return;
        }

        if (v2 instanceof Vector) {
            var v = (Vector<Summable>)v2;
            if (this.values.size() != v.values.size()) {
                addOperationResult = OperationResult.FAILURE;
            }
            this.values = Streams.zip(this.values.stream(), v.values.stream(),
                    (a, b) ->  {
                        a.sum(b);
                        return a;
                    }).collect(Collectors.toList());
            this.addOperationResult = OperationResult.SUCCESS;
            return;
        } else if (v2 instanceof MyInt) {
            var myInt = (MyInt)v2;
            for (var i : this.values) {
                i.sum(myInt);
            }
            return;
        }
    }

    @Override
    int getLength() {
        return this.values.size();
    }

    @Override
    public OperationResult getSumOperationResult() {
        return addOperationResult;
    }
}

class MyInt extends Summable {
    private OperationResult addOperationResult;

    int value;

    public MyInt(int value) {
        this.value = value;
    }

    @Override
    void sum(Summable number) {
        if (number.getLength() != this.getLength()) {
            return ;
        }

        MyInt myInt = new MyInt(0);
        myInt = Any.assignmentAttempt(number, myInt);
        if (myInt == null) {
            addOperationResult = OperationResult.FAILURE;
            return;
        }

        this.value += myInt.value;
    }

    @Override
    int getLength() {
        return 1;
    }

    @Override
    OperationResult getSumOperationResult() {
        return this.addOperationResult;
    }
}

class Task13 {
    public static void main(String[] args) {
        var vector = new Vector<Vector<MyInt>>();

        var v1 = new Vector<MyInt>();
        v1.add(new MyInt(1));
        v1.add(new MyInt(2));

        var v2 = new Vector<MyInt>();
        v2.add(new MyInt(2));
        v2.add(new MyInt(4));

        vector.add(v1);
        vector.add(v2);
        try {
            var vector2 = (Vector<Vector<MyInt>>)vector.deepClone();
            vector.sum(vector2); // [[1, 2], [2, 4]] + [[1, 2], [2, 4]]
            System.out.println(vector); //[[2, 4], [4, 8]]
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}