import de.se_rwth.commons.logging.Log;
import poker._parser.PokerGameParser;
import de.monticore.ast.ASTNode;

import java.io.StringReader;
import java.util.Optional;

public class PokerGameErrorHandling {

  public static void main(String[] args) {
    String pokerGameConfig = "PokerGame {\n" +
        "  Players {\n" +
        "    Player Alice,\n" +
        "    Player Bob\n" +
        "  }\n" +
        "  Deck {\n" +
        "    2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts, 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds, QClubs, KSpades, AHearts\n" +
        "  }\n" +
        "  Hands {\n" +
        "    Hand { Alice: 2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts },\n" +
        "    Hand { Bob: 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds }\n" +
        "  }\n" +
        "  Rounds {\n" +
        "    Round { Alice: Bet, Bob: Call },\n" +
        "    Round { Alice: Check, Bob: Raise }\n" +
        "  }\n" +
        "}";

    PokerGameParser parser = new PokerGameParser();
    try {
      Optional<ASTNode> result = parser.parse(new StringReader(pokerGameConfig));
      if (result.isPresent()) {
        System.out.println("Parsing successful.");
        // Proceed with further processing, e.g., validation or code generation
      } else {
        System.out.println("Parsing failed.");
      }
    } catch (Exception e) {
      Log.error("Parsing exception: " + e.getMessage());
    }

    if (!Log.getFindings().isEmpty()) {
      System.out.println("Errors found during parsing:");
      Log.getFindings().forEach(finding -> System.out.println(finding.getMsg()));
    }
  }
}