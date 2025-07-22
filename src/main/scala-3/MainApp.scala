package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.view.{PersonEditDialogController, PersonOverviewController}
import javafx.fxml.FXMLLoader
import javafx.scene as jfxs
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp3:

  var roots: Option[scalafx.scene.layout.BorderPane] = None
  var personOverviewControl: Option[PersonOverviewController] = None
  // ✅ declare controller

  val personData = new ObservableBuffer[Person]()
  personData ++= Seq(
    Person("Hans", "Muster"),
    Person("Ruth", "Mueller"),
    Person("Heinz", "Kurz"),
    Person("Cornelia", "Meier"),
    Person("Werner", "Meyer"),
    Person("Lydia", "Kunz"),
    Person("Anna", "Best"),
    Person("Stefan", "Meier"),
    Person("Martin", "Mueller")
  )

  override def start(): Unit =
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      scene = new Scene():
        root = roots.get

    showPersonOverview()

  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val overviewRoot = loader.getRoot[javafx.scene.layout.AnchorPane]
    personOverviewControl = Some(loader.getController[PersonOverviewController])
    roots.get.center = overviewRoot


  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PersonEditDialogController]

    val dialog = new Stage():
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene:
        root = roots2

    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    control.okClicked
