import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import poker._parser.PokerGameParser;

import java.io.StringReader;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
    Optional<de.monticore.ast.ASTNode> result = parser.parse(new StringReader(pokerGameConfig));

    assertTrue(result.isPresent());
    assertTrue(Log.getFindings().isEmpty());

    PokerGameVisitor visitor = new PokerGameVisitor();
    result.get().accept(visitor);
    String generatedCode = visitor.getGeneratedCode();

    String expectedCode = "public class PokerGame {\n" +
        "  public static void main(String[] args) {\n" +
        "    // Players\n" +
        "    Player Alice = new Player(\"Alice\");\n" +
        "    Player Bob = new Player(\"Bob\");\n" +
        "    // Deck\n" +
        "    Deck deck = new Deck();\n" +
        "    deck.addCard(new Card(\"2\", \"Hearts\"));\n" +
        "    deck.addCard(new Card(\"3\", \"Diamonds\"));\n" +
        "    deck.addCard(new Card(\"4\", \"Clubs\"));\n" +
        "    deck.addCard(new Card(\"5\", \"Spades\"));\n" +
        "    deck.addCard(new Card(\"6\", \"Hearts\"));\n" +
        "    deck.addCard(new Card(\"7\", \"Diamonds\"));\n" +
        "    deck.addCard(new Card(\"8\", \"Clubs\"));\n" +
        "    deck.addCard(new Card(\"9\", \"Spades\"));\n" +
        "    deck.addCard(new Card(\"10\", \"Hearts\"));\n" +
        "    deck.addCard(new Card(\"J\", \"Diamonds\"));\n" +
        "    deck.addCard(new Card(\"Q\", \"Clubs\"));\n" +
        "    deck.addCard(new Card(\"K\", \"Spades\"));\n" +
        "    deck.addCard(new Card(\"A\", \"Hearts\"));\n" +
        "    // Hands\n" +
        "    Hand AliceHand = new Hand(Alice);\n" +
        "    AliceHand.addCard(new Card(\"2\", \"Hearts\"));\n" +
        "    AliceHand.addCard(new Card(\"3\", \"Diamonds\"));\n" +
        "    AliceHand.addCard(new Card(\"4\", \"Clubs\"));\n" +
        "    AliceHand.addCard(new Card(\"5\", \"Spades\"));\n" +
        "    AliceHand.addCard(new Card(\"6\", \"Hearts\"));\n" +
        "    Hand BobHand = new Hand(Bob);\n" +
        "    BobHand.addCard(new Card(\"7\", \"Diamonds\"));\n" +
        "    BobHand.addCard(new Card(\"8\", \"Clubs\"));\n" +
        "    BobHand.addCard(new Card(\"9\", \"Spades\"));\n" +
        "    BobHand.addCard(new Card(\"10\", \"Hearts\"));\n" +
        "    BobHand.addCard(new Card(\"J\", \"Diamonds\"));\n" +
        "    // Rounds\n" +
        "    Round round = new Round();\n" +
        "    round.addAction(new Action(\"Alice\", \"Bet\"));\n" +
        "    round.addAction(new Action(\"Bob\", \"Call\"));\n" +
        "    round = new Round();\n" +
        "    round.addAction(new Action(\"Alice\", \"Check\"));\n" +
        "    round.addAction(new Action(\"Bob\", \"Raise\"));\n" +
        "  }\n" +
        "}\n";

    assertEquals(expectedCode, generatedCode);
  }

  @Test
  public void testParseAnotherPokerGame() throws Exception {
    String pokerGameConfig = "PokerGame {\n" +
        "  Players {\n" +
        "    Player Charlie,\n" +
        "    Player Dana\n" +
        "  }\n" +
        "  Deck {\n" +
        "    2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts, 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds, QClubs, KSpades, AHearts\n" +
        "  }\n" +
        "  Hands {\n" +
        "    Hand { Charlie: 2Hearts, 3Diamonds, 4Clubs, 5Spades, 6Hearts },\n" +
        "    Hand { Dana: 7Diamonds, 8Clubs, 9Spades, 10Hearts, JDiamonds }\n" +
        "  }\n" +
        "  Rounds {\n" +
        "    Round { Charlie: Bet, Dana: Call },\n" +
        "    Round { Charlie: Check, Dana: Raise }\n" +
        "  }\n" +
        "}";

    PokerGameParser parser = new PokerGameParser();
    Optional<de.monticore.ast.ASTNode> result = parser.parse(new StringReader(pokerGameConfig));

    assertTrue(result.isPresent());
    assertTrue(Log.getFindings().isEmpty());

    PokerGameVisitor visitor = new PokerGameVisitor();
    result.get().accept(visitor);
    String generatedCode = visitor.getGeneratedCode();

    String expectedCode = "public class PokerGame {\n" +
        "  public static void main(String[] args) {\n" +
        "    // Players\n" +
        "    Player Charlie = new Player(\"Charlie\");\n" +
        "    Player Dana = new Player(\"Dana\");\n" +
        "    // Deck\n" +
        "    Deck deck = new Deck();\n" +
        "    deck.addCard(new Card(\"2\", \"Hearts\"));\n" +
        "    deck.addCard(new Card(\"3\", \"Diamonds\"));\n" +
        "    deck.addCard(new Card(\"4\", \"Clubs\"));\n" +
        "    deck.addCard(new Card(\"5\", \"Spades\"));\n" +
        "    deck.addCard(new Card(\"6\", \"Hearts\"));\n" +
        "    deck.addCard(new Card(\"7\", \"Diamonds\"));\n" +
        "    deck.addCard(new Card(\"8\", \"Clubs\"));\n" +
        "    deck.addCard(new Card(\"9\", \"Spades\"));\n" +
        "    deck.addCard(new Card(\"10\", \"Hearts\"));\n" +
        "    deck.addCard(new Card(\"J\", \"Diamonds\"));\n" +
        "    deck.addCard(new Card(\"Q\", \"Clubs\"));\n" +
        "    deck.addCard(new Card(\"K\", \"Spades\"));\n" +
        "    deck.addCard(new Card(\"A\", \"Hearts\"));\n" +
        "    // Hands\n" +
        "    Hand CharlieHand = new Hand(Charlie);\n" +
        "    CharlieHand.addCard(new Card(\"2\", \"Hearts\"));\n" +
        "    CharlieHand.addCard(new Card(\"3\", \"Diamonds\"));\n" +
        "    CharlieHand.addCard(new Card(\"4\", \"Clubs\"));\n" +
        "    CharlieHand.addCard(new Card(\"5\", \"Spades\"));\n" +
        "    CharlieHand.addCard(new Card(\"6\", \"Hearts\"));\n" +
        "    Hand DanaHand = new Hand(Dana);\n" +
        "    DanaHand.addCard(new Card(\"7\", \"Diamonds\"));\n" +
        "    DanaHand.addCard(new Card(\"8\", \"Clubs\"));\n" +
        "    DanaHand.addCard(new Card(\"9\", \"Spades\"));\n" +
        "    DanaHand.addCard(new Card(\"10\", \"Hearts\"));\n" +
        "    DanaHand.addCard(new Card(\"J\", \"Diamonds\"));\n" +
        "    // Rounds\n" +
        "    Round round = new Round();\n" +
        "    round.addAction(new Action(\"Charlie\", \"Bet\"));\n" +
        "    round.addAction(new Action(\"Dana\", \"Call\"));\n" +
        "    round = new Round();\n" +
        "    round.addAction(new Action(\"Charlie\", \"Check\"));\n" +
        "    round.addAction(new Action(\"Dana\", \"Raise\"));\n" +
        "  }\n" +
        "}\n";

    assertEquals(expectedCode, generatedCode);
  }
}