package jp.ac.nii.prl.mape.execution.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.ansible")
public class AnsibleProperties {

	private String executable;

	public String getExecutable() {
		return executable;
	}

	public void setExecutable(String executable) {
		this.executable = executable;
	}
	
	
}
