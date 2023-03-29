package com.cnce.common.utils;

import java.math.BigDecimal;

/**
 * @author changtuo
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    /**
     * 单个十六进制转换为单个十进制字符
     * @param hex
     * @return String
     */
    public static String hexToDecChar(String hex){
        if(isBlank(hex)){
            return null;
        }
        Integer dec = Integer.parseInt(hex.replaceAll("0x",""),16);
        return String.valueOf(dec);
    }

//    /**
//     * 十六进制字符串转换为单个十进制字符串
//     * @param str
//     * @return String
//     */
//    public static String hexToDecStr(String str){
//        if(isBlank(str)){
//            return null;
//        }
//        String[] arrs = str.split(",");
//        StringBuilder sb = new StringBuilder();
//        for (int i=0;i<arrs.length;i++){
//            sb.append((char) hexToDec(arrs[i]).byteValue());
//        }
//        return sb.toString();
//    }

    /**
     * 十六进制转换为十进制
     * @param hex
     * @return String
     */
    public static Integer hexToDec(String hex){
        if(isBlank(hex)){
            return 0;
        }
        return Integer.parseInt(hex.replaceAll("0x",""),16);
    }

    /**
     * 取一个int的实际bytes
     * @param dec
     * @return
     */
    public static byte[] intToHexBytes(int dec){
        //eg:110--->0110 1110 ---> 00 6e
        byte[] bytes = new byte[2];
        String bins = hexTobin(intToHex(dec));
        if(bins.length()>8){
            bytes[0] = Byte.valueOf(bins.substring(0,bins.length()-8),2);
            bytes[1] = extendByte(bins.substring(bins.length()-8,bins.length()));
        }else{
            bytes[0] = 00 ;
            bytes[1] = extendByte(bins);
        }
        return bytes ;
    }

    /**
     * 二进制bin转换为byte
     * @param bins
     * @return
     */
    public static byte extendByte(String bins){
        byte b ;
        try{
            b = Byte.valueOf(bins,2);
        }catch (Exception e){
            //如果超出byte范围
            b = (byte) hexToInt(binToHex(bins));
        }
        return b;
    }

    /**
     * 十进制--->十六进制
     * @param n
     * @return
     */
    public static String intToHex(int n) {
        if(n==0){
            return "0";
        }
        StringBuilder sb = new StringBuilder(8);
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            sb = sb.append(b[n%16]);
            n = n/16;
        }
        return sb.reverse().toString();
    }

    /**
     * 十六进制--->十进制
     * @param hex
     * @return
     */
    public static int hexToInt(String hex){
        //eg:35--->53;3e--->62
        Double sum = 0.0;
        int pos = 0 ;
        for (int i=hex.length()-1; i >= 0;i--){
            pos = hex.length()-i-1 ;
            String s = transferBcd(String.valueOf(hex.charAt(i)).toLowerCase());
            sum += Integer.parseInt(s) * Math.pow(16,pos);
        }
        return sum.intValue() ;
    }
    /**
     * 2进制--->十六进制
     * @param bin
     * @return
     */
    public static String binToHex(String bin){
        //01111111--->127--->7f
        Double sum = 0.0;
        int pos = 0 ;
        for(int i = bin.length()-1; i>=0; i--){
            pos = bin.length()-i-1 ;
            if(String.valueOf(bin.charAt(i)).equals("1")){
                sum += Math.pow(2,pos);
            }
        }
        return intToHex(sum.intValue());
    }

    /**
     * 十六进制--->2进制
     * @param hex
     * @return
     */
    public static String hexTobin(String hex){
        // 7f ---> 01111111
        StringBuilder sb = new StringBuilder();
        for (int i=hex.length()-1 ; i >= 0 ;i--){
            int temp = Integer.parseInt(transferBcd(String.valueOf(hex.charAt(i))));
            int cnt = 0;
            while (temp != 0){
                if(temp%2==0){
                    sb.insert(0,"0");
                }else{
                    sb.insert(0,"1");
                }
                cnt++;
                temp = temp/2;
            }
            for(int j=0;j<4-cnt;j++){
                sb.insert(0,"0");
            }
        }
        return sb.toString();
    }

    /**
     * 两位十六进制，转换为一个十进制
     * @param high
     * @param low
     * @return
     */
    public static String addHighLow(String high,String low){
        Double sum = 0.0;
        if(high.length()==2){
            sum = Math.pow(2,8)*hexToDec(high)+hexToDec(low);
        }else if(high.length()==4){
            sum = Math.pow(2,16)*hexToDec(high)+hexToDec(low);
        }
        return String.valueOf(sum.intValue()) ;
    }

//    /**
//     *将一个十进制数num,转换为两位高低十六进制显示
//     * eg:568--->02,38
//     * @param num:十进制
//     * @return String
//     */
//    public static byte[] splitHighLow(int num){
//        String s = intToHex(num);
//        StringBuilder sb = new StringBuilder();
//        //2,38
//        for(int i=s.length();i>0;i-=2){
//            int start = (i-2)>0?(i-2):0;
//            sb.insert(0,s.substring(start,i)).insert(0,",");
//        }
//        String[] arr = sb.toString().replaceFirst(",","").split(",");
//        byte[] bs = new byte[arr.length];
//        for (int j =0;j<arr.length;j++){
//            bs[j] = (byte) Integer.parseInt(arr[j]);
//        }
//        return bs;
//    }

    public static String multiply(String one,String two){
        String result = "";
        try {
            BigDecimal bd = new BigDecimal(one);
            result = bd.multiply(new BigDecimal(two)).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String add(String one,String two){
        if(org.springframework.util.StringUtils.isEmpty(one) ||
                org.springframework.util.StringUtils.isEmpty(two)){
            return "";
        }
        String result = "";
        try {
            BigDecimal bd = new BigDecimal(one);
            result = bd.add(new BigDecimal(two)).toString();
        }catch (Exception e){
            //todo
            e.printStackTrace();
        }
        return result;
    }

    public static String divide(String dividend,String divisor){
        if(org.springframework.util.StringUtils.isEmpty(dividend)){
            return "";
        }
        BigDecimal bd = new BigDecimal(dividend);
        return bd.divide(new BigDecimal(divisor),3, BigDecimal.ROUND_HALF_UP).toString();
    }


    /**
     * 字节转十六进制
     * @param b 需要进行转换的byte字节
     * @return  转换后的Hex字符串
     */
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }


    /**
     * 字节数组转16进制
     * @param bytes 需要转换的byte数组
     * @return  转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String transferBcd(String s){
        switch(s.toLowerCase()){
            case "a":
                return "10";
            case "b":
                return "11";
            case "c":
                return "12";
            case "d":
                return "13";
            case "e":
                return "14";
            case "f":
                return "15";
            default:
                return s;
        }
    }

    /**
     * 逆转一个字符串
     * @param str
     * @return
     */
    public static String reverseStr(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = str.length()-1 ; i>=0 ;i--){
            sb.append(str.charAt(i));
        }
        return sb.toString() ;
    }

    public static void main(String[] args) {
        //System.out.println(multiply("2","2.568"));
        System.out.println("add,"+add("1.5","5"));
        System.out.println("**divide,"+divide("1213.6","3"));
        System.out.println("hexToDecChar,"+hexToDecChar("0605"));
        System.out.println("addHighLow,"+addHighLow("02","37"));//0237--567;
        System.out.println("addHighLow,"+addHighLow("0075","0077"));//00 75 00 77--7667831
        System.out.println(addHighLow("02","38"));//568

//        System.out.println("hexToDecStr,"+hexToDecStr("59,52,38,36,38,32,32,31,30,34,35,32,37,33,36,34,39"));
        System.out.println("!!****intToHex,"+intToHex(170));//568-->238;
//        System.out.println("splitHighLow,"+splitHighLow(1400)[0]+","+splitHighLow(1400)[1]);//2,38
        System.out.println(hexToDec("eeee"));//61166

        System.out.println(bytesToHex(new byte[]{'A','A'}));

        System.out.println("binToHex:"+binToHex("01111111"));//7f
        System.out.println("hexToInt:"+hexToInt("3e"));//35--->53;3e-->62
        System.out.println("hexTobin:"+hexTobin("7f"));//7f --->0111 1111;
        System.out.println("intToHexBytes:"+intToHexBytes(1400)[0]+","+intToHexBytes(1400)[1]);//110--->00 6e
    }

}
