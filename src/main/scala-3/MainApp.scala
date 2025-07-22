package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.view.{PersonEditDialogController, PersonOverviewController}
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}


object MainApp extends JFXApp3:

  //Window Root Pane
  var roots: Option[scalafx.scene.layout.BorderPane] = None
  //stylesheet
  var cssResource = getClass.getResource("view/DarkTheme.css")

  var personOverviewControl: Option[PersonOverviewController] = None

  // The data as an observable list of Persons.
  val personData = new ObservableBuffer[Person]()
  // Constructor
  personData += new Person("Hans", "Muster")
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")

  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource)
    // Load root layout from fxml file.
    loader.load()

    // retrieve the root component BorderPane from the FXML
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      icons += new Image(getClass.getResource("/images/Pog-Face-PNG-Image.png").toExternalForm)
      scene = new Scene():
        stylesheets = Seq(cssResource.toExternalForm)
        root = roots.get

    // call to display PersonOverview when app start
    showPersonOverview()
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    personOverviewControl = Option(loader.getController[PersonOverviewController])
    this.roots.get.center = roots

  val aString = new StringProperty("sunway")
  val bString = new StringProperty("monash")

  bString <==> aString

  aString.onChange((a, b, c) => {
    println("aString changed from " + b + " to " + c)
  })

  aString.onChange((_, _, c) => {
    println(c + " is updated in aString")
  })

  aString.value = "segi"
  bString.value = "monash"
  println(aString.value)
  println(bString.value)


  val add: (Int, Int) => Int = (a: Int, b: Int) =>
    a + b

  println(add(1, 2))

  extension (value: Int)
    def area: Double = 3.142 * value * value

  println(5.area)

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
        stylesheets = Seq(cssResource.toExternalForm)
        root = roots2

    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    control.okClicked