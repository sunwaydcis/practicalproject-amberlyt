package ch.makery.address

import ch.makery.address.model.Person
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import ch.makery.address.view.PersonEditDialogController
import scalafx.stage.Stage
import scalafx.stage.Modality

//Window Root Pane
var roots: Option[scalafx.scene.layout.BorderPane] = None
//stylesheet
var cssResource = getClass.getResource("view/DarkTheme.css")

object MainApp extends JFXApp3:

  var roots: Option[scalafx.scene.layout.BorderPane] = None
  
  /**
   * The data as an observable list of Persons.
   */
  val personData = new ObservableBuffer[Person]()

  /**
   * Constructor
   */
  personData += new Person("Hans", "Muster")
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")


  // ... THE REST OF THE CLASS ...


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
      val loader = FXMLLoader(resource)
      loader.load()
      val roots = loader.getRoot[jfxs.layout.AnchorPane]
      this.roots.get.center = roots

  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load();
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

  stage = new PrimaryStage():
    title = "AddressApp"
    scene = new Scene():
      stylesheets = Seq(cssResource.toExternalForm)
      root = roots.get