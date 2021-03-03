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
    public String addSymptom(String name) {
        try {
            String add = " insert into people(symptom) values ( ? )";
            PreparedStatement ps = db.getConnection().prepareStatement(add);
            ps.setString(1,name);
            int rs = ps.executeUpdate();
            return "Thank you for taking a part";
        }catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }
}
