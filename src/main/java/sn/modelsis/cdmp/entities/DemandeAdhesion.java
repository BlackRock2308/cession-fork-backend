package sn.modelsis.cdmp.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

