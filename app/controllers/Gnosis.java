package controllers;

import play.*;
import play.mvc.*;
//import static play.data.Form.*;
import java.util.Map;
import views.html.*;

public class Gnosis extends Controller {

    //Mapa de procesos
    public static Result mapa() {
        return ok(map.render());
    }

    //Enciclopedia - Procesos ITIL
    public static Result procesosItil() {
        return ok(gnosis.render());
    }

}
