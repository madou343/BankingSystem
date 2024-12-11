package org.example;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Main {

    //static = Objekt unabhängig

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Willkommen bei der Madous Bank.");
        LoginOrRegister();

    }
  
    static
    Scanner scanner = new Scanner(System.in);


    public static void Registration() {

        // text datei erstellen und auslesen danach nosql


        System.out.println("Bitte registrieren sie sich.");
        System.out.println("Wie lautet ihr Name: ");
        String registerName = scanner.next();

        System.out.println("Bitt legen sie ihr Passwort fest: ");
        String registerPasswort = scanner.next();

        System.out.println("Bitte wiederholen sie das Passwort: ");
        String repeatPasswort = scanner.next();

        double balance = 0.0;
        String balanceAsString = String.valueOf(balance);

        if(repeatPasswort.equals(registerPasswort)) {
            System.out.println("Sie haben sich erfolgreich Registriert");

            clientNumber++;
            String fileName = "Client" + clientNumber + ".txt";
            double accBalance = 0.0;
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

                writer.write(balanceAsString);


                writer.flush();
                writer.close();
                LoginOrRegister();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else {
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            System.out.println("Die Registrierung ist gescheitert bitte probieren sie es nochmal");
            Registration();
        }
    }

    public static void Login() {


        System.out.println("Nenne Sie mir Ihren Benutzername: ");
        String tryLoginName = scanner.next();

        System.out.println("Nenne Sie mir Ihr Passwort: ");
        String tryLoginPasswort = scanner.next();

        String clientNumberString = String.valueOf(clientNumber);
        if(clientNumber > 0){
        for(int i = 0; i < clientNumber; i++) {


            File datei = new File("C://Users//mtheele//IdeaProjects//BankingSystem//Client" + clientNumberString + ".txt");
            Scanner scan = null;
            try {
                scan = new Scanner(datei);
            } catch (FileNotFoundException e) {
                System.out.println("File not Found");
                System.out.println(clientNumberString);
            }

            while (scan.hasNext()) {
                String clientName = scan.next();
                String clientPassword = scan.next();
                if (clientName.equals(tryLoginName) && clientPassword.equals(tryLoginPasswort)) {

                    currentClient = clientNumber;
                    String getBalance = scan.next();
                    System.out.println("Guten Tag " + clientName);
                    System.out.println("Ihr Vermögen beläuft sich auf " + getBalance + " €");
                    // Here I will write some methods like showing balance or transfer money
                }
            }
        }
        } else{
            System.out.println("Es wurde noch kein Konto angelegt:(");
            Registration();
        }
    }
    static int clientNumber = 0;
   static int currentClient =1;
    public static void LoginOrRegister() {
        System.out.println("Wenn sie sich einloggen möchten drücken sie die '1' \nWenn sie sich bei uns registrieren möchten dann drücken sie die '2'. ");
        int loginOrRegister = scanner.nextInt();
        if(loginOrRegister == 1){
            Login();
        } else if(loginOrRegister == 2){
            Registration();
        }
    }
}