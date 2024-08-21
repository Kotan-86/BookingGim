package gim;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;



public class Calendar{
  //カレンダー配列の定義
  private int[] time = new int[365];
  //カレンダー配列の初期化
  public Calendar(){
    int i,j;
    BufferedReader reader = null;
    int day = 0;
    try{
      reader = new BufferedReader(new FileReader("Bookingdata.csv"));
      for(i=0; i<12; i++){
        if(i==0){
          for(j=0; j<31; j++){
            String line;
            while((line = reader.readLine()) != null){
              String[] bookingDatas = line.split(",");
              if(bookingDatas[2].equals("0")){
                time[day + j] = 0; //0:予約なしを示すマジックナンバー 
              }else if(bookingDatas[2].equals("1")){
                time[day + j] = 1; //1:予約ありを示すマジックナンバー 
              }
              day = day + 1;
            }
          }
        }
        else if(i==2||i==4||i==6||i==7||i==9||i==11){
          for(j=0; j<31; j++){
            String line;
            while((line = reader.readLine()) != null){
              String[] bookingDatas = line.split(",");
              if(bookingDatas[2].equals("0")){
                time[day + (j+1)] = 0; //0:予約なしを示すマジックナンバー 
              }else if(bookingDatas[2].equals("1")){
                time[day + (j+1)] = 1; //1:予約ありを示すマジックナンバー 
              }
              day = day + 1;
            }
          }
        }else if(i==3||i==5||i==8||i==10){
          for(j=0; j<30; j++){
            String line;
            while((line = reader.readLine()) != null){
              String[] bookingDatas = line.split(",");
              if(bookingDatas[2].equals("1")){
                time[day + (j+1)] = 0; //0:予約なしを示すマジックナンバー 
              }else if(bookingDatas[2].equals("0")){
                time[day + (j+1)] = 1; //1:予約ありを示すマジックナンバー 
              }
              day = day + 1;
            }
          }
        }else if(i==1){
          for(j=0; j<28; j++){
            String line;
            while((line = reader.readLine()) != null){
              String[] bookingDatas = line.split(",");
              if(bookingDatas[2].equals("0")){
                time[day + (j+1)] = 0; //0:予約なしを示すマジックナンバー 
              }else if(bookingDatas[2].equals("0")){
                time[day + (j+1)] = 1; //0:予約ありを示すマジックナンバー 
              }
              day = day + 1;
            }
          }
        }
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  //予約状況一覧表示
  public void show(int b_month){
    int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int[] addDays = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
    int startIndex = addDays[b_month-1];
    int endIndex = daysInMonth[b_month-1];
    int j;
    System.out.println(b_month + "月");
    for(j=0; j<endIndex; j++){
      if(time[startIndex]==0){
        System.out.println( (j+1) + "日予約〇");
      }else{
        System.out.println( (j+1) + "日予約×");
      }
      startIndex += 1;
    }
  }
  
  //予約が入っているかをチェック
  public void checkBooking(String name, int month, int day, Calendar c){
    //経過日数を計算する
    int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int daysPass = 0;
    for(int i=0; i<month-1; i++){
      daysPass += daysInMonth[i];
    }
    daysPass += day;
    //予約オブジェクト作成
    Booking[] booking = new Booking[100];
    int n;
    for(n=0; n<100; n++){
      booking[n] = new Booking();
    }
    
    //入力された予約情報を確認する
    System.out.println("予約者の名前:" + name);
    System.out.println("予約したい月日:" + month + "月" + day + "日");

    //予約状況を確認する
    if(time[daysPass-1] == 0){ //予約なしのとき
      System.out.println("予約できます。");
      for(n=0; n<100; n++){
        String g_name = booking[n].getBookingName();
        if(g_name == "null"){
          booking[n].inputBooking(name, month, day, c, daysPass);
          break;
        }else{
          continue;
        }
      }
    }else{ //予約ありのとき
      System.out.println("既に予約が入っております。他の月日を選択してください。");
    }
  }

  //予約状況を更新する
  public void setBooking(int month, int day, int daysPass){
    System.out.println(month + "月" + day + "日に予約します。");
    time[daysPass-1] = 1;
    String s_m = String.valueOf(month);
    String s_d = String.valueOf(day);
    
    //csvファイル更新
    //読み込み
    List<String[]> b_data = new ArrayList<>();
    String line;
    BufferedReader reader = null;
    FileWriter fw = null;
    
    try{
      reader = new BufferedReader(new FileReader("Bookingdata.csv"));
      while((line = reader.readLine()) != null){
        String[] bookingDatas = line.split(",");
        if (bookingDatas[0].equals(s_m) && bookingDatas[1].equals(s_d)) { //一致する月日の予約状況を1とする。
            bookingDatas[3-1] = "1";
        }
        b_data.add(bookingDatas);
      }
    }catch(IOException e){
      e.printStackTrace();
    }
    //最新のデータをtemp.csvに書き込む
    try{
      fw = new FileWriter("temp.csv");
      for (String[] row : b_data) {
        line = String.join(",", row);
        fw.write(line + "\n");
      }
    }catch (IOException e) {
      e.printStackTrace();
    }
    // 元のファイルを削除し、一時ファイルをリネームして元のファイル名にする
    try {
      java.nio.file.Files.delete(java.nio.file.Paths.get("Bookingdata.csv"));
      java.nio.file.Files.move(java.nio.file.Paths.get("temp.csv"), java.nio.file.Paths.get("Bookingdata.csv"));
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      try{
        fw.close();
        reader.close();
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }
}