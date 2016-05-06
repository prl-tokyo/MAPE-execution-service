package jp.ac.nii.prl.mape.execution.properties;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.ansible")
public class AnsibleProperties {

	@NotEmpty
	@Valid
	private String executable;
	
	@NotEmpty
	@Valid
	private String inventory;
	
	@NotEmpty
	@Valid
	private String create;
	
	@NotEmpty
	@Valid
	private String terminate;
	
	@NotEmpty
	@Valid
	private String tagKey;
	
	@NotEmpty
	@Valid
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

	public String getTagKey() {
		return tagKey;
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

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public void setTerminate(String terminate) {
		this.terminate = terminate;
	}
	
	
}
