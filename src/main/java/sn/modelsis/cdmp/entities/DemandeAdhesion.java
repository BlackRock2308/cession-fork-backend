package sn.modelsis.cdmp.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString

@Table(name = "demandeadhesion")
public class DemandeAdhesion extends Demande {

    @Column(name = "datedemandeAdhesion")
    private Date dateDemandeAdhesion;



}

