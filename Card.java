public class Card
{
    // Card suits (provided for your convenience - use is optional)
    public static final int SPADES   = 0;
    public static final int HEARTS   = 1;
    public static final int CLUBS    = 2;
    public static final int DIAMONDS = 3;

    // Card faces (provided for your convenience - use is optional)
    public static final int ACE      = 1;
    public static final int TWO      = 2;
    public static final int THREE    = 3;
    public static final int FOUR     = 4;
    public static final int FIVE     = 5;
    public static final int SIX      = 6;
    public static final int SEVEN    = 7;
    public static final int EIGHT    = 8;
    public static final int NINE     = 9;
    public static final int TEN      = 10;
    public static final int JACK     = 11;
    public static final int QUEEN    = 12;
    public static final int KING     = 13;


    // define fields here
    private int suit;
    private int face;

    // This constructor builds a card with the given suit and face, turned face down.
    public Card(int cardSuit, int cardFace)
    {
        suit = cardSuit;
        face = cardFace;
    }

    // This method retrieves the suit (spades, hearts, etc.) of this card.
    public int getSuit()
    {
        if (suit < 0 || suit > 4)
            return -1;
        else
            return suit;
    }

    // This method retrieves the face (ace through king) of this card.
    public int getFace()
    {
        if (face < 1 || face > 13)
            return  -1;
        else
            return face;
    }

    // This method retrieves the numerical value of this card
    // (usually same as card face, except 1 for ace and 10 for jack/queen/king)
    public int getValue()
    {
        if (face < 1 || face > 13)
            return  -1;
        else if (face >= 1 && face <= 10)
            return face;
        else
            return 10;
    }

    public String toString()
    {
        String name[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String suitCard[] = {" \u2660", " \u2665", " \u2663", " \u2666"};

        String nameCard = "[" + name[face-1] + suitCard[suit] + "]" + "  ";

        return  nameCard;
    }
}
