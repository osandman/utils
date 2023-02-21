package net.osandman.util.parsing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Получение средних оценок
 */
public class AverageGrade {
    public static final int ROUND_PLACES = 2;

    public static void main(String[] args) {
//        System.out.println(getAverageSumFromSingleDigits("Алгебра\tП 5 3 4 П 4 П 4 3 П Б Б Б 2 П 4 3 П П 4", 2));
        String all = "1\tАлгебра\tП 5 3 4 П 4 П 4 3 П Б Б Б 2 П 4 3 П П 4\t0\t10\t3\t4\n" +
                     "2\tАнгл. яз.\t3 П 3 4 П 4 3 4 П П П Б 4 П 2 П П 4\t0\t9\t1\t3\n" +
                     "3\tБилет в будущее\tП Б П П \t0\t4\t1\t\n" +
                     "4\tБиология\tП 4 2 5 4 Б П П \t0\t4\t1\t4\n" +
                     "5\tГеография\tП 3 3 4 П Б 4 П 3 4 П \t0\t5\t1\t4\n" +
                     "6\tГеометрия\t3 4 П 3 П П Б 4 4 3 4\t0\t4\t1\t4\n" +
                     "7\tИЗО\tП 4 5 5 Б 5 П П \t0\t4\t1\t5\n" +
                     "8\tИнформатика\t4 4 П 5 4\t0\t1\t0\t4\n" +
                     "9\tИстория (5-9)\t3 Н П 4 Н 2 Б Б 2 П 3 П \t0\t7\t2\t\n" +
                     "10\tКраев. мозаика\tП П П П \t0\t4\t0\t\n" +
                     "11\tЛитература\tП 2 4 4 П 4 П Б Б 2 П 4 П П \t0\t8\t2\t4\n" +
                     "12\tМузеи мира\tБ \t0\t1\t1\t\n" +
                     "13\tМузыка\tП 5 П Б 4 5\t0\t3\t1\t5\n" +
                     "14\tОбществознание\t3 4 П 4 3 5\t0\t1\t0\t\n" +
                     "15\tРазговоры о важном\tП Б \t0\t2\t1\t\n" +
                     "16\tРодная литература\tП Б 5 4 П 5\t0\t3\t1\t4\n" +
                     "17\tРодной язык\t4 3 4 П 5 4\t0\t1\t0\t4\n" +
                     "18\tРусск. яз.\tП П П 3 3 П П Б Б 4 5 5 4 П 3 П П П 5\t0\t11\t2\t\n" +
                     "19\tТехнология\t4 5 4 5 5 П П 5 П П Б Б 5 5 П П 4\t0\t8\t2\t\n" +
                     "20\tФиз. культура\tП 5 5 5 П П П П \t0\t5\t0\t\n" +
                     "21\tФизика\t2 4 П 5 П 3 5 П П Б 2 П 4 П П 2\t0\t8\t1\t3\n" +
                     "22\tФиз-ра для всех\tБ \t0\t1\t1\t\n" +
                     "23\tФМГ\tП П П П \t0\t4\t0\t\n" +
                     "24\tФранц. яз.\tП 4 4 П 4 П 4 П \t0\t4\t0\t4";

        System.out.println(getAllAverageGrades(all));
    }

    /**
     * Парсит строки, скопированные с сайта школьного портала и возвращает средние оценки по предметам
     */
    public static String getAllAverageGrades(String input) {
        String[] lines = input.split("\\n");
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            String[] words = line.split("\\t");
            String totalGrade = words.length < 7 ? "?" : words[6]; //если еще не выставили итоговую оценку пишем "?"
            builder.append(words[0]).append(". ").append(words[1]).append(": средний балл - ")
                    .append(getAverageSumFromSingleDigits(words[2], ROUND_PLACES)).append(", итоговый - ")
                    .append(totalGrade).append("\n");
        }
        return builder.toString();
    }

    /**
     * Нахождение среднего арифметического из строки символов, выбирая только одинарные цифры
     */
    public static double getAverageSumFromSingleDigits(String input, int places) {
        String[] onlyDigits = input.replaceAll("\\D", "").split("");
        if (onlyDigits.length == 0 || "".equals(onlyDigits[0])) return 0;
        int[] digits = Arrays.stream(onlyDigits).mapToInt(Integer::parseInt).toArray();
        double average = Arrays.stream(digits).average().orElse(Double.NaN);
        return round(average, places);
    }

    /**
     * Округление числа вверх до заданного количества знаков
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal result = new BigDecimal(value);
        return result.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
