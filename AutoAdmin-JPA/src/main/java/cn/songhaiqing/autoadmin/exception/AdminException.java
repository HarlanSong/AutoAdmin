package cn.songhaiqing.autoadmin.exception;

public class AdminException extends Throwable {
    private String errorMsg;
    public AdminException(String errorMsg){
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
