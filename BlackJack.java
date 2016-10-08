public class BlackJack
{
    public static void main (String[] args)
    {
        int numPlayers; //number of players that will be input
        double bankroll = 100;  //initial bankroll for each player
        double remaining;
        boolean bust; //holds true if player busts
        String name, hitStand;
        WhoWins win = new WhoWins();
        AdvPlay play = new AdvPlay();

        //get number of players from user
        System.out.print("Please enter number of players: ");
        numPlayers = IO.readInt();
        //error check to make sure at least one player but no more than 6
        while (numPlayers < 1 || numPlayers > 6)
        {
            System.out.println("Invalid number of players. (1-6)\nPlease enter re-enter: ");
            numPlayers = IO.readInt();
        }
        //create Player object for each player
        Player players[] = new Player[numPlayers];
        //get each player's name
        for (int i = 0; i < numPlayers; i++)
        {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            name = IO.readString();
            //call Player constructor and pass player's name, and bankroll
            players[i] = new Player(name, bankroll);
        }
        System.out.println();

        //array to hold bet value of each player
        double bet[] = new double[numPlayers];
        //array to hold player's insurance bet
        double insuranceBet[] = new double[numPlayers];
        //array to hold player's choice to double down
        char dblDwn[] = new char[numPlayers];
        //array to hold player's choice to split
        char splitHand[] = new char[numPlayers];
        //array to hold player's choice to take insurance
        char insure[] = new char[numPlayers];
        //array to hold used cards
        Card used[];

        //create Dealer object
        Dealer dealer = new Dealer();

        //game loop
        while (numPlayers > 0)
        {
            //create deck and shuffle
            Deck playDeck = new Deck();
            System.out.println();

            System.out.println("***All Players start with a bankroll of 100***");
                    //store value of player i into bet
            for (int i = 0; i < numPlayers; i++)
            {
                if (players[i] == null)
                {
                    System.out.println();
                }
                else
                {
                    System.out.print("Enter bet " + players[i].getName() + ": ");
                    bet[i] = IO.readInt();
                    while (bet[i] < 10 || bet[i] > players[i].getBankroll())
                    {
                        System.out.println("Invalid input.");
                        if (bet[i] < 10) {
                            System.out.print("Bet must be at lease 10.\nPlease enter bet: ");
                            bet[i] = IO.readInt();
                        } else
                        {
                            System.out.print("Bet exceeds amount in bankroll.\nPlease enter bet: ");
                            bet[i] = IO.readInt();
                        }
                    }
                }

            }
            System.out.println();
            //deal initial two cards to each player
            for (int j = 0; j < numPlayers; j++)
            {
                if (players[j] == null)
                {
                    System.out.println("\n");
                }
                else
                {
                    players[j].addToHand(playDeck.deal(true),true);
                    players[j].addToHand(playDeck.deal(true),true);
                }

            }
            //deal two cards to dealer
            dealer.addToHand(playDeck.deal(false));
            dealer.addToHand(playDeck.deal(true));

            //print dealer's hand while not showing first card
            System.out.println();
            System.out.println("-----" + dealer.getDealerName() + "-----");
            dealer.printHand(false);
            System.out.println();
            //loop to print current hand, bet, and score of each player
            for (int c = 0; c < numPlayers; c++)
            {
                if (players[c] == null)
                {
                    System.out.println("\n");
                }
                else
                {
                    System.out.println("\n-----" + players[c].getName() + "-----\n" + "Bet: " + bet[c]);
                    System.out.print("Hand: ");
                    players[c].printHand(true);
                    System.out.println("\nScore: " + players[c].sumHand(true));
                }

            }
            //loop for each player to play there turn
            for (int k = 0; k < numPlayers; k++)
            {
                if (players[k] == null)
                {
                    System.out.println();
                }
                else
                {
                    //if dealer face up card is an ace, ask players if they want to make an insurance bet
                    if(dealer.getHand(1) == 1)
                    {
                        System.out.print("(" + players[k].getName() + ") Insurance (Y or N)");
                        insure[k] = IO.readChar();
                        if(insure[k] == 'Y' || insure[k] == 'y')
                        {
                            insuranceBet[k] = bet[k]*0.5;
                        }
                        else
                        {
                            insuranceBet[k] = 0;
                        }

                    }
                    //if player's bankroll is less than double their bet, they cannot double down
                    if(players[k].getBankroll() < bet[k]*2)
                    {
                        System.out.println("(" + players[k].getName() + ") Bankroll to low to double down and split!");
                        //store used cards into array
                        used = playDeck.getCardsUsed();
                        //get number of cards remaining in deck
                        remaining = playDeck.getCardsRemaining();
                        //call to hint system method to give player hint before choosing to hit or stand
                        play.hintSystem(players[k], used, remaining, true);
                        System.out.println("\n(" + players[k].getName() + ") Enter 'Hit' or 'Stand': ");
                        hitStand = IO.readString();
                        //error check
                        while(!hitStand.toLowerCase().equals("hit") || !hitStand.toLowerCase().equals("stand"))
                        {
                            System.err.print("Invalid input. Please re-enter choice: ");
                            hitStand = IO.readString();
                        }
                        while (hitStand.toLowerCase().equals("hit")) //continues to add card to hand while user inputs 'hit'
                        {
                            bust = players[k].addToHand(playDeck.deal(true), true);
                            //if addToHand() returns false, player busts
                            if (!bust)
                            {
                                players[k].printHand(true);
                                System.out.println("\nYou bust! \nScore: " + players[k].sumHand(true));
                                hitStand = "stand";
                            }
                            //else player's score is <= 21
                            else
                            {
                                players[k].printHand(true);
                                System.out.println("New score: " + players[k].sumHand(true));
                                //store used cards into array
                                used = playDeck.getCardsUsed();
                                //get number of cards remaining in deck
                                remaining = playDeck.getCardsRemaining();
                                //call to hint system method to give player hint before choosing to hit or stand
                                play.hintSystem(players[k], used, remaining, true);
                                System.out.print("Enter 'Hit' or 'Stand': ");
                                hitStand = IO.readString();
                                //error check
                                while(!(hitStand.toLowerCase().equals("hit") || hitStand.toLowerCase().equals("stand")))
                                {
                                    System.err.print("Invalid input. Please re-enter choice: ");
                                    hitStand = IO.readString();
                                }
                            }
                        }
                    }
                    else
                    {
                        if(players[k].getCard(0, true) == players[k].getCard(1, true))
                        {
                            //store used cards into array
                            used = playDeck.getCardsUsed();
                            //get number of cards remaining in deck
                            remaining = playDeck.getCardsRemaining();
                            //call to hint system method to give player hint before choosing to hit or stand
                            play.hintSystem(players[k], used, remaining, true);
                            System.out.print("(" + players[k].getName() + ") Split or Double Down or Neither (S or D or N): ");
                            splitHand[k] = IO.readChar();
                            if (splitHand[k] == 'S' || splitHand[k] == 's')
                            {
                                play.split(players[k], playDeck);

                                players[k].printHand(true);
                                System.out.println("\n(" + players[k].getName() + ") Enter 'Hit' or 'Stand': ");
                                hitStand = IO.readString();
                                //error check
                                while(!(hitStand.toLowerCase().equals("hit") || !hitStand.toLowerCase().equals("stand")))
                                {
                                    System.err.print("Invalid input. Please re-enter choice: ");
                                    hitStand = IO.readString();
                                }
                                while (hitStand.toLowerCase().equals("hit")) //continues to add card to hand while user inputs 'hit'
                                {
                                    bust = players[k].addToHand(playDeck.deal(true), true);
                                    //if addToHand() returns false, player busts
                                    if (!bust)
                                    {
                                        players[k].printHand(true);
                                        System.out.println("\nYou bust! \nScore: " + players[k].sumHand(true));
                                        hitStand = "stand";
                                    }
                                    //else player's score is <= 21
                                    else
                                    {
                                        players[k].printHand(true);
                                        System.out.println("New score: " + players[k].sumHand(true));
                                        //store used cards into array
                                        used = playDeck.getCardsUsed();
                                        //get number of cards remaining in deck
                                        remaining = playDeck.getCardsRemaining();
                                        //call to hint system method to give player hint before choosing to hit or stand
                                        play.hintSystem(players[k], used, remaining, true);
                                        System.out.print("Enter 'Hit' or 'Stand': ");
                                        hitStand = IO.readString();
                                        //error check
                                        while(!(hitStand.toLowerCase().equals("hit") || hitStand.toLowerCase().equals("stand")))
                                        {
                                            System.err.print("Invalid input. Please re-enter choice: ");
                                            hitStand = IO.readString();
                                        }
                                    }
                                }
                            }
                            else if (splitHand[k] == 'D' || splitHand[k] == 'd')
                            {
                                dblDwn[k] = 'y';
                                play.doubleDown(players[k], playDeck);
                            }
                            else
                            {
                                //store used cards into array
                                used = playDeck.getCardsUsed();
                                //get number of cards remaining in deck
                                remaining = playDeck.getCardsRemaining();
                                //call to hint system method to give player hint before choosing to hit or stand
                                play.hintSystem(players[k], used, remaining, true);
                                System.out.println("\n(" + players[k].getName() + ") Enter 'Hit' or 'Stand': ");
                                hitStand = IO.readString();
                                //error check
                                while(!(hitStand.toLowerCase().equals("hit") || !hitStand.toLowerCase().equals("stand")))
                                {
                                    System.err.print("Invalid input. Please re-enter choice: ");
                                    hitStand = IO.readString();
                                }
                                while (hitStand.toLowerCase().equals("hit")) //continues to add card to hand while user inputs 'hit'
                                {
                                    bust = players[k].addToHand(playDeck.deal(true), true);
                                    //if addToHand() returns false, player busts
                                    if (!bust)
                                    {
                                        players[k].printHand(true);
                                        System.out.println("\nYou bust! \nScore: " + players[k].sumHand(true));
                                        hitStand = "stand";
                                    }
                                    //else player's score is <= 21
                                    else
                                    {
                                        players[k].printHand(true);
                                        System.out.println("New score: " + players[k].sumHand(true));
                                        //store used cards into array
                                        used = playDeck.getCardsUsed();
                                        //get number of cards remaining in deck
                                        remaining = playDeck.getCardsRemaining();
                                        //call to hint system method to give player hint before choosing to hit or stand
                                        play.hintSystem(players[k], used, remaining, true);
                                        System.out.print("Enter 'Hit' or 'Stand': ");
                                        hitStand = IO.readString();
                                        //error check
                                        while(!(hitStand.toLowerCase().equals("hit") || hitStand.toLowerCase().equals("stand")))
                                        {
                                            System.err.print("Invalid input. Please re-enter choice: ");
                                            hitStand = IO.readString();
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            //store used cards into array
                            used = playDeck.getCardsUsed();
                            //get number of cards remaining in deck
                            remaining = playDeck.getCardsRemaining();
                            //call to hint system method to give player hint before choosing to hit or stand
                            play.hintSystem(players[k], used, remaining, true);
                            System.out.print("(" + players[k].getName() + ") Double Down? (Y or N): ");
                            dblDwn[k] = IO.readChar();
                            if(dblDwn[k] == 'Y' || dblDwn[k] == 'y')
                            {
                                play.doubleDown(players[k], playDeck);
                            }
                            else
                            {
                                //store used cards into array
                                used = playDeck.getCardsUsed();
                                //get number of cards remaining in deck
                                remaining = playDeck.getCardsRemaining();
                                //call to hint system method to give player hint before choosing to hit or stand
                                play.hintSystem(players[k], used, remaining, true);
                                System.out.println("\n(" + players[k].getName() + ") Enter 'Hit' or 'Stand': ");
                                hitStand = IO.readString();
                                //error check
                                while(!(hitStand.toLowerCase().equals("hit") || hitStand.toLowerCase().equals("stand")))
                                {
                                    System.err.print("Invalid input. Please re-enter choice: ");
                                    hitStand = IO.readString();
                                }
                                while (hitStand.toLowerCase().equals("hit")) //continues to add card to hand while user inputs 'hit'
                                {
                                    bust = players[k].addToHand(playDeck.deal(true), true);
                                    //if addToHand() returns false, player busts
                                    if (!bust)
                                    {
                                        players[k].printHand(true);
                                        System.out.println("\nYou bust! \nScore: " + players[k].sumHand(true));
                                        hitStand = "stand";
                                    }
                                    //else player's score is <= 21
                                    else
                                    {
                                        players[k].printHand(true);
                                        System.out.println("New score: " + players[k].sumHand(true));
                                        //store used cards into array
                                        used = playDeck.getCardsUsed();
                                        //get number of cards remaining in deck
                                        remaining = playDeck.getCardsRemaining();
                                        //call to hint system method to give player hint before choosing to hit or stand
                                        play.hintSystem(players[k], used, remaining, true);
                                        System.out.print("Enter 'Hit' or 'Stand': ");
                                        hitStand = IO.readString();
                                        //error check
                                        while(!(hitStand.toLowerCase().equals("hit") || hitStand.toLowerCase().equals("stand")))
                                        {
                                            System.err.print("Invalid input. Please re-enter choice: ");
                                            hitStand = IO.readString();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //start of dealer's turn
            System.out.println("\n" + dealer.getDealerName() + "'s turn");
            //print dealer's hand while keeping first card hidden
            dealer.printHand(true);
            //if dealer's face down card has value of ten, account or insurance bets
            if(dealer.getHand(1) == 1)
            {
                if (dealer.getHand(0) >= 10 && dealer.getHand(0) <= 13)
                {
                    play.insurance(players, insuranceBet, true);
                }
                else
                {
                    play.insurance(players, insuranceBet, false);
                }
            }

            //dealer can hit if score is < 17
            if (dealer.sumHand() < 17) {
                //add card to hand
                boolean dealerGo = dealer.addToHand(playDeck.deal(false));
                System.out.println("\nDealer hits");
                //print dealer hand, still keeping first card hidden
                dealer.printHand(true);
                //if addToHand returns false, dealer busted and turn ends
                while (dealerGo) {
                    //if dealer score < 17, dealer hits again
                    if (dealer.sumHand() < 17) {
                        System.out.println("\nDealer hits");
                        dealerGo = dealer.addToHand(playDeck.deal(false));
                        dealer.printHand(true);
                        System.out.println();
                    }

                    //if score >= 17, dealer stays
                    else {
                        System.out.println("\nDealer stays\nScore:" + dealer.sumHand());
                        dealer.printHand(true);
                        break;
                    }
                }
                //if loop exited on bust, print dealer score and hand while showing first card
                if (dealer.sumHand() > 21) {
                    System.out.println("\nDealer busts!\nScore:" + dealer.sumHand());
                    dealer.printHand(true);
                }

            }
            //if dealer initial hand >= 17, dealer stays
            else
            {
                System.out.println("\nDealer stays\nScore:" + dealer.sumHand());
                dealer.printHand(true);
            }

            //loop to determine who wins
            for (int i = 0; i < numPlayers; i++)
            {
                if (players[i] == null)
                {
                    System.out.println();
                }
                else
                {
                    win.whoWin(players[i], dealer, dblDwn[i], bet[i]);

                    if(splitHand[i] == 'S' || splitHand[i] == 's')
                    {
                        win.whoWinSplit(players[i], dealer, bet[i]);
                    }
                }
            }
            char ans;
            //loops through each player to enter 'Y' to play again or 'N' to not play again
            for (int i = 0; i < numPlayers; i++)
            {
                //check if player i is already out of the game
                if (players[i] == null)
                {
                    System.out.println();
                }
                else
                {
                    //player can no longer play if bankroll is less than the 10 bet minimum
                    if (players[i].getBankroll() < 10)
                    {
                        System.out.println("\n" + players[i].getName() + " bankroll is too low to continue.\nThank you for playing!");
                        players[i] = null;
                    }
                    //ask the player for answer input
                    else
                    {
                        System.out.print("\n" + players[i].getName() + ", do you wish to keep playing? (Enter 'Y' or 'N'): ");
                        ans = IO.readChar();
                        //if no, then player is set to null to represent player is no longer in game
                        if (ans == 'N' || ans == 'n') {
                            System.out.println("Thank you for playing!");
                            players[i] = null;
                        }
                        //if yes, empty player's hand for the next round
                        else if (ans == 'Y' || ans == 'y')
                        {
                            System.out.println("Good Luck this round!");
                            players[i].emptyHand();
                        }
                        //else, user made invalid input and is no longer in the game
                        else
                        {
                            System.err.println("Invalid input. " + players[i].getName() + " is no longer in the game.");
                            players[i] = null;
                        }

                    }
                }
            }
            //empty dealer's hand
            dealer.emptyHand();

            //counts to see how many players remaining
            //when numOut is equal to the number of players that entered the game
            //game loop exits and program ends
            int numOut = 0;
            for(int i = 0; i < numPlayers; i++)
            {
                if(players[i] == null)
                    numOut++;
            }
            if (numOut == numPlayers)
                break;
        }
        System.out.println("Game Ended");
    }
}
