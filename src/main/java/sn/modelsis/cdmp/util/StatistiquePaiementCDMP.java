package sn.modelsis.cdmp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StatistiquePaiementCDMP {
    private double decote;
    private double montantCreance;
    private double rembourse;
    private  double solde;



    public  void jsonToObeject(String json)  {
        json = json.replaceAll("null", "0.0");
        String [] objs = json.split(",");
        List<Double> donne = new ArrayList<>();
        for(String obj : objs ){
            donne.add(Double.parseDouble(obj));
        }
        /*if(statistiquePaiementCDMP!=null){
            this.setDecote(statistiquePaiementCDMP.getDecote());
            this.setRembourse(statistiquePaiementCDMP.getRembourse());
            this.setMontantCreance(statistiquePaiementCDMP.getMontantCreance());
            this.setSolde(statistiquePaiementCDMP.getSolde());
        }*/

    }



}
