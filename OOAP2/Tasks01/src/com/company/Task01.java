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

//-------------------------------------------------------------------------------------
//Напишите небольшой пример кода с комментариями, где в наследовании применяется как расширение класса-родителя, так и специализация класса-родителя.
class Task02 {

}

//пример наследования как расширения класса-родителя
class PhotoCamera {
    public void makePhoto() {
        System.out.println("making photo");
    }
}

//наследник расширяет и частично изменяет поведение родителя
class VideoCamera extends PhotoCamera {
    public void makeVideo() {
        System.out.println("making video");
    }

    @Override
    public void makePhoto() {
        System.out.println("changing mode...");
        super.makePhoto();
    }
}

//пример наследования как специализации, когда базовый класс задает каркас поведения
abstract class BackgroundService {
    private int delaySec;

    public BackgroundService(int delaySec) {
        this.delaySec = delaySec;
    }

    public void start() {
        while (true) {
            process();
            try {
                Thread.sleep(delaySec * 1000);
            }
            catch (InterruptedException ex) {
                break;
            }
        }
    }

    protected abstract void process();
}

//а дочерний класс уточняет, конкретизирует поведение
class EmailSendingBackgroundService extends BackgroundService {

    public EmailSendingBackgroundService(int delaySec) {
        super(delaySec);
    }

    @Override
    protected void process() {
        System.out.println("sending emails");
    }
}

