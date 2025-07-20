package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.util.XDialog;

public interface LoginController {
    void open();
    void login();
    
    default void exit(){
        if (XDialog.confirm("Bạn muốn kết thúc?")) {
            System.exit(0);
        }
    }
}
