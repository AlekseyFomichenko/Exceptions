import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in, "Cp866");

        boolean flag = true;

        while (flag) {
            try {
                System.out.println("Введите Фамилию, Имя и Отчество:");
                String input = sc.nextLine(); // Ввод ФИО
                String[] fio = input.split("\s|, |,");
                if (fio.length != 3) {
                    throw new IndexOutOfRangeException("Неверное количество данных. Требуется 3 параметра");
                }
                for (String string : fio) {
                    if (string.matches(".*\\d.*")) {
                        throw new NotCorrectArgumentException("Параметр содержит цифру");
                    }
                }
                System.out.println();
                File file = new File(fio[0]);
                FileWriter fw = new FileWriter(file, true);
                System.out.println("Введите дату рождения:");
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); // Задаём нужный формат для даты
                Date birthday = df.parse(sc.nextLine()); // Ввод даты рождения
                if (isValidYear(birthday, df)) {
                    throw new IllegalArgumentException("Ошиблись с годом рождения");
                }
                System.out.println();

                System.out.println("Введите номер телефона через 8-ку:");
                long phoneNumber = sc.nextLong();
                if (!isValidPhoneNumber(phoneNumber)) {
                    throw new UserDataFormatException("Неверный формат номера телефона. Ожидается целое 11-значное число.");
                }
                System.out.println("Введите пол - символ \"ж\" или \"м\":");
                String gender = sc.next().toLowerCase();
                if (!isValidGender(gender)) {
                    throw new UserDataFormatException("Ожидается ввод только ж или м.");
                }
                System.out.println();

                String totalLine = String.format("<%s><%s><%s><%s><%d><%s>\n",
                        fio[0],
                        fio[1],
                        fio[2],
                        df.format(birthday),
                        phoneNumber,
                        gender);

                fw.write(totalLine);
                fw.close();
            } catch (ParseException e) {
                System.out.println("Неверный формат введённых данных: " + e.getMessage() + " " + e.getErrorOffset());
            } catch (IOException e) {
                System.out.println("Ошибка записи в файл. Данные не записались. " + e.getMessage());
            } catch (IndexOutOfRangeException e) {
                System.out.println("Неверный формат введённых данных: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный формат введённых данных: " + e.getMessage());
            } catch (NotCorrectArgumentException e) {
                System.out.println("Некорректный ввод: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Недопустимый символ. " + e.getMessage());
            } catch (UserDataFormatException e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            }

            System.out.println("Продолжить? Да/Нет");
            String answer = sc.next();
            if (answer.equals("Нет")) {
                flag = false;
            }
            System.out.println("\n");
        }
        sc.close();

    }

    private static Boolean isValidYear(Date birthday, DateFormat df) throws ParseException {
        return birthday.before(df.parse("01.01.1900")) || birthday.after(new Date());
    }
    private static Boolean isValidPhoneNumber(long phoneNumber) {
        return Long.toString(phoneNumber).length() == 11;
    }
    private static Boolean isValidGender(String input){
        return input.equals("м") || input.equals("ж");
    }
}
