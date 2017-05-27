package es.ulpgc.eite.clean.mvp.sample;

import org.junit.Test;

import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoModelMaster;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelListTest {

    @Test
    public void getCasesTwoWordsCapital_isCorrect() throws Exception {
        ListToDoModelMaster modelMaster = new ListToDoModelMaster();
        assertEquals("DA", modelMaster.calculateCases("Diseño de Aplicaciones"));
    }
    @Test
    public void getCasesTwoWordsWithoutCapital_isCorrect() throws Exception {
        ListToDoModelMaster modelMaster = new ListToDoModelMaster();
        assertEquals("DA", modelMaster.calculateCases("dfiseño de aplicaciones"));
    }

    @Test
    public void getCasesOneWordCapital_isCorrect() throws Exception {
        ListToDoModelMaster modelMaster = new ListToDoModelMaster();
        assertEquals("E", modelMaster.calculateCases("English"));
    }

    @Test
    public void getCases1WordsWithoutCapital_isCorrect() throws Exception {
        ListToDoModelMaster modelMaster = new ListToDoModelMaster();
        assertEquals("E", modelMaster.calculateCases("english"));
    }

}