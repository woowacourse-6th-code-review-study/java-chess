package db.connection;

public enum ConnectionConst {

    SERVER("localhost:13306"),
    OPTION("?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"),
    USERNAME("root"),
    PASSWORD("root");

    private final String value;

    ConnectionConst(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
