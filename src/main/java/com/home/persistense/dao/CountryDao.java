package com.home.persistense.dao;

import com.home.persistense.model.CountryEntity;
import com.home.persistense.model.enumaration.Currency;
import com.home.persistense.model.enumaration.FilterKey;

import java.util.Collection;
import java.util.Map;

public interface CountryDao extends GenericDao<CountryEntity, Integer> {
    void updateCountryDescription(int townId, String description);

    void updateCountryPopulation(int townId, Integer population);

    public Collection<CountryEntity> findByCurrency(Currency currency);

    public Collection<CountryEntity> findAllByFilter(Map<FilterKey, Object> filter);
}
