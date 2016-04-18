package jp.ac.nii.prl.mape.execution.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.execution.model.Instance;
import jp.ac.nii.prl.mape.execution.model.View;
import jp.ac.nii.prl.mape.execution.properties.AnsibleProperties;

@Service("viewService")
public class ViewServiceImpl implements ViewService {

	private final AnsibleProperties ansibleProperties;
	
	@Autowired
	public ViewServiceImpl(AnsibleProperties ansibleProperties) {
		this.ansibleProperties = ansibleProperties;
	}
	
	/* (non-Javadoc)
	 * @see jp.ac.nii.prl.mape.execution.service.ViewService#execute(jp.ac.nii.prl.mape.execution.model.View)
	 */
	@Override
	public boolean execute(View view) {
		for (Instance create:view.getAdditions()) {
			createInstance(create);
		}
		Collection<String> termIds = new ArrayList<>();
		for (Instance term:view.getTerminations()) {
			termIds.add(term.getInstId());
		}
		if (termIds.isEmpty())
			return true;
		return terminateInstances(termIds);
	}
	
	private boolean createInstance(Instance instance) {
		String cmd = String.format("%s %s --extra-vars='{\"insttype\":\"%s\", \"instcount\":1, \"instami\":\"%s\", \"instsg\":\"%s\"}'", 
				ansibleProperties.getExecutable(),
				ansibleProperties.getCreate(),
				instance.getInstType(),
				instance.getAmi(),
				instance.getSecurityGroupRef());
		System.out.println(cmd);
		return true;
	}
	
	private boolean terminateInstances(Collection<String> instIds) {
		String separator = ",";
		
		// join the list of IDs into a single string with separator
		Iterator<String> iter = instIds.iterator();
		StringBuilder sb = new StringBuilder();
		if (iter.hasNext()) {
		  sb.append("'" + iter.next() + "'");
		  while (iter.hasNext()) {
		    sb.append(separator).append("'" + iter.next() + "'");
		  }
		}
		String joined = sb.toString();
		
		String extraVars = String.format("\"instid:[%s]\"", joined);
		
		// create Ansible command
		String cmd = String.format("%s -i %s %s --extra-vars %s", 
				ansibleProperties.getExecutable(), 
				ansibleProperties.getInventory(),
				ansibleProperties.getTerminate(),
				extraVars);
		System.out.println(cmd);
		return true;
	}
}
