package sn.modelsis.cdmp.mappers;

import java.util.List;


public interface GenericMapper<E, D> {

    E asEntity(D d);

    D asDTO(E e);

    List<E> asEntityList(List<D> dList);

    List<D> asDTOList(List<E> eList);

}