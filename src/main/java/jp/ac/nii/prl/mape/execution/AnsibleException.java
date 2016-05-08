package jp.ac.nii.prl.mape.execution;

public class AnsibleException extends Exception {

	private static final long serialVersionUID = 9126829081114971177L;

	public AnsibleException() {
	}

	public AnsibleException(String message) {
		super(message);
	}

	public AnsibleException(Throwable cause) {
		super(cause);
	}

	public AnsibleException(String message, Throwable cause) {
		super(message, cause);

	}

	public AnsibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
