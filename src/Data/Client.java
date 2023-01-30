package Data;

public class Client {
    public int ID;
    private boolean hasFiledComplaint = false;
    public Client(int input){
        this.ID = input;
    }

    public boolean getHasFiledComplaint() {
        return hasFiledComplaint;
    }

    public int getID(){
        return this.ID;
    }
}
