package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
//import static play.data.Form.*;
import java.util.Map;
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
				return ok(index.render());
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			flash("error","Usuario y/o contraseña incorrectos");
			return redirect( "/" );
		}
		return redirect( "/" );
    }

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
	    	email,
	    	"activo");

	    user.save();

		return redirect( "/" );
	}

    //Dashboard
    public static Result dashboard() {
    	return ok(welcome.render());
    }

}
