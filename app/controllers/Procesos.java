package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
//import static play.data.Form.*;
import java.util.Map;
import play.api.libs.Crypto;

import models.*;

import views.html.*;

public class Procesos extends Controller {

    //Cuestionario_Gestion de Catalogo de Servicios
    public static Result cuestionarioGCS() {
    	return ok(proc1_gcs_q.render());
    }

    //Cuestionario_Gestion de Nivel de Servicios
    public static Result cuestionarioGNS() {
    	return ok(proc1_gcs_q.render());
    }

    //Cuestionario_Gestion de Incidentes
    public static Result cuestionarioGIN() {
    	return ok(proc1_gcs_q.render());
    }

    

}
