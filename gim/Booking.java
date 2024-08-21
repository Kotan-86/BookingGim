package gim;

public class Booking{
  private int month;
  private int day;
  private String name;

  //予約状況の初期化
  public Booking(){
    this.month = 0;
    this.day = 0;
    this.name = "null";
  }

  //予約情報を入力する
  public void inputBooking(String name, int month, int day, Calendar c, int daysPass){
    this.name = name;
    this.month = month;
    this.day = day;
    //入力された予約情報をもとに予約状況を更新する
    c.setBooking(month, day, daysPass);
    System.out.println("予約が完了しました。");
  }

  //名前を返す
  public String getBookingName(){
    return this.name;
  }
}