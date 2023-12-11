package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import beans.*;

public class SubscriberNavbar extends JPanel {
    private JButton leftMenu;
    private JLabel currentLocations;
    private JLabel currentMoney;
    private JButton basket;
    private JButton rightMenu;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public SubscriberNavbar(Account account, int nbReservations){
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40)); //Mettre une constante pour avoir la taille de la fenetre ?
        this.setLayout(new GridLayout(1, 5));
        //this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
        leftMenu = new JButton("Menu", new ImageIcon("src/ui/Images/menu.png"));
        currentLocations = new JLabel(nbReservations + " locations en cours",JLabel.CENTER); //Avoir le nombre de location avec la BD
        float amount = 0;
        if(account instanceof SubscriberAccount){
            List<SubscriberCard> listCard = ((SubscriberAccount)account).getSubscriberCards();
            if(listCard!=null && !listCard.isEmpty()){
                amount = listCard.get(0).getAmount();
            }
        }
        currentMoney = new JLabel(amount + " euros restants", JLabel.CENTER); //Il faut avoir l'information sur l'argent depuis la DAO !
        basket = new JButton("Panier", new ImageIcon("src/ui/Images/panier.png"));
        rightMenu = new JButton(account.getUser().getFirstName());
        this.add(leftMenu);
        this.add(currentLocations);
        this.add(currentMoney);
        this.add(basket);
        this.add(rightMenu);
    }

    public JButton getLeftMenu() {
        return leftMenu;
    }

    public JButton getRightMenu() {
        return rightMenu;
    }

    public JButton getBasket() {
        return basket;
    }
    
}
