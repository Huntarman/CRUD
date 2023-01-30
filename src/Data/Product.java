package Data;

import java.time.LocalDateTime;

public class Product {
    private String ProductName;
    private int productOrder;
    private int ID;
    private int OwnerID;
    private int returnStatus; //0- no action, 1 - awaiting producer response, 2 - accepted, 3 -rejected, 4 - ended
    private boolean acceptedForReturn;
    private LocalDateTime dateOfBeingFiled;
    private LocalDateTime dateOfGoingToProducer;
    private LocalDateTime dateOfProducerResponse;
    private LocalDateTime dateOfBeingReturned;
    private LocalDateTime dateOfFinalization;

    public Product (int productorder, int id, int ownerid, String name ){
        this.productOrder = productorder;
        this.ID = id;
        this.OwnerID = ownerid;
        this.returnStatus = 0;
        this.ProductName = name;
    }
    //getters
    public int getID(){return this.ID;}
    public int getOwnerID(){return this.OwnerID;}
    public int getProductOrder(){return this.productOrder;}
    public int getReturnStatus(){return  this.returnStatus;}
    public String getreturnStatusString(){
        String string ="";
        switch (this.returnStatus){
            case 0:
            default:
                string = "Nie zostala podjeta akcja";
                break;
            case 1:
                string = "Czeka na odpowiedz producenta";
                break;
            case 2:
                string = "Zaakceptowana";
                break;
            case 3:
                string = "Odrzucona";
                break;
            case 4:
                string = "Zfinalizowana";
                break;
        }
        return string;
    }
    public LocalDateTime getDateOfBeingFiled() {
        return dateOfBeingFiled;
    }

    public LocalDateTime getDateOfBeingReturned() {
        return dateOfBeingReturned;
    }

    public LocalDateTime getDateOfFinalization() {
        return dateOfFinalization;
    }

    public LocalDateTime getDateOfGoingToProducer() {
        return dateOfGoingToProducer;
    }

    public LocalDateTime getDateOfProducerResponse() {
        return dateOfProducerResponse;
    }
    public String getProductName(){return this.ProductName;}

    public String toString(){
        String string = "Produkt " + this.productOrder + "|" + this.ID + ";" + this.ProductName + ";ClientID:" + this.OwnerID;
        return string;
    }
    public String infoForProducer(){
        String string =this.productOrder+";"+this.ID +";"+ this.ProductName + ";" + this.OwnerID+ ";" + this.dateOfBeingFiled;
        return string;
    }

    //setters
    public void setAcceptedForReturn(boolean YesOrNo){this.acceptedForReturn = YesOrNo;}
    public void setDateOfBeingFiled(String dateString){
        this.dateOfBeingFiled = LocalDateTime.parse(dateString);
    }

    public void setDateOfGoingToProducer(String dateString){
        this.dateOfGoingToProducer = LocalDateTime.parse(dateString);
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    public void setDateOfProducerResponse(String dateString){
        this.dateOfProducerResponse = LocalDateTime.parse(dateString);
    }

    public void setDateOfBeingReturned(String dateString){
        this.dateOfBeingReturned = LocalDateTime.parse(dateString);
    }

    public void setDateOfFinalization(String dateString){
        this.dateOfFinalization = LocalDateTime.parse(dateString);
    }
}
