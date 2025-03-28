package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LinieDAO {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public LinieDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Linie> list() {
        String sql = "SELECT nr_linii AS nrLinii, " +
                "nr_przedsiebiorstwa AS nrPrzedsiebiorstwa, " +
                "czy_nocna AS czyNocna, " +
                "liczba_przystankow AS liczbaPrzystankow " +
                "FROM linie";
        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Linie.class));
    }


    public void save(Linie linia) {

        String sql = """
        INSERT INTO linie (nr_linii, nr_przedsiebiorstwa, czy_nocna)
        VALUES (:nrLinii, :nrPrzeds, :czyNocna)
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nrLinii", linia.getNrLinii())
                .addValue("nrPrzeds", linia.getNrPrzedsiebiorstwa())
                .addValue("czyNocna", linia.getCzyNocna());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public boolean existsByNrLinii(String nrLinii) {
        String sql = "SELECT COUNT(*) FROM linie WHERE nr_linii = :nrLinii";
        MapSqlParameterSource params = new MapSqlParameterSource("nrLinii", nrLinii);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

}
