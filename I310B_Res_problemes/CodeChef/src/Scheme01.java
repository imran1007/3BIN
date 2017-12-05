import java.util.*;

public class Scheme01 {

    private static java.util.Scanner scanner = new java.util.Scanner(System.in);

    private static int source;
    private static int destination;

    private static Town towns[];

    private static TreeSet<Integer> f;

    private static Comparator comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer i1, Integer i2) {
            int temp = towns[i1].cost - towns[i2].cost;
            if(temp != 0)
                return temp;
            return i1 - i2;
        }
    };

    private static boolean[] rf;

    public static void main(String[] args) {
        int nbTestsCases = scanner.nextInt();
        for(int i = 0; i < nbTestsCases; i++) {
            int nbTowns = scanner.nextInt();
            towns = new Town[nbTowns];
            f = new TreeSet<Integer>(comparator);
            rf = new boolean[nbTowns];
            for(int j = 0; j < nbTowns; j++) {
                towns[j] = new Town(j);
            }
            int nbRoads = scanner.nextInt();
            for(int j = 0; j < nbRoads; j++) {
                int source = scanner.nextInt() - 1;
                int destination = scanner.nextInt() - 1;
                int distance = scanner.nextInt();
                towns[source].roads.add(new Road(source, destination, distance));
            }
            for(int j = 0; j < nbTowns; j++) {
                towns[j].pointsDecreased = scanner.nextInt();
            }
            source = scanner.nextInt() - 1;
            destination = scanner.nextInt() - 1;
            f.add(source);
            boolean found = false;
            while(!f.isEmpty() && !found) {
                int town = f.pollFirst();
                if(town == destination){
                    found = true;
                }
                rf[town] = true;
                for(Road road : towns[town].roads) {
                    if(!rf[road.destination])
                        doAllTheWork(road);
                }
            }
            if(found) {
                System.out.println(towns[destination].cost * 10);
            } else {
                System.out.println(-1);
            }
        }
    }

    private static void doAllTheWork(Road road) {
        int temp = towns[road.source].cost + road.distance;
        if(road.distance > towns[road.destination].pointsDecreased)
            temp -= towns[road.destination].pointsDecreased;
        if(!f.contains(road.destination)) {
            towns[road.destination].cost = temp;
            f.add(road.destination);
        } else if (temp < towns[road.destination].cost) {
            towns[road.destination].cost = temp;
        }
    }

    private static class Town {
        int nom;
        List<Road> roads;
        int pointsDecreased;
        int cost;

        Town(int nom) {
            this.nom = nom;
            this.roads = new ArrayList<>();
            this.cost = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Town town = (Town) o;
            return nom == town.nom;
        }

        @Override
        public int hashCode() {
            return nom;
        }
    }

    private static class Road {
        private int source;
        private int destination;
        private int distance;

        Road(int souce, int destination, int distance) {
            this.source = souce;
            this.destination = destination;
            this.distance = distance;
        }
    }
}