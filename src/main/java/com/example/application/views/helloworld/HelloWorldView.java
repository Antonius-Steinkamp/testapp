package com.example.application.views.helloworld;

import org.springframework.stereotype.Component;

import com.example.application.HasVeryDynamicTitle;
import com.example.application.app.person.SamplePerson;
import com.example.application.app.person.SamplePersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import lombok.extern.java.Log;

@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
@Component // wird automatisch gefunden wenn HasVeryDynamicTitle implementiert wird
@Log
public class HelloWorldView extends Div implements HasVeryDynamicTitle, BeforeLeaveObserver, BeforeEnterObserver   {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1438618338023148991L;
	
	private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email address");
    private DatePicker dateOfBirth = new DatePicker("Birthday");
    private PhoneNumberField phone = new PhoneNumberField("Phone number");
    private TextField occupation = new TextField("Occupation");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<SamplePerson> binder = new Binder<>(SamplePerson.class);

    public HelloWorldView(SamplePersonService personService) {
        addClassName("hello-world-view");

        createTitleFor(this); 
        createFormLayout(this);
        createButtonLayout(this, save, cancel);

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            personService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new SamplePerson());
    }

    private void createTitleFor(HelloWorldView view) {
        view.add(new H3("Personal information"));
    }

    private void createFormLayout(HelloWorldView view) {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(firstName, lastName, dateOfBirth, phone, email, occupation);
        view.add(formLayout);
    }

    private void createButtonLayout(HelloWorldView view, Button primary, Button ... extraButtons) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        primary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(primary);
        for (Button button: extraButtons) {
            buttonLayout.add(button);
        }
        view.add(buttonLayout);
    }

	@Override //BeforeLeaveObserver 
	public void beforeLeave(BeforeLeaveEvent event) {
		log.info("beforeLeave (BeforeLeaveEvent " + event + ")" );
		
		// Alles gesichert? Können wir weg?
		
	}

	@Override // BeforeEnterObserver 
	public void beforeEnter(BeforeEnterEvent event) {
		log.info("beforeEnter(BeforeEnterEvent " + event + ")" );
		
		// Url auswerten
	}

}
