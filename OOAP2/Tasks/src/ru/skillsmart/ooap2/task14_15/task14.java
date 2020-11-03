package ru.skillsmart.ooap2.task14_15;

class Command {
    String command;

    public Command(String... args) {
        StringBuilder sb = new StringBuilder();
        for (var arg : args) {
            sb.append(arg);
        }
        command = sb.toString();
    }

    public void execute() {
        System.out.println(String.format("executing command %s", command));
    }
}

abstract class Linux {
    private int kernelVersionMicro;
    private int kernelVersionMinor;
    private int kernelVersionMajor;

    public String getKernelVersion() {
        return String.format("%d.%d.%d", kernelVersionMajor, kernelVersionMinor, kernelVersionMicro);
    }
}

class Debian extends Linux {
    private String packageManagerName = "dpkg";
    private String installFlag = "-i";

    public void installPackage(String packageName) {
        var command = new Command(packageManagerName, installFlag, packageName);
        command.execute();
    }
}

class Ubuntu extends Linux {
    private String packageManagerName = "apt";
    private String installFlag = "install";

    public void installPackage(String packageName) {
        var command = new Command(packageManagerName, installFlag, packageName);
        command.execute();
    }
}

class LinuxDistributive {
    private int distributiveId;

    public LinuxDistributive(int distributiveId) {
        this.distributiveId = distributiveId;
    }

    public void install(String packageName) {
        if (distributiveId == 0) {
            var command = new Command("dpkg", "-i", packageName);
            command.execute();
        } else if (distributiveId == 1) {
            var command = new Command("apt", "install", packageName);
            command.execute();
        }
    }
}

class Expression {
    @Override
    public String toString() {
        return "some expression";
    }

    public void method() {
        System.out.println("some method from expression");
    }
}

class SimpleExpression extends Expression {
    @Override
    public String toString() {
        return "some simple expression";
    }

    @Override
    public void method() {
        System.out.println("some method from simple expression");
    }
}

class ComplexExpression extends SimpleExpression {
    @Override
    public String toString() {
        return "some complex expression";
    }

    @Override
    public void method() {
        System.out.println("some complex expression");
    }
}


class Calculator {
    Expression getExpression() {
        System.out.println("Некоторая логика простого калькулятора");
        return new Expression();
    }

    // В java только массивы являются ковариантными, поэтому в примере ковариантного метода только их можно использовать.
    // Остальные обобщенные коллекции нельзя.
    public <T extends Expression> void covariantMethod(T[] values) {
        for (T value : values) {
            System.out.println(value.toString());
        }
    }

    //можно передавать как объект типа Expression, так и любого его потомка
    public void polymorphicMethod(Expression value) {
        value.method();
    }
}

class EngineeringCalculator extends Calculator {
    @Override
    SimpleExpression getExpression() {
        System.out.println("Некоторая логика инженерного калькулятора");
        return new SimpleExpression();
    }

    @Override
    public <T extends Expression> void covariantMethod(T[] values) {
        super.covariantMethod(values);
        System.out.println(values.length);
    }
}


public class task14 {
    public static void main(String[] args) {
        task14();
        task15();
    }

    public static void task14() {
        // Задание 14.
        //Приведите пример небольшой иерархии, где вместо некоторого
        // поля родительского класса с набором предопределённых значений (как в случае с полем female) применяется наследование.
        var linux = new Debian();
        linux.installPackage("python3");
    }

    public static void task15() {
        //Задание 15.
        //Если используемый вами язык программирования это допускает,
        // напишите примеры полиморфного и ковариантного вызовов метода.
        Calculator calculator = new EngineeringCalculator();
        Expression[] expressions = new Expression[2];
        expressions[0] = new SimpleExpression();
        expressions[1] = new ComplexExpression();
        //пример вызовы кариантного метода (справедливо только для массивов)
        calculator.covariantMethod(expressions);
        //пример вызова полиморфного метода -- передаем наследника класса Expression, а не объект класса Expression
        calculator.polymorphicMethod(new SimpleExpression());
    }
}
