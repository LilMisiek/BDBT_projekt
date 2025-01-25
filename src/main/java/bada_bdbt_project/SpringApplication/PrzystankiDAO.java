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

    // Pobieranie wszystkich przystanków
    public List<Przystanki> list() {
        String sql = "SELECT * FROM przystanki";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Przystanki.class));
    }

    // Zapis nowego przystanku
    public void save(Przystanki p) {
        String sql = """
            INSERT INTO przystanki (nr_przystanku, nr_adresu, nr_przedsiebiorstwa, nazwa_przystanku)
            VALUES (seq_przystanki.nextval, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                p.getNrAdresu(),           // ?1
                p.getNrPrzedsiebiorstwa(),// ?2
                p.getNazwaPrzystanku()   // ?3

        );

    }

    /**
     * Sprawdza, czy istnieje w tabeli przystanek o danej nazwie.
     * Zwraca true, jeśli tak, w przeciwnym razie false.
     */
    public boolean existsByNazwaPrzystanku(String nazwa) {
        String sql = "SELECT COUNT(*) FROM przystanki WHERE nazwa_przystanku = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{nazwa}, Integer.class);
        return count != null && count > 0;
    }

    // Read – odczytywanie danych z bazy
    public Przystanki get(int id) {
        String sql = "SELECT * FROM przystanki WHERE nr_przystanku = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Przystanki.class), id);
    }

    // Update – aktualizacja danych
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

    // Delete – wybrany rekord z danym id
    public void delete(int id) {
        String sql = "DELETE FROM przystanki WHERE nr_przystanku = ?";
        jdbcTemplate.update(sql, id);
    }
}
