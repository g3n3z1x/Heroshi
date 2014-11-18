package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Usuario extends Model {

  @Id
  @GeneratedValue
  public Long id;

  @OneToMany(cascade = {CascadeType.ALL})
  public List <Evaluacion> evaluaciones;
  
  @Column(unique=true)
  @Constraints.Required
  public String username;

  @Constraints.Required
  public String password;

  public String nombre;

  public String apellido;

  public String dni;

  public String empresa;

  public String ruc;

  public String tipo_empresa;

  public String email;
  
  public String estado;
  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date dueDate = new Date();
  public static Finder<Long,Usuario> find = new Finder<Long,Usuario>(
    Long.class, Usuario.class
  );

  //Busqueda usuario en BD
  public static Usuario getUserByUsername(String username){
    Usuario user = find.where().eq("username",username).findUnique();
    return user;
  } 

  //GyS
public Long getId() {
  return id;
}
public void setId(Long id) {
  this.id = id;
}

public String getUsername() {
  return username;
}

public void setUsername(String username) {
  this.username = username;
}

public String getPassword() {
  return password;
}

public void setPassword(String password) {
  this.password = password;
}

public String getNombre() {
  return nombre;
}

public void setNombre(String nombre) {
  this.nombre = nombre;
}

public String getApellido() {
  return apellido;
}

public void setApellido(String apellido) {
  this.apellido = apellido;
}

public String getDni() {
  return dni;
}

public void setDni(String dni) {
  this.dni = dni;
}

public String getEmpresa() {
  return empresa;
}

public void setEmpresa(String empresa) {
  this.empresa = empresa;
}

public String getRuc() {
  return ruc;
}

public void setRuc(String ruc) {
  this.ruc = ruc;
}

public String getTipo_empresa() {
  return tipo_empresa;
}

public void setTipo_empresa(String tipo_empresa) {
  this.tipo_empresa = tipo_empresa;
}

public String getEmail() {
  return email;
}

public void setEmail(String email) {
  this.email = email;
}

public String getEstado() {
  return estado;
}

public void setEstado(String estado) {
  this.estado = estado;
}

public Date getDueDate() {
  return dueDate;
}

public void setDueDate(Date dueDate) {
  this.dueDate = dueDate;
}

public static Finder<Long, Usuario> getFind() {
  return find;
}

public static void setFind(Finder<Long, Usuario> find) {
  Usuario.find = find;
} 

//Constructor
public Usuario(String username, String password, String nombre,
    String apellido, String dni, String empresa, String ruc,
    String tipo_empresa, String email, String estado) {
  super();
  this.username = username;
  this.password = password;
  this.nombre = nombre;
  this.apellido = apellido;
  this.dni = dni;
  this.empresa = empresa;
  this.ruc = ruc;
  this.tipo_empresa = tipo_empresa;
  this.email = email;
  this.estado = estado;
} 

}