package jp.ac.nii.prl.mape.execution.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class View {

	@GeneratedValue
	@JsonIgnore
	@Id
	private Long id;
	
	@OneToMany(mappedBy="view")
	@JsonManagedReference	
	private Collection<Instance> additions;
	
	@OneToMany(mappedBy="view")
	@JsonManagedReference
	private Collection<Instance> terminations;

	public Collection<Instance> getAdditions() {
		return additions;
	}

	public Long getId() {
		return id;
	}

	public Collection<Instance> getTerminations() {
		return terminations;
	}

	public void setAdditions(Collection<Instance> additions) {
		this.additions = additions;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTerminations(Collection<Instance> terminations) {
		this.terminations = terminations;
	}
	
}
