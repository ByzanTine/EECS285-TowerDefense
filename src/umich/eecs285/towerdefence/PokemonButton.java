package umich.eecs285.towerdefence;

public class PokemonButton extends TowerDefense_Button {
  private int ID;

  public PokemonButton(String PressedImage, String defaultImage, int id) {
    super(PressedImage, defaultImage);

    
    ID = id;
  }

  public void setID(int id) {
    ID = id;
  }
  
  public int getID() {
    return ID;
  }
  
  
 
}
