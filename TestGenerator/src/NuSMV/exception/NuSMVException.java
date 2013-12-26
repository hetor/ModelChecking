package NuSMV.exception;

import java.util.Map;


public class NuSMVException extends RuntimeException {
    private static final long serialVersionUID = 7673986530807260178L;

    private NuSMVExceptionCode errorCode;
    private Map<String, String> extra;

    public NuSMVException(NuSMVExceptionCode errorCode) {
        this(errorCode, "", null);
    }

    public NuSMVException(NuSMVExceptionCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public NuSMVException(NuSMVExceptionCode errorCode, String message, Map<String, String> extra) {
        super(message);
        if(errorCode == null){
            this.errorCode = NuSMVExceptionCode.NA;
        }else{
            this.errorCode = errorCode;
        }
        this.extra = extra;
    }

    public NuSMVExceptionCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(NuSMVExceptionCode errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
