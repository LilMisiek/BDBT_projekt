package bada_bdbt_project.SpringApplication;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TPMDAO {
    /* Import org.springframework.jd....Template */
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public TPMDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Import java.util.List */
    public List<TPM> list(){
        String sql = "SELECT * FROM TPM";
        List<TPM> listTPM = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TPM.class));
        return listTPM;
    }
    /* Insert – wstawianie nowego wiersza do bazy */
    public void save(TPM tpm) {
    }
    /* Read – odczytywanie danych z bazy */
    public TPM get(int id) {
        return null;
    }
    /* Update – aktualizacja danych */
    public void update(TPM tpm) {
    }
    /* Delete – wybrany rekord z danym id */
    public void delete(int id) {
    }

}
