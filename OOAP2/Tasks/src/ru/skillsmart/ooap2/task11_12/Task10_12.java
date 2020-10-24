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
            TTo assignmentAttempt(TFrom from, Class<TTo> classTo) {

        var classFrom = from.getType();
        if (classTo.isAssignableFrom(classFrom)) {
            return (TTo) from;
        }
        return null;
    }
}

/*public*/ class A extends Any {

}

class B extends A {

}

class C extends Any {

}

class Any extends General {

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

public class Task10_12 {
    public static void main(String[] args) {
       task10();
       task11();
    }

    private static void task10() {
        Any any = Test11.getSome();
        if (any instanceof None) {
            System.out.println("yes!!!");
        }
        Test11.setSome(any);
    }

    private static void task11() {
        A a;
        B b = new B();
        a = Any.assignmentAttempt(b, A.class);
        System.out.println(a instanceof B);
        a = new A();
        b = Any.assignmentAttempt(a, B.class);
        System.out.println(b instanceof A);
    }

    private static void task12() {

    }
}
