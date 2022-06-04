
import javax.swing.JOptionPane;

public class PSOimplementation {
        /*
        //Valores arbitrarios
	public final int numDimensions = 30; //Numero de dimensiones
	public final int numParticles = 30; //Numero de particulas
	public final int maxIterations = 10000; //Numero maximo de iteraciones
	//Valores sugeridos por van den bergh
        public final double c1 = 1.496180; //Coeficiente cognitivo
	public final double c2 = 1.496180; //Factor social
	public final double w = 0.729844; //Inercia
        */
        //JOptionPane
        String userInput = JOptionPane.showInputDialog("Numero de dimensiones, particulas y maximo de iteraciones separados por un espacio");
        String [] mlrX = userInput.split(" ");
        //END JOptionPane
        public final int numDimensions = Integer.parseInt(mlrX[0]); //Numero de dimensiones
	public final int numParticles = Integer.parseInt(mlrX[1]); //Numero de particulas
	public final int maxIterations = Integer.parseInt(mlrX[2]); //Numero maximo de iteraciones
        String userInput2 = JOptionPane.showInputDialog("Coeficiente cognitivo, factor social e inercia (entre 0 y 1) separados por un espacio");
        String [] mlrY = userInput.split(" ");

	//Valores sugeridos por van den bergh
        public final double c1 = Double.parseDouble(mlrY[0]); //Coeficiente cognitivo
	public final double c2 = Double.parseDouble(mlrY[1]); //Factor social
	public final double w = Double.parseDouble(mlrY[2]); //Inercia


	public  double[] r1; 
	public  double[] r2; 
	public double[] best;
	Particle[] particles; //Arreglo para todas las particulas
	
	public PSOimplementation() {
		//Algoritmo
		particles = new Particle[numParticles];
		PSOEngine PSO = new PSOEngine(numDimensions, numParticles, maxIterations, c1, c2, w);

		//Initializacion
		PSO.initParticles(particles);

		//Bucle de PSO
		int numIter = 0;
		while (numIter<maxIterations) {
			// Evalua el ajuste de cada particula
			for (int i=0; i<numParticles; i++) {
				particles[i].fitness = PSO.evaluateFitness(particles[i].position);

				//actualiza el mejor personal
				if (particles[i].fitness <= PSO.evaluateFitness(particles[i].personalBest)) {
					particles[i].personalBest = particles[i].position.clone();
				}
			}
			//Marca la mejor particula del conjunto
			best = PSO.findBest(particles);

			//Inicializa los vectores con valores aleatorios
			r1 = new double[numDimensions];
			r2 = new double[numDimensions];
			for (int i=0; i<numDimensions; i++) {
				r1[i] = Math.random();
				r2[i] = Math.random();
			}

			//Actualiza la velocidad y la posicion
			for (int i=0; i<numParticles;i++) {
				PSO.updateVelocity(particles[i], best, r1, r2);
				PSO.updatePosition(particles[i]);
                                System.out.print("\nPosicion \n");
                                print(particles[i].position);
                                System.out.print("\nVelocidad \n");
                                for(int j=0; j<particles[i].velocity.length; j++){
                                System.out.println();
                                System.out.print(particles[i].velocity[j]);
                                }
                                
			}
                        System.out.print("\nMejor global\n");
                        print(best);
                        System.out.print("\nFin de iteracion\n");
			numIter++;
		}
		//Imprime en pantalla la mejor solucion
		print((best));
		System.out.println(PSO.evaluateFitness(best));
	}


	public void print (double[] a) {
		System.out.print("< ");
		for (int i=0; i<a.length; i++) {
			System.out.print(a[i]  + " ");
		}
		System.out.println(" > ");
	}

}