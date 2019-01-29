import javafx.scene.paint.Color;

public class Planet extends AstronomischesObjekt {
    double umlaufbahnDurchmesser;
    long umlaufgeschwindigkeit;

    public Planet(String name, double durchmesser, double umlaufbahnDurchmesser, long umlaufgeschwindigkiet, Color farbe) {
        this.name = name;
        this.durchmesser = durchmesser;
        this.umlaufbahnDurchmesser = umlaufbahnDurchmesser;
        this.umlaufgeschwindigkeit = umlaufgeschwindigkiet;
        this.farbe = farbe;
    }
}
