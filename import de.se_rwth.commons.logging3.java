import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import poker._parser.PokerGameParser;

import java.io.StringReader;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PokerGameValidationTest {

  @Before
  public void setUp() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testValidPokerGameConfiguration() throws Exception {
    String pokerGameConfig = "PokerGame {\n" +
        "  Players {\n" +
        "    Player Eve,\n" +
        "    Player Frank\n" +
        "  }\n" +
        "  Deck {\n" +
        "    2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts, 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds, QClubs, KSpades, AHearts\n" +
        "  }\n" +
        "  Hands {\n" +
        "    Hand { Eve: 2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts },\n" +
        "    Hand { Frank: 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds }\n" +
        "  }\n" +
        "  Rounds {\n" +
        "    Round { Eve: Bet, Frank: Call },\n" +
        "    Round { Eve: Check, Frank: Raise }\n" +
        "  }\n" +
        "}";

    PokerGameParser parser = new PokerGameParser();
    Optional<de.monticore.ast.ASTNode> result = parser.parse(new StringReader(pokerGameConfig));

    assertTrue(result.isPresent());
    assertTrue(Log.getFindings().isEmpty());

    PokerGameValidator validator = new PokerGameValidator();
    result.get().accept(validator);
    assertFalse(validator.hasErrors());
  }

  @Test
  public void testInvalidPokerGameConfiguration() throws Exception {
    String pokerGameConfig = "PokerGame {\n" +
        "  Players {\n" +
        "    Player Eve,\n" +
        "    Player Frank\n" +
        "  }\n" +
        "  Deck {\n" +
        "    2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts, 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds, QClubs, KSpades, AHearts\n" +
        "  }\n" +
        "  Hands {\n" +
        "    Hand { Eve: 2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts },\n" +
        "    Hand { Frank: 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds, QClubs }\n" + // Invalid hand (6 cards)
        "  }\n" +
        "  Rounds {\n" +
        "    Round { Eve: Bet, Frank: Call },\n" +
        "    Round { Eve: Check, Frank: Raise }\n" +
        "  }\n" +
        "}";

    PokerGameParser parser = new PokerGameParser();
    Optional<de.monticore.ast.ASTNode> result = parser.parse(new StringReader(pokerGameConfig));

    assertTrue(result.isPresent());
    assertTrue(Log.getFindings().isEmpty());

    PokerGameValidator validator = new PokerGameValidator();
    result.get().accept(validator);
    assertTrue(validator.hasErrors());
  }
}