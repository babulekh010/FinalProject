package kg.megacom.NatvProject.mappers;

import java.util.List;

public interface BaseMapper<E,D>{
    D toDto(E e);
    E toEntity(D d);
    List<D> toDTOList(List<E> eList);
    List<E> toEntityList(List<D> dList);
}
