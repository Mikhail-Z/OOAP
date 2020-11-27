package ru.skillsmart.ooap2.task21;

interface Material {
    /**
     * использовать материал для шитья
     */
    void sew();
}

class Wool implements Material {

    @Override
    public void sew() {

    }
}

class Cotton implements Material {

    @Override
    public void sew() {

    }
}

interface Clothes {

    /**
     * Получить материалы, из которых изготовлена вещь
     * @return Материалы, из которых изготовлена вещь
     */
    Material[] getMaterials();

    /**
     * надеть одежду
     */
    void putOn();

    int getSize();

    String getBrand();
}

class Blouses implements Clothes {

    private final Material[] materials;
    private final String brand;
    private final int size;

    public Blouses(Material[] materials, String brand, int size) {
        this.materials = materials;
        this.brand = brand;
        this.size = size;
    }

    @Override
    public Material[] getMaterials() {
        return new Material[0];
    }

    @Override
    public void putOn() {

    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }
}

class Trousers implements Clothes {

    private final Material[] materials;
    private final String brand;
    private final int size;

    public Trousers(Material[] materials, String brand, int size) {
        this.materials = materials;
        this.brand = brand;
        this.size = size;
    }

    @Override
    public Material[] getMaterials() {
        return new Material[0];
    }

    @Override
    public void putOn() {

    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }
}

class Task21 {
    public static void main(String[] args) {
        //Задание 21.
        //
        //Приведите пример иерархии, которая реализует наследование вида, и объясните, почему.

        //Примером наследования вида является наследование класса "Брюки" и "Блузки" от класса "Одежда",
        // так как одежда предполагает несколько связанных сущностей, отражающих состояние вещи.
        // Например, одежда характеризуется формой и материалом, из которого она сделана. Оба этих признака часто используются вместе.
        // Мной признак формы был выделен основным. При этом признак материала был выделен в отдельную иерархию,
        // а класс одежды "содержит" ссылку на материал, т.е. материал находится в отношении композиции с классом одежда (одежда содержит материал).
    }
}
