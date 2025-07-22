package ch.makery.address.view

import ch.makery.address.MainApp
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class RootLayoutController():
  @FXML
  def handleClose(action: ActionEvent): Unit =
    System.exit(0)

  @FXML
  def handleDelete(action: ActionEvent): Unit =
    MainApp.personOverviewControl match{
      case Some(controller) => controller.handleDeletePerson(action)
      case None =>
    }