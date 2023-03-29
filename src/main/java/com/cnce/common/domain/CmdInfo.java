package com.cnce.common.domain;

import com.cnce.common.utils.StringUtils;

/**
 *
 */
public class CmdInfo {

    /**
     * 数据头：3个字节，固定为62 65 67  (beg)
     */
    private byte[] header;
    /**
     *站点类型:1个字节
     */
    private byte cmdType;
    /**
     *命令长度：2个字节
     */
    private byte[] cmdLen;
    /**
     *站点ID:2个字节
     */
    private byte[] deviceNo;
    /**
     *命令代码:1个字节
     */
    private byte cmdCode;
    /**
     * 数据...
     */
    private byte[] data;
    /**
     *1个字节
     */
    private byte sum;
    /**
     *数据尾:3个字节
     */
    private byte[] tail;

    public byte[] getHeader() {
        return header;
    }
    public int getHeaderSum(){
        int sum = 0;
        for (byte b : this.header){
            sum += b;
        }
        return sum;
    }
    public void setHeader(byte[] header) {
        this.header = header;
    }

    public byte[] getCmdLen() {
        return cmdLen;
    }
    public void setCmdLen(byte[] cmdLen) {
        this.cmdLen = cmdLen;
    }

    public byte getCmdType() {
        return cmdType;
    }
    public void setCmdType(byte cmdType) {
        this.cmdType = cmdType;
    }

    public byte[] getDeviceNo() {
        return deviceNo;
    }
    public int getDevNoSum(){
        int sum = 0;
        if(null==deviceNo){
            return sum;
        }
        for (byte b : this.deviceNo){
            sum += b;
        }
        return sum;
    }
    public void setDeviceNo(byte[] deviceNo) {
        this.deviceNo = deviceNo;
    }

    public byte getCmdCode() {
        return cmdCode;
    }
    public void setCmdCode(byte cmdCode) {
        this.cmdCode = cmdCode;
    }

    public byte[] getData() {
        return data;
    }
    public int getDataSum(){
        int sum = 0;
        if(null==data){
            return sum;
        }
        for (byte b : this.data){
            if(b<0){
                sum += (b+256);
            }else{
                sum += b;
            }
        }
        return sum;
    }
    public void setData(byte[] data) {
        this.data = data;
    }

    public byte getSum() {
        return sum;
    }
    public void setSum(byte sum) {
        this.sum = sum;
    }

    public byte[] getTail() {
        return tail;
    }
    public void setTail(byte[] tail) {
        this.tail = tail;
    }

    public String makeCmdStr(){
        StringBuilder sb = new StringBuilder();
        for (byte b : makeCmd()){
            sb.append(",").append(b);
        }
        return sb.toString().replaceFirst(",","");
    }

    public byte[] makeCmd(){
        //数据头：3个字节，固定为62 65 67  (beg)
        //命令长度：2个字节,从站点类型(含)到效验和之前的字节个数
        //站点类型:1个字节,0x02
        //站点ID:2个字节
        //命令代码:1个字节
        //数据n:
        //效验和:1个字节,从站点类型(含)到效验和之前的字节和
        //数据尾:3个字节
        calCmdLen();
        mkSum();
        //header,cmdLen,cmdType,deviceNo,cmdCode,data,sum,tail
        byte[] cmd = new byte[this.header.length+this.cmdLen.length+
                1+this.deviceNo.length+1+this.data.length+1+this.tail.length];
        int i = 0;
        System.arraycopy(header,0,cmd,i,3);
        i = i+3;
        System.arraycopy(cmdLen,0,cmd,i,2);
        i = i+2;
        cmd[i++] = this.cmdType;
        System.arraycopy(deviceNo,0,cmd,i,2);
        i = i+2;
        cmd[i++] = this.cmdCode;
        System.arraycopy(data,0,cmd,i,data.length);
        i = i+data.length;
        cmd[i++] = this.sum;
        System.arraycopy(tail,0,cmd,i,3);
        return cmd ;
    }

    private void calCmdLen(){
        //从站点类型到效验和之前的数据个(站点类型，站点ID，命令代码，数据data)
        //eg:63-->00 3f
        int length = 1+this.deviceNo.length+1+this.data.length ;
        this.setCmdLen(StringUtils.intToHexBytes(length));
    }

    private void mkSum(){
        //从站点类型（含）到校验和之前的数据
        //十进制相加的
        int cmdCode =  this.getCmdCode()<0?(this.getCmdCode()+256):this.getCmdCode();
        int sum = this.getCmdType()+this.getDevNoSum()+cmdCode+this.getDataSum() ;
        //取最后的低2位,2个字节，和!!
        String bins = StringUtils.hexTobin(StringUtils.intToHex(sum));
        byte bsum ;
        try {
            bsum = Byte.valueOf(bins.substring(bins.length()-8,bins.length()),2);
        }catch (Exception e){
            String sumStr = StringUtils.binToHex(bins.substring(bins.length()-8,bins.length()));
            bsum = (byte) StringUtils.hexToInt(sumStr);
        }
        this.setSum(bsum) ;
    }

}
