package com.example.application;

import com.vaadin.flow.router.HasDynamicTitle;

public interface HasVeryDynamicTitle extends HasDynamicTitle {
	static final String ENDING = "View";
	@Override
	default String getPageTitle() {
		final String result = this.getClass().getSimpleName();
		if (result.endsWith(ENDING)) {
			return result.substring(0, result.length()-ENDING.length());
		} 
		return result;
	}
}
