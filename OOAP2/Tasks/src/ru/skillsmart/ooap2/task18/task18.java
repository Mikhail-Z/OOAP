package ru.skillsmart.ooap2.task18;

class Scales {
    int a;


}

class LaboratoryScales extends Scales {

}

class CommercialScales extends Scales {

}

class Rectangle {
    protected int length;
    protected int width;

    public Rectangle(int length, int width) {
        this.length = length;
        this.width = width;
    }
}

class Square extends Rectangle {
    public Square(int length) {
        super(length, length);
    }
}

class SurfingBoard {
    void surfByWaves() {
        System.out.println("surfing by wind...");
    }
}

class JetBoard extends SurfingBoard {
    void surfByElectricEngine() {
        System.out.println("surfing by wind...");
    }
}


public class task18 {

    public static void main(String[] args) {
        task18();
    }

    //Задание 18.
    //Приведите словесные примеры случаев наследования подтипов, с ограничением и с расширением.
    static void task18() {
        //Наследование подтипов -- примером является отношение между абстрактными весами и различными типами весов по их применению, например (лабораторные, товарные и т.д.)
        // Scales -- LaboratoryScales, CommercialScales

        //наследование с ограничением -- примером является отношение между прямоугольником и квадратом (важным частным случаем прямоугольника).
        //Rectangle -- Square

        //наследование с раширением -- примером является иерархия обычной доски для серфинга и доски для серфинга с двигателем,
        // позволяющим передвигаться по водной глади без волн (SurfingBoard -- JetBoard)
    }
}
