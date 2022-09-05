package com.example.application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AccessingAllClassesInPackage {
	static final String BASE_PACKAGE = "com.example.application";
	
	static Predicate<? super String> isClass = (s) -> s.endsWith(".class");

    public static Set<Class<?>> findAllClassesUsingClassLoader(final String packageName) {
    	Function<String, Class<?>> mapper = (s) -> getClass(s, packageName);
    	
        InputStream stream = ClassLoader.getSystemClassLoader()
          .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
          .filter(isClass)
          .map(mapper)
          .peek(s -> System.out.println("- " + s))
          .collect(Collectors.toSet());
    }

    public static Set<String> findAllResourcenamesUsingClassLoader(final String packageName) {
    	Set<String> result = new HashSet<>();
    	
        InputStream stream = ClassLoader.getSystemClassLoader()
          .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        reader.lines().forEach(line -> {
        	if ( line.endsWith(".class") ) {
        		  try {
        	            Class<?> cls = Class.forName(packageName + "."
        	              + line.substring(0, line.lastIndexOf('.')));
        	            if (HasVeryDynamicTitle.class.isAssignableFrom(cls)) {
        	            	result.add(packageName + "." + line);
        	            }
        	        } catch (ClassNotFoundException e) {
        	            // No class
        	        }
        	} else {
        		result.addAll(findAllResourcenamesUsingClassLoader(packageName + "." + line));
        	}
        });
        
        return result;
    }

    private static Class<?> getClass(final String className, final String packageName) {
        Class<?> result = null;
        try {
            result = Class.forName(packageName + "."
              + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            System.out.println("NF: " + packageName + "." + className);
        }
        System.out.println("Search: " + packageName + "." + className + " is " + result);
        return result;
    }
    
    public static void main(String[] args) {
		findAllResourcenamesUsingClassLoader(BASE_PACKAGE).forEach(resourceName -> System.out.println(resourceName));
		// findAllClassesUsingClassLoader(BASE_PACKAGE).forEach(clasz -> System.out.println(clasz.getCanonicalName()));
		// getPackages(BASE_PACKAGE).forEach(p -> System.out.println("p: " + p.getName()));
	}
    
    private static List<Package> getPackages(final String baseName) {
    	List<Package> result = new ArrayList<>();
    	Package[] packages = Package.getPackages();
    	for (final Package p: packages) {
    		System.out.println( "-- " + p.getName());
    		if (p.getName().startsWith(baseName)) {
    			result.add(p);
    		}
    	}
    	
    	return result;
    }
}
