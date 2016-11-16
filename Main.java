import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
	
	/*
	 * Find a 8 radius circle where there is no SlimeChunks.
	 * May need a lot of time to find the good location.
	 * -100 000seeds = 4billion chunks tested = ~5results.
	 * 
	 * Inspired by sethbling's filter:
	 */
		
	//The chunks that needs to be tested (a circle)
	ArrayList<Integer[]> chunks = new ArrayList<Integer[]>();
		
		for(int x = -128; x<=128; x++){
			
			for (int z = -128; z<=128; z++){
				
				if(x*x + z*z < 128*128){
					/*
					 * Makes sure that there is no duplicate.
					 */
					if(!HasChunks(new Integer[] {(int) x/ 16, (int) z/16}, chunks)){
						
						chunks.add(new Integer[] {(int) x/ 16, (int) z/16});
					}
				}
			}
		}
		
		
		/*
		 * The programm testd many chunks with many seeds in order to try to find the good location.
		 */
		for(long seed = 32000; seed<1000000L; seed++){
		 
			//from chunk(-100;-100) to chunk(100;100) which is 4.10^4 chunks^2.seed^-1
			for(int cx = -100; cx<=100; cx++){
				for (int cz = -100; cz<=100; cz++){
			
					boolean flag = true;
						//browse the chunks which are in the 8chunk circle radius.
						for(Integer[] ch: chunks){
							
							if(isSlimeChunk(seed, cx+ch[0], cz+ch[1])){
								
								flag = false;
								break;
							}
						}
						//no chunks contains slimechunks --> good location, we need to output the result.
						if(flag){
						System.out.println("Seed="+String.valueOf(seed)+" ,ChunkX="+cx+" ,ChunkZ"+cz);
						
						}
					
				}
			}
		}
		
	}
	
	public static boolean HasChunks(Integer[] chunk, ArrayList<Integer[]> chunks){
		/*
		 *Whether or not the list already contains the chunk.
		 */
		for(Integer[] ch: chunks){
			if(ch[0]==chunk[0] && ch[1]==chunk[1]){
				return true;
			}
		}
		
		return false;
	}
	
	
	public static boolean isSlimeChunk(long seed, int x, int z){
		/*
		 * Whether or not the chunk is a slime chunk. (chance to be a slimechunk = 0.1)
		 */
		long rnseed = seed 
			      + (long)(x * x * 0x4c1906) 
					+ (long)(x * 0x5ac0db) 
					+ (long)(z * z) * 0x4307a7L
					+(long)(z * 0x5f24f)^0x3ad8025f;
		Random rnd = new Random( rnseed);
		return rnd.nextInt(10)==0;
	}
}
