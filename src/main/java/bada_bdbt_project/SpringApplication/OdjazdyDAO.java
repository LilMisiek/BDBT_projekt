package bada_bdbt_project.SpringApplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OdjazdyDAO {
    /* Import org.springframework.jd....Template */
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public OdjazdyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Import java.util.List */
    public List<Odjazdy> list(){
        String sql = "SELECT * FROM Odjazdy";
        List<Odjazdy> listOdjazdy = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Odjazdy.class));
        return listOdjazdy;
    }
    /* Insert – wstawianie nowego wiersza do bazy */
    public void save(Odjazdy odjazdy) {
    }
    /* Read – odczytywanie danych z bazy */
    public Odjazdy get(int id) {
        return null;
    }
    /* Update – aktualizacja danych */
    public void update(Odjazdy odjazdy) {
    }
    /* Delete – wybrany rekord z danym id */
    public void delete(int id) {
    }

}

