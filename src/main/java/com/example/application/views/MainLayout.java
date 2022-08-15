package com.example.application.views;


import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.HasVeryDynamicTitleClasses;
import com.example.application.app.meldepunkt.MeldepunktView;
import com.example.application.app.person.SamplePersonView;
import com.example.application.components.appnav.AppNav;
import com.example.application.components.appnav.AppNavItem;
import com.example.application.views.about.AboutView;
import com.example.application.views.fismessage.FisMessageView;
import com.example.application.views.helloworld.HelloWorldView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;

import lombok.extern.java.Log;

/**
 * The main view is a top-level placeholder for other views.
 */
@Log
public class MainLayout extends AppLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6154652183060289797L;
	private H1 viewTitle;
	
	@Autowired
	private final HasVeryDynamicTitleClasses classes;

    public MainLayout(final HasVeryDynamicTitleClasses classes) {
    	
    	this.classes = classes;
    	
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("view-header");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Testapp");
        appName.addClassNames("app-name");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("drawer-section");
        return section;
    }

    private AppNav createNavigation() {
        AppNav nav = new AppNav();
        nav.addClassNames("app-nav");

        nav.addItem(new AppNavItem("Hello World", HelloWorldView.class, "la la-user"));
        nav.addItem(new AppNavItem("About", AboutView.class, "la la-file"));
        nav.addItem(new AppNavItem("Persons", SamplePersonView.class, "la la-columns"));

        nav.addItem(new AppNavItem("FisMeldungen", FisMessageView.class, "la la-columns"));
        nav.addItem(new AppNavItem("Meldepunkte", MeldepunktView.class, "la la-columns"));
        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("app-nav-footer");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
    	Class<?> contentClass = getContent().getClass();
    	boolean fromClass = HasDynamicTitle.class.isAssignableFrom(contentClass);
    	String title;
    	if (fromClass) {
    		HasDynamicTitle o =(HasDynamicTitle)getContent(); 
    		title = o.getPageTitle();
    	} else {
    		PageTitle pageTitle = getContent().getClass().getAnnotation(PageTitle.class);
    		if (pageTitle != null && pageTitle.value() != null) {
        	title = pageTitle.value();
    		} else {
        		title = "";
        	}
    	}
    	
    	return title;
    }
}
