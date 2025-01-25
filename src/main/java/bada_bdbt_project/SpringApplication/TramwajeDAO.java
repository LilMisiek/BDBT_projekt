package bada_bdbt_project.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
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
        String sql = "SELECT nr_linii FROM Tramwaje WHERE nr_tramwaju = :tramId";
        MapSqlParameterSource params = new MapSqlParameterSource("tramId", nrTramwaju);

        String line = namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
        // jeśli line != null/"" => true
        return line != null && !line.isEmpty();
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
     * Pobiera pojedyńczy Tramwaj po nrTramwaju.
     */
    public Tramwaje getById(int nrTramwaju) {
        String sql = "SELECT " +
                "  nr_tramwaju, " +
                "  data_produkcji, " +
                "  data_zakupu, " +
                "  data_ostatniego_serwisu, " +
                "  nr_przedsiebiorstwa, " +
                "  nr_zajezdni, " +
                "  nr_linii, " +
                "  nr_modelu " +
                "FROM Tramwaje " +
                "WHERE nr_tramwaju = :tramId";

        MapSqlParameterSource params = new MapSqlParameterSource("tramId", nrTramwaju);

        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            Tramwaje tram = new Tramwaje();
            tram.setNrTramwaju(rs.getInt("nr_tramwaju"));
            // Konwersja z Date do LocalDate:
            tram.setDataProdukcji(rs.getDate("data_produkcji").toLocalDate());
            tram.setDataZakupu(rs.getDate("data_zakupu").toLocalDate());

            Date dataSerwisu = rs.getDate("data_ostatniego_serwisu");
            if (dataSerwisu != null) {
                tram.setDataOstatniegoSerwisu(dataSerwisu.toLocalDate());
            }
            tram.setNrPrzedsiebiorstwa(rs.getInt("nr_przedsiebiorstwa"));
            tram.setNrZajezdni(rs.getInt("nr_zajezdni"));
            tram.setNrLinii(rs.getString("nr_linii"));
            tram.setNrModelu(rs.getInt("nr_modelu"));
            return tram;
        });
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
}
