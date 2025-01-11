package bada_bdbt_project.SpringApplication;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PrzystankiDAO {
    /* Import org.springframework.jd....Template */
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public PrzystankiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Import java.util.List */
    public List<Przystanki> list(){
        String sql = "SELECT * FROM Przystanki";
        List<Przystanki> listPrzystanki = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Przystanki.class));
        return listPrzystanki;
    }
    /* Insert – wstawianie nowego wiersza do bazy */
    public void save(Przystanki przystanki) {
    }
    /* Read – odczytywanie danych z bazy */
    public Przystanki get(int id) {
        return null;
    }
    /* Update – aktualizacja danych */
    public void update(Przystanki przystanki) {
    }
    /* Delete – wybrany rekord z danym id */
    public void delete(int id) {
    }

}
