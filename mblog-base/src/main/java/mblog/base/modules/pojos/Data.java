/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.modules.pojos;

import java.io.Serializable;

/**
 * Json 统一返回消息类
 * 
 * @author langhsu
 *
 */
public class Data implements Serializable {
	private static final long serialVersionUID = -1491499610244557029L;
	public static int CODE_SUCCESS = 0;
	public static int CODE_FAILURED = -1;
	public static String[] NOOP = new String[]{};
	
	private int code; // 处理状态：0: 成功
	private String message;
    private Object data; // 返回数据

    private Data(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 处理成功，并返回数据
     * @param data 数据对象
     * @return data
     */
    public static final Data success(Object data){
        return new Data(CODE_SUCCESS, "操作成功", data);
    }
    
    /**
     * 处理成功
     * @param message 消息
     * @return data
     * @deprecated with 1.0.3
     */
    public static final Data success(String message){
        return new Data(CODE_SUCCESS, message, NOOP);
    }
    
    /**
     * 处理成功
     * @param message 消息
     * @param data 数据对象
     * @return data
     */
    public static final Data success(String message, Object data){
        return new Data(CODE_SUCCESS, message, data);
    }

    /**
     * 处理失败，并返回数据（一般为错误信息）
     * @param code 错误代码
     * @param message 消息
     * @return data
     */
    public static final Data failure(int code, String message){
        return new Data(code, message, NOOP);
    }
    
    /**
     * 处理失败
     * @param message 消息
     * @return data
     */
    public static final Data failure(String message){
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
		return "{code:\"" + code + "\", message:\"" + message + "\", data:\"" + data.toString() + "\"}";
	}
	
}
