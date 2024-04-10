package main.exp4;

class Operation {
    public enum Type {
        READ, WRITE
    }

    private Type type;
    private String dataItem;

    public Operation(Type type, String dataItem) {
        this.type = type;
        this.dataItem = dataItem;
    }

    // Getters
    public Type getType() {
        return type;
    }

    public String getDataItem() {
        return dataItem;
    }
}