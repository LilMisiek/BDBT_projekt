package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    // Pobieranie wszystkich linii
    public List<Linie> list() {
        String sql = "SELECT nr_linii AS nrLinii, " +
                "nr_przedsiebiorstwa AS nrPrzedsiebiorstwa, " +
                "czy_nocna AS czyNocna, " +
                "liczba_przystankow AS liczbaPrzystankow " +
                "FROM linie";
        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Linie.class));
    }

    // Implementacja innych metod CRUD (save, get, update, delete)
}
