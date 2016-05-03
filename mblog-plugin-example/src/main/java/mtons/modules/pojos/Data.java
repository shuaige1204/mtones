/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mtons.modules.pojos;

import java.io.Serializable;

/**
 * 
 * @author langhsu
 *
 */
public class Data implements Serializable {
    private static final long serialVersionUID = -1491499610244557029L;
    public static int         CODE_SUCCESS     = 0;
    public static int         CODE_FAILURED    = -1;
    public static String[]    NOOP             = new String[] {};

    private int               code;
    private String            message;
    private Object            data;

    private Data(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * @return data
     */
    public static final Data success(Object data) {
        return new Data(CODE_SUCCESS, "", data);
    }

    /**
     * 澶勭悊鎴愬姛
     * @param message 娑堟伅
     * @return data
     * @deprecated with 1.0.3
     */
    public static final Data success(String message) {
        return new Data(CODE_SUCCESS, message, NOOP);
    }

    /**
     * @return data
     */
    public static final Data success(String message, Object data) {
        return new Data(CODE_SUCCESS, message, data);
    }

    /**
     * @return data
     */
    public static final Data failure(int code, String message) {
        return new Data(code, message, NOOP);
    }

    /**
     * @return data
     */
    public static final Data failure(String message) {
        return failure(CODE_FAILURED, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "{code:\"" + code + "\", message:\"" + message + "\", data:\"" + data.toString()
               + "\"}";
    }

}
