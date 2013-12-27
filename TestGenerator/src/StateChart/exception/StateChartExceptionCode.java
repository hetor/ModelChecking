package StateChart.exception;

public enum StateChartExceptionCode {

	NA(""),
	NULL_POINTER("��ָ��"),
	NOT_NULL(""),
	BLANK_STRING(""),
	BLANK_NAME(""),
	ILLEGAL_VALUE(""),
	EMPTY_CONTAINER(""),
	INVALID_KEY(""),
	
	//umlstatechart
	UMLSTATECHART_INVALID("״̬ͼ���󲻿��ã�ȱ�����ݣ�"),
	STATE_INVALID("״̬���󲻿��ã�ȱ�����ݣ�"),
	
	//xmi parse error
	XMI_PARSE_ERROR(""),
	
	//xmi format
	NO_INIT_STATE(""),
	MULTI_INIT_STATE(""),
	NO_TRANSITION_INIT_STATE(""),
	MULTI_TRANSITION_INIT_STATE(""),
	XMI_FORMAT_ERROR(""),
	
	//method param
	METHOD_PARAM_ERROR("");
	
	private String mess;
	
	private StateChartExceptionCode(String mess) {
	    this.mess = mess;
	}
	
	public String getMess() {
	    return mess;
	}
}
