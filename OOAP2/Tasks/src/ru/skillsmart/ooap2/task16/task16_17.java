package ru.skillsmart.ooap2.task16;

import java.util.Set;

enum Format {
    Italic,
    Bold,
    Underline
}

class Text {
    private String text;

    public void add(char c) {

    }

    public void insert(char c, int pos) {

    }

    public void remove(char c) {

    }

    public void remove(char c, int pos) {

    }
}

class UnderLineText extends Text {

}

class BoldText extends Text {

}

class FormattedText {
    private Text text;
    private Set<Format> formats;

    public FormattedText(Text text) {
        this.text = text;
    }

    public void addFormat(Format format) {
        formats.add(format);
    }

    public void deleteFormat(Format format) {
        formats.add(format);
    }
}

class Processor implements Comparable<Processor> {
    private int frequencyMhz;

    public Processor(int frequencyMhz) {
        this.frequencyMhz = frequencyMhz;
    }

    public int getFrequencyMhz() {
        return frequencyMhz;
    }

    public void setFrequencyMhz(int frequencyMhz) {
        this.frequencyMhz = frequencyMhz;
    }

    @Override
    public int compareTo(Processor o) {
        if (this.frequencyMhz < o.frequencyMhz) {
            return -1;
        } else if (this.frequencyMhz > o.frequencyMhz) {
            return 1;
        }

        return 0;
    }
}

class VideoCard implements Comparable<VideoCard> {
    private int memoryMB;

    public VideoCard(int memoryMB) {
        this.memoryMB = memoryMB;
    }

    public int getMemoryMB() {
        return memoryMB;
    }

    public void setMemoryMB(int memoryMB) {
        this.memoryMB = memoryMB;
    }

    @Override
    public int compareTo(VideoCard o) {
        if (this.memoryMB < o.memoryMB) {
            return -1;
        } else if (this.memoryMB > o.memoryMB) {
            return 1;
        }

        return 0;
    }
}

class ROM implements Comparable<ROM> {
    private float readSpeed;

    public ROM(float readSpeed) {
        this.readSpeed = readSpeed;
    }

    public float getReadSpeed() {
        return readSpeed;
    }

    public void setReadSpeed(float readSpeed) {
        this.readSpeed = readSpeed;
    }

    @Override
    public int compareTo(ROM o) {
        if (this.readSpeed < o.readSpeed) {
            return -1;
        } else if (this.readSpeed > o.readSpeed) {
            return 1;
        }

        return 0;
    }
}

enum OperationResult {
    OK,
    ERR,
    NIL
}

abstract class Computer {

    private Processor processor;
    private VideoCard videoCard;
    private ROM rom;

    public Computer(Processor processor, VideoCard videoCard, ROM rom) {
        this.processor = processor;
        this.videoCard = videoCard;
        this.rom = rom;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public VideoCard getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(VideoCard videoCard) {
        this.videoCard = videoCard;
    }

    public ROM getRom() {
        return rom;
    }

    public void setRom(ROM rom) {
        this.rom = rom;
    }
}

class NoteBook extends Computer {

    public NoteBook(Processor processor, VideoCard videoCard, ROM rom) {
        super(processor, videoCard, rom);
    }
}

class ComputerDetailsManager {

    private Processor minRequiredProcessor;
    private VideoCard minRequiredVideoCard;
    private ROM minRequiredROM;

    public ComputerDetailsManager(Processor minRequiredProcessor, VideoCard minRequiredVideoCard, ROM minRequiredROM) {
        this.minRequiredProcessor = minRequiredProcessor;
        this.minRequiredVideoCard = minRequiredVideoCard;
        this.minRequiredROM = minRequiredROM;
    }

    public boolean areMinRequiredComponentsWeakerThan(Processor processor, VideoCard videoCard, ROM rom) {
        var result = minRequiredProcessor.compareTo(processor) + minRequiredVideoCard.compareTo(videoCard) + minRequiredROM.compareTo(rom);
        if (result <= 0) {
            return true;
        }

        return false;
    }
}

class GamingComputer {

    private Computer computer;
    private ComputerDetailsManager manager = new ComputerDetailsManager(
            new Processor(3000), new VideoCard(4096), new ROM(1000));

    public GamingComputer(Computer computer) {
        this.computer = computer;
    }

    public void editVideos() {
        if (!manager.areMinRequiredComponentsWeakerThan(
                computer.getProcessor(), computer.getVideoCard(), computer.getRom())) {

            throw new UnsupportedOperationException();
        }

        System.out.println("editing videos...");
    }

    public void playGames() {
        if (!manager.areMinRequiredComponentsWeakerThan(
                computer.getProcessor(), computer.getVideoCard(), computer.getRom())) {

            throw new UnsupportedOperationException();
        }

        System.out.println("playing games...");
    }
}

class Vegetable {

}

class Tomato extends Vegetable  {

}

class Animal {

}

class Hare extends Animal {

}


public class task16_17 {
    public static void main(String[] args) {
        task16();
        task17();
    }

    // Задание 16.
    //Приведите два словесных примера отношения "содержит" между классами, которое похоже на "является", но по вышеупомянутому правилу таковым не является.
    public static void task16() {
        //1. Есть класс, соответствующий тексту (Text). Пусть есть необходимость применения к нему форматирования (подчеркивание, жирность, курсив).
        //Создание отдельных классов, соответствующих каждому форматированию, на первый взгляд подходит под отношение "есть",
        //так как "курсивный текст" есть "текст", однако разновидностей форматирования существуюе множество, поэтому правильней будет
        //иметь отдельный класс, соответствующий форматированному тексту, в внутри держать поля, соответствующий нужной комбинации форматирования.
        //поэтому вместо кучи классов вида "UnderLineText" и "BoldText", лучше иметь один класс, связанный с классом "текст" через композицию.

        //2. Есть класс Компьютер (Computer). Также существуют специальные игровые компьютеры, предназначенные для игр и видеомантажа.
        // Также существуют ноутбуки -- один из видов компьютера наряду со стационарным компьютером. Также существуют игровые ноутбуки.
        var noteBook = new NoteBook(
                new Processor(4000), new VideoCard(8192), new ROM(1200));
        var computer = new GamingComputer(noteBook);
        computer.playGames();
    }

    public static void task17() {
        // Задание 17.
        //Приведите два словесных примера отношения "является" между классами, которое однозначно таковым является
        // и не может быть переведено в отношение "содержит".
        //примерами являются иерархии Vegetable-Tomato (овощь - томат) и Animal-Hare (животное - заяц)
    }
}