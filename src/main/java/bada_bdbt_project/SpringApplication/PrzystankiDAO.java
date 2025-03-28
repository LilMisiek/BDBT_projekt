package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrzystankiDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PrzystankiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Przystanki> list() {
        String sql = "SELECT * FROM przystanki";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Przystanki.class));
    }


    public void save(Przystanki p) {
        String sql = """
            INSERT INTO przystanki (nr_przystanku, nr_adresu, nr_przedsiebiorstwa, nazwa_przystanku)
            VALUES (seq_przystanki.nextval, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                p.getNrAdresu(),           
                p.getNrPrzedsiebiorstwa(),
                p.getNazwaPrzystanku()   

        );

    }


    public boolean existsByNazwaPrzystanku(String nazwa) {
        String sql = "SELECT COUNT(*) FROM przystanki WHERE nazwa_przystanku = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{nazwa}, Integer.class);
        return count != null && count > 0;
    }


    public Przystanki get(int id) {
        String sql = "SELECT * FROM przystanki WHERE nr_przystanku = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Przystanki.class), id);
    }


    public void update(Przystanki p) {
        String sql = """
            UPDATE przystanki
            SET nr_adresu = ?, nr_przedsiebiorstwa = ?, nazwa_przystanku = ?
            WHERE nr_przystanku = ?
        """;
        jdbcTemplate.update(
                sql,
                p.getNrAdresu(),
                p.getNrPrzedsiebiorstwa(),
                p.getNazwaPrzystanku(),
                p.getNrPrzystanku()
        );
    }


    public void delete(int id) {
        String sql = "DELETE FROM przystanki WHERE nr_przystanku = ?";
        jdbcTemplate.update(sql, id);
    }
}
