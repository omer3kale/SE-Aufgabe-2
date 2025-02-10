import poker._ast.ASTPokerGame;
import poker._ast.ASTPlayer;
import poker._ast.ASTHand;
import poker._ast.ASTRound;
import poker._visitor.PokerGameVisitor;

public class PokerGameVisitor implements PokerGameVisitor {

  private StringBuilder generatedCode = new StringBuilder();

  @Override
  public void visit(ASTPokerGame node) {
    generatedCode.append("public class PokerGame {\n");
    generatedCode.append("  public static void main(String[] args) {\n");
    generatedCode.append("    // Players\n");
    for (ASTPlayer player : node.getPlayers()) {
      generatedCode.append("    Player ").append(player.getName()).append(" = new Player(\"").append(player.getName()).append("\");\n");
    }
    generatedCode.append("    // Deck\n");
    generatedCode.append("    Deck deck = new Deck();\n");
    node.getDeck().forEach(card -> {
      generatedCode.append("    deck.addCard(new Card(\"").append(card.getRank()).append("\", \"").append(card.getSuit()).append("\"));\n");
    });
    generatedCode.append("    // Hands\n");
    for (ASTHand hand : node.getHands()) {
      generatedCode.append("    Hand ").append(hand.getPlayerName()).append("Hand = new Hand(").append(hand.getPlayerName()).append(");\n");
      hand.getCards().forEach(card -> {
        generatedCode.append("    ").append(hand.getPlayerName()).append("Hand.addCard(new Card(\"").append(card.getRank()).append("\", \"").append(card.getSuit()).append("\"));\n");
      });
    }
    generatedCode.append("    // Rounds\n");
    for (ASTRound round : node.getRounds()) {
      generatedCode.append("    Round round = new Round();\n");
      round.getPlayerActions().forEach(action -> {
        generatedCode.append("    round.addAction(new Action(\"").append(action.getPlayerName()).append("\", \"").append(action.getAction()).append("\"));\n");
      });
    }
    generatedCode.append("  }\n");
    generatedCode.append("}\n");
  }

  public String getGeneratedCode() {
    return generatedCode.toString();
  }
}