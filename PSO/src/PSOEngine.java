public class PSOEngine {
    
    //VALORES POR DEFECTO
    int numDimensions = 30;
    int numParticles = 30; 
    int maxIterations = 10000; 
    double c1 = 1.496180; 
    double c2 = 1.496180; 
    double w = 0.729844; 

    public PSOEngine (int numDimensions, int numParticles, int maxIterations, double c1, double c2, double w ) {
        this.numDimensions = numDimensions;
        this.numParticles = numParticles;
        this.maxIterations = maxIterations;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
    }


    public void initParticles(Particle[] particles) {
//Por cada particula
        for (int i=0; i<particles.length;i++) {
            double[] positions = new double[numDimensions];
            double[] velocities = new double [numDimensions];
            //VALORES ALEATORIOS Y VELOCIDAD EN 0
            for (int j=0; j<numDimensions; j++) {
                positions[j] = ((Math.random()* ((5.12-(-5.12)))) - 5.12);
                velocities[j] = 0;
            }
            //Crear particula
            particles[i] = new Particle(positions, velocities);
            //Asignar al las particulas el mejor personal
            particles[i].personalBest = particles[i].position.clone();
        }
    }

    public void updateVelocity(Particle particle, double[] best, double[] r1, double[] r2) {
        //VARIABLES TEMPORALES PARA CALCULAR
        double[] velocities = particle.velocity.clone();
        double[] personalBest = particle.personalBest.clone();
        double[] positions = particle.position.clone();
        double[] bestNeigh = best.clone();

        double[] inertiaTerm = new double[numDimensions];
        double[] difference1 = new double[numDimensions];
        double[] difference2 = new double[numDimensions];

        double[] c1Timesr1 = new double[numDimensions];
        double[] c2Timesr2 = new double[numDimensions];

        double[] cognitiveTerm = new double[numDimensions];
        double[] socialTerm = new double[numDimensions];

        //INERCIA
        for (int i=0; i<numDimensions; i++) {
            inertiaTerm[i] = w*velocities[i];
        }

        //COGNITIVO

        //Diferencia mejor vs actual
        for (int i=0; i<numDimensions; i++) {
            difference1[i] = personalBest[i] - positions[i];
        }

        //c1*r1
        for (int i=0; i<numDimensions; i++) {
            c1Timesr1[i] = c1*r1[i];
        }

        //c1*r1*diff = componente cognitivo
        for (int i=0; i<numDimensions; i++) {
            cognitiveTerm[i] = c1Timesr1[i]*difference1[i];
        }
        
        //SOCIAL

        //Diferencia mejor vs actual
        for (int i=0; i<numDimensions; i++) {
            difference2[i] = bestNeigh[i] - positions[i];
        }
        
        //c2*r2
        for (int i=0; i<numDimensions; i++) {
            c2Timesr2[i] = c2*r2[i];
        }
        //Calcular c2*r2*diff2 = componente social
        for (int i=0; i<numDimensions; i++) {
            socialTerm[i] = c2Timesr2[i]*difference2[i];
        }

        for (int i=0; i<numDimensions; i++) {
            particle.velocity[i] = inertiaTerm[i]+cognitiveTerm[i]+socialTerm[i];
        }
    }

    public void updatePosition(Particle particle) {
        for (int i=0; i<numDimensions; i++) {
            particle.position[i] = particle.position[i]+particle.velocity[i];
            
        }

    }

    
    public double[] findBest(Particle[] particles) {
        double[] best = null;
        double bestFitness = Double.MAX_VALUE;
        for(int i=0; i<numParticles; i++) {
            if (evaluateFitness(particles[i].personalBest)<= bestFitness) {
                bestFitness = evaluateFitness(particles[i].personalBest);
                best = particles[i].personalBest;
            }
        }
        return best;
    }

//OPTIMIZACION MEDIANTE FORMULA
    public double evaluateFitness(double[] positions) {
        double fitness = 0;
        for (int i=0; i<numDimensions; i++) {
            fitness = fitness + (Math.pow(positions[i],2)-(10*Math.cos(2*Math.PI*positions[i])));
        }

        fitness = fitness + (10*numDimensions);
        return fitness;
    }
}