package Data;

import java.time.LocalDateTime;

public class ComplaintInfo {
    private int ProductID;
    private int productOrder;
    private int OwnerID;
    private String name;
    private LocalDateTime dateOfBeingFiled;
    private LocalDateTime dateOfPossibleReturn;
    private boolean Accepted;


    public ComplaintInfo(int productOrder , int productid, int ownerid, String naem, LocalDateTime date){
        this.productOrder = productOrder;
        this.ProductID = productid;
        this.OwnerID = ownerid;
        this.name = naem;
        this.dateOfBeingFiled = date;
    }

    public String toString(){
        String string = " |\t" + this.ProductID+";"+this.OwnerID+";"+this.name+";"+this.dateOfBeingFiled;
        return string;
    }
    public void setAccepted(boolean trueOrNot){
        this.Accepted = trueOrNot;
    }
    public void setDateOfPossibleReturn( LocalDateTime date){
        this.dateOfPossibleReturn = date.plusDays(7);
    }
    public String toWorker(){
        String string = this.productOrder+";"+this.Accepted;
        return string;
    }

    public String getdateOfReturn() {
        String string = this.dateOfPossibleReturn + "";
        return string;
    }
}
