import controllers.IndexController;
import views.IndexView;

public class DentistOffice {
    public static void main(String[] args) {
        new IndexController(new IndexView());

    }
}