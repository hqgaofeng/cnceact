package com.cnce.manager.file;

import java.io.File;

public class ResultEntity {

    private String code;

    private String message;
    private File file;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ResultEntity(){}

    public ResultEntity(String code, String message, File file) {
        this.code = code;
        this.message = message;
        this.file = file;
    }

}
