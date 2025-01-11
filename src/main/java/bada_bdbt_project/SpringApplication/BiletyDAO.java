package bada_bdbt_project.SpringApplication;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BiletyDAO {
    /* Import org.springframework.jd....Template */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BiletyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Import java.util.List */
    public List<Bilety> list(){
        String sql = "SELECT * FROM Bilety";
        List<Bilety> listBilety = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Bilety.class));
        return listBilety;
    }
    /* Insert – wstawianie nowego wiersza do bazy */
    public void save(Bilety bilety) {
    }
    /* Read – odczytywanie danych z bazy */
    public Bilety get(int id) {
        return null;
    }
    /* Update – aktualizacja danych */
    public void update(Bilety bilety) {
    }
    /* Delete – wybrany rekord z danym id */
    public void delete(int id) {
    }

}
