
package App;

import Data.Client;
import Data.Date;
import Data.Product;
import Exceptions.MyException;
import File.FileReaderWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/*
 Michał Zajdel
 compiled with java 17
 CMD command
 cd lab03
 compilation : javac -sourcepath src/ -d build/production/Lab03 src/App/Worker.java
 jar:jar cfv Lab03_pop.jar build/production
 */
public class Worker {

    public static void main(String[] args){
        //global date
        File time = new File("time.txt");
        FileReaderWriter.writeToFile(" ",time);
        Date date = new Date();
        FileReaderWriter.writeTime(date.getDate());

        //create nextID files
        File nextIDC = new File("nextIDClient.txt");
        FileReaderWriter.writeToFile("1",nextIDC);
        File nextIDP = new File("nextIDProducer.txt");
        FileReaderWriter.writeToFile("1",nextIDP);

        ArrayList<Client> ClientList = new ArrayList<>();
        //ArrayList<Producer> ProducentList = new ArrayList<>();
        ArrayList<Product> ProductList = new ArrayList<>();
        int IdC = 0;
        int IdP = 0;
        int productOrder =0;

        System.out.println("Hello, Worker!" );
        File filenameWorker = new File("nextIDClient.txt");
        File filenameProducer = new File("nextIDProducer.txt");
        Scanner scanner = new Scanner(System.in);

        boolean end = false;
        String operation;
        while (!end){
            System.out.println(FileReaderWriter.getTime());
            System.out.println("Co chcesz zrobic?");
            System.out.println("1 - Przyjmij reklamacje");
            System.out.println("2 - Zgłos reklamacje producentowi");
            System.out.println("3 - Przyjmij odpowiedz od producenta");
            System.out.println("4 - Przekaż informacje klientowi");
            System.out.println("5 - Potwierdz ukonczenie operacji");
            System.out.println("6 - add client");
            System.out.println("7 - Wyjdz");
            int a;
            a = scanner.nextInt();

            switch (a){
                case 1:
                    System.out.println("Podaj ID klienta:");
                    int id1 = scanner.nextInt();
                    try{rightArraySize(id1,ClientList);
                        File ClientWorkerFileName = new File("ClientWorker"+id1+".txt");
                        operation = FileReaderWriter.readFirstLineFromFile(ClientWorkerFileName);
                        if (operation.length()>0){
                            String[] operationArray = operation.split(";");
                            if(operationArray[0].equals( "1" )){
                                ProductList.add(new Product(productOrder,Integer.parseInt(operationArray[2]),Integer.parseInt(operationArray[1]),operationArray[3]));
                                ProductList.get(productOrder).setDateOfBeingFiled(operationArray[4]);
                                productOrder++;
                                System.out.println("Otrzymano zgłoszenie reklamacji!!!");
                                TimePass();
                            }
                            else System.out.println("Wrong number of action. Different action is required");
                        }
                        else{
                            System.out.println("Klient nie zglosil reklamacji");
                        }
                    } catch (Exception e){
                        System.out.println("Something went wrong: " + e);
                    }
                    break;

                case 2:
                    if (ProductList.size()>0){
                        System.out.println("Ktory produkt chcesz przekazac producentowi:");
                        for (Product product: ProductList){
                            System.out.println(product.toString());
                        }
                        int productID = scanner.nextInt();
                        if(productID>ProductList.size()-1){
                            System.out.println("Zle ID");   break;
                        }
                        else{
                            File WorkerProducerFileName = new File("WorkerProducer"+ProductList.get(productID).getID()+".txt");
                            String lancuszek = "1;" + ProductList.get(productID).infoForProducer();
                            FileReaderWriter.writeToFile(lancuszek,WorkerProducerFileName);
                            ProductList.get(productID).setDateOfGoingToProducer(FileReaderWriter.getTime());TimePass();
                            ProductList.get(productID).setReturnStatus(1);
                            System.out.println("Przekazano reklamacje dla producenta");
                        }

                    }
                    else{
                        System.out.println("Nie ma produktow do reklamacji");
                    }
                    break;

                case 3:
                    System.out.println("Od ktorego producenta (1-2):");
                    int id3 = scanner.nextInt();
                    File WorkerProducerFilename = new File("WorkerProducer" + id3 +".txt");
                    String Msg = FileReaderWriter.readFirstLineFromFile(WorkerProducerFilename);
                    String[] MsgArr = Msg.split(";");
                    if (MsgArr[0] .equals( "2" )){
                        int iterator = Integer.parseInt(MsgArr[1]);
                        if (MsgArr[2].equals("true")){
                            ProductList.get(iterator).setReturnStatus(2);
                            ProductList.get(iterator).setDateOfBeingReturned(MsgArr[4]);
                        }
                        else{
                            ProductList.get(iterator).setReturnStatus(3);
                        }
                        ProductList.get(iterator).setDateOfProducerResponse(MsgArr[3]);TimePass();
                    }
                    else{
                        System.out.println("Wrong number of action. Different action is required");
                    }
                    break;

                case 4:
                    for(Product product : ProductList){
                        System.out.println(product.getProductOrder() + "|" + product.getreturnStatusString() + "|" + product.getReturnStatus());
                    }
                    System.out.println("O ktorym statusie chcesz poinformowac klienta");
                    int b = scanner.nextInt();
                    if (b< ProductList.size()){
                        File Filename = new File("ClientWorker"+ProductList.get(b).getOwnerID()+".txt");
                            switch (ProductList.get(b).getReturnStatus()){
                                case 0:
                                case 1:
                                case 4:
                                default:
                                    System.out.println("Invalid Return Status");
                                    break;

                                case 2:
                                    operation = "2;accepted;"+ ProductList.get(b).getID()+ ";"+ ProductList.get(b).getProductName() +";"+ ProductList.get(b).getDateOfBeingReturned() ;
                                    FileReaderWriter.writeToFile(operation,Filename);TimePass();
                                    break;

                                case 3:
                                    operation = "2;rejected;"+ ProductList.get(b).getID()+ ";"+ ProductList.get(b).getProductName();
                                    FileReaderWriter.writeToFile(operation,Filename);
                                    ProductList.get(b).setReturnStatus(4);
                                    ProductList.get(b).setDateOfFinalization(FileReaderWriter.getTime());TimePass();
                                    break;
                            }
                    }
                    else{
                        System.out.println("Nieprawidlowy produkt");
                    }
                    break;

                case 5:
                    System.out.println("Operacje ktorego przedmiotu chcesz potwierdzic?");
                    for (Product product : ProductList){
                        System.out.println(product.getProductOrder() + product.toString());
                    }
                    int idSzybko = scanner.nextInt();
                    if(idSzybko< ProductList.size()) {
                        File OneLastTime = new File("ClientWorker" + ProductList.get(idSzybko).getOwnerID() + ".txt");
                        String lastline = FileReaderWriter.readFirstLineFromFile(OneLastTime);
                        try {
                            clientHasNotReceivedProduct(lastline);
                            System.out.println("Klient otrzymal zwrot");
                            ProductList.get(idSzybko).setReturnStatus(4);
                            ProductList.get(idSzybko).setDateOfFinalization(FileReaderWriter.getTime());
                            TimePass();
                        } catch (MyException e) {
                            System.out.println("Something went wrong: " + e);
                        }
                    }
                    else System.out.println("Wrong number of action. Different action is required");
                    break;

                case 6:
                    IdC++; ClientList.add(new Client(IdC));
                    break;
                case 7:
                    end = true;
                    break;
            }

        }
        //deletes files
            File fileToDelete = new File("time.txt");
            try {
                if(fileToDelete.delete()) System.out.println(fileToDelete + "deleted");
            }catch (Exception e){
                e.printStackTrace();
            }

        File filenameIDclient = new File("nextIDClient.txt");
        for (int i = 1; i<=FileReaderWriter.readNextID(filenameIDclient); i++){
            File fileToDelete4 = new File("ClientWorker"+i+".txt");
            try {
                if(fileToDelete4.delete()) System.out.println(fileToDelete4 + "deleted");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        File filenameIDproducer = new File("nextIDProducer.txt");
        for (int i = 1; i<=FileReaderWriter.readNextID(filenameIDproducer); i++){
            File fileToDelete4 = new File("WorkerProducer"+i+".txt");
            try {
                if(fileToDelete4.delete()) System.out.println(fileToDelete4 + "deleted");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        File fileToDelete2 = new File("nextIDClient.txt");
            try {
                if(fileToDelete2.delete()) System.out.println(fileToDelete2 + "deleted");
            }catch (Exception e){
                e.printStackTrace();
            }

            File fileToDelete3 = new File("nextIDProducer.txt");
                if(fileToDelete3.delete()) System.out.println(fileToDelete3 + "deleted");

    }
    public static void rightArraySize(int a, ArrayList<Client> clientlist) throws MyException {
        if (a>clientlist.size()){
            throw new MyException("Array list is too small. Add client with action 6");
        }
    }
    public static void clientHasNotReceivedProduct(String a) throws MyException {
        if (a.equals("3;")){
        }else throw new MyException("Klient nie odebral zwroconego produktu");
    }
    public static void TimePass(){
        //simulates passage of time
        Random rand = new Random();
        FileReaderWriter.changeTime(rand.nextInt(2),rand.nextInt(5)+1,rand.nextInt(40),rand.nextInt(60));
    }
}
