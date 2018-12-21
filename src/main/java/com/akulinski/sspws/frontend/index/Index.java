package com.akulinski.sspws.frontend.index;

import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Route
@SpringComponent
public class Index extends UI {

    private UserRepository userRepository;

    private Label label;

    private String currentPrincipalName;

    private GridLayout gridLayout;

    @Autowired
    public Index(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VaadinServletRequest vsRequest = (VaadinServletRequest)vaadinRequest;
        HttpServletRequest hsRequest = vsRequest.getHttpServletRequest();
        currentPrincipalName = hsRequest.getUserPrincipal().getName();

        gridLayout = new GridLayout(4,4);
        label = new Label(currentPrincipalName);
        gridLayout.addComponent(label,3,0);
        setContent(gridLayout);
    }
}
