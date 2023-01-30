package Data;

import java.time.LocalDateTime;
import java.util.Random;

public class Date {
    Random rand = new Random();
    int year = rand.nextInt(5)+2017;
    int month = rand.nextInt(12)+1;
    int day = rand.nextInt(28)+1;
    int hour = rand.nextInt(24);
    int minute = rand.nextInt(60);
    int second = rand.nextInt(60);
    public LocalDateTime date ;
    public Date(){
        this.date = LocalDateTime.of(year,month,day,hour,minute,second);
    }
    public LocalDateTime getDate(){
        return this.date;
    }
}
