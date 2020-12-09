package ru.skillsmart.ooap2.task23;

import java.util.UUID;

class Good {
    private int price;

    public Good(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

class Furniture extends Good {
    private int length;
    private int height;
    private int width;

    public Furniture(int price, int length, int height, int width) {
        super(price);
        this.length = length;
        this.height = height;
        this.width = width;
    }
}

class Table extends Furniture {
    private String type;

    public Table(int price, int length, int height, int width, String type) {
        super(price, length, height, width);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

//--------------------------------------------------------------------------------------------

abstract class Entity {
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}

class Book extends Entity {

    private int pagesCount;

    public Book(String name, int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getPagesCount() {
        return pagesCount;
    }
}

class Toy extends Entity {

    private String material;

    public Toy(String name, String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }
}

public class Main {
    public static void main(String[] args) {
        //Задание 23.
        //
        //Приведите словесные примеры применения абстрагирования и факторизации.

        //Примером абстрагирования является является выделение товара как более общей сущности для мебели (Good - Furniture - Table). Например, если заведение
        // было изначально выставочным центром с мебелью, однако затем перепрофилировалось в мебельный магазин,
        // то у каждого экспаната появилась цена. Возможно, попимо мебели в будущем будут продаваться и другие товары.

        //Игрушка и книга -- общего между ними ничего нет. Однако для операций над этими объектами в БД понадобилось иметь
        // у каждого объекта идентификатор. Поэтому был введен класс некой абстрактной сущности, имеющей лишь поле,
        // соответствующее суррогатному ключу в БД. Это пример факторизации (Entity - Book and Toy).
    }
}
