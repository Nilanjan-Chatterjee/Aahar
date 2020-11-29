package com.example.aahar;
import com.google.gson.annotations.SerializedName;
public class SignupResponse {
    @SerializedName("error")
    private boolean err;
    @SerializedName("message")
    private String msg;
    private String name;
    public SignupResponse(boolean err, String msg,String name) {
        this.err = err;
        this.msg = msg;
        this.name=name;
    }
    public SignupResponse(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public String getname() {
        return name;
    }

    public boolean isErr() {
        return err;
    }
    public String getMsg() {
        return msg;
    }
}
