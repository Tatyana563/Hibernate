package com.home.persistense.service;

import com.home.persistense.model.CityEntity;
import com.home.persistense.model.CountryEntity;
import com.home.persistense.model.enumaration.Currency;
import com.home.persistense.model.enumaration.Type;

import java.util.Collection;

public interface CityService extends GenericService<CityEntity, Integer> {
    void updateCityPopulation(int townId, int townPopulation);

    public void updateCityType(int townId, Type type);

}
