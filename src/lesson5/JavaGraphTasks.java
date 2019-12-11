package lesson5;

import kotlin.NotImplementedError;
import lesson5.impl.GraphBuilder;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        LinkedList<Graph.Edge> path = new LinkedList<>();
        if (graph.getEdges().size() == 0) {
            return path;
        }
        int k = 0;
        Graph.Vertex start = null;
        for (Graph.Vertex v : graph.getVertices()) {
            if (graph.getNeighbors(v).size() % 2 != 0) {
                k++;
                start = v;
            }
        }
        if (k != 0 && k != 2) {
            return path;
        }
        if (start == null) {
            start = graph.getVertices().iterator().next();
        }
        Set<Graph.Edge> visited = new HashSet<>();
        findLoopRecur(start, graph, path, visited);
        return path;
    }

    private static boolean findLoopRecur(Graph.Vertex start, Graph graph, LinkedList<Graph.Edge> path, Set<Graph.Edge> visited) {
        int edgesCount = graph.getEdges().size();
        if (path.size() == edgesCount) {
            return true;
        }
        for (Graph.Vertex next : graph.getNeighbors(start)) {
            Graph.Edge e = graph.getConnection(start, next);
            if (!visited.contains(e)) {
                path.add(e);
                visited.add(e);
                if (findLoopRecur(next, graph, path, visited)) {
                    return true;
                }
                else {
                    path.removeLast();
                    visited.remove(e);
                }
            }
        }
        return false;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        GraphBuilder builder = new GraphBuilder();
        for (Graph.Vertex v : graph.getVertices()) {
            builder.addVertex(v.getName());
        }
        ArrayList<Graph.Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(Graph.Edge::getWeight));
        int cost = 0;
        ArrayList<Graph.Vertex> vertices = new ArrayList<>(graph.getVertices());
        HashMap<String, Integer> treesId = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            treesId.put(vertices.get(i).getName(), i);
        }
        for (Graph.Edge e : edges) {
            Graph.Vertex a = e.getBegin();
            Graph.Vertex b = e.getEnd();
            if (!treesId.get(a.getName()).equals(treesId.get(b.getName()))) {
                cost += e.getWeight();
                builder.addConnection(a, b, e.getWeight());
                int oldId = treesId.get(b.getName());
                int newId = treesId.get(a.getName());
                for (Map.Entry<String, Integer> entry : treesId.entrySet()) {
                    if (entry.getValue().equals(oldId)) {
                        treesId.put(entry.getKey(), newId);
                    }
                }
            }
        }
        return builder.build();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        Path maxPath = new Path();
        for (Graph.Vertex start : graph.getVertices()) {
            maxPath = findPathRecur(start, graph, new Path(start), maxPath);
        }
        return maxPath;
    }
    private static Path findPathRecur(Graph.Vertex start, Graph graph, Path path, Path maxPath) {
        for (Graph.Vertex next : graph.getNeighbors(start)) {
            if (!path.contains(next)) {
                maxPath = findPathRecur(next, graph, new Path(path, graph, next), maxPath);
            }
        }
        if (path.compareTo(maxPath) > 0) {
            return path;
        }
        else {
            return maxPath;
        }
    }
}
