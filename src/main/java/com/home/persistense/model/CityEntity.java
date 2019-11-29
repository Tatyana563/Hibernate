package com.home.persistense.model;

import com.home.persistense.model.enumaration.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name="changePopulation",
                procedureName = "update_city_population",
                parameters = {
                        @StoredProcedureParameter(name="p_id",
                                type=Integer.class,
                                mode=ParameterMode.IN),
                        @StoredProcedureParameter(name="p_population",
                                type = Integer.class,
                                mode=ParameterMode.IN)
                }
        )
)

@Getter
@Setter
@Entity
@Table(name = "city")
@NoArgsConstructor
public class CityEntity extends CommonInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city")
    @SequenceGenerator(name = "seq_city", sequenceName = "city_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private Type type;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CAPITAL")
    private Boolean capital;

    @Column(name = "COUNTRY_ID", insertable = false, updatable = false)
    private Integer countryId;

    @ManyToOne(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="COUNTRY_ID")
    private CountryEntity country;

    @Override
    public String toString() {
        return "CityEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", capital=" + capital +
                ", countryId=" + countryId +
                ", country=" + country +
                '}';
    }

}
