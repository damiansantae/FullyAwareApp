package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.os.Handler;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.GenericModel;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.realmDatabase.DatabaseFacade;

/**
 * Model of a task to do list.
 *
 * @author Damián Santamaría Eiranova
 * @author Iván González Hernández
 * @author Jordi Vílchez Lozano
 * @version 1.0, 28/05/2017
 */
public class ListToDoModelMaster extends GenericModel<ListToDoMaster.ModelToPresenter>
    implements ListToDoMaster.PresenterToModel {



    private DatabaseFacade database;
    private boolean taskRunning;
    private String toastBackConfirmation;


    /**
     * Method that recovers a reference to the PRESENTER
     * You must ALWAYS call {@link super#onCreate(Object)} here
     *
     * @param presenter Presenter interface
     */
    @Override
    public void onCreate(ListToDoMaster.ModelToPresenter presenter) {
        super.onCreate(presenter);
        database = DatabaseFacade.getInstance();
        toastBackConfirmation = "Press back again to close the FullyAware application";
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
    public String getToastBackConfirmation() {
        return toastBackConfirmation;
    }


    @Override
    public void backPressed() {
        if (taskRunning) {
            getPresenter().confirmBackPressed();
        } else {
            taskRunning = true;
            startDelayedTimer();
        }
    }

    private void startDelayedTimer() {
        getPresenter().delayedTaskToBackStarted();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (taskRunning) {
                    taskRunning = false;
                }

            }
        }, 5000);

    }


    /**
     * This method calculates 1 or 2 cases depending if the name of the subject
     * which is liked to the task is a compose name or not. Such as "Electronic Devices",
     * in this case, it will return "ED"
     *
     * @param subjectName is the subject name which is going to extract its case(s)
     * @return a String compose by 1 or 2 chars
     */
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

    @Override
    public List<Task> orderSubjects() {
        List<Task> taskFromDatabase = database.getToDoItemsFromDatabase();
        List<Subject> subjectsFromDatabase = database.getSubjectsFromDatabase();
        List<Task> orderedList = new LinkedList<>();

        for (Subject subject : subjectsFromDatabase) {

            for (int i = 0; i < taskFromDatabase.size(); i++) {
                Task currentTask = taskFromDatabase.get(i);
                if (currentTask.getSubject().equals(subject))
                    orderedList.add(currentTask);
            }
        }
        return orderedList;

    }

    @Override
    public boolean compareDateWithCurrent(String date) {
        boolean isTaskForgotten = false;

        String day = date.substring(0, 2);
        int intDay = Integer.parseInt(day);

        String month = date.substring(3, 5);
        int intMonth = Integer.parseInt(month) - 1;

        String year = date.substring(6, 10);
        int intYear = Integer.parseInt(year) - 1900;

        String hour = date.substring(13, 15);
        int intHour = Integer.parseInt(hour);

        String minutes = date.substring(16);
        int intMinutes = Integer.parseInt(minutes);

        Date deadlineDate = new Date(intYear, intMonth, intDay, intHour, intMinutes);

        Date currentDate = new Date();

        if (currentDate.after(deadlineDate)) {
            isTaskForgotten = true;
        }
        return isTaskForgotten;
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