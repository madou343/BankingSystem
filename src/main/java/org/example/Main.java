package org.example;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    //static = Objekt unabhängig

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        Initialisierung();

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

        System.out.println("Bitte legen sie ihr Passwort fest: ");
        String registerPasswort = scanner.next();

        System.out.println("Bitte wiederholen sie das Passwort: ");
        String repeatPasswort = scanner.next();

        double balance = 0.0;
        String balanceAsString = String.valueOf(balance);

        if(repeatPasswort.equals(registerPasswort)) {
            System.out.println("Sie haben sich erfolgreich Registriert");

            String csFile = "DataOfBank.csv";

            try{ // Append-Modus
                // Datenzeilen anhängen
                FileWriter writer = new FileWriter(csFile, true);
                String stringCurrenClient = String.valueOf(currentClient);
                writer.append(stringCurrenClient + "," + registerName + "," + registerPasswort + "," + balanceAsString + "\n");

                currentClient++;
                writer.flush();
                writer.close();

                System.out.println("Daten erfolgreich angehängt! \n");
            } catch (IOException e) {
                System.out.println("Fehler beim Anhängen an die Datei: " + e.getMessage() + "\n");
            }


        }else {
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            System.out.println("Die Registrierung ist gescheitert bitte probieren sie es nochmal");
            Registration();
        }
        LoginOrRegister();
    }

    public static void Login() {


        System.out.println("Nenne Sie mir Ihren Benutzername: ");
        String tryLoginName = scanner.next();

        System.out.println("Nenne Sie mir Ihr Passwort: ");
        String tryLoginPasswort = scanner.next();

        String file = "C://Users//mtheele//IdeaProjects//BankingSystem//DataOfBank.csv";
        BufferedReader reader = null;
        String line = "";
            try {
                reader = new BufferedReader(new FileReader(file));
                while((line = reader.readLine()) != null) {

                    String[] row = line.split(",");

                    if(row[1].equals(tryLoginName) && row[2].equals(tryLoginPasswort)){
                        EnterDashboard(tryLoginName, row);
                    }

                    System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

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

    public static void EnterDashboard(String tryLoginName, String[] row){
        System.out.println("Willkommen " + tryLoginName + " im Hauptmenü.");
        System.out.println("Drücken sie folgende Tasten um:");
        System.out.println("Kontostand: '1'");
        System.out.println("Einzahlung: '2'");
        System.out.println("Auszahlung: '3'");
        System.out.println("Überweisen: '4'");
        System.out.println("Ausloggen: '5'");
        int theInput = scanner.nextInt();
        String currentBalance = row[3];
        if(theInput == 1){
            System.out.println("\nIhr Kontostand beläuft sich auf " + row[3] + " €");
        } else if(theInput == 2){
            String newBalanceAmount = DepositMoney(currentBalance);
            row[3] = newBalanceAmount;
        } else if(theInput == 3){
            String newBalanceAmount = WithdrawMoney(currentBalance);
            row[3] = newBalanceAmount;
        } else if(theInput == 4){
            row[3] = TransferMoney(row[3]);
        }
        EnterDashboard(tryLoginName, row);
    }

    public static String TransferMoney(String SendersBalance){
        System.out.println("An wen möchten sie gerne Geld versenden?");
        int recipientNumber = scanner.nextInt();
        String line = "";
        BufferedReader reader = null;
        String file = "C://Users//mtheele//IdeaProjects//BankingSystem//DataOfBank.csv";
        try{
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){

                String[] rowOfRecipient = line.split(",");
                int Iban = Integer.parseInt(rowOfRecipient[0]);
                if(Iban == recipientNumber){
                    System.out.println("Wie viel Geld möchten sie gerne überweisen.");
                    double transferSum = scanner.nextDouble();
                    String showAmount = String.valueOf(transferSum);
                    double currentBalance = Double.parseDouble(SendersBalance);
                    double finalBalanceSender = currentBalance - transferSum;
                    double roundedFinalBalanceSender = Math.round(finalBalanceSender * 100) / 100.0;
                    SendersBalance = String.valueOf(roundedFinalBalanceSender);

                    double balanceOfRecipient = Double.parseDouble(rowOfRecipient[3]);
                    double finalBalanceRecipient = balanceOfRecipient + transferSum;
                    double roundedFinalBalanceRecipient = Math.round(finalBalanceRecipient * 100) / 100.0;
                    rowOfRecipient[3] = String.valueOf(roundedFinalBalanceRecipient);
                    System.out.println("Sie haben erfolgreich " + showAmount + " €" + " versendet.");
                }

            }
        } catch  (Exception e) {
            e.printStackTrace();
        }
        return SendersBalance;
    }

    public static String DepositMoney(String balanceAsString) {
        System.out.println("Wie viel Geld möchten sie einzahlen?");
        double scan = scanner.nextDouble();
        String deopositString = String.valueOf(scan);
        double balanceAsDouble = Double.parseDouble(balanceAsString);
        double totalBalance = balanceAsDouble + scan;
        String returnStringTotalBalance = String.valueOf(totalBalance);
        System.out.println("\nSie haben erfolgreich " + deopositString + " €"  + " eingezahlt.\n");
        return returnStringTotalBalance;
    }

    public static String WithdrawMoney(String balanceAsString) {
        System.out.println("Wie viel Geld möchten sie sich auszahlen?");
        String returnStringTotalBalance = balanceAsString;
        double scan = scanner.nextDouble();
        if(scan <= 1000) {
            double balanceAsDouble = Double.parseDouble(balanceAsString);
            double totalBalance = balanceAsDouble - scan;
            double roundedBalance = Math.round(totalBalance * 100) / 100.0;
            returnStringTotalBalance = String.valueOf(roundedBalance);
            System.out.println("\nIhr Kontostand nach der auszahlung beläuft sich auf " + returnStringTotalBalance + " €");
        }else{
            System.out.println("Sie können maximal 1000 " + " €" + " abheben.");
            WithdrawMoney(balanceAsString);
        }
        return returnStringTotalBalance;
    }

    public static void Initialisierung() {

            String fileName = "DataOfBank.csv";

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
                writer.write("0," + "MadousBank,");
                writer.write("einszwei,");

                writer.write("100.0\n");


                writer.flush();
                writer.close();
                LoginOrRegister();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}