public class Player
{
    private String playerName;
    private double bankroll;
    int numCards, numCards2;
    private Card playerHand[] = new Card[8];
    private Card splitHand[] = new Card[8];

    public Player(String playerName, double bankroll)
     {
        this.playerName = playerName;
        this.bankroll = bankroll;
    }

    /*
        Add or deduct to player's bankroll
        @param amount, the amount to adjust account
        @param transType, true = add, false = deduct
    */
    public void bet(double amount, boolean transType)
    {
        if (transType)
        {
            this.bankroll += amount;
        }
        else
        {
            this.bankroll -= amount;
        }
    }

    /*
       Add a card to player's hand
        @param card, card to be added to hand
        @return true if player hand <= 21, false otherwise
    */

    public boolean addToHand (Card card, boolean hand)
    {
        if(hand)
        {
            this.playerHand[numCards] = card;
            this.numCards++;

            if(sumHand(true) <= 21) //return true if player hand less than 21
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            this.splitHand[numCards2] = card;
            this.numCards2++;

            if(sumHand(false) <= 21) //return true if player hand less than 21
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public int sumHand(boolean hand)
    {
        int sum = 0;                             //sum of player's hand
        int numAce = 0;                          //number of Ace's in player's hand

        if(hand)
        {
            //loop to sum values in player hand
            for (int i = 0; i < numCards; i++)
            {
                if(this.playerHand[i].getValue() == 1)
                {
                    sum += 11;
                    numAce++;
                }
                else
                {
                    sum += this.playerHand[i].getValue();
                }

                if((this.playerHand[i].getValue() == 1) && numAce > 1)           //if more than one ace in hand, all except one have to be 1
                {
                    sum -= 10 * (numAce - 1);       //subtract excess value from multiple aces
                }
            }

            if (sum > 21 && numAce > 0)            //if remaining ace causes a bust, change value to 1
            {
                sum -= 10;
            }
        }
        else
        {
            //loop to sum values in player hand
            for (int i = 0; i < numCards2; i++)
            {
                if(this.splitHand[i].getValue() == 1)
                {
                    sum += 11;
                    numAce++;
                }
                else
                {
                    sum += this.splitHand[i].getValue();
                }

                if((this.splitHand[i].getValue() == 1) && numAce > 1)           //if more than one ace in hand, all except one have to be 1
                {
                    sum -= 10 * (numAce - 1);       //subtract excess value from multiple aces
                }
            }

            if (sum > 21 && numAce > 0)            //if remaining ace causes a bust, change value to 1
            {
                sum -= 10;
            }
        }


        return sum;                             //return sum of hand
    }

    public void emptyHand()
    {
        for(int i = 0; i < numCards; i++)
        {
            this.playerHand[i] = null;
        }
        this.numCards = 0;

        for(int i = 0; i < numCards2; i++)
        {
            this.splitHand[i] = null;
        }
        this.numCards2 = 0;
    }

    //method splits player's hand into two separate hands
    public void splitHand()
    {
        //set new hand's first card equal to the second card of original hand
        splitHand[0] = playerHand[1];
        //set second element in original hand to null
        playerHand[1] = null;
        //increment number of cards in split hand
        numCards2++;
        //decrement number of cards in original hand
        numCards--;
    }

    public String getName()
    {
        return playerName;
    }

    public double getBankroll()
    {
        return bankroll;
    }

    public int getNumCards()
    {
        return numCards;
    }

    public int getCard(int index, boolean hand)
    {
        if(hand)
            return playerHand[index].getFace();
        else
            return splitHand[index].getFace();
    }

    public void printHand (boolean hand)
    {
        if(hand)
        {
            for (int i = 0; i < numCards; i++)
            {
                System.out.print(this.playerHand[i].toString());
            }
        }
        else
        {
            for (int i = 0; i < numCards2; i++)
            {
                System.out.print(this.splitHand[i].toString());
            }
        }

    }
}
