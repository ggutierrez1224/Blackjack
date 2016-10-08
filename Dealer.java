public class Dealer
{
    private String dealerName;
    private int numOfCards;
    private Card dealerHand[] = new Card [8];

    public Dealer()
    {
        this.dealerName = "Dealer";
    }

    public boolean addToHand (Card card)
    {

            this.dealerHand[numOfCards] = card;
            this.numOfCards++;

            if(sumHand() <= 21)
            {
                return true;
            }
            else
            {
                return false;
            }



    }

    public int sumHand()
    {
        int sum = 0;                             //sum of player's hand
        int numAce = 0;                          //number of Ace's in player's hand

        // loop to count number of aces in player hand
        for (int j = 0; j < numOfCards; j++)
        {
            if(this.dealerHand[j].getFace() == 1)
            {
                numAce++;
            }
        }
        //loop to sum values in dealer hand
        for (int i = 0; i < numOfCards; i++)
        {
            sum += this.dealerHand[i].getValue();
            if(numAce > 1)                      //if more than one ace in hand, all except one have to be 1
            {
                sum -= 10 * (numAce - 1);       //subtract excess value from multiple aces
            }
        }

        if (sum > 21 && numAce > 0)            //if remaining ace causes a bust, change value to 1
        {
            sum -= 10;
        }

        return sum;                             //return sum of hand
    }

    public void emptyHand()
    {
        for(int i = 0; i < numOfCards; i++)
        {
            this.dealerHand[i] = null;
        }
        this.numOfCards = 0;
    }

    public String getDealerName()
    {
        return dealerName;
    }

    public int getHand(int index)
    {
        return dealerHand[index].getFace();
    }

    public void printHand (boolean showFirst)
    {
      for (int i = 0; i < numOfCards; i++)
      {
          if(i == 0 && showFirst == false)
          {
              System.out.print("[?]");
          }
          else
          {
              System.out.print(this.dealerHand[i].toString());
          }
      }
    }
}




