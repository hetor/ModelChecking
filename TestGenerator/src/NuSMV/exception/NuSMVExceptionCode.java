package NuSMV.exception;

public enum NuSMVExceptionCode {

	NA,
	NULL_POINTER,
	NOT_NULL,
	BLANK_STRING,
	BLANK_NAME,
	ILLEGAL_VALUE,
	EMPTY_CONTAINER,
	INVALID_KEY,

	// user login
	USERNAME_ERROR,
	PASSWORD_ERROR,
	USER_FORBIDDEN,
	USER_DELETED,
	USER_OUTDATED,
	PLATFORM_NOT_MATCH,

	// operator user
	EMPLOYEE_CODE_DUPLICATED,

	// token authorize
	TOKEN_MATCH_FAILED,
	TOKEN_OUTDATED,
	TOKEN_INVALID,
	TOKEN_DESERIALIZE_FAILED,

	// duplicated
	NAME_DUPLICATED,
	USERNAME_DUPLICATED,
	CUSTOMER_FULLNAME_DUPLICATED,
	CUSTOMER_SHORTNAME_DUPLICATED,
	EMAIL_DUPLICATED,
	OFFICENO_DUPLICATED,
	OFFICENO_NULL,
	AIRLINE_NULL,
	BUSINESS_MAIL_DUPLICATED,
	USER_EMAIL_DUPLICATED,

	// file upload
	FILE_UPLOAD_FAILED,

	// department
	DEPARTMENT_NOT_EMPTY,

	// customer group
	CUSTOMER_GROUP_NOT_EMPTY,

	// finance info create error
	FINANCE_CREATE_ERROR,

	MAIL_NOT_FOUND,

	// customer
	BLANK_CUSTOMER_FULLNAME,
	BLANK_CUSTOMER_SHORTNAME,
	DUPLICATED_BIG_CUSTOMER_NO,

	// security code
	SECURITY_CODE_OUTDATED,

}