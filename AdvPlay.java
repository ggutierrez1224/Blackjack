import java.text.DecimalFormat;

public class AdvPlay
{
    private int splitSum;

    public void doubleDown (Player a, Deck b)
    {
        boolean bust;

        bust = a.addToHand(b.deal(true), true);
        if (!bust)
        {
            a.printHand(true);
            System.out.println("\nYou bust! \nScore: " + a.sumHand(true));
        }
        //else player's score is <= 21
        else
        {
            a.printHand(true);
            System.out.println("New score: " + a.sumHand(true));
        }
    }

    public int split(Player a, Deck b)
    {
        Card used[];
        double remaining;
        String hitStand;
        boolean bust;

        //call method to split player's hand
        a.splitHand();

        a.printHand(false);

        used = b.getCardsUsed();
        //get number of cards remaining in deck
        remaining = b.getCardsRemaining();
        //call to hint system method to give player hint before choosing to hit or stand
        hintSystem(a, used, remaining, false);

        System.out.println("\n(" + a.getName() + ") Enter 'Hit' or 'Stand': ");
        hitStand = IO.readString();
        //error check
        while(!(hitStand.toLowerCase().equals("hit") || hitStand.toLowerCase().equals("stand")))
        {
            System.err.print("Invalid input. Please re-enter choice: ");
            hitStand = IO.readString();
        }
        while (hitStand.toLowerCase().equals("hit")) //continues to add card to hand while user inputs 'hit'
        {
            bust = a.addToHand(b.deal(true), false);
            //if addToHand() returns false, player busts
            if (!bust)
            {
                a.printHand(false);
                System.out.println("\nYou bust! \nScore: " + a.sumHand(false));
                hitStand = "stand";
            }
            //else player's score is <= 21
            else
            {
                a.printHand(false);
                System.out.println("New score: " + a.sumHand(false));
                used = b.getCardsUsed();
                //get number of cards remaining in deck
                remaining = b.getCardsRemaining();
                //call to hint system method to give player hint before choosing to hit or stand
                hintSystem(a, used, remaining, false);
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
        splitSum = a.sumHand(false);
        return splitSum;
    }

    public void insurance(Player a[], double b[], boolean valTen)
    {
        if(valTen)
        {
            for(int i = 0; i < a.length; i++)
            {
                a[i].bet(b[i], true);
            }
        }
        else
        {
            for(int i = 0; i < a.length; i++)
            {
                a[i].bet(b[i], false);
            }
        }

    }

    public void hintSystem(Player a, Card used[], double remaining, boolean hand) //hand variable is true if hand is original hand or split hand
    {
        int count[] = new int[14];
        int faceVal, sum, diff;
        double get21, bust,bustCount;
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("\n****** HINT ******");

        //get score of player and store to sum
        sum = a.sumHand(hand);
        //subtract the sum from 21 to find how much is left until 21
        diff = 21 - sum;
        //if the difference between 21 and the player's sum is 0, player already has 21 and does not need hint
        if(diff == 0)
        {
            System.out.println("Player already has 21!");
        }
        //else the difference between 21 and the player's sum is > 0
        else
        {
            //populate count array with 0's
            for (int i = 0; i < count.length - 1; i++)
            {
                count[i] = 0;
            }

            //fill count array with a value of one if that card corresponding to that element is face up
            for (int i = 0; i < used.length - 1; i++)
            {
                if (used[i] == null)
                    break;
                faceVal = used[i].getFace();
                count[faceVal-1]++;
            }

            //if diff is > 10, the is 0 probability to bust on the nex card
            if(diff > 10)
            {
                bust = 0;
            }
            // else if diff = 1, player probability to bust relies on ace
            else if(diff == 1)
            {
                //get # of face-up aces and subtract from remaining cards in deck
                //divide that by total remaining cards in deck
                bust = (remaining - (4 - count[0]))/remaining;
            }
            //calculate prob to bust
            else
            {
                bustCount = ((13 - (diff)) * 4);
                for(int i = diff; i < count.length-1; i++ )
                {
                    if(count[i] > 0)
                        bustCount -= count[i];
                }
                bust = (bustCount/remaining) * 100;
            }
                //calculate prob to get 21 and output probabilities
                if (diff >= 1 && diff <= 10)
                {
                    get21 = ((4 - count[diff-1])/remaining) * 100;
                    System.out.print("(" + a.getName() + ") " + diff + " is needed to get 21!\n" + ("(" + a.getName() + ") " + " Probability to get 21: "));
                    System.out.println(df.format(get21) + "%");
                    System.out.print("(" + a.getName() + ") " + " Probability to bust if you hit: ");
                    System.out.println(df.format(bust) + "%");
                }
                //if diff = 11, prob of getting 21 is based on Ace
                else if (diff == 11)
                {
                    get21 = ((4-count[0])/ remaining) * 100;
                    System.out.print(("(" + a.getName() + ") " + " Ace is needed to get 21! \n" + ("(" + a.getName() + ")" + " Probability to get Ace: ")));
                    System.out.println(df.format(get21) + "%");
                    System.out.print("(" + a.getName() + ") " + " Probability to bust if you hit: ");
                    System.out.println(df.format(bust) + "%");
                }
                else
                {
                    System.out.println("(" + a.getName() + ") " + " Next card will not cause you to bust!");
                }
        }
        System.out.println("******************");
    }
}
