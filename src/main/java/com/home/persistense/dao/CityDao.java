package com.home.persistense.dao;

import com.home.persistense.model.CityEntity;
import com.home.persistense.model.enumaration.Type;

public interface CityDao extends GenericDao<CityEntity, Integer> {
    void updateCityPopulation(int townId, int townPopulation);
    public void updateCityType(int townId, Type type);
}
