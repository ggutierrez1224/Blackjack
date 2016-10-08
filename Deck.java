import java.util.Random;

public class Deck
{
    // Card array that will be used as game deck
    private Card cards[] = new Card[52];
    //array to hold cards that are face up (to be used for hint system)
    private Card cardsUsed[] = new Card[52];
    //holds the number of cards remaining in deck
    private double cardsRemaining = 52;
    //holds number of cards in cardsUsed array
    private int cardsInUsed = 0;


    // This constructor builds a deck of 52 cards.
    public Deck()
    {
        int count = 0;

        for (int i = 0; i < 4; i++)
        {
            for (int j = 1; j <= 13; j++)
            {
                cards[count] = new Card(i,j);
                count++;
            }
        }
        shuffle();
    }


    // This method takes the top card off the deck and returns it.
    public Card deal(boolean addToUsed)
    {
        if(addToUsed)
        {
            Card next = cards[0];
            for (int i = 1; i < cards.length; i++)
            {
                cards[i-1] = cards[i];
            }
            cardsRemaining--;
            cards[cards.length-1] = null;
            cardsUsed[cardsInUsed] = next;
            cardsInUsed++;
            return next;
        }
        else
        {
            Card next = cards[0];
            for (int i = 1; i < cards.length; i++)
            {
                cards[i-1] = cards[i];
            }
            cardsRemaining--;
            cards[cards.length-1] = null;
            return next;
        }
    }


    // this method returns true if there are no more cards to deal, false otherwise
    public boolean isEmpty()
    {
       if (cards[0] == null)
           return true;
        else
           return  false;
    }

    public double getCardsRemaining()
    {
        return cardsRemaining;
    }
    public Card[] getCardsUsed()
    {
        return cardsUsed;
    }
    //this method puts the deck int some random order
    public void shuffle()
    {
        System.out.println("Shuffling deck!");
        Random ranNum = new Random();
        Card temp;
        int x;

        for (int i = 0; i< cards.length - 1; i++)
        {
            x = ranNum.nextInt(cards.length);
            temp = cards[i];
            cards[i] = cards[x];
            cards[x] = temp;
        }
    }
}
