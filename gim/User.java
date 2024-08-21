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

public class User{
  private int number;
  private String name;

  public User(){
    number = 0;
    name = "";
  }

  //名前を取得する
  public String getName(){
    return this.name;
  }

  //番号を取得する
  public int getNumber(){
    return this.number;
  }

  //ログインする
  public boolean login(int number, String name){
    String num = String.valueOf(number);
    BufferedReader reader = null;
    try{
      reader = new BufferedReader(new FileReader("Userdata.csv"));
      String line;
      while((line = reader.readLine()) != null){
        String[] userDatas = line.split(",");
        if((userDatas.length>=2) && userDatas[0].equals(num) && userDatas[1].equals(name)){ //番号と名前が一致した場合
          return true;
        }
      }
    }catch(IOException e){
      e.printStackTrace();
    }finally{
      if(reader != null){
        try{
          reader.close();
        }catch (IOException e){
          e.printStackTrace();
        }
      }
    }
    return false;
  }
  
  //名前と番号を登録する
  public void registerName(String name){
    try{
      //ファイルを開く
      FileWriter fileWriter = new FileWriter("Userdata.csv", true);
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));

      //番号を自動登録
      String line = "";
      String lastNum = "";
      BufferedReader reader = null;
      try{
        reader = new BufferedReader(new FileReader("Userdata.csv"));
        while((line = reader.readLine()) != null){
          String[] userDatas = line.split(",");
          lastNum = userDatas[0];
        }
      }catch(IOException e){
        e.printStackTrace();
      }finally{
        if(reader != null){
          try{
            reader.close();
          }catch (IOException e){
            e.printStackTrace();
          }
        }
      }
      this.number = Integer.parseInt(lastNum);
      this.number = this.number + 1;

      //名前を代入する
      this.name = name;
      
      //登録情報書き込み
      printWriter.println(); //改行コードを出力
      printWriter.print(this.number);
      printWriter.print(",");
      printWriter.print(this.name);

      //ファイルを閉じる
      printWriter.close();
    }catch(IOException ex){
      ex.printStackTrace();
    }
    System.out.print("番号：" + this.number + ", ");
    System.out.println("名前：" + this.name + "を登録しました。");
  }
}