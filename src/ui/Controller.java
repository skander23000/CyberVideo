package ui;

import beans.*;
import facade.ui.FacadeIHM;

import javax.swing.*;

/**
 * Controller pour le mode MVC
 */
public class Controller {

    FacadeIHM facadeIHM;
    JFrame frame;
    BasePage currentPage;
    Account currentAccount;
    enum State { IDLE, SIGNIN_NORMAL, SIGNUP_NORMAL, SIGNIN_FOR_RENT, SIGNUP_FOR_RENT, SIGNIN_FOR_SUBSCRIBE, SIGNUP_FOR_SUBSCRIBE, SIGNIN_FOR_RETURN_FILM, LOGGED_NORMAL, LOGGED_PREMIUM, CHANGE_ACCOUNT_SETTING, SHOW_TOP10W_NO_CONNECT, SHOW_TOP10M_NO_CONNECT, SHOW_FILMS_NO_CONNECT, SHOW_BLURAY_NO_CONNECT, SHOW_RESEARCH_RESULTS_NO_CONNECT, SHOW_FILM_DETAILS_NO_CONNECT, SHOW_TOP10W_CONNECT, SHOW_TOP10M_CONNECT, SHOW_FILMS_CONNECT, SHOW_BLURAY_CONNECT, SHOW_RESEARCH_RESULTS_CONNECT, SHOW_FILM_DETAILS_CONNECT, RENT_FILM, SHOW_VALIDATION_RENT, SHOW_ERROR_RENT, SHOW_VALIDATION_SUBSCRIBE, SHOW_RETURN_PAGE, SHOW_VALIDATION_RETURN_PAGE, SHOW_ERROR_RETURN_PAGE};
    State state = State.IDLE;

    public Controller(JFrame frame, FacadeIHM fihm) {
        this.frame = frame;
        facadeIHM = fihm;
        currentAccount = null;
    }

    public void traite(BasePage page, Keyword action){
        if(action==Keyword.LOGIN) { //Faire les cas ou l'utilisateur se login à cause d'une demande d'abonnement ou de location
            if(currentAccount instanceof NormalAccount)
                state = State.LOGGED_NORMAL;
            else if(currentAccount instanceof SubscriberAccount)
                state = State.LOGGED_PREMIUM;
            frame.remove(currentPage);
        }
        if(action==Keyword.SHOWLOGINPAGE) {
            showLoginPage();
            state = State.SIGNIN_NORMAL;
        }
        if(action==Keyword.SUBSCRIBE){
            switch (state) {
                case IDLE:
                    showLoginPage();
                    state = State.SIGNIN_FOR_SUBSCRIBE;
                    break;
                case LOGGED_NORMAL: 
                    //Voir s'il faut afficher une pop up pour informer l'utilisateur qu'il s'est bien abonné
                    facadeIHM.subscribeToService();
                    break;
                case LOGGED_PREMIUM:
                    //Show pop up ou page pour créditer sa carte abonnée
                default:
                    break;
            }
        }
        if(action==Keyword.RETURN_BLURAY){
            switch(state) {
                case IDLE:
                    showLoginPage();
                    state = State.SIGNIN_FOR_RETURN_FILM;
                    break;
                default:
                    break;
            }
        }
        if(action == Keyword.SHOWADVANCEDRESEARCH){
            switch(state){
                case IDLE :
                    state = State.SHOW_RESEARCH_RESULTS_NO_CONNECT;
                    break;
                case LOGGED_NORMAL :
                    state = State.SHOW_RESEARCH_RESULTS_CONNECT;
                    break;
                case LOGGED_PREMIUM :
                    state = State.SHOW_RESEARCH_RESULTS_CONNECT;
                    break;
                default:
                    break;
            }
            showResearchPage(Section.ADVANCED); //Vérifier le state !
        }
    }

    public void exitPage(BasePage page) {
        back();
    }

    public void showMainPage() {
        MainPage page = new MainPage(frame, this);
        page.setController(this);
        showPage(page);
    }


    public void showLoginPage() {
        LoginPage page = new LoginPage(frame);
        page.setController(this);
        showPage(page);
    }

    public void showSignupPage() {
        SignupPage page = new SignupPage(frame);
        page.setController(this);
        showPage(page);
    }

    public void showResearchPage(Section s){
        ResearchResults page = new ResearchResults(frame, s, ((MainPage) currentPage).getChosenCriterias());
        page.setController(this);
        showPage(page);
    }

    private void showPage(BasePage page) {
        if(currentPage!=null){
            currentPage.setVisible(false);
        }
        currentPage = page;
        currentPage.setVisible(true);
        frame.add(currentPage);
        frame.setVisible(true);
    }

    public FacadeIHM getFacadeIHM(){
        return facadeIHM;
    }

    public Account getCurrentAccount(){ //Trouver un moyen lorsque utilisateur non connecté de pouvoir avoir un Account par défaut pour faire les vérifications pour la MainPage
        if(currentAccount == null)
            return null;
        return currentAccount;
    }

    public void setAccount(Account acc){
        this.currentAccount = acc;
    }

    // sortie le current page
    private void back() {
        /*BasePage lastPage = pagePile.pop();
        if(lastPage!=null){
            frame.remove(lastPage);
        }
        curPage = pagePile.getLast();
        if(curPage!=null){
            System.out.println("Back to :["+curPage.getClass()+"]... pile_size:"+pagePile.size());
            curPage.setVisible(true);
        }*/
    }

    public void showFilm() {
        BasePage page = new Affichage_Film(frame);
        page.setController(this);
        showPage(page);
    }
}
