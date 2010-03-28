package com.natureinspiredcode.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.natureinspiredcode.client.model.Solution;

@RemoteServiceRelativePath("solution")
public interface SolutionService extends RemoteService {
	Solution findSolution(); 
}
