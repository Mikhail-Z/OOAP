package ru.skillsmart.ooap2.task11_12;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;


class General implements Serializable {
    public <T> void deepCopy(T target) throws Exception {
        try {
            target = getCopy();
        } catch (Exception e) {
            throw e;
        }
    }

    public <T> T deepClone() throws Exception {
        try {
            return getCopy();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public <T> String serialize() throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsString((T)this);
    }

    public static <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    // использовать в коде структуру valus instanceof
    /*public <T> boolean isType() {
        Class<T> type;
        if (type.isInstance(this)) {

        }
        return this instanceof T;
    }*/

    @JsonIgnore
    public final Class<?> getType() {
        return this.getClass();
    }

    private <T> T getCopy() throws Exception  {
        try {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            var objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject((T)this);
            var bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            var objectInputStream = new ObjectInputStream(bais);

            return (T) objectInputStream.readObject();
        }
        catch (Exception e) {
            throw e;
        }
    }


    public static <TFrom extends Any, TTo extends Any>
            TTo assignmentAttempt(TFrom from, TTo to) {

        var classFrom = from.getType();
        var classTo = to.getType();
        if (classTo.isAssignableFrom(classFrom)) {
            return (TTo) from;
        }
        //в java в отличие от python или c++ нет множественного наследования, поэтому возвращаю null,
        // a не None (который тоже здесь описан)
        return null;
    }
}

/*public*/ class A extends Any {
    private int a;
    private int b;
    private int c;
    private int d;
    //метод публичен в родительском классе А и публичен в его потомке B;
    public int getA() {
        return this.a;
    }

    //метод скрыт в родительском классе А и публичен в его потомке B;
    protected int getB() {
        return this.b;
    }

    //метод публичен в родительском классе А и скрыт в его потомке B -- такая видимость методов в иерархии невозможно,
    // так как в классах-наследниках привилегия доступа у метода должны быть такой же или более слабой
    public int getC() {
        return this.c;
    }

    //метод скрыт в родительском классе А и скрыт в его потомке B.
    protected int getD() {
        return this.d;
    }
}

class B extends A {
    @Override
    public int getA() {
        return super.getA() + 1;
    }

    @Override
    public int getB() {
        return this.getB() + 2;
    }

    /*@Override
    private int getC() {
        return super.getC();
    }*/

    @Override
    protected int getD() {
        return super.getD() + 4;
    }
}

class C extends Any {

}

public class Any extends General {

}


final class None extends Any/*A, B,....*/ {

}

class Test11 {
    public static Any getSome() {
        return new None();
    }

    public static void setSome(Any any) {
        if (any instanceof None) {
            System.out.println("wrong value!!!");
        }
    }
}

class Task10_12 {
    public static void main(String[] args) {
       task10();
       task11();
       task12();
    }

    /*
     Если используемый вами язык программирования допускает множественное наследование,
     постройте небольшую иерархию, используя уже готовые General и Any,
     и замкните её снизу классом None. Приведите пример полиморфного использования Void.
     */
    private static void task10() {
        Any any = Test11.getSome();
        if (any instanceof None) {
            System.out.println("yes!!!");
        }
        Test11.setSome(any);
    }

    /*
     Добавьте в классы General и Any попытку присваивания и её реализацию
     */
    private static void task11() {
        A a = new A();
        B b = new B();
        a = Any.assignmentAttempt(b, a);
        System.out.println(a instanceof B); //true
        a = new A();
        b = new B();
        b = Any.assignmentAttempt(a, b); //null
        System.out.println(b instanceof A); //false
    }

    /*
    Разберитесь, какие из четырёх вариантов скрытия методов доступны в используемом вами языке программирования.
    Приведите примеры кода для каждого из доступных вариантов.
     */
    private static void task12() {
        //Примеры приведены классами A и B
    }
}
