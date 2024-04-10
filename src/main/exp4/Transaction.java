package main.exp4;

import java.util.ArrayList;
import java.util.List;

class Transaction {
    int id;
    long timestamp;
    List<Operation> operations = new ArrayList<>();

    public Transaction(int id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public List<Operation> getOperations() {
        return operations;
    }
}