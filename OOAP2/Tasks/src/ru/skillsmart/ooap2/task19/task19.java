package ru.skillsmart.ooap2.task19;

import ru.skillsmart.ooap2.task11_12.Any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class HttpRequest {
    void doRequest(String body, Map<String, String> headers, String url) {
        dataInterchange(body, headers, url);
    }

    protected void dataInterchange(String body, Map<String, String> headers, String url) {
        System.out.println("");
    }
}

class HttpsRequest extends HttpRequest {
    protected String secretKey;

    @Override
    void doRequest(String body, Map<String, String> headers, String url) {
        handshake(url);
        var encryptedBody = encrypt(body, this.secretKey);
        super.doRequest(encryptedBody, headers, url);
    }

    private void handshake(String url) {
        var certificate = getServerCertificate(url);

        var canTrust = checkCertificateInCertificateAuthority(certificate);
        if (canTrust) {
            var publicKey = getPublicKeyFromCertificate(certificate);
            this.secretKey = createSecretKey();
            var encryptedSecretKey = encrypt(secretKey, publicKey);
            super.dataInterchange(encryptedSecretKey, null, url);
        }
    }

    private String createSecretKey() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
    }

    private String encrypt(String text, String key) {
        return text; //тут должны быть еще дополнительные действия, но я их опущу
    }

    private String getPublicKeyFromCertificate(String certificate) {
        return certificate.substring(0, 10);
    }

    private String getServerCertificate(String url) {
        //заглушка
        return "some certificate";
    }

    private boolean checkCertificateInCertificateAuthority(String certificate) {
        //здесь должна быть более сложная логика
        return true;
    }
}

class Match {
    private boolean isFired;

    public boolean isFired() {
        return isFired;
    }

    public void setFired() {
        isFired = true;
    }
}

class GasStove {
    private boolean isGasTurnedOn;
    private boolean isGasFired;

    public void turnOn(Match match) {
        match.setFired();
        turnOnGas();
        isGasFired = true;
    }

    protected void turnOnGas() {
        isGasTurnedOn = true;
    }
}

class ElectricGasStove extends GasStove {
    //@Override
    public void turnOn() {
        turnOnGas();
        turnOnElectricIgnition();
    }

    private void turnOnElectricIgnition() {

    }
}

abstract class BackgroundService {

    public void start() throws InterruptedException {
        while (true) {
            process();
            TimeUnit.MINUTES.sleep(1);
        }
    }

    public abstract void process();
}

final class PrintNameBackgroundService extends BackgroundService {

    @Override
    public void process() {
        System.out.println("my name is PrintNameBackgroundService");
    }
}

enum OperationResult {
    SUCCESS,
    FAILURE
}

abstract class Summable extends Any {
    abstract void sum(Summable number);
    abstract int getLength();
    abstract OperationResult getSumOperationResult();
    abstract List<Summable> getValues();
}

class MyInt extends Summable {
    private OperationResult addOperationResult;

    int value;

    public MyInt(int value) {
        this.value = value;
    }

    @Override
    void sum(Summable number) {
        if (number.getLength() != this.getLength()) {
            return ;
        }

        MyInt myInt = new MyInt(0);
        myInt = Any.assignmentAttempt(number, myInt);
        if (myInt == null) {
            addOperationResult = OperationResult.FAILURE;
            return;
        }

        this.value += myInt.value;
        addOperationResult = OperationResult.SUCCESS;
    }

    @Override
    int getLength() {
        return 1;
    }

    @Override
    OperationResult getSumOperationResult() {
        return this.addOperationResult;
    }

    @Override
    List<Summable> getValues() {
        var list = new ArrayList<Summable>();
        list.add(new MyInt(this.value));
        return list;
    }
}


public class task19 {
    public static void main(String[] args) {
        //примером наследования с вариаций типа является иерархия обычной газовой плиты и газовой плиты с электоподжигом
        // GasStove -- ElectricGasStove, так как процесс ее зажигания не требует спичек

        //примером наследования с функциональной вариацией -- иерархия классов HttpRequest и HttpsRequest,
        // так как https запрос помимо самой перадачи данных требует предварительно создать ключ сессии, которым шифруются данные

        //примером наследования c конкретизацией является иерархия абстрактного BackgroundService
        // и реализции PrintNameBackgroundService, реализующего основную логику

        //примером структурного наследования можно считать наследование класса от интерфейса Summable, который добавляет возможность суммирования в класс.
        // Это может быть полезно для работы с разными математическими понятиями, поддерживающими операцию суммы, например скалярами (MyInt) и векторами (Vector).
    }
}
