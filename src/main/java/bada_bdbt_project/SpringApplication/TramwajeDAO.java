package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * DAO do obsługi tabeli TRAMWAJE.
 */
@Repository
public class TramwajeDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TramwajeDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Pobiera wszystkie rekordy z tabeli TRAMWAJE.
     */
    public List<Tramwaje> list() {
        String sql = "SELECT " +
                "  nr_tramwaju, " +
                "  data_produkcji, " +
                "  data_zakupu, " +
                "  data_ostatniego_serwisu, " +
                "  nr_przedsiebiorstwa, " +
                "  nr_zajezdni, " +
                "  nr_linii, " +
                "  nr_modelu " +
                "FROM Tramwaje";
        return namedParameterJdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Tramwaje.class));
    }

    public boolean isTramAssigned(int nrTramwaju) {
        String sql = "SELECT COUNT(*) FROM Linie WHERE Nr_linii = (SELECT Nr_linii FROM Tramwaje WHERE Nr_tramwaju = :nrTramwaju)";
        MapSqlParameterSource params = new MapSqlParameterSource("nrTramwaju", nrTramwaju);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    public List<Tramwaje> listWithAssignedLine() {
        String sql = """
            SELECT nr_tramwaju, data_produkcji, data_zakupu,
                   data_ostatniego_serwisu, nr_przedsiebiorstwa,
                   nr_zajezdni, nr_linii, nr_modelu
            FROM Tramwaje
            WHERE nr_linii IS NOT NULL
        """;
        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Tramwaje.class));
    }


    /**
     * Dodaje nowy rekord Tramwaje do bazy.
     */
    public void save(Tramwaje tramwaj) {
        String sql = "INSERT INTO Tramwaje (" +
                "nr_tramwaju, data_produkcji, data_zakupu, data_ostatniego_serwisu, " +
                "nr_przedsiebiorstwa, nr_zajezdni, nr_linii, nr_modelu" +
                ") VALUES (" +
                ":nrTramwaju, :dataProdukcji, :dataZakupu, :dataOstSerwisu, " +
                ":nrPrzeds, :nrZajezdni, :nrLinii, :nrModelu" +
                ")";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nrTramwaju", tramwaj.getNrTramwaju())
                .addValue("dataProdukcji", Date.valueOf(tramwaj.getDataProdukcji()))
                .addValue("dataZakupu", Date.valueOf(tramwaj.getDataZakupu()))
                .addValue("dataOstSerwisu",
                        tramwaj.getDataOstatniegoSerwisu() != null
                                ? Date.valueOf(tramwaj.getDataOstatniegoSerwisu())
                                : null)
                .addValue("nrPrzeds", tramwaj.getNrPrzedsiebiorstwa())
                .addValue("nrZajezdni", tramwaj.getNrZajezdni())
                .addValue("nrLinii", tramwaj.getNrLinii())
                .addValue("nrModelu", tramwaj.getNrModelu());

        namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Aktualizuje istniejący rekord Tramwaje w bazie.
     */
    public void update(Tramwaje tramwaj) {
        String sql = "UPDATE Tramwaje SET " +
                "  data_produkcji = :dataProdukcji, " +
                "  data_zakupu = :dataZakupu, " +
                "  data_ostatniego_serwisu = :dataOstSerwisu, " +
                "  nr_przedsiebiorstwa = :nrPrzeds, " +
                "  nr_zajezdni = :nrZajezdni, " +
                "  nr_linii = :nrLinii, " +
                "  nr_modelu = :nrModelu " +
                "WHERE nr_tramwaju = :nrTramwaju";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nrTramwaju", tramwaj.getNrTramwaju())
                .addValue("dataProdukcji", Date.valueOf(tramwaj.getDataProdukcji()))
                .addValue("dataZakupu", Date.valueOf(tramwaj.getDataZakupu()))
                .addValue("dataOstSerwisu",
                        tramwaj.getDataOstatniegoSerwisu() != null
                                ? Date.valueOf(tramwaj.getDataOstatniegoSerwisu())
                                : null)
                .addValue("nrPrzeds", tramwaj.getNrPrzedsiebiorstwa())
                .addValue("nrZajezdni", tramwaj.getNrZajezdni())
                .addValue("nrLinii", tramwaj.getNrLinii())
                .addValue("nrModelu", tramwaj.getNrModelu());

        namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Usuwa rekord Tramwaje o danym nr_tramwaju.
     */
    public void delete(int nrTramwaju) {
        String sql = "DELETE FROM Tramwaje WHERE nr_tramwaju = :tramId";
        MapSqlParameterSource params = new MapSqlParameterSource("tramId", nrTramwaju);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public String getNrLiniiById(int nrTramwaju) {
        String sql = "SELECT Nr_linii FROM Tramwaje WHERE Nr_tramwaju = :nrTramwaju";
        MapSqlParameterSource params = new MapSqlParameterSource("nrTramwaju", nrTramwaju);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            // Handle case where tramwaj doesn't exist
            return null;
        }
    }
    // Method to check if tram exists
    public boolean existsById(int nrTramwaju) {
        String sql = "SELECT COUNT(*) FROM Tramwaje WHERE Nr_tramwaju = :nrTramwaju";
        MapSqlParameterSource params = new MapSqlParameterSource("nrTramwaju", nrTramwaju);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
