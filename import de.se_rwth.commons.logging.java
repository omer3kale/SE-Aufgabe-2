import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import poker._parser.PokerGameParser;

import java.io.StringReader;

import static org.junit.Assert.assertTrue;

public class PokerGameTest {

  @Before
  public void setUp() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testParsePokerGame() throws Exception {
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
    java.util.Optional<de.monticore.ast.ASTNode> result = parser.parse(new StringReader(pokerGameConfig));

    assertTrue(result.isPresent());
    assertTrue(Log.getFindings().isEmpty());
  }
}