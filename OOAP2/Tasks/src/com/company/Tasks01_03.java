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

//----------------------------------------------------------------------------------------

//Задание 3.
//Расскажите, как в выбранном вами языке программирования поддерживается концепция "класс как модуль".

//В java и c# самым базовым является класс Object, определяющий базовые операции, применимые ко всем объектам (пребразование в текстовый формат, равенство, хэш-код объекта, клонирование (поверхностное))
//Создавая новые классы, мы неявно наследуемся от этого класса, т.е. опираемся на существующий модуль, благодаря чему реализовывать методы перечисленные выше уже не нужно, если их поведение устраивает.
//Java и c# запрещают множественное наследование, возможно из-за неопределенности при вызове метода родительского класса в наследнике

//Чтобы методы и поля класса родителя были видны в наследнике и их можно было при желании переопределить в наследнике, нужно использовать модификатор доступа protected или public.

// Можно запретить наследование, оставив класс доступным клиентскому коду, использовав модификато public final (final запрещает наследование)

//Для задания каркаса поведения используются абстрактные классы, когда часть поведения нужно реализовать в наследниках.
//Однако разрешается реализовывать несколько интерфейсов, чтобы класс можно было использовать в разных частях клиентетского кода, который бы видел только предназначенные для него методы.