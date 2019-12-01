package com.home.persistense.model;

import com.home.persistense.model.enumaration.Currency;
import com.home.persistense.model.enumaration.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "changeDescription",
                procedureName = "update_country_description",
                parameters = {
                        @StoredProcedureParameter(name = "p_id",
                                type = Integer.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(name = "p_description",
                                type = String.class,
                                mode = ParameterMode.IN)
                }
        )
)

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "COUNTRY")
public class CountryEntity extends CommonInfoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_country")
    @SequenceGenerator(name = "gen_country", sequenceName = "country_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY")
    private Currency currency;


    @Enumerated(EnumType.STRING)
    @Column(name = "LANGUAGE")
    private Language language;

    @Column(name = "DESCRIPTION")
    private String description;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY)
    private List<CityEntity> cities = new ArrayList<CityEntity>();

    @Override
    public String toString() {
        return "CountryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency=" + currency +
                ", language=" + language +
                ", description='" + description + '\'' +
                '}';
    }
}
