package umich.eecs285.towerdefence;

public class CreatePokemonButton extends TowerDefense_Button {

  private boolean isClicked;

  public CreatePokemonButton(String PressedImage, String defaultImage) {
    super(PressedImage, defaultImage);
    
    isClicked = false;
    
  }

  public void setIsClicked(boolean isClicked) {
    this.isClicked = isClicked;

  }

  public boolean IsClicked() {
    return isClicked;
 
  }
  
}