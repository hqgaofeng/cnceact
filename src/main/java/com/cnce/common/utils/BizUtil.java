package com.cnce.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;


public class BizUtil {
    private static Logger logger = LoggerFactory.getLogger(BizUtil.class);

    public static String getSocketString(ByteBuffer buffer,String ischeckSum) {
        String result = "";
        CharBuffer charBuffer;
        //ISO-8859-1,此处编码不能修改!!
        Charset charset = Charset.forName("ISO-8859-1");
        try {
            charBuffer = charset.decode(buffer.asReadOnlyBuffer());
            if(isHeatBeat(charBuffer)){
                //ANCY:YR321654987321654",Rx:YR863866040846719
                String reqtrimMsg = charBuffer.toString();
                return reqtrimMsg.replaceAll("\\\"","");
            }
            String tempStr;
            StringBuilder sb = new StringBuilder();
            //校验和一个字节!!
            for(int i=0;i<charBuffer.array().length;i++){
                char b = charBuffer.array()[i];
                //以十六进制显示
                tempStr = Integer.toHexString(0xFF & b) ;
                sb.append(",").append(tempStr.length()>1?tempStr:"0"+tempStr);
            }
            //去掉头尾的其他字符
            String reqtrimMsg = sb.toString();
            if(sb.toString().indexOf("53,74,61")>0 && sb.toString().indexOf("6f,76,72")>0){
                reqtrimMsg = sb.toString().substring(sb.toString().indexOf("53,74,61"),
                        sb.toString().indexOf("6f,76,72")+8);
            }
            String[] arr = reqtrimMsg.split(",");
            int sum = 0 ;//使用十进制来存放和
            for(int i=0 ;i< arr.length;i++){
                if(i>=5 && i<arr.length-4){
                    sum += com.cnce.common.utils.StringUtils.hexToDec(arr[i]);
                }
            }
            if("true".equals(ischeckSum) && !checkSum(sum,arr[arr.length-4])){
                logger.info("check_sum_fail:"+Integer.toHexString(0xFF & sum)+
                        ","+arr[arr.length-4]);
                return "check_sum_fail";
            }
            result = sb.toString().replaceFirst(",","");
            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return result ;
    }

    private static boolean isHeatBeat(CharBuffer charBuffer){
        ////ANCY:YR321654987321654",Rx:YR863866040846719
        return charBuffer.toString().startsWith("414E4359")
                || charBuffer.toString().startsWith("5952")
                || charBuffer.toString().startsWith("ANCY")
                || charBuffer.toString().startsWith("YR")
                || charBuffer.toString().startsWith("Rx:")
                || charBuffer.toString().startsWith("52783A");
    }

    public static boolean isHeatBeat(String msg){
        //ANCY:YR321654987321654",Rx:YR863866040846719
        return msg.toString().startsWith("414E4359")
                || msg.toString().startsWith("5952")
                || msg.toString().startsWith("ANCY")
                || msg.toString().startsWith("YR")
                || msg.toString().startsWith("Rx:")
                || msg.toString().startsWith("52783A");
    }

    private static boolean checkSum(int sum,String by){
        String calSum = Integer.toHexString(0xFF & sum);
        return calSum.toLowerCase().equals(by.toLowerCase());
    }

    /**
     * eg:11--->12;1a-->1b;1f--->2f
     * @param num
     * @return
     */
    public static String str2numberAdd2Hex(String num){
        if(StringUtils.isEmpty(num)){
            return "";
        }
        //类似05的话，Integer之后0会去掉的,0A
        String units = num.substring(1,2) ;//个位
        String decade = num.substring(0,1) ;//十位
        int tempint = com.cnce.common.utils.StringUtils.hexToInt(units)+1;
        if(tempint>15){
            //十位+1大于15，暂时业务达不到。。。
            tempint = com.cnce.common.utils.StringUtils.hexToInt(decade)+1;
            return com.cnce.common.utils.StringUtils.intToHex(tempint)+units;
        }else{
            return decade+com.cnce.common.utils.StringUtils.intToHex(tempint);
        }
    }


    public static String android = "android";
    public static String iphone = "iphone";
    public static String ipad = "ipad";
    public static String postman = "postman";
    //获取用户操作系统
    public static String getOS(String userAgent) {
        if (com.cnce.common.utils.StringUtils.isEmpty(userAgent)) {
            return null;
        }
        if (userAgent.toLowerCase().contains(android)) {
            return android;
        } else if (userAgent.toLowerCase().contains(iphone)) {
            return iphone;
        } else if (userAgent.toLowerCase().contains(ipad)) {
            return ipad;
        }else if(userAgent.toLowerCase().contains(postman)){
            return postman;
        }else {
            return "others";
        }
    }
    /**
     * android : 所有android设备
     * mac os : iphone ipad
     * windows phone:Nokia等windows系统的手机
     */
    public boolean  isMobileDevice(String requestHeader){
        String[] deviceArray = new String[]{"android","mac os","windows phone"};
        if(requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();
        for(int i=0;i<deviceArray.length;i++){
            if(requestHeader.indexOf(deviceArray[i])>0){
                return true;
            }
        }
        return false;
    }
    /**
     iPhone微信：
     User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B440 MicroMessenger/6.2.4 NetType/WIFI Language/en
     iPhone—safari
     User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12B440 Safari/600.1.4
     Android-Chrome
     User-Agent:Mozilla/5.0 (Linux; Android 4.4.4; HTC D820u Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.89 Mobile Safari/537.36

     PC-Chrome
     User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36
     PC-Firefox
     User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0
     PC-IE
     User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko
     **/
    public static boolean isWeb(String userAgent){
        if(com.cnce.common.utils.StringUtils.isEmpty(userAgent)){
            return false;
        }
        return userAgent.toLowerCase().contains("mozilla");
    }

}
