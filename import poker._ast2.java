import poker._ast.ASTPokerGame;
import poker._ast.ASTPlayer;
import poker._ast.ASTHand;
import poker._ast.ASTRound;
import poker._visitor.PokerGameVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PokerGameValidator implements PokerGameVisitor {

  private List<String> errors = new ArrayList<>();

  @Override
  public void visit(ASTPokerGame node) {
    Set<String> playerNames = new HashSet<>();
    for (ASTPlayer player : node.getPlayers()) {
      if (!playerNames.add(player.getName())) {
        errors.add("Duplicate player name: " + player.getName());
      }
    }

    Set<String> cardSet = new HashSet<>();
    node.getDeck().forEach(card -> {
      if (!cardSet.add(card.getRank() + card.getSuit())) {
        errors.add("Duplicate card in deck: " + card.getRank() + card.getSuit());
      }
    });

    for (ASTHand hand : node.getHands()) {
      if (hand.getCards().size() != 5) {
        errors.add("Invalid hand size for player " + hand.getPlayerName() + ": " + hand.getCards().size());
      }
      for (String card : hand.getCards()) {
        if (!cardSet.contains(card)) {
          errors.add("Card " + card + " in hand of " + hand.getPlayerName() + " is not in the deck.");
        }
      }
    }

    for (ASTRound round : node.getRounds()) {
      round.getPlayerActions().forEach(action -> {
        if (!playerNames.contains(action.getPlayerName())) {
          errors.add("Action by unknown player: " + action.getPlayerName());
        }
      });
    }
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public List<String> getErrors() {
    return errors;
  }
}