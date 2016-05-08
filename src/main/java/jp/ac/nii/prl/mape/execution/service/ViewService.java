package jp.ac.nii.prl.mape.execution.service;

import jp.ac.nii.prl.mape.execution.AnsibleException;
import jp.ac.nii.prl.mape.execution.model.View;

public interface ViewService {

	void execute(View view) throws AnsibleException;

}