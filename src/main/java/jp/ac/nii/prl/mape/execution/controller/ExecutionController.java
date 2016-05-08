package jp.ac.nii.prl.mape.execution.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import jp.ac.nii.prl.mape.execution.AnsibleException;
import jp.ac.nii.prl.mape.execution.ExecutionException;
import jp.ac.nii.prl.mape.execution.model.View;
import jp.ac.nii.prl.mape.execution.service.ViewService;

@RestController
@Component
@RequestMapping("/execute")
public class ExecutionController {
	
	private final ViewService viewService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExecutionController.class);
	
	@Autowired
	public ExecutionController(ViewService viewService) {
		this.viewService = viewService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> execute(@RequestBody View view) throws ExecutionException {
		
		logger.info("Executing changes");
		
		try {
			viewService.execute(view);
		} catch (AnsibleException e) {
			logger.error(e.getMessage());
			throw new ExecutionException("Could not execute plan");
		}
		
		logger.info("Changes done");
		
		// create response
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(view.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}

}
