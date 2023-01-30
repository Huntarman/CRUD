package File;

import java.io.*;
import java.time.LocalDateTime;

public class FileReaderWriter {

    public static int readNextID(File filename){
        int nextID = 0;
        try{BufferedReader reader = new BufferedReader( new FileReader(filename));

            nextID =Integer.parseInt(reader.readLine());

        }catch (IOException e) {
            e.printStackTrace();
        }

        return nextID;
    }
    public static void writeNextID(int previousid, File filename){
        int nextID = previousid + 1;
        try{BufferedWriter writer = new BufferedWriter( new FileWriter(filename));
            writer.write(nextID + "");
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setIDto1(File filename){
        try{BufferedWriter writer = new BufferedWriter( new FileWriter(filename));
            writer.write(1 + "");
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTime(LocalDateTime time){
        try{BufferedWriter writer = new BufferedWriter( new FileWriter("time.txt"));
            writer.write(time + "");
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getTime(){
        String time = null;
        try{BufferedReader reader = new BufferedReader( new FileReader("time.txt"));

            time = reader.readLine();

        }catch (IOException e) {
            e.printStackTrace();
        }

        return time;
    }
    public static void changeTime(int days,int hours, int minutes, int seconds){
        LocalDateTime time = null;
        try{BufferedReader reader = new BufferedReader( new FileReader("time.txt"));
            time = LocalDateTime.parse(reader.readLine());
        }catch (IOException e) {
            e.printStackTrace();
        }
        try{BufferedWriter writer = new BufferedWriter( new FileWriter("time.txt"));
            writer.write(time.plusDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds) + "");
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeToFile(String what, File filename){
        try{BufferedWriter writer = new BufferedWriter( new FileWriter(filename));
            writer.write(what);
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readFirstLineFromFile(File filename){
        String strings = null;
        try{BufferedReader reader = new BufferedReader( new FileReader(filename));
            strings = reader.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
