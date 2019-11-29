import com.home.persistense.model.CityEntity;
import com.home.persistense.model.CountryEntity;
import com.home.persistense.model.enumaration.Currency;
import com.home.persistense.model.enumaration.FilterKey;
import com.home.persistense.model.enumaration.Language;
import com.home.persistense.model.enumaration.Type;
import com.home.persistense.service.CityService;
import com.home.persistense.service.CityServiceImpl;
import com.home.persistense.service.CountryService;
import com.home.persistense.service.CountryServiceImpl;
import com.home.persistense.util.HibernateConnectionUtil;
import org.hamcrest.CoreMatchers;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class DemoApplicationTest {

    private Session session = null;
    private CityService cityService = new CityServiceImpl();
    private CountryService countryService = new CountryServiceImpl();

    @Before
    public void init() {
        session = HibernateConnectionUtil.getConnection().openSession();
    }


    @Test
    public void testCity() {
        CityEntity city = new CityEntity();
        city.setName("Drezden");
        city.setPopulation(70);
        city.setSquare(200.21);
        city.setDescription("Dresden has a long history as the capital and royal residence for the Electors and Kings of Saxony");
        city.setType(Type.KNOWLEDGE_CAPITAL);
        city.setCountryId(1);
        // cityService.createOrUpdate(city);// ok
        //System.out.println(cityService.findAll()); - ok
        // cityService.updateCityPopulation(10,250); - ok
         cityService.remove(30);
        // System.out.println(cityService.findById(14)); ok
//        cityService.updateCityType(14, Type.GLOBAL_GIANT);
//        CityEntity cityEntity = cityService.findById(14).get();
//        System.out.println(cityEntity);
//        Assert.assertTrue(cityEntity.getType() == Type.GLOBAL_GIANT);
//        Assert.assertEquals(cityEntity.getType(), Type.GLOBAL_GIANT);
//        Assert.assertThat(cityEntity.getType(), CoreMatchers.is(Type.GLOBAL_GIANT));

    }

    @Test
    public void testCountry() {
        CountryEntity country = new CountryEntity();
        country.setId(1);
        country.setName("Germany");
        country.setCurrency(Currency.DM);
        country.setLanguage(Language.German);
        country.setDescription("a nice country");
        country.setPopulation(1000);
        country.setSquare(250.36);
        CityEntity cityEntity = cityService.findById(14).get();
        cityEntity.setCountry(country);
        cityService.createOrUpdate(cityEntity);
        //countryService.createOrUpdate(country);
        System.out.println(countryService.findById(1).get().getCities());
        // countryService.updateCountryDescription(1,"a marvelous country"); - ok
        // System.out.println(cityService.findAll()); - ok
        // countryService.remove(1);
        // countryService.updateCountryPopulation(1,5000);-ok
        Collection<CountryEntity> savedEnt = countryService.findByCurrency(Currency.DM);
        System.out.println(savedEnt);
        savedEnt.forEach(item->System.out.println(item.getCities()));

    }
    @Test
    public void testCountryFilter() {
        Map<FilterKey, Object> filter = new HashMap<>();
        filter.put(FilterKey.CITY_TYPE,Type.KNOWLEDGE_CAPITAL);

        System.out.println(countryService.findAllByFilter(filter));
    }
}
