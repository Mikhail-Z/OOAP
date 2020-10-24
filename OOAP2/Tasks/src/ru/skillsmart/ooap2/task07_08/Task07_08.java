package ru.skillsmart.ooap2.task07_08;

import java.util.LinkedList;

class Calculator {
    Expression getExpression() {
        System.out.println("Некоторая логика простого калькулятора");
        throw new UnsupportedOperationException();
    }
}

class EngineeringCalculator extends Calculator {
    @Override
    SimpleExpression getExpression() {
        System.out.println("Некоторая логика инженерного калькулятора");
        throw new UnsupportedOperationException();
    }
}

class Expression {
    @Override
    public String toString() {
        return "some expression";
    }
}

class SimpleExpression extends Expression {
    @Override
    public String toString() {
        return "some simple expression";
    }
}

class ComplexExpression extends SimpleExpression {
    @Override
    public String toString() {
        return "some complex expression";
    }
}


public class Task07_08 {

    static void Task07() {
        //Задание 7.
        //Приведите пример кода с комментариями, где применяется динамическое связывание.
        //динамическое связывание - решение, кокой метод объекта будет вызван, решается на этапе выполнения.
        // В java и c# реализуется с помощью vtable (таблицы виртуальных методов).
        //Если переменной типа Expression присвоить ссылку на тип ComplexExpression, то компилятор разрешит выполнение функции во время выполнения
        Expression expression = new ComplexExpression();
        System.out.println(expression); //some complex expression
    }

    private static LinkedList<ComplexExpression> getComplexExpresionList() {
        var list = new LinkedList<ComplexExpression>();
        list.add(new ComplexExpression());
        list.add(new ComplexExpression());
        return list;
    }

    private static LinkedList<ComplexExpression> getComplexExpresionList(LinkedList<? extends Expression> expressions) {
        var list = new LinkedList<ComplexExpression>();
        list.add(new ComplexExpression());
        list.add(new ComplexExpression());
        return list;
    }

    private static LinkedList<ComplexExpression> getComplexExpresionList2(LinkedList<? super Expression> expressions) {
        var list = new LinkedList<ComplexExpression>();
        list.add(new ComplexExpression());
        list.add(new ComplexExpression());
        return list;
    }

    static void Task08() {
        //В java массивы являются ковариантными --  и в методе calculate(SimpleExpression[] expressions)
        // переменной с типом массива родительского класса присваивается ссылка на массив дочернего класса
        Expression[] expressionArray = new SimpleExpression[]{new SimpleExpression(), new ComplexExpression()};
        System.out.println(expressionArray[0]); //some simple expression
        System.out.println(expressionArray[1]); //some complex expression

        //Обобщенные типы (дженерики) являются инвариантными, так как из-за необходимости запускать новый код на старой виртуальной машине
        // при компиляции происходит стирание типов (type erasure), из-за чего в рантайме нет информации о том,
        // каким конкретно типом (указанным типом или его наследником) был параметризирован обобщенный тип
        // параметризирующий класс.
        // Для обеспечения ковариантности в java была придумана специальная языковая конструкция -- <? extends T>,
        // т.е. реализация происходит в форме обобщенных типов
        LinkedList<? extends Expression> list = getComplexExpresionList();
        Expression expression = list.get(0);
        //list.add(new Expression()); //compile error
        System.out.println(expression);
        getComplexExpresionList(new LinkedList<ComplexExpression>());

        //в java с 5 версии переопределение методов ковариантно относительно возвращаемых значений -- пример тому метод
        //getExpression в классах Calculator и EngineeringCalculator. Ковариантности относительно параметров метода нет.
        System.out.println("---------------------------------------------------------");

        //контравариантность противоположна ковариантности, позволяя писать в обобщенный тип данные обобщающего типа, дочернего по отношению к указанному.
        // в java имеет вид <? super T>

        LinkedList<? super Expression> list2 = new LinkedList<>();
        list2.add(new ComplexExpression());
        //Expression expression2 = list2.get(0); //compile error
    }

    public static void main(String[] args) {
        Task07();
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        Task08();
    }
}