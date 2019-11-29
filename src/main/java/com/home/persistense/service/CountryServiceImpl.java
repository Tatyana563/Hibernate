package com.home.persistense.service;

import com.home.persistense.dao.CountryDao;
import com.home.persistense.dao.CountryDaoImpl;
import com.home.persistense.model.CountryEntity;
import com.home.persistense.model.enumaration.Currency;
import com.home.persistense.model.enumaration.FilterKey;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class CountryServiceImpl implements CountryService {

    private CountryDao countryDao = CountryDaoImpl.getInstance();

    @Override
    public void updateCountryDescription(int townId, String description) {
        countryDao.updateCountryDescription(townId, description);
    }

    @Override
    public void updateCountryPopulation(int townId, Integer population) {
        countryDao.updateCountryPopulation(townId, population);
    }

    @Override
    public Collection<CountryEntity> findByCurrency(Currency currency) {
        return countryDao.findByCurrency(currency);
    }

    @Override
    public Collection<CountryEntity> findAllByFilter(Map<FilterKey, Object> filter) {
        return countryDao.findAllByFilter(filter);
    }

    @Override
    public void createOrUpdate(CountryEntity entity) {
        countryDao.save(entity);
    }

    @Override
    public Collection<CountryEntity> findAll() {
        return countryDao.findAll();
    }

    @Override
    public Optional<CountryEntity> findById(int id) {
        Optional<CountryEntity> country = countryDao.findById(id);
        return country;
    }

    @Override
    public void remove(int id) {
        CountryEntity countryEntity = findById(id).get();
        countryDao.delete(countryEntity);
    }
}
