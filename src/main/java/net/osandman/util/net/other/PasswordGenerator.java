package net.osandman.util.net.other;

import java.io.ByteArrayOutputStream;
import java.util.Random;


/**
 * Требования к паролю:
 * 1) 8 символов.
 * 2) только цифры и латинские буквы разного регистра.
 * 3) обязательно должны присутствовать цифры, и буквы разного регистра.
 * Все сгенерированные пароли должны быть уникальные.
 *
 * Пример правильного пароля:
 * wMh7smNu
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println("generated password: " + password);
    }

    private static ByteArrayOutputStream getPassword() {
        Random random = new Random();
        int passLength = 8;
        int maxCharCode = 127;
        String charSet = "[0-9A-Za-z]";
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        while (result.size() < passLength) {
            int aByte = 33 + random.nextInt(maxCharCode - 33); //генерация кода символа от 33 до 127 в кодах ascii
            String character = String.valueOf((char) aByte);
            if (character.matches(charSet)) {
                result.write(aByte);
            }
        }
        //проверка чтобы символ из каждого множества встечался хотя бы один раз
        if (result.toString().matches(".*[a-z].*")
            && result.toString().matches(".*[A-Z].*")
            && result.toString().matches(".*[0-9].*")) {
            return result;
        } else {
            System.out.println(result);
            System.out.println("Don`t match, generate again ...");
            return getPassword();
        }
    }
}
