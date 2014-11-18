package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Aux_last extends Model {

  @Id
  @GeneratedValue
  public Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  public Usuario usuario;

  public Long gcs_le;

  public Long gns_le;

  public Long gin_le;
  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date dueDate = new Date();

  public static Finder<Long,Aux_last> find = new Finder<Long,Aux_last>(
    Long.class, Aux_last.class
  );

  //Retornar aux_last (1 = SCM, 2 = SLM, 3 = IM)
  public static Aux_last getAuxLastById(Long uid){
    Aux_last aux_last = find.where().eq("usuario_id",uid).findUnique();
    return aux_last;
  }

  //GyS
public Long getId() {
  return id;
}
public void setId(Long id) {
  this.id = id;
}

public Long getGcs_le() {
  return gcs_le;
}

public void setGcs_le(Long gcs_le) {
  this.gcs_le = gcs_le;
}

public Long getGns_le() {
  return gns_le;
}

public void setGns_le(Long gns_le) {
  this.gns_le = gns_le;
}

public Long getGin_le() {
  return gin_le;
}

public void setGin_le(Long gin_le) {
  this.gin_le = gin_le;
}

public Usuario getUsuario() {
  return usuario;
}

public void setUsuario(Usuario usuario) {
  this.usuario = usuario;
}

public Date getDueDate() {
  return dueDate;
}

public void setDueDate(Date dueDate) {
  this.dueDate = dueDate;
}

//Constructor
public Aux_last(Usuario usuario) {
  super();
  this.usuario = usuario;
} 

}