public class WhoWins
{
    public void whoWin(Player player, Dealer dealer, char dblDwn, double bet)
    {
        if (player.sumHand(true) <= 21 && dealer.sumHand() <= 21) {
        //if player score higher than dealer, player wins
        if (player.sumHand(true) > dealer.sumHand()) {
            //if player doubled down and won, double earnings
            if (dblDwn == 'Y' || dblDwn == 'y') {
                System.out.println("\n" + player.getName() + " beats Dealer!");
                //awarding player's winnings
                player.bet(bet * 2, true);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
            //player did not double down
            else {
                System.out.println("\n" + player.getName() + " beats Dealer!");
                //awarding player's winnings
                player.bet(bet, true);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }

        }
        //else if score equal, it is a tie
        else if (player.sumHand(true) == dealer.sumHand() && dealer.sumHand() <= 21)
        {
            System.out.println("\n" + player.getName() + " ties with dealer");
            System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
        }
        //player score less than dealer
        else
        {
            if (dblDwn == 'Y' || dblDwn == 'y')
            {
                System.out.println("\n" + player.getName() + " does not beat Dealer");
                //deduct player's losses
                player.bet(bet * 2, false);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
            else
            {
                System.out.println("\n" + player.getName() + " does not beat Dealer");
                //deduct player's losses
                player.bet(bet, false);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }

        }
    }
    //player busted and did not win
        else if (player.sumHand(true) <= 21 && dealer.sumHand() > 21)
        {
            if (dblDwn == 'Y' || dblDwn == 'y') {
                System.out.println("Dealer busted! " + player.getName() + " beat dealer!");
                player.bet(bet * 2, true);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());

            }
            else
            {
                System.out.println("Dealer busted! " + player.getName() + " beat dealer!");
                player.bet(bet, true);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
        }
        else
        {
            if (dblDwn == 'Y' || dblDwn == 'y')
            {
                System.out.println("\n" + player.getName() + " busted");
                player.bet(bet * 2, false);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
            else
            {
                System.out.println("\n" + player.getName() + " busted");
                player.bet(bet, false);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
        }
    }

    public void whoWinSplit (Player player, Dealer dealer, double bet)
    {
        if (player.sumHand(false) <= 21 && dealer.sumHand() <= 21) {
            //if player score higher than dealer, player wins
            if (player.sumHand(false) > dealer.sumHand())
            {
                System.out.println("\n" + player.getName() + " beats Dealer!");
                //awarding player's winnings
                player.bet(bet, true);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
            //else if score equal, it is a tie
            else if (player.sumHand(false) == dealer.sumHand() && dealer.sumHand() <= 21)
            {
                System.out.println("\n" + player.getName() + " ties with dealer");
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
            //player score less than dealer
            else
            {
                System.out.println("\n" + player.getName() + " does not beat Dealer");
                //deduct player's losses
                player.bet(bet, false);
                System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
            }
        }
        //player busted and did not win
        else if (player.sumHand(false) <= 21 && dealer.sumHand() > 21)
        {
            System.out.println("Dealer busted! " + player.getName() + " beat dealer!");
            player.bet(bet, true);
            System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());
        }
        else
        {
            System.out.println("\n" + player.getName() + " busted");
            player.bet(bet, false);
            System.out.println(player.getName() + "'s updated bankroll: " + player.getBankroll());

        }
    }
}
