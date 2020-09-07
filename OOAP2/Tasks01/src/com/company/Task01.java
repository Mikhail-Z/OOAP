package com.company;

import java.util.LinkedList;
import java.util.List;

//Напишите небольшой пример кода с комментариями, где применяются наследование, композиция и полиморфизм.
class Task01 {
    static void doTask() {
        var enterprises = new LinkedList<Enterprise>();
        enterprises.add(new Bank());
        enterprises.add(new CarFactory());
        for (var enterprise : enterprises) {
            //пример полиморфизма (для клиентского кода работа с сущностями разных классов одинакова)
            enterprise.doWork();
        }
    }
}

abstract class Enterprise {
    protected List<Employee> employees;

    abstract void doWork();
}

//пример наследования (автомобильный завод является предприятием)
class CarFactory extends Enterprise {
    @Override
    void doWork() {
        System.out.println("making machines");
        useConveyor();
    }

    //пример инкапсуляции (метод предназначен только для внутреннего кода - клиентскому коду о нем знать не нужно)
    private void useConveyor() {

    }
}

//пример наследования (банк является предприятием)
class Bank extends Enterprise {
    @Override
    void doWork() {
        System.out.println("regulating payments");
    }
}


class Employee {
    private String name;
    private String position;

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
