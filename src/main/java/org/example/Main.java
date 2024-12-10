package org.example;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Main {

    //static = Objekt unabhängig

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Willkommen bei der Madous Bank.");

        Registration();

        Login();

    }
static int clientNumber = 1;
    static
    Scanner scanner = new Scanner(System.in);

    public static void Registration() {

        // text datei erstellen und auslesen danach nosql


        System.out.println("Bitte registrieren sie sich an.");
        System.out.println("Wie lautet ihr Name: ");
        String registerName = scanner.next();

        System.out.println("Bitt legen sie ihr Passwort fest: ");
        String registerPasswort = scanner.next();

        System.out.println("Bitte wiederholen sie das Passwort: ");
        String repeatPasswort = scanner.next();

        if(repeatPasswort.equals(registerPasswort)) {
            System.out.println("Sie haben sich erfolgreich Registriert");

            String fileName = "Client" + clientNumber + ".txt";
            File dateiClient = new File(fileName);
            try {
                // Datei sofort erstellen, wenn sie noch nicht existiert
                if (dateiClient.createNewFile()) {
                    System.out.println("Datei wurde sofort erstellt: " + dateiClient.getName());
                } else {
                    System.out.println("Datei existiert bereits: " + dateiClient.getName());
                }

                // Schreiben in die Datei
                FileWriter writer = new FileWriter(dateiClient);
                writer.write(registerName + "\n");
                writer.write(registerPasswort + "\n");

                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else {
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            Registration();
        }
    }

    public static void Login() {
        System.out.println("Nenne Sie mir Ihren Benutzername: ");
        String tryLoginName = scanner.next();

        System.out.println("Nenne Sie mir Ihr Passwort: ");
        String tryLoginPasswort = scanner.next();

        File datei = new File("C://Users//mtheele//IdeaProjects//BankingSystem//Client1.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(datei);
        } catch(FileNotFoundException e) {
            System.out.println("File not Found");
        }

        while(scan.hasNext()) {
            String clientName = scan.next();
            String clientPassword = scan.next();
            System.out.println(clientName);

        }
    }
}