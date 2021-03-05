package repositories.interfaces;

import domain.Serials;

public interface ISerialsRepository {
    Serials getSerial();
    //Serials getSerial(String serial); // will be implemented in case if I will add searching serials by name
}
