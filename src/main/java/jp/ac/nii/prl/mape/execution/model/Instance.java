package jp.ac.nii.prl.mape.execution.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Instance {
	
	@GeneratedValue
	@JsonIgnore
	@Id
	private Long id;
	
	@NotEmpty
	private String instId;
	
	@NotEmpty
	private String ami;

	@NotEmpty
	private String instType;

	@NotEmpty
	private String securityGroupRef;
	
	@JsonBackReference
	@ManyToOne
	private View view;
	
	public String getAmi() {
		return ami;
	}

	public Long getId() {
		return id;
	}

	public String getInstId() {
		return instId;
	}
	
	public String getInstType() {
		return instType;
	}

	public String getSecurityGroupRef() {
		return securityGroupRef;
	}

	public View getView() {
		return view;
	}

	public void setAmi(String ami) {
		this.ami = ami;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public void setSecurityGroupRef(String securityGroupRef) {
		this.securityGroupRef = securityGroupRef;
	}

	public void setView(View view) {
		this.view = view;
	}
}
