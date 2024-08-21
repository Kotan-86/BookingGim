import gim.User;
import gim.Calendar;
import gim.Booking;
import java.util.Scanner;
import java.util.InputMismatchException;

class Main {
  public static void main(String[] args) {
    //ユーザオブジェクト作成
    User user = new User();
    
    //ログイン情報入力
    Scanner scanner = new Scanner(System.in);
    int number = 0;
    String num;
    String name;
    
    //番号入力
    while(true){
      try{
        System.out.println("会員番号を入れてください。");
        num = scanner.nextLine();
        number = Integer.parseInt(num);
        //例外処理
        if(number<1){
          System.out.println("1以上の整数を入力してください。");
          number = 0;
        }
      }catch(NumberFormatException e){ //例外処理
        System.out.println("1以上の整数を入力してください。");
        number=0;
      }finally{
        if(number!=0){
          break;
        }
      }
    }

    //名前入力
    while(true){
      System.out.println("名前を入れてください");
      name = scanner.nextLine();
      char[] c_name = name.toCharArray();
      //例外処理
      for(char ch : c_name){
        if(Character.isDigit(ch)){
          System.out.println("数字は入力出来ません。");
          name = "";
          break;
        }else if(Character.isLetter(ch)){
          break;
        }else{
          System.out.println("記号などは入力出来ません。");
          name = "";
          break;
        }
      }
      if(name!=""){
        break;
      }
    }
    
    //会員情報照会
    if(user.login(number, name)){
      System.out.println("ログイン成功");
      //予約情報入力
      String s_month;
      String s_day;
      int month;
      int day;
      Calendar c = new Calendar();
      while(true){
        try {
          //予約したい月を入力する
          System.out.println("予約したい月を入力してください。");
          s_month = scanner.next();
          month = Integer.parseInt(s_month);
          if(month<1 || month>12){
            System.out.println("1月~12月までの間で入力してください。");
            continue;
          }
          c.show(month);

          //予約したい日を入力する
          System.out.println("予約したい日を入力してください");
          s_day = scanner.next();
          day = Integer.parseInt(s_day);
          if((day<1 || day>31) && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)){
            System.out.println("1月, 3月, 5月, 7月, 8月, 10月, 12月は、1日から31日までの間で入力してください。");
            continue;
          }else if((day<1 || day>30) && (month == 4 || month == 6 || month == 9 || month == 11)){
            System.out.println("4月, 6月, 9月, 11月は、1日から30日までの間で入力してください。");
            continue;
          }else if((day<1 || day>28) && (month == 2)){
            System.out.println("2月は、1日から28日までの間で入力してください。");
            System.out.println("閏年例外処理は勘弁してください。");
            continue;
          }
          break;
        } catch(NumberFormatException e) { 
          System.out.println("整数を入力してください。");
        }
      }
      
      //名前と月日とオブジェクトcをオブジェクトcのcheckBookingに渡す
      c.checkBooking(name, month, day, c);
    }else{
      System.out.println("ログイン失敗");
      //会員登録
      //番号は自動登録
      //名前入力
      while(true){
        System.out.println("登録する名前を入れてください。");
        name = scanner.nextLine();
        char[] c_name = name.toCharArray();
        //例外処理
        for(char ch : c_name){
          if(Character.isDigit(ch)){
            System.out.println("数字は入力出来ません。");
            name = "";
            break;
          }else if(Character.isLetter(ch)){
            continue;
          }else{
            System.out.println("記号などは入力出来ません。");
            name = "";
            break;
          }
        }
        if(name!=""){
          break;
        }
      }
      //会員登録メソッド呼び出し
      user.registerName(name);
    }
  }
}