package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
//import static play.data.Form.*;
import java.util.Map;
//import play.api.libs.Crypto;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
//import java.text.DecimalFormat;
//import java.math.*;
import play.api.libs.json.Json;
import play.mvc.BodyParser;
import play.libs.Json.*;
import static play.libs.Json.toJson;                          
//import org.codehaus.jackson.JsonNode;           
//import org.codehaus.jackson.node.ObjectNode; 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;

import models.*;

import views.html.*;

public class Procesos extends Controller {

    //Info previa_Gestion de Catalogo de Servicios
    public static Result previoGCS() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
            view = ok(proc1_gcs_previo.render());
        }
        return view;
    }

    //Info previa_Gestion de Catalogo de Servicios
    public static Result previoGNS() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
           view = ok(proc1_gns_previo.render());
        }
        return view;
    }

    //Info previa_Gestion de Catalogo de Servicios
    public static Result previoGIN() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
           view = ok(proc1_gin_previo.render());
        }
        return view;
    }

    //Cuestionario_Gestion de Catalogo de Servicios
    public static Result cuestionarioGCS() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
    	   Usuario usuario = Usuario.getUserByUsername(session("username"));
           view = ok(proc1_gcs_q.render(usuario));
        }
        return view;
    }

    //Cuestionario_Gestion de Nivel de Servicios
    public static Result cuestionarioGNS() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
           Usuario usuario = Usuario.getUserByUsername(session("username"));
           view = ok(proc1_gns_q.render(usuario));
        }
        return view;
    }

    //Cuestionario_Gestion de Incidentes
    public static Result cuestionarioGIN() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
           Usuario usuario = Usuario.getUserByUsername(session("username"));
           view = ok(proc1_gin_q.render(usuario));
        }
        return view;
    }

    //GESTION DE CATALOGO DE SERVICIOS
    //Guardar datos de cuestionario y generar metricas
    public static Result evaluacionGCS() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
        
            Map<String, String[]> formData = request().body().asFormUrlEncoded();
            String[] numero_servicios = formData.get("numero_servicios");
            String[] doc_servicio = formData.get("doc_servicio");
            String numero_descritos = String.valueOf(formData.get("numero_descritos")[0]);
            String[] tipo_almacen = formData.get("tipo_almacen");
            String[] clientes_acc = formData.get("clientes_acc");
            String[] tec_acc = formData.get("tec_acc");
            String[] adm_acc = formData.get("adm_acc");
            String[] doc_completo = formData.get("doc_completo");
            String numero_cumplen = String.valueOf(formData.get("numero_cumplen")[0]);
            String periodicidad = formData.get("periodicidad")[0];
            String num_revisados = formData.get("num_revisados")[0];
            String adm_doc = formData.get("adm_doc")[0];
            String reuniones = formData.get("reuniones")[0];
            String actualizado = formData.get("actualizado")[0];

            String respuestas = new String();
            respuestas =    Arrays.toString(numero_servicios)+"_"+
                            Arrays.toString(doc_servicio)+"_"+
                            numero_descritos+"_"+
                            Arrays.toString(tipo_almacen)+"_"+
                            Arrays.toString(clientes_acc)+"_"+
                            Arrays.toString(tec_acc)+"_"+
                            Arrays.toString(adm_acc)+"_"+
                            Arrays.toString(doc_completo)+"_"+
                            numero_cumplen+"_"+
                            periodicidad+"_"+
                            num_revisados+"_"+
                            adm_doc+"_"+
                            reuniones+"_"+
                            actualizado;
            List<String[]> rptas = new ArrayList<String[]>();
            rptas.add(numero_servicios);
            rptas.add(doc_servicio);
            rptas.add(new String[]{numero_descritos});
            rptas.add(tipo_almacen);
            rptas.add(clientes_acc);
            rptas.add(tec_acc);
            rptas.add(adm_acc);
            rptas.add(doc_completo);
            rptas.add(new String[]{numero_cumplen});
            rptas.add(new String[]{periodicidad});
            rptas.add(new String[]{num_revisados});
            rptas.add(new String[]{adm_doc});
            rptas.add(new String[]{reuniones});
            rptas.add(new String[]{actualizado});
            
            //new String[]{}
            //Grabar evaluacion
            Usuario usuario = Usuario.getUserByUsername(session("username"));
            Evaluacion evaluacion = new Evaluacion(
                    usuario,
                    "Gestion de Catalogo de Servicios",
                    respuestas);
            evaluacion.save();
                    
            //Generar metricas
            float numser = 0.0f;
            for (int i=0; i < numero_servicios.length; i++) {
                if (numero_servicios[i].equals("1"))
                    numser++;
            }
            float numdes = Float.parseFloat(numero_descritos);
            if(numdes > numser)
                numdes = numser;
            float l_nivel_documentacion = 
                (numdes)/(numser) * 100.0f;
            
            float max = 0.0f;
            for (int i=0; i < tipo_almacen.length; i++) {
                if (max < Float.parseFloat(tipo_almacen[i]) )
                    max = Float.parseFloat(tipo_almacen[i]);
            }
            float l_nivel_disponibilidad_repo = (max);

            float val1 = 0.0f;
            for (int i=0; i < clientes_acc.length; i++) {
                if(clientes_acc[i].equals("1")){
                    val1 = 1.0f;
                }
            }
            float val2 = 0.0f;
            for (int i=0; i < tec_acc.length; i++) {
                if(tec_acc[i].equals("1")){
                    val2 = 1.0f;
                }
            }
            float val3 = 0.0f;
            for (int i=0; i < adm_acc.length; i++) {
                if(adm_acc[i].equals("1")){
                    val3 = 1.0f;
                }
            }
            float l_accesibilidad = val1*50 + val2*25 + val3*25;

            float sum = 0.0f;
            for (int i=0; i < doc_completo.length; i++) {
                sum += Float.parseFloat(doc_completo[i]);
            }
            float l_nivel_documentacion_mejor = (sum/8)*100.0f;

            float numcum = Float.parseFloat(numero_cumplen);
            if(numcum > numser)
                numcum = numser;
            float l_nivel_documentacion_todos =
                (numcum)/(numser) * 100.0f;

            float l_periodicidad = 
                ((Float.parseFloat(periodicidad))*(Float.parseFloat(num_revisados))) / 100.0f;

            float l_responsable = (
                (Float.parseFloat(adm_doc)) *
                (Float.parseFloat(reuniones)) *
                (Float.parseFloat(actualizado)) ) / 10000.0f;

            float l_total = 
                l_nivel_documentacion * 0.10f +
                l_nivel_disponibilidad_repo * 0.10f +
                l_accesibilidad * 0.15f +
                l_nivel_documentacion_mejor * 0.20f +
                l_nivel_documentacion_todos * 0.10f +
                l_periodicidad * 0.20f +
                l_responsable * 0.15f;

            //Evaluacion CMMI
            int scm01 = 0;
            int scm02 = 0;
            int scm03 = 0;
            int scm04 = 0;
            int scm05 = 0;
            int scm06 = 0;
            int scm07 = 0;
            //><
            if(l_nivel_documentacion == 0)
                scm01 = 1;
            else if(l_nivel_documentacion == 100)
                scm01 = 3;
            else
                scm01 = 2;

            if(l_nivel_disponibilidad_repo == 0)
                scm02 = 1;
            else if(l_nivel_disponibilidad_repo >= 50)
                scm02 = 3;
            else
                scm02 = 2;

            if(l_accesibilidad <= 25)
                scm03 = 1;
            else if(l_accesibilidad >= 51)
                scm03 = 3;
            else
                scm03 = 2;

            if(l_nivel_documentacion_mejor == 0)
                scm04 = 1;
            else if(l_nivel_documentacion_mejor >= 50)
                scm04 = 3;
            else
                scm04 = 2;

            if(l_nivel_documentacion_todos == 0)
                scm05 = 1;
            else if(l_nivel_documentacion_todos >= 50)
                scm05 = 3;
            else
                scm05 = 2;

            if(l_periodicidad <= 25)
                scm06 = 1;
            else if(l_periodicidad >= 51)
                scm06 = 3;
            else
                scm06 = 2;

            if(l_responsable <= 25)
                scm07 = 1;
            else if(l_responsable >= 51)
                scm07 = 3;
            else
                scm07 = 2;
            
            //Truncar a 2 decimales
            //DecimalFormat df = new DecimalFormat("##.##");
            //df.setRoundingMode(RoundingMode.DOWN);
            
            //Grabar a BD
            MetricaGCS metrica = new MetricaGCS(
                evaluacion,
                l_nivel_documentacion,
                l_nivel_disponibilidad_repo,
                l_accesibilidad,
                l_nivel_documentacion_mejor,
                l_nivel_documentacion_todos,
                l_periodicidad,
                l_responsable,
                l_total,
                scm01,
                scm02,
                scm03,
                scm04,
                scm05,
                scm06,
                scm07);
            metrica.save();

            //Grabar id de metrica
            evaluacion.setMetricas_gcs(metrica);
            evaluacion.save();

            //Guardar evaluacion ID en sesion
            session("evid", String.valueOf((int)(long) evaluacion.getId()));

            //Obtener y guardar ultima evaluacion SCM (1)
            if( Aux_last.getAuxLastById(Long.parseLong(session("id"))) != null){
                //existe evaluacion previa
                Aux_last last = Aux_last.getAuxLastById(Long.parseLong(session("id")));

                if(Aux_last.getAuxLastById(Long.parseLong(session("id"))).getGcs_le() != null){
                    Evaluacion evaluacion_anterior = 
                    Evaluacion.getEvaluacionById(last.getGcs_le());
                    last.setGcs_le(evaluacion.getId());
                    last.save();
                    view = ok(proc1_gcs_r.render(metrica, rptas, evaluacion_anterior));
                }else{
                    //primera evaluacion GCS
                    last.setGcs_le(evaluacion.getId());
                    last.save();
                    view = ok(proc1_gcs_r.render(metrica, rptas, null));
                }
            }else{
                //primera evaluacion de usuario
                Aux_last last = new Aux_last(usuario);
                last.setGcs_le(evaluacion.getId());
                last.save();
                view = ok(proc1_gcs_r.render(metrica, rptas, null));
            }
        
        }
        return view;
    }

        //GESTION DE NIVEL DE SERVICIO
        public static Result evaluacionGNS() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
        
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        String[] numero_servicios = formData.get("numero_servicios");
        String[] doc_condiciones = formData.get("doc_condiciones");
        String numero_sla = String.valueOf(formData.get("numero_sla")[0]);
        String[] tipo_almacen = formData.get("tipo_almacen");
        String[] doc_completo = formData.get("doc_completo");
        String numero_cumplen = String.valueOf(formData.get("numero_cumplen")[0]);
        String periodicidad = formData.get("periodicidad")[0];
        String num_revisados = formData.get("num_revisados")[0];
        String cum_totalmente = String.valueOf(formData.get("cum_totalmente")[0]);
        String cum_parcialmente = String.valueOf(formData.get("cum_parcialmente")[0]);
        String cum_no = String.valueOf(formData.get("cum_no")[0]);
        String[] informando_estado = formData.get("informando_estado");
        String num_proy = String.valueOf(formData.get("num_proy")[0]);
        String num_ser_inm = String.valueOf(formData.get("num_ser_inm")[0]);
        String num_ser_ent = String.valueOf(formData.get("num_ser_ent")[0]);
        String num_ser_con = String.valueOf(formData.get("num_ser_con")[0]);
        String num_ser_tel = String.valueOf(formData.get("num_ser_tel")[0]);
        String num_evalua = String.valueOf(formData.get("num_evalua")[0]);
        String[] nuevos_requisitos = formData.get("nuevos_requisitos");
        String[] nuevos_add = formData.get("nuevos_add");
        String[] resp_doc = formData.get("resp_doc");
        String[] resp_reu = formData.get("resp_reu");
        String[] resp_act = formData.get("resp_act");

        String respuestas = new String();
        respuestas =    Arrays.toString(numero_servicios)+"_"+
                        Arrays.toString(doc_condiciones)+"_"+
                        numero_sla+"_"+
                        Arrays.toString(tipo_almacen)+"_"+
                        Arrays.toString(doc_completo)+"_"+
                        numero_cumplen+"_"+
                        periodicidad+"_"+
                        num_revisados+"_"+
                        cum_totalmente+"_"+
                        cum_parcialmente+"_"+
                        cum_no+"_"+
                        Arrays.toString(informando_estado)+"_"+
                        num_proy+"_"+
                        num_ser_inm+"_"+
                        num_ser_ent+"_"+
                        num_ser_con+"_"+
                        num_ser_tel+"_"+
                        num_evalua+"_"+
                        Arrays.toString(nuevos_requisitos)+"_"+
                        Arrays.toString(nuevos_add)+"_"+
                        Arrays.toString(resp_doc)+"_"+
                        Arrays.toString(resp_reu)+"_"+
                        Arrays.toString(resp_act);
        List<String[]> rptas = new ArrayList<String[]>();
        rptas.add(numero_servicios);
        rptas.add(doc_condiciones);
        rptas.add(new String[]{numero_sla});
        rptas.add(tipo_almacen);
        rptas.add(doc_completo);
        rptas.add(new String[]{numero_cumplen});
        rptas.add(new String[]{periodicidad});
        rptas.add(new String[]{num_revisados});
        rptas.add(new String[]{cum_totalmente});
        rptas.add(new String[]{cum_parcialmente});
        rptas.add(new String[]{cum_no});
        rptas.add(informando_estado);
        rptas.add(new String[]{num_proy});
        rptas.add(new String[]{num_ser_inm});
        rptas.add(new String[]{num_ser_ent});
        rptas.add(new String[]{num_ser_con});
        rptas.add(new String[]{num_ser_tel});
        rptas.add(new String[]{num_evalua});
        rptas.add(nuevos_requisitos);
        rptas.add(nuevos_add);
        rptas.add(resp_doc);
        rptas.add(resp_reu);
        rptas.add(resp_act);
        
        //new String[]{}
        //Grabar evaluacion
        Usuario usuario = Usuario.getUserByUsername(session("username"));
        Evaluacion evaluacion = new Evaluacion(
                usuario,
                "Gestion del Nivel de Servicios",
                respuestas);
        evaluacion.save();
                
        //Generar metricas
        float numser = 0.0f;
        for (int i=0; i < numero_servicios.length; i++) {
            if (numero_servicios[i].equals("1"))
                numser++;
        }
        float numdes = Float.parseFloat(numero_sla);
        if(numdes > numser)
            numdes = numser;
        float l_nivel_documentacion = 
            (numdes)/(numser) * 100.0f;
        
        float max = 0.0f;
        for (int i=0; i < tipo_almacen.length; i++) {
            if (max < Float.parseFloat(tipo_almacen[i]) )
                max = Float.parseFloat(tipo_almacen[i]);
        }
        float l_nivel_disponibilidad_repo = (max);

        float sum = 0.0f;
        for (int i=0; i < doc_completo.length; i++) {
            sum += Float.parseFloat(doc_completo[i]);
        }
        float l_nivel_documentacion_mejor = (sum/10)*100.0f;

        float numcum = Float.parseFloat(numero_cumplen);
        if(numcum > numser)
            numcum = numser;
        float l_nivel_documentacion_todos =
            (numcum)/(numser) * 100.0f;

        float l_periodicidad = 
            ((Float.parseFloat(periodicidad))*(Float.parseFloat(num_revisados))) / 100.0f;

        float l_condiciones = 
            ((  (Float.parseFloat(cum_totalmente)) * 1.0f +   
                (Float.parseFloat(cum_parcialmente)) * 0.5f +
                (Float.parseFloat(cum_no)) * 0.0f
             ) / numser) * 100.0f;
        if(l_condiciones > 100.0f){
            l_condiciones = 100.0f;
        }

        float l_evaluacion_reportes = 
            ((  (Float.parseFloat(num_ser_inm)) + 
                (Float.parseFloat(num_ser_ent)) +
                (Float.parseFloat(num_ser_con)) +
                (Float.parseFloat(num_ser_tel))
                ) / (Float.parseFloat(num_proy)) ) * 100.0f;
        if(l_evaluacion_reportes > 100.0f){
            l_evaluacion_reportes = 100.0f;
        }

        float l_requisitos_nivel_servicio = 
            (   (Float.parseFloat(num_proy)) / (Float.parseFloat(num_evalua)) )
                / 
            (   (Float.parseFloat(nuevos_requisitos[0])) *
                (Float.parseFloat(nuevos_add[0]))
                  ) * 100.0f;
        if(l_requisitos_nivel_servicio > 100.0f){
            l_requisitos_nivel_servicio = 100.0f;
        }

        float l_responsable_nivel_servicio =
            (   Float.parseFloat(resp_doc[0]) *
                Float.parseFloat(resp_reu[0]) *
                Float.parseFloat(resp_act[0])  ) / 10000f;

        float l_total = (
            l_nivel_documentacion * 0.15f +
            l_nivel_disponibilidad_repo * 0.10f +
            l_nivel_documentacion_mejor * 0.10f +
            l_nivel_documentacion_todos * 0.10f +
            l_periodicidad * 0.10f +
            l_condiciones * 0.15f +
            l_evaluacion_reportes * 0.10f +
            l_requisitos_nivel_servicio * 0.10f +
            l_responsable_nivel_servicio * 0.10f
            );

        //Evaluacion CMMI
        int slm01 = 0;
        int slm02 = 0;
        int slm03 = 0;
        int slm04 = 0;
        int slm05 = 0;
        int slm06 = 0;
        int slm07 = 0;
        int slm08 = 0;
        int slm09 = 0;
        //><
        if(l_nivel_documentacion == 0)
            slm01 = 1;
        else if(l_nivel_documentacion == 100)
            slm01 = 3;
        else
            slm01 = 2;

        if(l_nivel_disponibilidad_repo <= 25)
            slm02 = 1;
        else if(l_nivel_disponibilidad_repo >= 75)
            slm02 = 3;
        else
            slm02 = 2;

        if(l_nivel_documentacion_mejor == 0)
            slm03 = 1;
        else if(l_nivel_documentacion_mejor == 100)
            slm03 = 3;
        else
            slm03 = 2;

        if(l_nivel_documentacion_todos == 0)
            slm04 = 1;
        else if(l_nivel_documentacion_todos >= 90)
            slm04 = 3;
        else
            slm04 = 2;

        if(l_periodicidad == 0)
            slm05 = 1;
        else if(l_periodicidad >= 90)
            slm05 = 3;
        else
            slm05 = 2;

        if(l_condiciones <= 25)
            slm06 = 1;
        else if(l_condiciones >= 90)
            slm06 = 3;
        else
            slm06 = 2;
        
        if(l_evaluacion_reportes <= 25)
            slm07 = 1;
        else if(l_evaluacion_reportes >= 51)
            slm07 = 3;
        else
            slm07 = 2;

        if(l_requisitos_nivel_servicio <= 50)
            slm08 = 1;
        else if(l_requisitos_nivel_servicio >= 90)
            slm08 = 3;
        else
            slm08 = 2;

        if(l_responsable_nivel_servicio <= 25)
            slm09 = 1;
        else if(l_responsable_nivel_servicio >= 50)
            slm09 = 3;
        else
            slm09 = 2;
        
        //Truncar a 2 decimales
        //DecimalFormat df = new DecimalFormat("##.##");
        //df.setRoundingMode(RoundingMode.DOWN);
        
        //Grabar a BD
        MetricaGNS metrica = new MetricaGNS(
            evaluacion,
            l_nivel_documentacion,
            l_nivel_disponibilidad_repo,
            l_nivel_documentacion_mejor,
            l_nivel_documentacion_todos,
            l_periodicidad,
            l_condiciones,
            l_evaluacion_reportes,
            l_requisitos_nivel_servicio,
            l_responsable_nivel_servicio,
            l_total,
            slm01,
            slm02,
            slm03,
            slm04,
            slm05,
            slm06,
            slm07,
            slm08,
            slm09);
        metrica.save();

        //Grabar id de metrica
        evaluacion.setMetricas_gns(metrica);
        evaluacion.save();

        //Guardar evaluacion ID en sesion
        session("evid", String.valueOf((int)(long) evaluacion.getId()));

        //Obtener y guardar ultima evaluacion SLM (2)
            if( Aux_last.getAuxLastById(Long.parseLong(session("id"))) != null){
                //existe evaluacion previa
                Aux_last last = Aux_last.getAuxLastById(Long.parseLong(session("id")));

                if(Aux_last.getAuxLastById(Long.parseLong(session("id"))).getGns_le() != null){
                    Evaluacion evaluacion_anterior = 
                        Evaluacion.getEvaluacionById(last.getGns_le());
                    last.setGns_le(evaluacion.getId());
                    last.save();
                    view = ok(proc1_gns_r.render(metrica, rptas, evaluacion_anterior));
                }else{
                    //primera evaluacion Gns
                    last.setGns_le(evaluacion.getId());
                    last.save();
                    view = ok(proc1_gns_r.render(metrica, rptas, null));
                }
            }else{
                //primera evaluacion de usuario
                Aux_last last = new Aux_last(usuario);
                last.setGns_le(evaluacion.getId());
                last.save();
                view = ok(proc1_gns_r.render(metrica, rptas, null));
            }
        }
        return view;
    }

    //GESTION DE INCIDENTES
    public static Result evaluacionGIN() {
        Result view = null;
        if(session("id") == null){
            flash("error","Su sesión se cerró. Vuelva a iniciar sesión.");
            return redirect( "/" );
        }else{
        
            Map<String, String[]> formData = request().body().asFormUrlEncoded();
            String[] existe_im = formData.get("existe_im");
            String[] formatos = formData.get("formatos");
            String[] documentados = formData.get("documentados");
            String[] almacenados = formData.get("almacenados");
            String[] herramienta = formData.get("herramienta");
            String numero_incidentes = String.valueOf(formData.get("numero_incidentes")[0]);
            String[] modelo = formData.get("modelo");
            String[] modelo_considera = formData.get("modelo_considera");
            String[] registro_campos = formData.get("registro_campos");
            String[] reporte_campos = formData.get("reporte_campos");
            String[] frecuencia = formData.get("frecuencia");
            String[] responsable = formData.get("responsable");
            String[] reuniones = formData.get("reuniones");
            String[] actualizada = formData.get("actualizada");

            String respuestas = new String();
            respuestas =    Arrays.toString(existe_im)+"_"+
                            Arrays.toString(formatos)+"_"+
                            Arrays.toString(documentados)+"_"+
                            Arrays.toString(almacenados)+"_"+
                            Arrays.toString(herramienta)+"_"+
                            numero_incidentes+"_"+
                            Arrays.toString(modelo)+"_"+
                            Arrays.toString(modelo_considera)+"_"+
                            Arrays.toString(registro_campos)+"_"+
                            Arrays.toString(reporte_campos)+"_"+
                            Arrays.toString(frecuencia)+"_"+
                            Arrays.toString(responsable)+"_"+
                            Arrays.toString(reuniones)+"_"+
                            Arrays.toString(actualizada);
            List<String[]> rptas = new ArrayList<String[]>();
            rptas.add(existe_im);
            rptas.add(formatos);
            rptas.add(documentados);
            rptas.add(almacenados);
            rptas.add(herramienta);
            rptas.add(new String[]{numero_incidentes});
            rptas.add(modelo);
            rptas.add(modelo_considera);
            rptas.add(registro_campos);
            rptas.add(reporte_campos);
            rptas.add(frecuencia);
            rptas.add(responsable);
            rptas.add(reuniones);
            rptas.add(actualizada);
            //14 var
            
            //new String[]{}
            //Grabar evaluacion
            Usuario usuario = Usuario.getUserByUsername(session("username"));
            Evaluacion evaluacion = new Evaluacion(
                    usuario,
                    "Gestion de Incidentes",
                    respuestas);
            evaluacion.save();
                    
            //Generar metricas
            float numfor = 0.0f;
            for (int i=0; i < formatos.length; i++) {
                if (formatos[i].equals("1"))
                    numfor++;
            }
            float l_procedimientos = 
                ( numfor * 20.0f ) /
                (   Float.parseFloat(documentados[0]) *
                    Float.parseFloat(almacenados[0])   );
            
            float l_herramienta_gin =
                Float.parseFloat(herramienta[0]) * 
                Float.parseFloat(numero_incidentes);

            float modcon = 0.0f;
            for (int i=0; i < modelo_considera.length; i++) {
                if (modelo_considera[i].equals("1"))
                    modcon++;
            }
            float l_clasificacion =
                Float.parseFloat(modelo[0]) *
                modcon * 25.0f;

            float regcam = 0.0f;
            for (int i=0; i < registro_campos.length; i++) {
                if (registro_campos[i].equals("1"))
                    regcam++;
            }
            float l_detalle_registro = regcam * 10.0f;

            float repcam = 0.0f;
            for (int i=0; i < reporte_campos.length; i++) {
                if (reporte_campos[i].equals("1"))
                    repcam++;
            }
            float l_reportes = repcam * 12.5f;

            float l_periodicidad = 
                (   Float.parseFloat(numero_incidentes) *
                    Float.parseFloat(frecuencia[0])     ) / 100.0f;

            float l_responsable = 
                (   Float.parseFloat(responsable[0]) *
                    Float.parseFloat(reuniones[0]) *
                    Float.parseFloat(actualizada[0]) ) / 10000.0f;

            float l_total = (
                l_procedimientos * 0.20f +
                l_herramienta_gin * 0.15f +
                l_clasificacion * 0.20f +
                l_detalle_registro * 0.15f +
                l_reportes * 0.10f +
                l_periodicidad * 0.10f +
                l_responsable * 0.10f
                );

            //Evaluacion CMMI
            int im01 = 0;
            int im02 = 0;
            int im03 = 0;
            int im04 = 0;
            int im05 = 0;
            int im06 = 0;
            int im07 = 0;
            //><

            if(l_procedimientos <= 25)
                im01 = 1;
            else if(l_procedimientos >= 50)
                im01 = 3;
            else
                im01 = 2;

            if(l_herramienta_gin == 0)
                im02 = 1;
            else if(l_herramienta_gin >= 75)
                im02 = 3;
            else
                im02 = 2;

            if(l_clasificacion <= 25)
                im03 = 1;
            else if(l_clasificacion >= 50)
                im03 = 3;
            else
                im03 = 2;

            if(l_detalle_registro <= 30)
                im04 = 1;
            else if(l_detalle_registro >= 80)
                im04 = 3;
            else
                im04 = 2;

            if(l_reportes <= 25)
                im05 = 1;
            else if(l_reportes >= 75)
                im05 = 3;
            else
                im05 = 2;

            if(l_periodicidad <= 25)
                im06 = 1;
            else if(l_periodicidad >= 75)
                im06 = 3;
            else
                im06 = 2;
            
            if(l_responsable <= 25)
                im07 = 1;
            else if(l_responsable >= 50)
                im07 = 3;
            else
                im07 = 2;
            
            //Truncar a 2 decimales
            //DecimalFormat df = new DecimalFormat("##.##");
            //df.setRoundingMode(RoundingMode.DOWN);
            
            //Grabar a BD
            MetricaGIN metrica = new MetricaGIN(
                evaluacion,
                l_procedimientos,
                l_herramienta_gin,
                l_clasificacion,
                l_detalle_registro,
                l_reportes,
                l_periodicidad,
                l_responsable,
                l_total,
                im01,
                im02,
                im03,
                im04,
                im05,
                im06,
                im07
                );
            metrica.save();

            //Grabar id de metrica
            evaluacion.setMetricas_gin(metrica);
            evaluacion.save();

            //Guardar evaluacion ID en sesion
            session("evid", String.valueOf((int)(long) evaluacion.getId()));

            //Obtener y guardar ultima evaluacion IM (1)
            if( Aux_last.getAuxLastById(Long.parseLong(session("id"))) != null){
                //existe evaluacion previa
                Aux_last last = Aux_last.getAuxLastById(Long.parseLong(session("id")));

                if(Aux_last.getAuxLastById(Long.parseLong(session("id"))).getGin_le() != null){
                    Evaluacion evaluacion_anterior = 
                    Evaluacion.getEvaluacionById(last.getGin_le());
                    last.setGin_le(evaluacion.getId());
                    last.save();
                    view = ok(proc1_gin_r.render(metrica, rptas, evaluacion_anterior));
                }else{
                    //primera evaluacion Gin
                    last.setGin_le(evaluacion.getId());
                    last.save();
                    view = ok(proc1_gin_r.render(metrica, rptas, null));
                }
            }else{
                //primera evaluacion de usuario
                Aux_last last = new Aux_last(usuario);
                last.setGin_le(evaluacion.getId());
                last.save();
                view = ok(proc1_gin_r.render(metrica, rptas, null));
            }
        }
        return view;
    }

    //Métodos para Obtener datos para gráficos
    //TODOS FUNCIONAN CON LLAMADAS AJAX

    //Javascript Routing (obligatorio)
    public static Result javascriptRoutes() {
    response().setContentType("text/javascript");
    return ok(
        Routes.javascriptRouter("myJsRoutes",
            routes.javascript.Procesos.getEvaluacion(),
            routes.javascript.Procesos.getProgresoEvaluaciones(),
            routes.javascript.Procesos.getDistribucionEvaluaciones())
        );
    }

    //Llamada de AJAX para datos de graficos CMMI (para cada proceso)
    public static Result getEvaluacion() {
        Result view = null;
        //obtener la ultima evaluacion guardada en sesion
        if(session("evid") == null){
            view = ok();
        }else{
            Long evid = Long.parseLong(session("evid"));
            Evaluacion eval = Evaluacion.getEvaluacionById(evid);
            System.out.println("Ev. ID: "+evid);
            //por tipo de evaluacion
            if(eval.getTipo().equals("Gestion de Catalogo de Servicios")){
                MetricaGCS met = eval.getMetricas_gcs();
                //System.out.println("#"+evid);
                
                //crear el los objetos json con los datos de evaluacion
                JSONObject jo1 = new JSONObject();
                JSONObject jo2 = new JSONObject();
                JSONObject jo3 = new JSONObject();
                JSONObject jo4 = new JSONObject();
                JSONObject jo5 = new JSONObject();
                JSONObject jo6 = new JSONObject();
                JSONObject jo7 = new JSONObject();
                try {
                    jo1.put("year", "SCM01");
                    jo1.put("value", met.getScm01());
                    jo2.put("year", "SCM02");
                    jo2.put("value", met.getScm02());
                    jo3.put("year", "SCM03");
                    jo3.put("value", met.getScm03());
                    jo4.put("year", "SCM04");
                    jo4.put("value", met.getScm04());
                    jo5.put("year", "SCM05");
                    jo5.put("value", met.getScm05());
                    jo6.put("year", "SCM06");
                    jo6.put("value", met.getScm06());
                    jo7.put("year", "SCM07");
                    jo7.put("value", met.getScm07());
                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                
                //Agregar objetos al array
                JSONArray ja = new JSONArray();
                ja.put(jo1);
                ja.put(jo2);
                ja.put(jo3);
                ja.put(jo4);
                ja.put(jo5);
                ja.put(jo6);
                ja.put(jo7);
                
                //System.out.println(ja);
                
                view = ok(ja.toString());
            }else if(eval.getTipo().equals("Gestion del Nivel de Servicios")){
                MetricaGNS met = eval.getMetricas_gns();
                //System.out.println("#"+evid);
                
                //crear el los objetos json con los datos de evaluacion
                JSONObject jo1 = new JSONObject();
                JSONObject jo2 = new JSONObject();
                JSONObject jo3 = new JSONObject();
                JSONObject jo4 = new JSONObject();
                JSONObject jo5 = new JSONObject();
                JSONObject jo6 = new JSONObject();
                JSONObject jo7 = new JSONObject();
                JSONObject jo8 = new JSONObject();
                JSONObject jo9 = new JSONObject();
                try {
                    jo1.put("year", "SLM01");
                    jo1.put("value", met.getSlm01());
                    jo2.put("year", "SLM02");
                    jo2.put("value", met.getSlm02());
                    jo3.put("year", "SLM03");
                    jo3.put("value", met.getSlm03());
                    jo4.put("year", "SLM04");
                    jo4.put("value", met.getSlm04());
                    jo5.put("year", "SLM05");
                    jo5.put("value", met.getSlm05());
                    jo6.put("year", "SLM06");
                    jo6.put("value", met.getSlm06());
                    jo7.put("year", "SLM07");
                    jo7.put("value", met.getSlm07());
                    jo8.put("year", "SLM08");
                    jo8.put("value", met.getSlm08());
                    jo9.put("year", "SLM09");
                    jo9.put("value", met.getSlm09());
                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                
                //Agregar objetos al array
                JSONArray ja = new JSONArray();
                ja.put(jo1);
                ja.put(jo2);
                ja.put(jo3);
                ja.put(jo4);
                ja.put(jo5);
                ja.put(jo6);
                ja.put(jo7);
                ja.put(jo8);
                ja.put(jo9);
                
                //System.out.println(ja);
                
                view = ok(ja.toString());
            }else{
                //GIN
                MetricaGIN met = eval.getMetricas_gin();
                //System.out.println("#"+evid);
                
                //crear el los objetos json con los datos de evaluacion
                JSONObject jo1 = new JSONObject();
                JSONObject jo2 = new JSONObject();
                JSONObject jo3 = new JSONObject();
                JSONObject jo4 = new JSONObject();
                JSONObject jo5 = new JSONObject();
                JSONObject jo6 = new JSONObject();
                JSONObject jo7 = new JSONObject();
                try {
                    jo1.put("year", "IM01");
                    jo1.put("value", met.getIm01());
                    jo2.put("year", "IM02");
                    jo2.put("value", met.getIm02());
                    jo3.put("year", "IM03");
                    jo3.put("value", met.getIm03());
                    jo4.put("year", "IM04");
                    jo4.put("value", met.getIm04());
                    jo5.put("year", "IM05");
                    jo5.put("value", met.getIm05());
                    jo6.put("year", "IM06");
                    jo6.put("value", met.getIm06());
                    jo7.put("year", "IM07");
                    jo7.put("value", met.getIm07());
                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                
                //Agregar objetos al array
                JSONArray ja = new JSONArray();
                ja.put(jo1);
                ja.put(jo2);
                ja.put(jo3);
                ja.put(jo4);
                ja.put(jo5);
                ja.put(jo6);
                ja.put(jo7);
                
                //System.out.println(ja);
                
                view = ok(ja.toString());
            }
        }
        return view;
    }

    //PROGRESO DE EVALUACIONES
    public static Result getProgresoEvaluaciones() {
        Result view = null;
        //obtener la ultima evaluacion guardada en sesion
        if(session("id") == null){
            view = ok();
        }else{
            List<Evaluacion> evaluaciones =
                Evaluacion.getEvaluacionAll(Long.parseLong(session("id")));
            //System.out.println("Ev. ID: "+evid);

            if(evaluaciones != null){
                
                //crear el los objetos json con los datos de evaluacion
                JSONObject jo1 = new JSONObject();
                JSONObject jo2 = new JSONObject();
                JSONObject jo3 = new JSONObject();
                JSONObject jo4 = new JSONObject();
                JSONObject jo5 = new JSONObject();
                
                int size = evaluaciones.size();

                float gcs_v5 = 0.0f;
                float gns_v5 = 0.0f;
                float gin_v5 = 0.0f;
                float gcs_v4 = 0.0f;
                float gns_v4 = 0.0f;
                float gin_v4 = 0.0f;
                float gcs_v3 = 0.0f;
                float gns_v3 = 0.0f;
                float gin_v3 = 0.0f;
                float gcs_v2 = 0.0f;
                float gns_v2 = 0.0f;
                float gin_v2 = 0.0f;
                float gcs_v1 = 0.0f;
                float gns_v1 = 0.0f;
                float gin_v1 = 0.0f;
                
                
                if(size >= 5){
                    if(evaluaciones.get(4).getMetricas_gcs() != null){
                        gcs_v5 = evaluaciones.get(4).getMetricas_gcs().getTotal();
                    }
                    if(evaluaciones.get(4).getMetricas_gns() != null){
                        gns_v5 = evaluaciones.get(4).getMetricas_gns().getTotal();
                    }
                    if(evaluaciones.get(4).getMetricas_gin() != null){
                        gin_v5 = evaluaciones.get(4).getMetricas_gin().getTotal();
                    }
                }

                if(size >= 4){
                    if(evaluaciones.get(3).getMetricas_gcs() != null){
                        gcs_v4 = evaluaciones.get(3).getMetricas_gcs().getTotal();
                    }
                    if(evaluaciones.get(3).getMetricas_gns() != null){
                        gns_v4 = evaluaciones.get(3).getMetricas_gns().getTotal();
                    }
                    if(evaluaciones.get(3).getMetricas_gin() != null){
                        gin_v4 = evaluaciones.get(3).getMetricas_gin().getTotal();
                    }
                }

                if(size >= 3){
                    if(evaluaciones.get(2).getMetricas_gcs() != null){
                        gcs_v3 = evaluaciones.get(2).getMetricas_gcs().getTotal();
                    }
                    if(evaluaciones.get(2).getMetricas_gns() != null){
                        gns_v3 = evaluaciones.get(2).getMetricas_gns().getTotal();
                    }
                    if(evaluaciones.get(2).getMetricas_gin() != null){
                        gin_v3 = evaluaciones.get(2).getMetricas_gin().getTotal();
                    }
                }

                if(size >= 2){
                    if(evaluaciones.get(1).getMetricas_gcs() != null){
                        gcs_v2 = evaluaciones.get(1).getMetricas_gcs().getTotal();
                    }
                    if(evaluaciones.get(1).getMetricas_gns() != null){
                        gns_v2 = evaluaciones.get(1).getMetricas_gns().getTotal();
                    }
                    if(evaluaciones.get(1).getMetricas_gin() != null){
                        gin_v2 = evaluaciones.get(1).getMetricas_gin().getTotal();
                    }
                }

                if(size >= 1){
                    if(evaluaciones.get(0).getMetricas_gcs() != null){
                        gcs_v1 = evaluaciones.get(0).getMetricas_gcs().getTotal();
                    }
                    if(evaluaciones.get(0).getMetricas_gns() != null){
                        gns_v1 = evaluaciones.get(0).getMetricas_gns().getTotal();
                    }
                    if(evaluaciones.get(0).getMetricas_gin() != null){
                        gin_v1 = evaluaciones.get(0).getMetricas_gin().getTotal();
                    }
                }
                
                try {
                    jo5.put("evaluacion", 5);
                    jo5.put("gcs", gcs_v5);
                    jo5.put("gns", gns_v5);
                    jo5.put("gin", gin_v5);

                    jo4.put("evaluacion", 4);
                    jo4.put("gcs", gcs_v4);
                    jo4.put("gns", gns_v4);
                    jo4.put("gin", gin_v4);

                    jo3.put("evaluacion", 3);
                    jo3.put("gcs", gcs_v3);
                    jo3.put("gns", gns_v3);
                    jo3.put("gin", gin_v3);

                    jo2.put("evaluacion", 2);
                    jo2.put("gcs", gcs_v2);
                    jo2.put("gns", gns_v2);
                    jo2.put("gin", gin_v2);

                    jo1.put("evaluacion", 1);
                    jo1.put("gcs", gcs_v1);
                    jo1.put("gns", gns_v1);
                    jo1.put("gin", gin_v1);
                    
                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                
                //Agregar objetos al array
                JSONArray ja = new JSONArray();
                ja.put(jo1);
                ja.put(jo2);
                ja.put(jo3);
                ja.put(jo4);
                ja.put(jo5);
                                
                //System.out.println(ja);
                //System.out.println("HOLIHOLI_pri");
                view = ok(ja.toString());
            }else{
                //System.out.println("HOLIHOLI");
                view = ok();
            }
        }
        return view;
    }

    public static Result getDistribucionEvaluaciones() {
        Result view = null;
        if(session("id") == null){
            view = ok();
        }else{
            List<Evaluacion> evaluaciones =
                Evaluacion.getEvaluacionAll(Long.parseLong(session("id")));
            if(evaluaciones != null){
                int size = evaluaciones.size();
                int c_gcs = 0;
                int c_gns = 0;
                int c_gin = 0;
                for(int i = 0; i < size; i++){
                    if(evaluaciones.get(i).getMetricas_gcs() != null){
                        c_gcs++;
                    }
                    if(evaluaciones.get(i).getMetricas_gns() != null){
                        c_gns++;
                    }
                    if(evaluaciones.get(i).getMetricas_gin() != null){
                        c_gin++;
                    }
                }

                JSONObject jo1 = new JSONObject();
                JSONObject jo2 = new JSONObject();
                JSONObject jo3 = new JSONObject();

                try {
                    jo1.put("label", "Gestión de Catálogo de Servicios");
                    jo1.put("value", c_gcs);
                    jo2.put("label", "Gestión del Nivel de Servicio");
                    jo2.put("value", c_gns);
                    jo3.put("label", "Gestión de Incidentes");
                    jo3.put("value", c_gin);
                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                JSONArray ja = new JSONArray();
                ja.put(jo1);
                ja.put(jo2);
                ja.put(jo3);

                view = ok(ja.toString());
            }else{
                view = ok();
            }
        }
        return view;
    }

}
