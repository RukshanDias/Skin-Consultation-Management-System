package Console;

import java.util.Scanner;

public interface SkinConsultationManager {
    void addDoctor(Scanner input);
    void removeDoctor(Scanner input);
    void displayDoctors();
    void storeData();
    void loadData();
}
