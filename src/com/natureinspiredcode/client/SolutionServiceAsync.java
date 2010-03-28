package com.natureinspiredcode.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.natureinspiredcode.client.model.Solution;

public interface SolutionServiceAsync {
	void findSolution(AsyncCallback<Solution> callback); 
}
