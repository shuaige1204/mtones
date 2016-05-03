package mtons.modules.exception;

/**
 * 
 * @author langhsu
 *
 */
public class MtonsException extends RuntimeException {
    private static final long serialVersionUID = -7443213283905815106L;
    private int               code;

    public MtonsException() {
    }

    /**
     * MtonsException
     */
    public MtonsException(int code) {
        super("code=" + code);
        this.code = code;
    }

    /**
     * MtonsException
     */
    public MtonsException(String message) {
        super(message);
    }

    /**
     * MtonsException
     */
    public MtonsException(Throwable cause) {
        super(cause);
    }

    /**
     * MtonsException
     */
    public MtonsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * MtonsException
     */
    public MtonsException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
