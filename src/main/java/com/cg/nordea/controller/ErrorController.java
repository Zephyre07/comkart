package com.cg.nordea.controller;

import static com.cg.nordea.constants.ConstantUtility.ARROW;
import static com.cg.nordea.constants.ConstantUtility.EMPTY_STRING;
import static com.cg.nordea.constants.ConstantUtility.ERROR_CODE_202;
import static com.cg.nordea.constants.ConstantUtility.ERROR_CODE_400;
import static com.cg.nordea.constants.ConstantUtility.ERROR_CODE_401;
import static com.cg.nordea.constants.ConstantUtility.ERROR_CODE_500;
import static com.cg.nordea.constants.ConstantUtility.INTERNAL_ERROR;
import static com.cg.nordea.constants.ConstantUtility.NORDIA_POC;
import static com.cg.nordea.constants.ConstantUtility.NO_DATA_FOUND;
import static com.cg.nordea.constants.ConstantUtility.OBJECTS;
import static com.cg.nordea.constants.ConstantUtility.SEARCH_CRITERIA_NOT_ENOUGH;
import static com.cg.nordea.constants.ConstantUtility.UN_AUTHORIZED_USER;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.cg.nordea.exceptions.AuthorizationException;
import com.cg.nordea.exceptions.ComKartException;
import com.cg.nordea.exceptions.NoDataFoundException;
import com.cg.nordea.exceptions.MessagesErrorVO;
import com.cg.nordea.exceptions.ValidationException;

@RestControllerAdvice
public class ErrorController {
	Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@ExceptionHandler(value = { ValidationException.class })
	public @ResponseBody ResponseEntity<MessagesErrorVO> validationException(ValidationException exception) {
		logger.info("in validationException");
		logger.info(exception.getMessage());
		MessagesErrorVO messagesErrorVO = new MessagesErrorVO(ERROR_CODE_400, exception.getMessage(),
				SEARCH_CRITERIA_NOT_ENOUGH);
		return new ResponseEntity<>(messagesErrorVO, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(AuthorizationException.class)
	public @ResponseBody ResponseEntity<MessagesErrorVO> authorizationException(AuthorizationException exception) {
		logger.info("in authorizationException");
		logger.info(exception.getMessage());
		MessagesErrorVO messagesErrorVO = new MessagesErrorVO(ERROR_CODE_401, exception.getMessage(),
				UN_AUTHORIZED_USER);
		return new ResponseEntity<>(messagesErrorVO, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(ComKartException.class)
	public @ResponseBody ResponseEntity<MessagesErrorVO> noDataException(ComKartException exception) {
		logger.info("in noDataException");
		logger.info(exception.getMessage());
		MessagesErrorVO messagesErrorVO = new MessagesErrorVO(ERROR_CODE_202, INTERNAL_ERROR, exception.getMessage());
		return new ResponseEntity<>(messagesErrorVO, HttpStatus.ACCEPTED);
	}

	@ExceptionHandler(NoDataFoundException.class)
	public @ResponseBody ResponseEntity<MessagesErrorVO> customerNoDataException(NoDataFoundException exception) {
		logger.info("in customerNoDataException");
		logger.info(exception.getMessage());
		MessagesErrorVO messagesErrorVO = new MessagesErrorVO(ERROR_CODE_202, NO_DATA_FOUND, exception.getMessage());
		return new ResponseEntity<>(messagesErrorVO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseEntity<MessagesErrorVO> generalException(Exception exception) {
		exception.printStackTrace();
		String getLoggerTrace = getErrorTrace(exception);
		logger.info("in generalException {} ", getLoggerTrace);
		logger.info(exception.getLocalizedMessage());

		MessagesErrorVO messagesErrorVO = new MessagesErrorVO(ERROR_CODE_500, exception.getMessage(), getLoggerTrace);
		return new ResponseEntity<>(messagesErrorVO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MessagesErrorVO> handleArgumentNotValidException(MethodArgumentNotValidException validException,
			WebRequest webRequest) {
		BindingResult bindingResult = validException.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		final StringJoiner joiner = new StringJoiner(",");
		fieldErrors.forEach(fieldError -> joiner.add(fieldError.getDefaultMessage()));
		MessagesErrorVO exceptionDetails = new MessagesErrorVO(joiner.toString(), webRequest.getDescription(false), LocalDateTime.now().toString());
		return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	public String getErrorTrace(Exception exception) {
		String topStack = exception.getStackTrace()[0].getClassName().concat(ARROW)
				.concat(exception.getStackTrace()[0].getMethodName()).concat(ARROW)
				.concat(String.valueOf(exception.getStackTrace()[0].getLineNumber()));

		String secondStack = EMPTY_STRING;
		List<StackTraceElement> errorStackTrace = asList(exception.getStackTrace()).stream()
				.filter(obj -> obj.getClassName().contains(NORDIA_POC)).collect(toList());

		if (topStack.contains(OBJECTS) && errorStackTrace.get(0) != null) {
			secondStack = (errorStackTrace.get(0).getClassName() == null ? EMPTY_STRING
					: errorStackTrace.get(0).getClassName())
							.concat(ARROW)
							.concat(errorStackTrace.get(0).getMethodName() == null ? EMPTY_STRING
									: errorStackTrace.get(0).getMethodName())
							.concat(ARROW).concat(String.valueOf(errorStackTrace.get(0).getLineNumber()));
		}

		return topStack.concat(secondStack);
	}

}
