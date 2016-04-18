package jp.ac.nii.prl.mape.execution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jp.ac.nii.prl.mape.execution.model.View;
import jp.ac.nii.prl.mape.execution.properties.AnsibleProperties;

@RestController
@Component
@RequestMapping("/execute")
public class ExecutionController {
	
	private final AnsibleProperties ansibleProperties;
	
	@Autowired
	public ExecutionController(AnsibleProperties ansibleProperties) {
		this.ansibleProperties = ansibleProperties;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> execute(@RequestBody View view) {
		
		// create response
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(view.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}

}
