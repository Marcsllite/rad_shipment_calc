package com.marcsllite.dao;

import com.marcsllite.model.db.A1Model;

public class A1DaoJpa extends AbstractDao<A1Model, String> {
    public static final String A1_CSV_PATH = "classpath:csv/A1(TBq).csv";

    @Override
    void createTableFromCSV(String csvPath) {
        getEntityManager()
            .createNamedQuery(A1Model.CREATE_TABLE)
            .setParameter("path", A1_CSV_PATH);
    }
}
