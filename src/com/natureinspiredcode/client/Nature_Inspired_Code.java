package com.natureinspiredcode.client;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.Link;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.natureinspiredcode.client.model.Solution;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Nature_Inspired_Code implements EntryPoint,ClickHandler,AsyncCallback<Solution>,ValueChangeHandler<String> {
	
	static final String GITHUB="GitHub", CONTACT="Contact", PORTFOLIO="Portfolio", 
	ARTICLES="Articles", FAQ="FAQ", SERVICES="Services", SKILLS = "Skills", BLOG = "Blog"; 
	
	private static final Map<Integer, String> MENU_OPTIONS_MAP;
	private final VerticalPanel vPanel = new VerticalPanel();
	
	static {
		TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
		treeMap.put(0, SKILLS);
		treeMap.put(1, SERVICES);
		treeMap.put(2, PORTFOLIO);
		treeMap.put(3, ARTICLES);
		treeMap.put(4, CONTACT);
		treeMap.put(5, FAQ);
		treeMap.put(6, GITHUB);
		treeMap.put(7, BLOG);
		MENU_OPTIONS_MAP = Collections.unmodifiableMap(treeMap);
	}
	
	private final SolutionServiceAsync solutionService = GWT.create(SolutionService.class);
	private final Button btn = new Button("Find another solution");	
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		solutionService.findSolution(this);
		RootPanel.get("button").add(vPanel);
		vPanel.addStyleName("center");
		vPanel.add(btn);
		btn.addClickHandler(this);
		History.addValueChangeHandler(this);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		solutionService.findSolution(this);
	}

	@Override
	public void onFailure(Throwable caught) {
		GWT.log("error", caught);
	}

	@Override
	public void onSuccess(Solution result) {		
		if (result == null) {
			solutionService.findSolution(this);
		} else {
			for (int i = 0; i < 8; i++) {
				int pos = result.board().get(i) - 1;
				String value = MENU_OPTIONS_MAP.get(i);				 
				for (int j = 0; j < 8; j++) {
					String id = String.valueOf(i) + String.valueOf(j);
					RootPanel rootPanel = RootPanel.get(id); 
					rootPanel.clear();
					if (j == pos) {
						rootPanel.add(new Hyperlink(value, value));
					} else {
						rootPanel.add(new Label(""));
					}
				}
			}
		}
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		Window.alert("I have not yet finished this page. Feel free to email me at james@natureinspirecode.com");
	}

}
