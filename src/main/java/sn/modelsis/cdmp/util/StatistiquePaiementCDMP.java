package sn.modelsis.cdmp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
@Setter
public class StatistiquePaiementCDMP {
    private double decote;
    private double montantCreance;
    private double rembourse;
    private  double solde;



    public  void jsonToObeject(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StatistiquePaiementCDMP statistiquePaiementCDMP = objectMapper.readValue(json, StatistiquePaiementCDMP.class);
        this.setDecote(statistiquePaiementCDMP.getDecote());
        this.setRembourse(statistiquePaiementCDMP.getRembourse());
        this.setMontantCreance(statistiquePaiementCDMP.getMontantCreance());
        this.setSolde(statistiquePaiementCDMP.getSolde());

    }



}
