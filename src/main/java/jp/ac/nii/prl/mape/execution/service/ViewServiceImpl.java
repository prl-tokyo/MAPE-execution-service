package jp.ac.nii.prl.mape.execution.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
		// create Ansible extra variables
		String extraVars = String.format("--extra-vars={\"insttype\":\"%s\",\"instcount\":\"1\",\"instami\":\"%s\",\"instsg\":\"%s\"}", 
				instance.getInstType(),
				instance.getAmi(), 
				instance.getSecurityGroupRef());
		
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
			e.printStackTrace();
			return false;
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;				
	}
	
	private boolean terminateInstances(Collection<String> instIds) {
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
			e.printStackTrace();
			return false;
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
