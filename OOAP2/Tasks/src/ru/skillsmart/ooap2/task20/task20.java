package ru.skillsmart.ooap2.task20;

import java.util.ArrayList;
import java.util.List;

class HttpResponse {
    private int statusCode;
    private byte[] body;

    public HttpResponse(int statusCode, byte[] body) {
        this.statusCode = statusCode;
        this.body = body;
    }
}

class FailedAmazonS3Response extends HttpResponse {
    private String key;
    private String bucketName;
    private String region;

    public FailedAmazonS3Response(String key, String bucketName, String region, int statusCode, byte[] body) {
        super(statusCode, body);
        this.key = key;
        this.bucketName = bucketName;
        this.region = region;
    }
}

enum OperationResult {
    OK,
    FAIL,
    NIL
}

class Tree<T> {
    private Node<T> root;
    private OperationResult addOperationResult;

    public Tree(T rootData) {
        root = new Node<>(rootData, null);
        addOperationResult = OperationResult.NIL;
    }

    public static class Node<T> {
        private T value;
        private Node<T> parent;
        private List<Node<T>> children;

        public Node(T value, Node<T> parent) {
            children = new ArrayList<>();
            this.value = value;
            this.parent = parent;
        }
    }

    public void add(T parent, T value) {
        Node<T> parentNode = searchNode(root, parent);
        if (!parentNode.children.contains(parent)) {
            parentNode.children.add(new Node<>(value, parentNode));
            addOperationResult = OperationResult.OK;
            return;
        }

        addOperationResult = OperationResult.FAIL;
    }

    public OperationResult getAddOperationResult() {
        return addOperationResult;
    }

    private Node<T> searchNode(Node<T> currentNode, T value) {
        if (currentNode.value == value) {
            return currentNode;
        }
        for (var node : currentNode.children) {
            var foundNode = searchNode(node, value);
            if (foundNode != null) {
                return foundNode;
            }
        }
        return null;
    }
}

class ClassDiagram extends Tree<Class> {

    public ClassDiagram(Class rootData) {
        super(rootData);
    }
}

public class task20 {
    public static void main(String[] args) {
        // Примером наследования реализации является наследование класса "диаграмма классов" от класса "дерево",
        // т.к. происходит наследование программной реализации структуры данных "дерево",
        // т.е. в данном случае нет иерархии на основе предметной области
        var objectClass = Object.class;
        var classTree = new ClassDiagram(objectClass);
        var httpResponseClass = HttpResponse.class;
        classTree.add(objectClass, httpResponseClass);

        // Примером льготного наследования является наследование от класса "FailedAmazonS3Response" от класса "HttpResponse",
        // так как неудачный ответ от хранилища Amazon S3 является по сути обычным ответом на http-запрос, который внутри себя содержит http-статус код и кое-что еще.
        // Ответ от Amazon S3 просто содержит дополнительную поясняющую информацию.
    }
}