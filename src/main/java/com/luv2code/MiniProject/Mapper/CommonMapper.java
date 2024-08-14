package com.luv2code.MiniProject.Mapper;

import java.util.List;

public interface CommonMapper <E, D>{
    E toEntity(D d);

    D toDto(E e);

    List<E> toListE(List<D> list);

    List<D> toListD(List<E> list);
}
