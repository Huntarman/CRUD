package App;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

import Exceptions.MyException;
import File.FileReaderWriter;
public class ClientInterface {

    public static void main(String[] args) throws MyException {


        System.out.println("Hello, Client!\n Input name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        File filename = new File("nextIDClient.txt");
        int ID = FileReaderWriter.readNextID(filename);

        String filetoWorkername = "ClientWorker" + ID + ".txt";
        File filetoWorker = new File(filetoWorkername);
        FileReaderWriter.writeNextID(FileReaderWriter.readNextID(filename),filename);

        boolean end = false;
        boolean ProductBeingReturned = false;
        boolean canExpectProduct = false;
        LocalDateTime dateOfReturn = null;

        while (!end){
            System.out.println(FileReaderWriter.getTime());
            System.out.println("Welcome "+ name + "|| Your ID is : " + ID);
            System.out.println("You're a client");
            System.out.println("Co chcesz zrobic?");
            System.out.println("1 - Reklamuj produkt");
            System.out.println("2 - Sprawdz status reklamacji");
            System.out.println("3 - Odbierz towar");
            System.out.println("4 - Wyjdz");
            System.out.println("5 - pass time");
            int a;
            a = scanner.nextInt();

            switch (a){
                case 1:
                        if (!ProductBeingReturned) {
                            System.out.println("Podaj Id reklamowanego produktu (1-2)");
                            int IDProdukt = scanner.nextInt();
                            System.out.println("Podaj nazwe produktu");
                            String Productname = scanner.next();
                            String value = 1 + ";" + ID + ";" + IDProdukt + ";" + Productname + ";" + FileReaderWriter.getTime();
                            FileReaderWriter.writeToFile(value, filetoWorker);
                            ProductBeingReturned = true;
                            TimePass();
                        } else {
                            System.out.println("Inny produkt jest w trakcie reklamacji");
                        }
                    break;

                case 2:
                    String[] oper = FileReaderWriter.readFirstLineFromFile(filetoWorker).split(";");
                    try {
                        isComplaintinProcess(ProductBeingReturned);
                        if (oper[0].equals("2")) {
                            if (oper[1].equals("accepted")) {
                                System.out.println("Twoja reklamacja zostala zaakceptowana :)");
                                System.out.println("Twoj nowy produkt: " + oper[2] + "; " + oper[3] + "bedzie gotowy do odebrania " + oper[4]);
                                dateOfReturn = LocalDateTime.parse(oper[4]);
                            } else {
                                System.out.println("Twoja reklamacja zostala odrzucona");
                                System.out.println("Twoj produkt: " + oper[2] + ";" + oper[3] + "zostal tobie odeslany");
                                FileReaderWriter.writeToFile("3;", filetoWorker);
                                dateOfReturn = LocalDateTime.parse(FileReaderWriter.getTime());
                            }
                            canExpectProduct = true;
                            TimePass();
                        } else if (ProductBeingReturned) {
                            System.out.println("Twoja reklamacja jest w trakcie weryfikacji");
                        }
                    }catch (Exception e){
                        System.out.println("Something went wrong " + e);
                    }
                    break;

                case 3:
                    try{
                        canGetReturned(canExpectProduct,filetoWorker,dateOfReturn);
                        ProductBeingReturned = false;
                    }catch (Exception e){
                        System.out.println("Something went wrong " + e);
                    }
                    break;

                case 4:
                    end = true;TimePass();
                    break;

                case 5:
                    TimePass();
                    break;

                default:
                    System.out.println("Nieprawidlowe dzialanie");
                    break;
            }

        }
    }
    public static void canGetReturned(boolean yesorno, File filename, LocalDateTime dateOfReturn) throws MyException{
        if (yesorno){
            if (LocalDateTime.parse(FileReaderWriter.getTime()).isAfter(dateOfReturn)){
                System.out.println("Towar został przyjety");
                FileReaderWriter.writeToFile("3;",filename);
                TimePass();
            }
            else{
                throw new MyException("Towar nie jest jeszcze gotowy do odebrania");
            }
        }

    }
    public static void isComplaintinProcess(boolean a) throws MyException {
        if (a == false){
            throw new MyException("Nic zlozyles jeszcze reklamacji.");
        }
    }
    public static void TimePass(){
        //simulates passage of time
        Random rand = new Random();
        FileReaderWriter.changeTime(rand.nextInt(2),rand.nextInt(5)+1,rand.nextInt(40),rand.nextInt(60));
    }

}
