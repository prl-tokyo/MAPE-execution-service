package jp.ac.nii.prl.mape.execution;

import org.springframework.web.client.RestClientException;

public class ExecutionException extends RestClientException {

	private static final long serialVersionUID = -6064159576630249534L;

	public ExecutionException(String msg) {
		super(msg);
	}

	public ExecutionException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
