package com.corruptionfinder.blockchain;

import java.security.MessageDigest;

public class SHA256{
    public static String applySHA256(String input){
        try{
            MessageDigest digest =  MessageDigest.getInstance("SHA-256");
            byte hash[] = digest.digest(input.getBytes("UTF-8"));
            StringBuilder s = new StringBuilder();
            for(int i=0;i<hash.length;i++){
                String temp = Integer.toHexString(0xff & hash[i]);
                if(temp.length() == 1){
                    s.append('0');

                }
                s.append(temp);
            }
            return  s.toString();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}