package jp.ac.nii.prl.mape.execution.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.ansible")
public class AnsibleProperties {

	private String executable;
	
	private String inventory;
	
	private String create;
	
	private String terminate;
	
	private String tagName;
	
	private String tagValue;

	public String getCreate() {
		return create;
	}

	public String getExecutable() {
		return executable;
	}

	public String getInventory() {
		return inventory;
	}

	public String getTagName() {
		return tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public String getTerminate() {
		return terminate;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public void setExecutable(String executable) {
		this.executable = executable;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public void setTerminate(String terminate) {
		this.terminate = terminate;
	}
	
	
}
