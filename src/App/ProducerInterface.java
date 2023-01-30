package App;

import Data.ComplaintInfo;
import Exceptions.MyException;
import File.FileReaderWriter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ProducerInterface {
    public static void main(String[] args){
        System.out.println("Hello, Producer!");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        File filename = new File("nextIDProducer.txt");
        int ID = FileReaderWriter.readNextID(filename);
        FileReaderWriter.writeNextID(FileReaderWriter.readNextID(filename),filename);

        File WorkerProducerFileName = new File("WorkerProducer"+ID+".txt");
        ArrayList<ComplaintInfo> ComplaintList = new ArrayList<>();
        int i=0;
        String[] commandLine;
        boolean end = false;
        while (!end){
            System.out.println(FileReaderWriter.getTime());
            System.out.println("Welcome "+ name + "|| Your ID is : " + ID);
            System.out.println("You're a producer.");
            System.out.println("Co chcesz zrobic?");
            System.out.println("1 - Przyjmij reklamowany towar");
            System.out.println("2 - Odpowiedz na wniosek reklamacji");
            System.out.println("3 - Wyjdz");
            int a;
            a = scanner.nextInt();
            switch (a){
                case 1:
                    String line = FileReaderWriter.readFirstLineFromFile(WorkerProducerFileName);
                    try{
                        RequestListNULL(line);
                        commandLine = FileReaderWriter.readFirstLineFromFile(WorkerProducerFileName).split(";");
                        ComplaintList.add(new ComplaintInfo(Integer.parseInt(commandLine[1]),Integer.parseInt(commandLine[2]),Integer.parseInt(commandLine[4]), commandLine[3] , LocalDateTime.parse(commandLine[5])));
                        System.out.println("Otrzymano zgłoszenie reklamacji!!!");TimePass();
                    }catch (Exception e){
                        System.out.println("Something went wrong" + e);
                    }
                    break;
                case 2:
                    if(ComplaintList.size()>0) {
                        for (ComplaintInfo complaint : ComplaintList){
                            System.out.println(i + complaint.toString());i++;
                        }
                        System.out.println("Na ktora reklamacje chcesz odpowiedziec:");
                        i = scanner.nextInt();
                        if (i>ComplaintList.size()-1){System.out.println("Nie ma takiej reklamacji"); break;}
                        System.out.println("Akceptujesz ponizsza reklamacje Y/N ");
                        System.out.println(ComplaintList.get(i).toString());
                        String confirmation = scanner.next();
                        try{
                            isCorrectstring(confirmation);
                            switch (confirmation) {
                                case "Y":
                                case "y":
                                    ComplaintList.get(i).setAccepted(true);
                                    ComplaintList.get(i).setDateOfPossibleReturn(LocalDateTime.parse(FileReaderWriter.getTime()));
                                    System.out.println("Zaakceptowano reklamacje!");
                                    String messageAccept = "2;" + ComplaintList.get(i).toWorker() + ";" + FileReaderWriter.getTime() + ";" + ComplaintList.get(i).getdateOfReturn();
                                    FileReaderWriter.writeToFile(messageAccept, WorkerProducerFileName);
                                    System.out.println("Przekazano informacje pracownikowi");
                                    TimePass();
                                    break;

                                case "N":
                                case "n":
                                    ComplaintList.get(i).setAccepted(false);
                                    System.out.println("Odrzucono reklamacje!");
                                    String messageReject = 2 + ";" + ComplaintList.get(i).toWorker() + ";" + FileReaderWriter.getTime();
                                    FileReaderWriter.writeToFile(messageReject, WorkerProducerFileName);
                                    System.out.println("Przekazano informacje pracownikowi");
                                    TimePass();
                                    break;
                            }}catch (Exception e){
                            System.out.println("Something went wrong: " + e);
                        }
                        i++;
                    }
                    else{
                        System.out.println("Nie ma zadnych reklamacji");
                    }
                    break;

                case 3:
                    end = true;
                    break;

                default:
                    System.out.println("Zla instrukcja" );
                    break;
            }

        }
    }
    public static void TimePass(){
        //simulates passage of time
        Random rand = new Random();
        FileReaderWriter.changeTime(rand.nextInt(2),rand.nextInt(5)+1,rand.nextInt(40),rand.nextInt(60));
    }
    public static void isCorrectstring(String a)throws Exception{
        if ( a.equals("Y") || a.equals("y") || a.equals("N") || a.equals("n")  ){}
        else{
            throw new MyException("Wrong string input, please input: Y or y or N or n");
        }
    }
    public static void RequestListNULL(String line)throws MyException{
        if (line == null) throw new MyException("No complaints impending");
    }
}
