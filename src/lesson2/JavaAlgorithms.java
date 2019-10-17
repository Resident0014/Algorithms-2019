package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    // Память O(n*m) Ресурсоемкость O(n*m)
    static public String longestCommonSubstring(String first, String second) {
        int a[][] = new int[first.length() + 1][second.length() + 1];
        int max = 0;
        int end = -1;
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                a[i][j] = first.charAt(i - 1) == second.charAt(j - 1) ? a[i - 1][j - 1] + 1 : 0;
                if (a[i][j] > max) {
                    max = a[i][j];
                    end = i;
                }
            }
        }
        if (max > 0) {
            return first.substring(end - max, end);
        }
        return "";
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        }
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                lines.add(line.replace(" ", ""));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error while reading file");
            return null;
        }
        int n = lines.size();
        int m = lines.get(0).length();
        Set<String> found = new HashSet<>();
        for (String word : words) {
            m:
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (lines.get(i).charAt(j) == word.charAt(0)) {
                        Stack<Integer> stack = new Stack<>();
                        stack.push(i * m + j);
                        boolean[][] visited = new boolean[word.length()][n * m];
                        while (!stack.empty() && stack.size() < word.length()) {
                            int v = stack.peek();
                            int x = stack.size();
                            visited[x - 1][v] = true;
                            int row = v / m;
                            int col = v % m;
                            // если можно пойти вверх
                            if (row >= 1 && lines.get(row - 1).charAt(col) == word.charAt(x)) {
                                int w = (row - 1) * m + col;
                                if (!visited[x][w] && stack.search(w) == -1) {
                                    stack.push(w);
                                    continue;
                                }
                            }
                            // влево
                            if (col >= 1 && lines.get(row).charAt(col - 1) == word.charAt(x)) {
                                int w = row * m + col - 1;
                                if (!visited[x][w] && stack.search(w) == -1) {
                                    stack.push(w);
                                    continue;
                                }
                            }
                            // вниз
                            if (row + 1 < n && lines.get(row + 1).charAt(col) == word.charAt(x)) {
                                int w = (row + 1) * m + col;
                                if (!visited[x][w] && stack.search(w) == -1) {
                                    stack.push(w);
                                    continue;
                                }
                            }
                            // вправо
                            if (col + 1 < m && lines.get(row).charAt(col + 1) == word.charAt(x)) {
                                int w = row * m + col + 1;
                                if (!visited[x][w] && stack.search(w) == -1) {
                                    stack.push(w);
                                    continue;
                                }
                            }
                            stack.pop();
                        }
                        if (stack.size() == word.length()) {
                            found.add(word);
                            break m;
                        }
                    }
                }
            }
        }
        return found;
    }
}
