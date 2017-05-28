package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;


/**
 * Model of a task done list.
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public class ListDoneModelMaster extends GenericModel<ListDoneMaster.ModelToPresenter>
        implements ListDoneMaster.PresenterToModel {


    private DatabaseFacade database;
    private boolean taskRunning;


    /**
     * Method that recovers a reference to the PRESENTER
     * You must ALWAYS call {@link super#onCreate(Object)} here
     *
     * @param presenter Presenter interface
     */
    @Override
    public void onCreate(ListDoneMaster.ModelToPresenter presenter) {
        super.onCreate(presenter);
        database = DatabaseFacade.getInstance();

    }

    /**
     * Called by layer PRESENTER when VIEW pass for a reconstruction/destruction.
     * Usefull for kill/stop activities that could be running on the background Threads
     *
     * @param isChangingConfiguration Informs that a change is occurring on the configuration
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {

    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Presenter To Model //////////////////////////////////////////////////////////////

    @Override
    public String calculateCases(String subjectName) {

        char firstChar = 0;
        char secondChar;
        int actualPosition = 0;
        String result;
        for (int i = 0; i < subjectName.length(); i++) {
            char currentChar = subjectName.charAt(i);
            if (currentChar != ' ') {
                firstChar = currentChar;
                actualPosition = i + 1;
                break;
            }
        }
        if (isOneWord(subjectName)) {
            StringBuilder sb = new StringBuilder();
            sb.append(firstChar);
            result = sb.toString().toUpperCase();
            return result;
        } else {
            int spacePosition = 0;
            for (int j = actualPosition; j < subjectName.length(); j++) {

                if (subjectName.charAt(j) == ' ') {
                    spacePosition = j;
                }

            }
            secondChar = subjectName.charAt(spacePosition + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(firstChar);
        sb.append(secondChar);
        result = sb.toString();

        return result.toUpperCase();
    }


    private boolean isOneWord(String word) {
        int wordCount = 0;

        boolean isWord = false;
        int endOfLine = word.length() - 1;

        for (int i = 0; i < word.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(word.charAt(i)) && i != endOfLine) {
                isWord = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(word.charAt(i)) && isWord) {
                wordCount++;
                isWord = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(word.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        if (wordCount == 1) {
            return true;
        } else {
            return false;
        }

    }

}