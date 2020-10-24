package ru.skillsmart.ooap2.task09_10;

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
}

/*public*/ class Any extends General {

}

class Test extends Any {
    public int a = 1;
    public String b = "test";

    public Test2 test2 = new Test2();
}

class Test2 extends Any {
    public int a = 2;
    public String b = "test2";


    //cannot override: overridden method is final
    /*@Override
    public Class<?> getType() {
        return super.getType();
    }*/
}

class Task09_10 {
    public static void main(String[] args) {

    }

    /*
    Постройте в вашем языке программирования базовую иерархию из двух классов General и Any.
    Унаследуйте General от универсального базового класса, если таковой имеется в языке или стандартной
    библиотеке/фреймворке, и реализуйте семь фундаментальных операций для него, используя для этого по возможности
    возможности стандартных библиотек.
     */
    private static void Task09() {
        try {
            var test = new Test();
            Test test2 = test.deepClone();
            System.out.println(test2);

            test2.deepCopy(test);

            Class<?> type = test2.getType();

            var json = test2.serialize();
            var originalTest2 = Any.deserialize(json, type);

            System.out.println(originalTest2 instanceof Test); //true
            System.out.println(originalTest2 instanceof Any); //true
            System.out.println(originalTest2 instanceof Test2); //false
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    Выясните, имеется ли в вашем языке программирования возможность запрета переопределения методов в потомках,
    и приведите пример кода.
     */
    private static void Task10() {
        //метод getType объявлен финальным -- поэтому переопределить его в классе-наследнике нельзя
    }
}
