package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrzystankiDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PrzystankiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Pobieranie wszystkich przystanków
    public List<Przystanki> list(){
        String sql = "SELECT * FROM przystanki";
        List<Przystanki> listPrzystanki = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Przystanki.class));
        return listPrzystanki;
    }

    // Insert – wstawianie nowego wiersza do bazy
    public void save(Przystanki przystanki) {
        String sql = "INSERT INTO przystanki (nr_przystanku, nr_adresu, nr_przedsiebiorstwa, nazwa_przystanku) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, przystanki.getNrPrzystanku(), przystanki.getNrAdresu(), przystanki.getNrPrzedsiebiorstwa(), przystanki.getNazwaPrzystanku());
    }

    // Read – odczytywanie danych z bazy
    public Przystanki get(int id) {
        String sql = "SELECT * FROM przystanki WHERE nr_przystanku = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Przystanki.class), id);
    }

    // Update – aktualizacja danych
    public void update(Przystanki przystanki) {
        String sql = "UPDATE przystanki SET nr_adresu = ?, nr_przedsiebiorstwa = ?, nazwa_przystanku = ? WHERE nr_przystanku = ?";
        jdbcTemplate.update(sql, przystanki.getNrAdresu(), przystanki.getNrPrzedsiebiorstwa(), przystanki.getNazwaPrzystanku(), przystanki.getNrPrzystanku());
    }

    // Delete – wybrany rekord z danym id
    public void delete(int id) {
        String sql = "DELETE FROM przystanki WHERE nr_przystanku = ?";
        jdbcTemplate.update(sql, id);
    }
}
