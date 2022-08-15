package com.example.application.app.meldepunkt;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.example.application.HasVeryDynamicTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Route(value = "meldepunkte/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class MeldepunktView extends Div implements BeforeEnterObserver,  HasVeryDynamicTitle {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3173315111237462819L;
	
	/**
	 * 
	 */
	private static final String SAMPLEPERSON_ID = "samplePersonID";
	
    /**
     * 
     */
    private static final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "meldepunkte/%s/edit";

    private Grid<Meldepunkt> grid = new Grid<>(Meldepunkt.class, false);

    private TextField name;
    private IntegerField eigenschaften;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button remove = new Button("Remove");

    private BeanValidationBinder<Meldepunkt> binder;

    private Meldepunkt samplePerson;

    private final MeldepunktService samplePersonService;

    @Autowired
    public MeldepunktView(final MeldepunktService samplePersonService) {
        this.samplePersonService = samplePersonService;
        addClassNames("master-detail-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        createGrid(samplePersonService);

        createForm(samplePersonService);

    }

	private void createForm(MeldepunktService samplePersonService) {
		// Configure Form
        binder = new BeanValidationBinder<>(Meldepunkt.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.samplePerson == null) {
                    this.samplePerson = new Meldepunkt();
                }
                binder.writeBean(this.samplePerson);

                Meldepunkt updatedPerson = samplePersonService.update(this.samplePerson);
                clearForm();
                refreshGrid();
                Notification.show("Person " + updatedPerson + " stored.");
                UI.getCurrent().navigate(MeldepunktView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });
        
        remove.addClickListener(e -> {
            try {
	            if (this.samplePerson == null || this.samplePerson.getId() == null) {
	                Notification.show("No Person to remove.");
	                return;
	            }
	            Notification.show("Deleting " + this.samplePerson);
	            this.samplePersonService.delete(this.samplePerson.getId());
	            clearForm();
	            refreshGrid();
	            UI.getCurrent().navigate(MeldepunktView.class);
            } catch (Exception validationException) {
                Notification.show("An exception happened while trying to remove " + this.samplePerson);
            }
        });
	}

	private void createGrid(MeldepunktService samplePersonService) {
		// Configure Grid
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("eigenschaften").setAutoWidth(true);

        grid.setItems(query -> samplePersonService.list( PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MeldepunktView.class);
            }
        });
	}

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> samplePersonId = event.getRouteParameters().get(SAMPLEPERSON_ID).map(UUID::fromString);
        if (samplePersonId.isPresent()) {
            Optional<Meldepunkt> samplePersonFromBackend = samplePersonService.get(samplePersonId.get());
            if (samplePersonFromBackend.isPresent()) {
                populateForm(samplePersonFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %s", samplePersonId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MeldepunktView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Name");
        eigenschaften = new IntegerField("Eigenschaften");

        formLayout.add(name, eigenschaften);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        remove.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel, remove);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(final Meldepunkt value) {
        this.samplePerson = value;
        binder.readBean(this.samplePerson);

    }

}
