package lesson1;

import kotlin.NotImplementedError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */

    // Память O(n), Ресурсоемкость (nlogn)
    //n - кол-во строк в файле
    static public void sortAddresses(String inputName, String outputName) {


        Scanner sc;
        try {
            sc = new Scanner(new File(inputName));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        }
        TreeMap<String, TreeSet<String>> res = new TreeMap<>((o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            String[] addr1 = o1.split(" ");
            String[] addr2 = o2.split(" ");
            int compStreet = addr1[0].compareTo(addr2[0]);
            if (compStreet == 0) {
                return Integer.parseInt(addr1[1]) - Integer.parseInt(addr2[1]);
            }
            else {
                return compStreet;
            }
        });
        while (sc.hasNext()) {
            String[] lineParts = sc.nextLine().split(" - ");
            String name = lineParts[0];
            String addr = lineParts[1];
            if (res.containsKey(addr)) {
                res.get(addr).add(name);
            }
            else {
                TreeSet<String> names = new TreeSet<>();
                names.add(name);
                res.put(addr, names);
            }
        }
        sc.close();
        PrintWriter pw;
        try {
            pw = new PrintWriter(outputName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error opening output file");
        }
        for (Map.Entry<String, TreeSet<String>> e : res.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getKey()).append(" - ");
            boolean first = true;
            for (String name : e.getValue()) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(name);
                first = false;
            }
            pw.println(sb.toString());
        }
        pw.close();

    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    // Память O(n), Ресурсоемкость O(nlogn)
    static public void sortTemperatures(String inputName, String outputName) {
        Scanner sc;
        try {
            sc = new Scanner(new File(inputName));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        }
        ArrayList<Float> list = new ArrayList<>();
        while (sc.hasNext()) list.add(Float.parseFloat(sc.nextLine()));
        Collections.sort(list);
        PrintWriter pw;
        try {
            pw = new PrintWriter(outputName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error opening output file");
        }
        for (float f : list) {
            pw.println(String.format(Locale.US, "%.1f", f));
        }
        pw.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    // Память = O(n), Ресурсоемкость = O(n)
    static public void sortSequence(String inputName, String outputName) {
        Scanner sc;
        try {
            sc = new Scanner(new File(inputName));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        }
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        while (sc.hasNext()) {
            int x = Integer.parseInt(sc.nextLine());
            list.add(x);
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        int maxCount = Collections.max(map.values());
        int minNumber = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getValue() == maxCount && e.getKey() < minNumber) {
                minNumber = e.getKey();
            }
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(outputName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error opening output file");
        }
        for (int x : list) {
            if (x != minNumber) {
                pw.println(x);
            }
        }
        for (int i = 0; i < maxCount; i++) {
            pw.println(minNumber);
        }
        pw.close();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
