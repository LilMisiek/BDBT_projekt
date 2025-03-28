package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @GetMapping("/linie/{nrLinii}/przystanki")
    public List<Przystanki> getPrzystankiDlaLinii(@PathVariable String nrLinii) {

        String sql = """
                SELECT DISTINCT 
                    p.nr_przystanku     AS nrPrzystanku,
                    p.nazwa_przystanku  AS nazwaPrzystanku,
                    p.nr_przedsiebiorstwa AS nrPrzedsiebiorstwa,
                    p.nr_adresu         AS nrAdresu
                FROM odjazdy o
                JOIN tramwaje t ON o.nr_tramwaju = t.nr_tramwaju
                JOIN linie l ON t.nr_linii = l.nr_linii
                JOIN przystanki p ON o.nr_przystanku = p.nr_przystanku
                WHERE l.nr_linii = :nrLinii
                ORDER BY p.nazwa_przystanku
                """;

        MapSqlParameterSource params = new MapSqlParameterSource("nrLinii", nrLinii);

        return namedParameterJdbcTemplate.query(
                sql,
                params,
                BeanPropertyRowMapper.newInstance(Przystanki.class)
        );
    }
}
