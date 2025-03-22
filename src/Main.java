import db.Database;
import db.exception.EntityNotFoundException;
import example.Human;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Human[] humans = {
                new Human("Gholi"),
                new Human("Jamshid"),
                new Human("Akbar"),
        };

        System.out.println("#### Test add method ####");

        for (Human h : humans) {
            System.out.println("Adding " + h.name + " to the database.");
            Database.add(h);
        }

        for (Human h : humans) {
            System.out.println("Id of \"" + h.name + "\" is " + h.id + ".");
        }

        System.out.println();
        System.out.println("#### Test get method ####");

        UUID gholiId = humans[0].id;
        Human gholi = null;
        try {
            gholi = (Human) Database.get(gholiId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("successfully got " + gholi.name + " from the database.");

        System.out.println();
        System.out.println("#### Test update method ####");

        gholi.name = "Gholi Mohammadi";
        try {
            Database.update(gholi);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Human gholiAgain = null;
        try {
            gholiAgain = (Human) Database.get(gholiId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Updated name: \"" + gholiAgain.name + "\".");

        System.out.println();
        System.out.println("#### Test delete method ####");

        UUID jamshidId = humans[1].id;
        try {
            Database.delete(jamshidId);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Human jamshid = (Human) Database.get(jamshidId);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}