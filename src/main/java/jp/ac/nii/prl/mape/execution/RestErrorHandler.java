package jp.ac.nii.prl.mape.execution;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(ExecutionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleExecutionException(ExecutionException ex) {
		
	}

}
