package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.util.XDialog;

public interface LoginController {
    void open();
    void login();
    
    default void exit(){      
      if (XDialog.confirm("Bạn có chắc chắn muốn thoát khỏi ứng dụng không?", "Xác nhận thoát"))  {
            System.exit(0);
        }
    }
}
