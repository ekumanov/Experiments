import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PartySeats {
    public static final int TOTAL_SEATS = 240;
    public static final double MIN_PERCENTAGE = 4.0;
    public static Map<String, Double> results = new LinkedHashMap<>();

    static {
        results.put("ГЕРБ", 25.33);
        results.put("ПП", 20.20);
        results.put("ДПС", 13.75);
        results.put("Възраждане", 10.18);
        results.put("БСП", 9.30);
        results.put("ДБ", 7.45);
        results.put("БВ", 4.63);
        results.put("ИТН", 3.83);
    }

    public static void main(String[] args) {
        List<String> filtered = new LinkedList<>(results.keySet());
        filtered.removeIf(s -> results.get(s) < MIN_PERCENTAGE);
        filtered.sort((party1, party2) -> (int) (results.get(party2) - results.get(party1)));

        double totalPercentage = filtered.stream().map(s -> results.get(s)).mapToDouble(d -> d).sum();
        double factor = TOTAL_SEATS / totalPercentage;
        int usedSeats = 0;
        Map<String, Double> remainders = new LinkedHashMap<>();
        Map<String, Integer> partySeats = new LinkedHashMap<>();
        for (String party : filtered) {
            double scaled = results.get(party) * factor;
            int seats = (int) Math.floor(scaled);
            partySeats.put(party, seats);
            double remainder = scaled - seats;
            remainders.put(party, remainder);
            usedSeats += seats;
        }
        System.out.println("First round seats: " + usedSeats);

        int remainingSeats = TOTAL_SEATS - usedSeats;
        for (int i = 0; i < remainingSeats; i++) {
            String party = findMaxRemainderParty(remainders);
            System.out.println("Adding a seat to: " + party);
            partySeats.put(party, partySeats.get(party) + 1);
            usedSeats++;
        }

        System.out.println("---------");

        for (Map.Entry<String, Integer> partySeatEntry : partySeats.entrySet()) {
            System.out.println(partySeatEntry.getKey() + ": " + partySeatEntry.getValue());
        }

        System.out.println("---------");
        System.out.println("Total seats: " + usedSeats);
    }

    private static String findMaxRemainderParty(Map<String, Double> remainders) {
        double highestRemainder = 0;
        String party = null;
        for (Map.Entry<String, Double> partyRemainder : remainders.entrySet()) {
            if (partyRemainder.getValue() >= highestRemainder) {
                party = partyRemainder.getKey();
                highestRemainder = partyRemainder.getValue();
            }
        }
        remainders.remove(party);
        return party;
    }
}
