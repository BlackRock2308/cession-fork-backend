package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString @SuperBuilder
public class ParametrageDecoteDTO implements Serializable {

    private Long idDecote;

    private Long borneInf;

    private Long borneSup;

    private Double decoteValue;

    private Set<ConventionDto> conventions = new HashSet<>();

}
