package com.example.botto.myapplication.tools;

import android.widget.*;

/**
 * Created by bottookku on 12.01.2020.
 */

public class Error {
    public final static int CONST_ERROR_INET_NE_RABOTAET_TIMEOUT = 0;
    public final static int CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER = 1;
    public final static int CONST_ERROR_LOGIN_NOT_PASS = 2;
    public final static int CONST_ERROR_INET_NE_RABOTAET_DISABLED = 3;
    public final static int CONST_ERROR_THIS_USER_NOT_HAVE_A_COOKIES = 4;
    public final static int CONST_ERROR_USERNAME_NOT_HAVE_IN_TABLE = 5;

    public final static String CONST_ERROR_INET_NE_RABOTAET_TIMEOUT_MSG = "Превышено время ожидания";
    public final static String CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER_MSG = "Неожиданный ответ сервера";
    public final static String CONST_ERROR_LOGIN_NOT_PASS_MSG = "Логин или пароль не совпадают";
    public final static String CONST_ERROR_INET_NE_RABOTAET_DISABLED_MSG = "Интернет отключен";
    public final static String CONST_ERROR_THIS_USER_NOT_HAVE_A_COOKIES_MSG = "Нет Cookies у этого пользователя";
    public final static String CONST_ERROR_USERNAME_NOT_HAVE_IN_TABLE_MSG = "Этот пользователь отсутствует в таблице.";

    static int error_number;
    static boolean isHaveNofif;
    static boolean isHaveToast;
    static boolean isErrorResponsedFromServer;
    static int errorCodeResponsedFromServer;
    static String  errorMsgResponsedFromServer;

    public static void Error(int error_number_,boolean isHaveNofif_,boolean isHaveToast_, boolean isErrorResponsedFromServer_, String msg){
        error_number = error_number_;
        isHaveNofif = isHaveNofif_;
        isHaveToast = isHaveToast_;
        isErrorResponsedFromServer = isErrorResponsedFromServer_;
        if(isErrorResponsedFromServer){
            errorMsgResponsedFromServer = msg;
            errorCodeResponsedFromServer = error_number_;
        }else {
            switch (error_number) {
                case CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER:
                    errorMsgResponsedFromServer = CONST_ERROR_UNEXPECTED_RESPONSE_FROM_SERVER_MSG;
                    break;
                case CONST_ERROR_INET_NE_RABOTAET_TIMEOUT:
                    errorMsgResponsedFromServer = CONST_ERROR_INET_NE_RABOTAET_TIMEOUT_MSG;
                    break;
                case CONST_ERROR_LOGIN_NOT_PASS:
                    errorMsgResponsedFromServer = CONST_ERROR_LOGIN_NOT_PASS_MSG;
                    break;
                case CONST_ERROR_INET_NE_RABOTAET_DISABLED:
                    errorMsgResponsedFromServer = CONST_ERROR_INET_NE_RABOTAET_DISABLED_MSG;
                    break;
                case CONST_ERROR_THIS_USER_NOT_HAVE_A_COOKIES:
                    errorMsgResponsedFromServer = CONST_ERROR_THIS_USER_NOT_HAVE_A_COOKIES_MSG;
                    break;
                case CONST_ERROR_USERNAME_NOT_HAVE_IN_TABLE:
                    errorMsgResponsedFromServer = CONST_ERROR_USERNAME_NOT_HAVE_IN_TABLE_MSG;
                    break;
            }
        }
        sendError();
    }

    static void sendError(){
        if(isHaveToast){
            Toast.Toast(errorMsgResponsedFromServer,false);
        }
        if(isHaveNofif){
            //ДОДЕЛАТЬ НОТИФИКАЦИИ.
        }
    }
}
