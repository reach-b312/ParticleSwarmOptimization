import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import javax.swing.JOptionPane; //ELIMINAR: Se va a solicitar durante implementacion 

public class PSOAgent extends Agent {
  @Override
  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new PSOBehaviour());
  } 

  private class PSOBehaviour extends Behaviour {
    int cont=0;
    @Override
    public void action() {
        PSOimplementation p = new PSOimplementation(); 
        cont+=1;
    } 
    @Override
    public boolean done() {
      if (cont == 1)
        return true;
      else
	return false;
    }
   @Override
    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}