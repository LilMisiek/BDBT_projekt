package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
        String sql = "SELECT \n" +
                "    o.nr_odjazdu, \n" +
                "    l.nr_linii, \n" +
                "    l.czy_nocna, \n" +
                "    p.nr_przystanku, \n" +
                "    p.nazwa_przystanku, \n" +
                "    o.godzina, \n" +
                "    o.czy_na_zadanie \n" +
                "FROM \n" +
                "    odjazdy o \n" +
                "JOIN \n" +
                "    tramwaje t ON o.Nr_tramwaju = t.Nr_tramwaju \n" +
                "JOIN \n" +
                "    linie l ON t.Nr_linii = l.Nr_linii \n" +
                "JOIN \n" +
                "    przystanki p ON o.Nr_przystanku = p.Nr_przystanku";
        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Odjazdy.class));
    }

    // Metoda do filtrowania odjazdów na podstawie linii i przystanków
    public List<Odjazdy> findRozklad(List<String> selectedLinie, List<Integer> selectedPrzystanki) {
        StringBuilder sql = new StringBuilder(
                "SELECT o.nr_odjazdu, l.nr_linii, l.czy_nocna, p.nr_przystanku, p.nazwa_przystanku, o.godzina, o.czy_na_zadanie " +
                        "FROM odjazdy o " +
                        "JOIN tramwaje t ON o.nr_tramwaju = t.nr_tramwaju " +
                        "JOIN linie l ON t.nr_linii = l.nr_linii " +
                        "JOIN przystanki p ON o.nr_przystanku = p.nr_przystanku " +
                        "WHERE 1=1"
        );

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (selectedLinie != null && !selectedLinie.isEmpty()) {
            sql.append(" AND l.nr_linii IN (:linie)");
            params.addValue("linie", selectedLinie);
        }

        if (selectedPrzystanki != null && !selectedPrzystanki.isEmpty()) {
            sql.append(" AND p.nr_przystanku IN (:przystanki)");
            params.addValue("przystanki", selectedPrzystanki);
        }

        return namedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Odjazdy.class));
    }

    // Implementacja innych metod CRUD (save, get, update, delete)

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

