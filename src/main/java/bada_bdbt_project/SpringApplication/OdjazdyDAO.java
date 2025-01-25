package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Repository
public class OdjazdyDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OdjazdyDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // Pobieranie wszystkich odjazdów
    public List<Odjazdy> findAll() {
        String sql = "SELECT odjazdy.*, przystanki.nazwa_przystanku " +
                "FROM odjazdy " +
                "JOIN przystanki ON odjazdy.nr_przystanku = przystanki.nr_przystanku";


        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Odjazdy.class));
    }

    // Metoda do filtrowania odjazdów na podstawie linii i przystanków
    public List<Odjazdy> findRozklad(List<String> selectedLinie, List<Integer> selectedPrzystanki) {
        StringBuilder sql = new StringBuilder(
                "SELECT o.nr_odjazdu, o.nr_linii, p.nazwa_przystanku, " +
                        "o.godzina, o.czy_na_zadanie " +
                        "FROM odjazdy o " +
                        "JOIN tramwaje t ON o.nr_tramwaju = t.nr_tramwaju " +
                        "JOIN linie l ON t.nr_linii = l.nr_linii " +
                        "JOIN przystanki p ON o.nr_przystanku = p.nr_przystanku " +
                        "WHERE 1=1"
        );

        MapSqlParameterSource params = new MapSqlParameterSource();

        // Dodanie warunku dla wybranych linii, jeśli istnieją
        if (selectedLinie != null && !selectedLinie.isEmpty()) {
            sql.append(" AND l.nr_linii IN (:linie)");
            params.addValue("linie", selectedLinie);
        }

        // Dodanie warunku dla wybranych przystanków, jeśli istnieją
        if (selectedPrzystanki != null && !selectedPrzystanki.isEmpty()) {
            sql.append(" AND p.nr_przystanku IN (:przystanki)");
            params.addValue("przystanki", selectedPrzystanki);
        }

        // Opcjonalnie: Sortowanie wyników, np. według godziny odjazdu
        sql.append(" ORDER BY o.godzina ASC");

        // Wykonanie zapytania i mapowanie wyników
        return namedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Odjazdy.class));
    }




    public void update(Odjazdy odjazd) {
        String sql = "UPDATE odjazdy " +
                "SET nr_tramwaju = :nrTramwaju, " +
                "    nr_przystanku = :nrPrzystanku, " +
                "    godzina = :godzina, " +
                "    czy_na_zadanie = :czyNaZadanie " +
                "WHERE nr_odjazdu = :nrOdjazdu";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nrTramwaju", odjazd.getNrTramwaju())
                .addValue("nrPrzystanku", odjazd.getNrPrzystanku())
                .addValue("godzina", Time.valueOf(odjazd.getGodzina())) // Convert LocalTime to Time
                .addValue("czyNaZadanie", odjazd.getCzyNaZadanie())
                .addValue("nrOdjazdu", odjazd.getNrOdjazdu());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public Odjazdy getById(int nrOdjazdu) {
        String sql = "SELECT o.nr_odjazdu, t.nr_tramwaju, p.nr_przystanku, o.godzina, o.czy_na_zadanie, " +
                "       l.nr_linii, l.czy_nocna, p.nazwa_przystanku " +
                "FROM odjazdy o " +
                "JOIN tramwaje t ON o.nr_tramwaju = t.nr_tramwaju " +
                "JOIN linie l ON t.nr_linii = l.nr_linii " +
                "JOIN przystanki p ON o.nr_przystanku = p.nr_przystanku " +
                "WHERE o.nr_odjazdu = :nrOdjazdu";

        MapSqlParameterSource params = new MapSqlParameterSource("nrOdjazdu", nrOdjazdu);
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            Odjazdy odjazd = new Odjazdy();
            odjazd.setNrOdjazdu(rs.getInt("nr_odjazdu"));
            odjazd.setNrTramwaju(rs.getInt("nr_tramwaju"));
            odjazd.setNrPrzystanku(rs.getInt("nr_przystanku"));
            Time time = rs.getTime("godzina");
            if (time != null) {
                odjazd.setGodzina(time.toLocalTime()); // Convert Time to LocalTime
            }
            odjazd.setCzyNaZadanie(rs.getString("czy_na_zadanie"));
            odjazd.setNrLinii(rs.getString("nr_linii"));
            odjazd.setCzyNocna(rs.getString("czy_nocna"));
            odjazd.setNazwaPrzystanku(rs.getString("nazwa_przystanku"));
            return odjazd;
        });
    }

    // Implementacja innych metod CRUD (save, get, update, delete)

    public void save(Odjazdy odjazd) {
        // 1. Wstawienie nowego odjazdu
        String insertSql = "INSERT INTO odjazdy (nr_tramwaju, nr_przystanku, godzina, czy_na_zadanie, nr_linii) " +
                "VALUES (:nrTramwaju, :nrPrzystanku, :godzina, :czyNaZadanie, :nrLinii)";
        MapSqlParameterSource insertParams = new MapSqlParameterSource();

        insertParams.addValue("nrTramwaju", odjazd.getNrTramwaju());
        insertParams.addValue("nrPrzystanku", odjazd.getNrPrzystanku());
        insertParams.addValue("godzina", odjazd.getGodzina());
        insertParams.addValue("czyNaZadanie", odjazd.getCzyNaZadanie());
        insertParams.addValue("nrLinii", odjazd.getNrLinii());

        namedParameterJdbcTemplate.update(insertSql, insertParams);

        // 2. Aktualizacja nr_linii w tabeli tramwaje
        String updateSql = "UPDATE tramwaje SET nr_linii = :nrLinii WHERE nr_tramwaju = :nrTramwaju";
        MapSqlParameterSource updateParams = new MapSqlParameterSource();
        updateParams.addValue("nrLinii", odjazd.getNrLinii());
        updateParams.addValue("nrTramwaju", odjazd.getNrTramwaju());

        namedParameterJdbcTemplate.update(updateSql, updateParams);
    }

    /* Read – odczytywanie danych z bazy */
    public Odjazdy get(int id) {
        return getById(id); // Implemented using getById
    }

    /* Delete – wybrany rekord z danym id */
    public void delete(int nrOdjazdu) {
        String sql = "DELETE FROM odjazdy WHERE nr_odjazdu = :nrOdjazdu";
        MapSqlParameterSource params = new MapSqlParameterSource("nrOdjazdu", nrOdjazdu);
        namedParameterJdbcTemplate.update(sql, params);
    }

}
