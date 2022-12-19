package Console;

import UI.MainMenuFrame;

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

//        loadData(doctorsList);

        //        testing
//        LocalDate d = LocalDate.of(2020,01,19);
//        Console.Doctor d1 = new Console.Doctor("shan","zips",d, "0332343122","233","ART");
//        Console.Doctor d2 = new Console.Doctor("shan","alias",d, "0332343122","233","ART");
//        Console.Doctor d3 = new Console.Doctor("shan","dias",d, "0332343122","233","ART");
//        Console.Doctor d4 = new Console.Doctor("shan","bias",d, "0332343122","233","ART");
//        Console.Doctor d5 = new Console.Doctor("shan","baas",d, "0332343122","233","ART");
//
//        doctorsList.add(d1);
//        doctorsList.add(d2);
//        doctorsList.add(d3);
//        doctorsList.add(d4);
//        doctorsList.add(d5);

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
                    break menuLoop;
                }
                default -> System.out.println("Invalid option!!...");
            }

        }
    }

    /**
     * This method is used to validate integer inputs.
     * @param message   text that display before getting input
     * @param input     Scanner object
     * @param maxNo     maximum integer value that can have
     * @return  Fully validated int value
     */
    private static int intValidation(String message, Scanner input, int maxNo){
        int userInput;
        while (true){
            System.out.print(message);
            try {
                userInput = input.nextInt();
                if (userInput >= 1 && userInput <= maxNo) {
                    break;
                }else {
                    System.out.println("Out of range!! Pls enter number between 1 and "+maxNo);
                }
            }catch (InputMismatchException e){
                System.out.println("Pls enter number not letters.");
            }
            input.nextLine();
        }
        return userInput;
    }

    /**
     * This method is used to validate String inputs
     * @param message   text that display before getting input
     * @param input     Scanner object
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
                errorMessage = "Pls enter 10 numbers only";
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

    private static LocalDate dateValidation(Scanner input){
        LocalDate DOB;
        while (true){
            try {
                System.out.print("Enter a date [yyyy-mm-dd]: ");
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

    private static String IdValidation(String message ,Scanner input){
        String userInput;
        while (true){
            System.out.print(message);
            userInput = input.next();
            if (userInput.matches("^[a-zA-Z0-9]+$") && (!Doctor.getMedicalLicenseNoSet().contains(userInput))){
                break;
            }else {
                System.out.println("ID is not unique..\nPls try again..");
            }
        }
        return userInput;
    }
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
            String medicalLicenceNo = IdValidation("Enter Doctor's medical licence number: ",input);
            String specialisation = stringValidation("Enter Doctor's specialisation: ",input, "text");

            doctorsList.add(new Doctor(name,surname,DOB,mobileNo,medicalLicenceNo,specialisation));
        }else {
            System.out.println(MAX_DOCTORS_COUNT+" doctors already enrolled..\nPls try again later..");
        }
    }

    @Override
    public void removeDoctor(Scanner input){
        if (!doctorsList.isEmpty()){
            System.out.println("Removing Doctor");
            for (Doctor doctor : doctorsList){
                System.out.println(doctor.getMedicalLicenseNo() + "\t-->\t" + doctor.getName());
            }
//            String medicalLicenceNo = stringValidation("Enter Doctor's medical licence number: ",input, "Id");
            String medicalLicenceNo;
            do {
                System.out.print("Enter Doctor's medical licence number: ");
                medicalLicenceNo = input.next();
            }while (!Doctor.getMedicalLicenseNoSet().contains(medicalLicenceNo));

            // searching algo
            for (Doctor doctor: doctorsList){
                if (doctor.getMedicalLicenseNo().equals(medicalLicenceNo)){
                    doctorsList.remove(doctor);
                    System.out.printf("Doctor %s %s have been removed..",doctor.getName(),doctor.getSurname());
                    break;
                }
            }
        }else {
            System.out.println("There aren't any doctors to remove..");
        }
    }

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
            FileOutputStream fileOutputStream = new FileOutputStream("data.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Doctor doctor : doctorsList){
                objectOutputStream.writeObject(doctor);
            }
            objectOutputStream.close();
            System.out.println("Data successfully stored in a file..");
        }catch (IOException e){
            System.out.println("An error occurred.." + e);
        }
    }

    @Override
    public void loadData(){
        try {
            FileInputStream fileInputStream = new FileInputStream("data.txt");
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
            System.out.println("File successfully loaded..");
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
