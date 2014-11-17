package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
//import static play.data.Form.*;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import play.api.libs.Crypto;

import models.*;

import views.html.*;

public class Usuarios extends Controller {

	//Pagina inicial
    public static Result welcome() {
    	return ok(welcome.render());
    }

    //Logueo
    public static Result login() {
    	Result view = null;
    	Map<String, String[]> formData = request().body().asFormUrlEncoded();
    	String username = formData.get("username")[0];
		String password = formData.get("password")[0];
		Usuario user = null;
		try{
			user = Usuario.getUserByUsername(username);
			// Comprobar password y cargar sesión.
			if (Crypto.sign(password).equals(user.getPassword()))
			//if(password.equals(user.getPassword()))
			{
				// Cargar sesión
				setSession(user);
				//Cargar lista evaluaciones
				List<Evaluacion> evaluaciones = 
					Evaluacion.getEvaluacionAll(Long.parseLong(session("id")));
				System.out.println("*Login");
				view = ok(index.render(evaluaciones));
			}else if(user == null){
				flash("error","Usuario no existe.");
				view = redirect( "/" );
			}else if(!(Crypto.sign(password).equals(user.getPassword()))){
				flash("error","Contraseña incorrecta.");
				view = redirect( "/" );
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			flash("error","Usuario y/o contraseña incorrectos");
			view = redirect( "/" );
		}
		
		return view;
    }

    //Establece sesion de usuario
    public static void setSession(Usuario user)
	{
		String uuid = java.util.UUID.randomUUID().toString();
		session("uuid", uuid);
		session("username", user.getUsername());
		session("password", user.getPassword());
		session("id", user.getId().toString());
		//session("usuario.nombres",user.getNombre());
		//session("usuario.apellidos", user.getApellido());
	}

	//Mostrar pagina de creacion de usuario
	public static Result showCreateUser(){
		return ok(createuser.render());
	}

	//Crear usuario nuevo
	public static Result crearUsuario(){
		Map<String, String[]> formData = request().body().asFormUrlEncoded();
    	String username = formData.get("username")[0];
		String password = formData.get("password")[0];
		String nombre = formData.get("nombre")[0];
		String apellido = formData.get("apellido")[0];
		String dni = formData.get("dni")[0];
		String empresa = formData.get("empresa")[0];
		String ruc = formData.get("ruc")[0];
		String tipo_empresa = formData.get("tipo_empresa")[0];
		String tipo_servicio_1 = "";
		String tipo_servicio_2 = "";
		if(formData.get("tipo_servicio").length == 1){
			tipo_servicio_1 = formData.get("tipo_servicio")[0];
		}else{
			tipo_servicio_1 = formData.get("tipo_servicio")[0];
			tipo_servicio_2 = formData.get("tipo_servicio")[1];
		}
		String email = formData.get("email")[0];
		
		if(	username == "" 			|| 
			password == ""
			)
	    {
	    	flash("error","Debe escribir los campos necesarios");
    		return redirect(request().getHeader("referer"));
	    }

	    Usuario user = new Usuario(
	    	username,
	    	Crypto.sign(password),
	    	nombre,
	    	apellido,
	    	dni,
	    	empresa,
	    	ruc,
	    	tipo_empresa,
	    	tipo_servicio_1,
	    	tipo_servicio_2,
	    	email,
	    	"activo");

	    //Restriccion de llave duplicada (usuario es unico)
	    if(Usuario.getUserByUsername(username) == null){
	    	user.save();
	    }else if(username.equals(Usuario.getUserByUsername(username).getUsername()))
	    {
	    	flash("error","El nombre de usuario ingresado no es único. Elija otro.");
    		return redirect(request().getHeader("referer"));
	    }else{
	    	user.save();
		}
	    flash("success","Su cuenta ha sido creada con éxito.");
    	return redirect( "/" );
	}

    //Dashboard
    public static Result dashboard() {
    	Result view = null;
    	if(session("id") == null){
    		flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
    		return redirect( "/" );
    	}else{
			List<Evaluacion> evaluaciones = 
				Evaluacion.getEvaluacionAll(Long.parseLong(session("id")));
			view = ok(index.render(evaluaciones));
    	}
    	return view;
    }

    public static Result logout() {
	    session().clear();
	    flash("success", "Su sesión ha sido cerrada con éxito.");
	    return redirect(routes.Usuarios.welcome());
	}

	//Listar evaluaciones de usuario
	public static Result listarEvaluaciones(){
		Result view = null;
    	if(session("id") == null){
    		flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
    		return redirect( "/" );
    	}else{
    		List<Evaluacion> evaluaciones =
				Evaluacion.getEvaluacionAll(Long.parseLong(session("id")));
			view = ok(evaluations.render(evaluaciones));
    	}
		return view;
	}

	//Listar evaluaciones de usuario
	public static Result mostrarEvaluacion(Long evid){
		session("evid", String.valueOf((int)(long) evid));
		Evaluacion evaluacion = Evaluacion.getEvaluacionById(evid);
		String respuestas = evaluacion.getRespuestas();

		//Algoritmo mágico de transformación espacio-temporal
		List<String[]> rptas = new ArrayList<String[]>();
		int asn_count = 1;
		System.out.println("**1");
		for(int i=0; i < respuestas.length() ;i++){
			if(respuestas.charAt(i) == '_'){
				asn_count++;
				System.out.println("**"+asn_count);
			}
			if((respuestas.charAt(i)) == '['){
				String linearr[] = new String[50];
				StringBuilder sb = new StringBuilder();
				int c = 0;
				int j = 1;
				while(!((respuestas.charAt(i+j)) == ']')){
					while(!((respuestas.charAt(i+j)) == ']' &&
							(respuestas.charAt(i+j)) == ',')){
						if(respuestas.charAt(i+j) != ' ' &&
							respuestas.charAt(i+j) != ','){
							sb.append(respuestas.charAt(i+j));
						}
						j++;
						if(i+j == respuestas.length())
							break;
						if(((respuestas.charAt(i+j)) == ']'))
							break;
						if(((respuestas.charAt(i+j)) == ','))
							break;
					}
					System.out.println(sb.toString());
					linearr[c] = sb.toString().trim();
					c++;
					sb = new StringBuilder();
					if(i+j >= respuestas.length())
							break;
				}
				rptas.add(linearr);
			}
			if((respuestas.charAt(i)) == '_' && !(respuestas.charAt(i+1) == '[')){
				String line = "";
				StringBuilder sb = new StringBuilder();
				int j = 1;
				while(!((respuestas.charAt(i+j)) == '_')){
					sb.append(respuestas.charAt(i+j));
					j++;
					if(i+j == respuestas.length())
						break;
					if(((respuestas.charAt(i+j)) == '_'))
						break;
				}
				System.out.println(sb.toString());
				line = sb.toString();
				rptas.add(new String[]{line});	
			}
		}
		//System.out.println(rptas.size()); = 15
		
		Result view = null;
		if(evaluacion.getTipo().equals("Gestion de Catalogo de Servicios")){
			MetricaGCS metrica = evaluacion.getMetricas_gcs();
			view = ok(showresult_gcs.render(metrica, rptas));
		}
		else if(evaluacion.getTipo().equals("Gestion del Nivel de Servicios")){
			MetricaGNS metrica = evaluacion.getMetricas_gns();
			view = ok(showresult_gns.render(metrica, rptas));
		}
		else{
			MetricaGIN metrica = evaluacion.getMetricas_gin();
			view = ok(showresult_gin.render(metrica, rptas));
		}
		return view;
	}

}
