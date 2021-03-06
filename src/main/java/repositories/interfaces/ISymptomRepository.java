package repositories.interfaces;

import domain.Symptom;

public interface ISymptomRepository {
   Symptom addSymptom(String name);
   String getTopSymptom();
}
