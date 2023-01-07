package Console;

import UI.MainMenuFrame;
import UI.PatientDetailsFrame;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{
    private static final int MAX_DOCTORS_COUNT = 7;
    private static LinkedList<Doctor> doctorsList = new LinkedList<>();
    private static boolean isGuiOpen = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WestminsterSkinConsultationManager westSkinConsultCenter = new WestminsterSkinConsultationManager();

        // load data from text file to program
        westSkinConsultCenter.loadData();
        PatientDetailsFrame.loadConsultationsData();

        // main menu
        menuLoop:
        while (true){
            System.out.println("""
                    \n-----------------MENU--------------------
                    Select a number from below
                    \t1 --> Add Doctor
                    \t2 --> Remove Doctor
                    \t3 --> Display all Doctors
                    \t4 --> Save Doctors in a file
                    \t5 --> Read Doctors from file
                    \t6 --> GUI
                    \t99 --> Exit
                    """);
            System.out.print("Enter your option : ");
            String option = input.next();

            switch (option){
                case "1" -> westSkinConsultCenter.addDoctor(input);
                case "2" -> westSkinConsultCenter.removeDoctor(input);
                case "3" -> westSkinConsultCenter.displayDoctors();
                case "4" -> westSkinConsultCenter.storeData();
                case "5" -> westSkinConsultCenter.loadData();
                case "6" -> {
                    if (isGuiOpen){
                        System.out.println("GUI is already opened.");
                    }else {
                        new MainMenuFrame();
                        isGuiOpen = true;
                    }
                }
                case "99" -> {
                    System.out.println("Programme terminated...\nSee you again..");
                    PatientDetailsFrame.storeConsultationsData();
                    break menuLoop;
                }
                default -> System.out.println("Invalid option!!...Pls try again..");
            }

        }
    }

    /**
     * This method is used to validate String inputs
     * @param message   text that display before getting input
     * @param input     Scanner object
     * @param inputType type of input - name, number
     * @return  Fully validated String value
     */
    private static String stringValidation(String message, Scanner input, String inputType){
        String userInput;
        String errorMessage = "";
        String condition= "";

        switch (inputType) {
            case "name", "text", "surname" -> {
                errorMessage = "Pls enter letters only..";
                condition = "^[a-zA-Z]*$";
            }
            case "mobileNo" -> {
                errorMessage = "Invalid mobile no..Pls enter 10 numbers only";
                condition = "^[0-9]{10}$";
            }
            case "Id" -> {
                errorMessage = "invalid ID\nPls enter a another ID..";
                condition = "^[a-zA-Z0-9]+$";
            }
        }

        while (true){
            System.out.print(message);
            userInput = input.next().toLowerCase();
            if (userInput.matches(condition)){  // if the input fulfill the string format
                break;
            }else {
                System.out.println(errorMessage);
            }
        }
        return userInput;
    }

    /***
     * This method is used to validate Date of birth input
     * @param input - Scanner input
     * @return valid LocalDate value
     */
    private static LocalDate dateValidation(Scanner input){
        LocalDate DOB;
        while (true){
            try {
                System.out.print("Enter date of birth [yyyy-mm-dd]: ");
                String str = input.next();
                DOB = LocalDate.parse(str);
                if(DOB.isBefore(LocalDate.now())){
                    break;
                }else {
                    System.out.println("Invalid Date of Birth..");
                }
            }catch (DateTimeParseException e){
                System.out.println("Invalid date..\nPls enter date as [yyyy-mm-dd]");
            }
        }
        return DOB;
    }

    /**
     * This method is used to validate medical number.
     * @param input - Scanner object
     * @return valid & unique medical licence number
     */
    private static String IdValidation(Scanner input){
        String userInput;
        while (true){
            System.out.print("Enter Doctor's medical licence number: ");
            userInput = input.next();
            if (userInput.matches("^[a-zA-Z0-9]+$") && (!Doctor.getMedicalLicenseNoSet().contains(userInput))){
                break;
            }else {
                System.out.println("ID is not unique..\nPls try again..");
            }
        }
        return userInput;
    }

    /***
     * This method is used to add Doctor to list
     * @param input Scanner object
     */
    @Override
    public void addDoctor(Scanner input){
        // check if there is a space left for doctors
        if (doctorsList.size() < MAX_DOCTORS_COUNT){
            // getting doctor's details
            String name = stringValidation("Enter Doctor's name: ", input, "name");
            String surname = stringValidation("Enter Doctor's surname: ", input, "surname");
            // get date from user
            LocalDate DOB = dateValidation(input);

            String mobileNo = stringValidation("Enter Doctor's mobile number: ",input, "mobileNo");
            String medicalLicenceNo = IdValidation(input);
            String specialisation = stringValidation("Enter Doctor's specialisation: ",input, "text");

            doctorsList.add(new Doctor(name,surname,DOB,mobileNo,medicalLicenceNo,specialisation));
            System.out.println("New Doctor added..");
        }else {
            System.out.println(MAX_DOCTORS_COUNT+" doctors already enrolled..\nPls try again later..");
        }
    }

    /***
     * This method is used to remove a Doctor from list
     * @param input - Scanner object
     */
    @Override
    public void removeDoctor(Scanner input){
        if (!doctorsList.isEmpty()){
            System.out.println("Removing Doctor");
            for (Doctor doctor : doctorsList){
                System.out.println(doctor.getMedicalLicenseNo() + "\t-->\t" + doctor.getName());
            }
            String medicalLicenceNo;
            do {
                System.out.print("Enter Doctor's medical licence number: ");
                medicalLicenceNo = input.next();
                if (!Doctor.getMedicalLicenseNoSet().contains(medicalLicenceNo)){
                    System.out.println("Invalid medical licence number.. Try again..");
                }
            }while (!Doctor.getMedicalLicenseNoSet().contains(medicalLicenceNo));

            // searching algorithm
            for (Doctor doctor: doctorsList){
                if (doctor.getMedicalLicenseNo().equals(medicalLicenceNo)){
                    doctorsList.remove(doctor);
                    System.out.printf("Doctor %s %s have been removed..\nTotal number of Doctors: %d",doctor.getName(),doctor.getSurname(), doctorsList.size());
                    break;
                }
            }
        }else {
            System.out.println("There aren't any doctors to remove..");
        }
    }

    /***
     * This method is used to display details of doctors. sorted by surname
     */
    @Override
    public void displayDoctors(){
        if (!doctorsList.isEmpty()){
            System.out.println("Displaying details of all doctors..");
            Doctor[] s1;
            s1 = sortDoctors();

            String leftAlignFormat = "| %-7s | %-8s | %-7s | %-8s |  %-8s | %-18s |%n";
            System.out.format("+---------+----------+------------+------------+-----------+--------------------+%n");
            System.out.format("| Name    | Surname  |  Birthday  | Mobile No  | License No|  Specialisation    |%n");
            System.out.format("+---------+----------+------------+------------+-----------+--------------------+%n");
            for (Doctor doc: s1){
                System.out.format(leftAlignFormat,doc.getName(), doc.getSurname(), doc.getDOB().toString(), doc.getMobileNo(), doc.getMedicalLicenseNo(), doc.getSpecialisation());
            }
            System.out.format("+---------+----------+------------+------------+-----------+--------------------+%n");

        }else {
            System.out.println("There aren't any doctors to display..");
        }
    }

    /**
     * This method is used to sort Doctors by Surname and then First name
     * @return an Array sorted by Surname and First name
     */
    private static Doctor[] sortDoctors(){
        // copy linked list to array
        Doctor[] sortList = new Doctor[doctorsList.size()];
        doctorsList.toArray(sortList);

        Comparator<Doctor> bySurname = Comparator.comparing(Doctor::getSurname);
        Comparator<Doctor> byName = Comparator.comparing(Doctor::getName);
        java.util.Arrays.sort(sortList, bySurname.thenComparing(byName));

        return sortList;
    }

    /**
     * This method is used to store user input data to a file.
     */
    @Override
    public void storeData(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("doctorsData.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Doctor doctor : doctorsList){
                objectOutputStream.writeObject(doctor);
            }
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Doctors data successfully stored in a file..");
        }catch (IOException e){
            System.out.println("An error occurred.." + e);
        }
    }

    /***
     * This method is used to Load data to system, from file
     */
    @Override
    public void loadData(){
        try {
            FileInputStream fileInputStream = new FileInputStream("doctorsData.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while ((fileInputStream.available() > 0)){
                Doctor docObj = (Doctor) objectInputStream.readObject();

                // check Medical Num is unique
                if (!Doctor.getMedicalLicenseNoSet().contains(docObj.getMedicalLicenseNo())){
                    doctorsList.add(docObj);
                    Doctor.getMedicalLicenseNoSet().add(docObj.getMedicalLicenseNo()); //adding Medical No to HashSet
                }

                // check the list size reach max length
                if (doctorsList.size() >= MAX_DOCTORS_COUNT){
                    System.out.println("Doctor list is currently full\ncannot add more Doctors..");
                    break;
                }
            }
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Doctor file successfully loaded..");
        }catch (IOException e){
            System.out.println("an error occurred when loading data "+ e);
        } catch (ClassNotFoundException e) {
            System.out.println("Clas not found..");
        }
    }

    public LinkedList<Doctor> getDoctorsList() {
        return doctorsList;
    }

    public void setIsGuiOpen(boolean isGuiOpen) {
        WestminsterSkinConsultationManager.isGuiOpen = isGuiOpen;
    }
}
