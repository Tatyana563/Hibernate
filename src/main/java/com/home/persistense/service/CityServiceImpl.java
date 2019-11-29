package com.home.persistense.service;

import com.home.persistense.dao.CityDao;
import com.home.persistense.dao.CityDaoImpl;
import com.home.persistense.model.CityEntity;
import com.home.persistense.model.CountryEntity;
import com.home.persistense.model.enumaration.Currency;
import com.home.persistense.model.enumaration.Type;

import java.util.Collection;
import java.util.Optional;

public class CityServiceImpl implements CityService {

    private CityDao cityDao = CityDaoImpl.getInstance();

    @Override
    public void updateCityPopulation(int townId, int townPopulation) {
        cityDao.updateCityPopulation(townId, townPopulation);
    }

    @Override
    public void createOrUpdate(CityEntity entity) {

        cityDao.save(entity);
    }

    @Override
    public Collection<CityEntity> findAll() {
        return cityDao.findAll();
    }

    @Override
    public Optional<CityEntity> findById(int id) {
        Optional<CityEntity> city = cityDao.findById(id);
        return Optional.ofNullable(city.orElse(new CityEntity()));
    }

    @Override
    public void remove(int id) {
        CityEntity cityEntity = findById(id).get();
        cityDao.delete(cityEntity);
    }

    @Override
    public void updateCityType(int townId, Type type) {
        cityDao.updateCityType(townId,type);
    }


}
