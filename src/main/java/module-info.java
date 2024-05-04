module surya.project.eb_swsmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jdk.httpserver;
    requires java.desktop;

    opens surya.project.eb_swsmaven to javafx.fxml;
    exports surya.project.eb_swsmaven;
}