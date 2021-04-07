module Model {
    requires com.google.common;
    requires slf4j.api;
    requires java.sql;

    opens model;
    exports model;
    exports model.exceptions;
}
