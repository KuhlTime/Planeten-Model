import javafx.scene.paint.Color;

public class Sternsystem {
    String name;
    Stern stern;
    Planet[] planeten;

    // constructor
    public Sternsystem(String name, Stern stern, Planet[] planeten) {
        this.name = name;
        this.stern = stern;
        this.planeten = planeten;
    }

    static Sternsystem sonnensystem;

    static long secPerDay = 24*60*60;

    static {
        sonnensystem = new Sternsystem(
                "Sonnensytem",
                new Stern("Sonne", 1392684, Color.YELLOW),
                new Planet[]{
                        new Planet("Merkur", 4879.4, 57909E3, 88*secPerDay, Color.GRAY),
                        new Planet("Venus", 12103.6, 108160E3, 225*secPerDay, Color.TAN),
                        new Planet("Erde", 12756.3, 1496E5, 365*secPerDay, Color.TURQUOISE),
                        new Planet("Mars", 6792.4, 22799E4, 687*secPerDay, Color.INDIANRED),
                        new Planet("Jupiter", 142984, 77836E5, 4329*secPerDay, Color.TAN),
                        new Planet("Saturn", 120536, 14335E5, 10751*secPerDay, Color.TAN),
                        new Planet("Uranus", 51118, 28724E5, 30664*secPerDay, Color.LIGHTSTEELBLUE),
                        new Planet("Neptun", 49528, 44984E5, 60148*secPerDay, Color.MIDNIGHTBLUE)
                });
    }

    /*Planet größterPlanet() {
        Planet planet;

    }*/
}
