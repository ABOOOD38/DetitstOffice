package controllers;

import views.interfaces.View;
import views.SignInView;

import java.awt.event.ActionListener;
import java.util.List;

public class IndexController {
    private View indexView;

    public IndexController(View indexView) {
        this.indexView = indexView;

        indexView.display();
        indexView.registerListeners(Listeners());
    }

    private List<ActionListener> Listeners() {
        return List.of(logInBtnListener());
    }

    private ActionListener logInBtnListener() {
        return actionEvent -> {
            new SignInController(new SignInView());
            clean();
        };
    }

    private void clean() {
        indexView.getJFrame().dispose();
        indexView = null;
    }
}
