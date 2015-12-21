package com.naman14.timber;

import java.util.ArrayList;

/**
 * Created by admin on 2015/12/17.
 */
public  class Data {
    //public static  String [][] USER ={
    //        {"1001","111","sunwell",/*"male",*/"18317019213"},
      //      {"1002","111","ivyhan",/*"female",*/"13162580001"},
        //    {"1003","111","zhiyu",/*"female",*/"13162580002"}
    //};

    //private static String[][] USER=new String[][];
    private  static ArrayList<Info> USER = new ArrayList<Info>();
    //private static  int length=0;

    public static void  put(String username,String passwd,
                            String nickname,String gender,String phone){

        //int l = USER.length;

        USER.add(new Info(username,passwd,nickname,gender,phone));

        //USER[length] =new String[4];

        //USER[length][0]=username;
        //USER[length][1]=passwd;
        //USER[length][2]=nickname;
        //USER[length][3]=phone;
        //USER[length][4]=gender;
    }

    public static int getLength ()
    {return USER.size();}

    public static String getUsername (int i)
    {
        return USER.get(i).username;
    }

    public static String getPsswd (int i)
    {
        return USER.get(i).passwd;
    }

    public static String getNickname (int i)
    {
        return USER.get(i).nickname;
    }

    public static String getGender (int i)
    {
        return USER.get(i).gender;
    }

    public static String getPhone (int i)
    {
        return USER.get(i).phone;
    }
}

class  Info{
    String username;
    String passwd;
    String nickname;
    String gender;
    String phone;

    Info (String u,String p,String nn,String g,String ph){
        username=u;
        passwd = p;
        nickname = nn;
        gender= g;
        phone=ph;

    }

}
