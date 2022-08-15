package com.example.application;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.java.Log;

@Getter
@Log
@Component
public class HasVeryDynamicTitleClasses {

	  private final List<HasVeryDynamicTitle> classes;

	  public HasVeryDynamicTitleClasses(final List<HasVeryDynamicTitle> filters) {
	    this.classes = filters;
	    
	    log.info("Views found: " + classes.size());
	  }

}
