package repositories;

import domain.Symptom;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.ISymptomRepository;

import javax.ws.rs.BadRequestException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SymptomRepository implements ISymptomRepository {
    private IDBRepository db = new PostgresRepository(); // create ab object in order to have connection with db

    @Override
    public Symptom addSymptom(String name) { // Add a new symptom depending on what user inputted
        try {
            String add = " insert into people(symptom) values ( ? )";
            PreparedStatement ps = db.getConnection().prepareStatement(add);
            ps.setString(1,name);
            int rs = ps.executeUpdate(); // insert data into DB
            return null;
        }catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }
    @Override
    public String getTopSymptom(){ // obtain from added symptoms statistics
        try {

            String get = "SELECT symptom,COUNT(symptom) AS \"value_occurrence\" FROM  people GROUP BY symptom ORDER BY value_occurrence DESC LIMIT    1";
            PreparedStatement ps = db.getConnection().prepareStatement(get);
            ResultSet rs = ps.executeQuery(); // insert data into DB
            if(rs.next()) {
                String symptom = rs.getString("symptom");
                String quantity = rs.getString("value_occurrence");
                return "Mostly experienced symptom is " + symptom + " in quantity " + quantity ;
            }
            return null;
        }catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }
}
