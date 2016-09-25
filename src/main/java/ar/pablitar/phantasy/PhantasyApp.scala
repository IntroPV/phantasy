package ar.pablitar.phantasy

import com.uqbar.vainilla.DesktopGameLauncher
import com.uqbar.vainilla.Game
import java.awt.Dimension
import ar.pablitar.vainilla.commons.math.Vector2D

class PhantasyGame extends Game {

  def getDisplaySize(): Dimension = {
    new Dimension(PhantasyApp.SCREEN_WIDTH, PhantasyApp.SCREEN_HEIGHT)
  }

  def getTitle(): String = {
    "Phantasy"
  }

  def initializeResources(): Unit = {
    
  }

  def setUpScenes(): Unit = {
    this.setCurrentScene(new PhantasyScene)
  }
}

object PhantasyApp extends App {
  val SCREEN_WIDTH = 1024
  val SCREEN_HEIGHT = 768
  new DesktopGameLauncher(new PhantasyGame).launch() 

  def screenCenter = {
    Vector2D(SCREEN_WIDTH / 2 , SCREEN_HEIGHT / 2)
  }
}