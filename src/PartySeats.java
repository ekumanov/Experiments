import java.util.LinkedHashMap;
import java.util.Map;

public class PartySeats {
    public static final int TOTAL_SEATS = 240;
    public static Map<String, Double> results = new LinkedHashMap<>();

    static {
        results.put("ГЕРБ", 22.4);
        results.put("ПП", 19.1);
        results.put("ДПС", 11.5);
        results.put("БСП", 10.8);
        results.put("Възраждане", 8.4);
        results.put("ДБ", 8.3);
        results.put("БВ", 4.0);
    }

    public static void main(String[] args) {
        double totalPercentage = results.values().stream().mapToDouble(d -> d).sum();
        double factor = TOTAL_SEATS / totalPercentage;
        int usedSeats = 0;
        Map<String, Double> remainders = new LinkedHashMap<>();
        Map<String, Integer> partySeats = new LinkedHashMap<>();
        for (Map.Entry<String, Double> partyResult : results.entrySet()) {
            double scaled = partyResult.getValue() * factor;
            int seats = (int) Math.floor(scaled);
            partySeats.put(partyResult.getKey(), seats);
            double remainder = scaled - seats;
            remainders.put(partyResult.getKey(), remainder);
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
