package jp.ac.nii.prl.mape.execution.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.execution.AnsibleException;
import jp.ac.nii.prl.mape.execution.model.Instance;
import jp.ac.nii.prl.mape.execution.model.View;
import jp.ac.nii.prl.mape.execution.properties.AnsibleProperties;

@Service("viewService")
public class ViewServiceImpl implements ViewService {

	private final AnsibleProperties ansibleProperties;
	
	private final Logger logger = LoggerFactory.getLogger(ViewServiceImpl.class);
	
	@Autowired
	public ViewServiceImpl(AnsibleProperties ansibleProperties) {
		this.ansibleProperties = ansibleProperties;
	}
	
	/* (non-Javadoc)
	 * @see jp.ac.nii.prl.mape.execution.service.ViewService#execute(jp.ac.nii.prl.mape.execution.model.View)
	 */
	@Override
	public void execute(View view) throws AnsibleException {
		logger.debug("Creating instances");
		
		if ( view.getAdditions().isEmpty())
			logger.debug("No instances to create");
		
		for (Instance create:view.getAdditions()) {
			createInstance(create);
		}
		if (!view.getAdditions().isEmpty())
			logger.debug("Instances created");
		
		Collection<String> termIds = new ArrayList<>();
		for (Instance term:view.getTerminations()) {
			termIds.add(term.getInstId());
		}
		if (termIds.isEmpty()) {
			logger.debug("No instances to terminate");
			return;
		}
		
		logger.debug("Terminating instances");
		
		terminateInstances(termIds);
		
		logger.debug("Instances terminated");
	}
	
	private void createInstance(Instance instance) throws AnsibleException {
		
		// create Ansible extra variables
		String extraVars = String.format("--extra-vars={\"insttype\":\"%s\",\"instcount\":\"1\",\"instami\":\"%s\",\"instsg\":\"%s\",\"tagKey\":\"%s\",\"tagVal\":\"%s\"}", 
				instance.getInstType(),
				instance.getAmi(), 
				instance.getSecurityGroupRef(),
				ansibleProperties.getTagKey(),
				ansibleProperties.getTagValue());
		
		System.out.println(extraVars);
		
		// execute Ansible
		Process p = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(ansibleProperties.getExecutable(), 
					ansibleProperties.getCreate(), 
					extraVars);
			p = pb.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = r.readLine()) != null)
				System.out.println(line);

		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new AnsibleException("IO exception", e);
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new AnsibleException("InterruptedException", e);
		}
		if (p.exitValue() != 0) {
			logger.error(String.format("Ansible exit value is %s", p.exitValue()));
			throw new AnsibleException(String.format("Ansible exit value is %s", p.exitValue()));
		}
	}
	
	private void terminateInstances(Collection<String> instIds) throws AnsibleException {
		String separator = ",";
		
		// join the list of IDs into a single string with separator
		Iterator<String> iter = instIds.iterator();
		StringBuilder sb = new StringBuilder();
		if (iter.hasNext()) {
		  sb.append("\"" + iter.next() + "\"");
		  while (iter.hasNext()) {
		    sb.append(separator).append("\"" + iter.next() + "\"");
		  }
		}
		String joined = sb.toString();
		
		// create Ansible extra variables
		String extraVars = String.format("--extra-vars={\"instid\":[%s]}", joined);
		
		// execute Ansible
		Process p = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(ansibleProperties.getExecutable(),
					"-i", ansibleProperties.getInventory(),
					ansibleProperties.getTerminate(),
					extraVars);
			p = pb.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = r.readLine()) != null)
				System.out.println(line);
			BufferedReader re = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String linee = null;
			while ((linee = re.readLine()) != null)
				System.out.println(linee);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new AnsibleException("IO Exception", e);
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new AnsibleException("InterruptedException", e);
		}
		if (p.exitValue() != 0) {
			logger.error(String.format("Ansible exit value is %s", p.exitValue()));
			throw new AnsibleException(String.format("Ansible exit value is %s", p.exitValue()));
		}
	}
}
