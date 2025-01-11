package bada_bdbt_project.SpringApplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Uklad_liniiDAO {
    /* Import org.springframework.jd....Template */
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public Uklad_liniiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Import java.util.List */
    public List<Uklad_linii> list(){
        String sql = "SELECT * FROM Uklad_linii";
        List<Uklad_linii> listUklad_linii = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Uklad_linii.class));
        return listUklad_linii;
    }
    /* Insert – wstawianie nowego wiersza do bazy */
    public void save(Uklad_linii uklad_linii) {
    }
    /* Read – odczytywanie danych z bazy */
    public Uklad_linii get(int id) {
        return null;
    }
    /* Update – aktualizacja danych */
    public void update(Uklad_linii uklad_linii) {
    }
    /* Delete – wybrany rekord z danym id */
    public void delete(int id) {
    }

}


