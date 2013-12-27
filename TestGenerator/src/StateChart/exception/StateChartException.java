package StateChart.exception;

import java.util.Map;


public class StateChartException extends RuntimeException {
    private static final long serialVersionUID = 7673986530807260178L;

    private StateChartExceptionCode errorCode;
    private Map<String, String> extra;

    public StateChartException(StateChartExceptionCode errorCode) {
        this(errorCode, errorCode.getMess(), null); //change mess param by hetao
    }

    public StateChartException(StateChartExceptionCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public StateChartException(StateChartExceptionCode errorCode, String message, Map<String, String> extra) {
        super(message);
        if(errorCode == null){
            this.errorCode = StateChartExceptionCode.NA;
        }else{
            this.errorCode = errorCode;
        }
        this.extra = extra;
    }

    public StateChartExceptionCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(StateChartExceptionCode errorCode) {
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
