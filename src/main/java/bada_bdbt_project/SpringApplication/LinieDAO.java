package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public class LinieDAO {
        /* Import org.springframework.jd....Template */
        private JdbcTemplate jdbcTemplate;
        @Autowired
        public LinieDAO(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        /* Import java.util.List */
        public List<Linie> list(){
            String sql = "SELECT * FROM Linie";
            List<Linie> listLinie = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Linie.class));
            return listLinie;
        }
        /* Insert – wstawianie nowego wiersza do bazy */
        public void save(Linie linie) {
        }
        /* Read – odczytywanie danych z bazy */
        public Linie get(int id) {
            return null;
        }
        /* Update – aktualizacja danych */
        public void update(Linie linie) {
        }
        /* Delete – wybrany rekord z danym id */
        public void delete(int id) {
        }

    }


