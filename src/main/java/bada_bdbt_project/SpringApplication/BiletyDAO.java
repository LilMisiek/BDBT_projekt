package bada_bdbt_project.SpringApplication;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BiletyDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BiletyDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Bilety> list(){
        String sql = "SELECT * FROM Bilety";
        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Bilety.class));
    }
    public void save(Bilety bilety) {
        String sql = "INSERT INTO Bilety (NAZWA, CZAS_WAZNOSCI, CENA, NR_PRZEDSIEBIORSTWA, IMIE, NAZWISKO, RODZAJ) " +
                "VALUES (:nazwa, :czasWaznosci, :cena, :nrPrzedsiebiorstwa, :imie, :nazwisko, :rodzaj)";

        bilety.setNrPrzedsiebiorstwa(1);

        MapSqlParameterSource insertParams = new MapSqlParameterSource();
        insertParams.addValue("nazwa", bilety.getNazwa());
        insertParams.addValue("czasWaznosci", bilety.getCzasWaznosci());
        insertParams.addValue("cena", bilety.getCena());
        insertParams.addValue("nrPrzedsiebiorstwa", bilety.getNrPrzedsiebiorstwa());
        insertParams.addValue("imie", bilety.getImie());
        insertParams.addValue("nazwisko", bilety.getNazwisko());
        insertParams.addValue("rodzaj", bilety.getRodzaj());

        namedParameterJdbcTemplate.update(sql, insertParams);
    }

    public int getLastInsertedId() {
        String sql = "SELECT MAX(NR_BILETU) FROM Bilety";
        return namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(sql, Integer.class);
    }
    public Bilety get(int nrBiletu) {
        String sql = "SELECT * FROM Bilety WHERE NR_BILETU = :nrBiletu";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nrBiletu", nrBiletu);
        return namedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Bilety.class));
    }




    public void update(Bilety bilety) {
    }
    public void delete(int id) {
    }

}
