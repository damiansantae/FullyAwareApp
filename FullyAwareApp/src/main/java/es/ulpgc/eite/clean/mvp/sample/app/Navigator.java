package es.ulpgc.eite.clean.mvp.sample.app;

import es.ulpgc.eite.clean.mvp.sample.dummy.Dummy;
public interface Navigator {
  void goToNextScreen(Dummy.DummyTo presenter);
}
