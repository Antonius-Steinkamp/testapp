package com.example.application.views.fismessage;

import com.example.application.HasVeryDynamicTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "fismessage", layout = MainLayout.class)
@Uses(Icon.class)
@org.springframework.stereotype.Component
public class FisMessageView extends Div implements HasVeryDynamicTitle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1438618338023148991L;
	
	private TextField fisMessagePoint = new TextField("Message Point");
    private TextField product = new TextField("Product");
    private DateTimePicker dateOfBirth = new DateTimePicker("Birthday");

    private Button execute = new Button("Execute");

    private Binder<FisMessage> binder = new Binder<>(FisMessage.class);

    public FisMessageView() {
        addClassName("fis-message-view");
        addClassName("hello-world-view");


        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();

        execute.addClickListener(e -> {
            Notification.show(binder.getBean() + " executed.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new FisMessage());
    }

    private Component createTitle() {
        return new H3("Enter Fis Message");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        ResponsiveStep step = new ResponsiveStep("0", 1);
        formLayout.setResponsiveSteps(step);
        formLayout.add(fisMessagePoint, product, dateOfBirth);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        execute.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(execute);
        return buttonLayout;
    }

}
