package com.solvd.airport.persistence;

import org.apache.ibatis.annotations.Param;

public interface ContactDAO<T> extends AbstractDAO<T> {
    int createPersonAssociation(
            @Param("personInfoId") int personInfoId,
            @Param("contactId") int contactId
            );
}
