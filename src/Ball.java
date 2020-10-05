import java.awt.*;

/**
	Esta classe representa a bola usada no jogo. A classe principal do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {

	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola 
		(em pixels por milissegundo), mas não define a direção deste movimento. A direção do movimento é determinada 
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retângulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retângulo que a representa).
		@param width largura do retângulo que representa a bola.
		@param height altura do retângulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por milissegundo).
		@param sentidoX define o sentido atual da bola no eixo x.
		@param sentidoY define o sentido atual da bola no eixo Y.
	*/

	private double cx;
	private double cy;
	private double width;
	private double height;
	private Color color;
	private double speed;
	private double sentidoX;
	private double sentidoY;


	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.speed = speed;
		this.sentidoX = 1.0; 
		this.sentidoY = 1.0;
	}

	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/
	public void draw(){

		GameLib.setColor(Color.YELLOW);
		GameLib.fillRect(this.cx, this.cy, this.width, this.height);
	}

	/**
		Método que devolve a nova posição da bola após um tempo determinado
		
		@param cxy posição atual da bola nos eixos x ou y
		@param speed velocidade da bola
		@param delta quantidade de milissegundos que se passaram entre o ciclo anterior de atualização do jogo e o atual.
		@param sentidoxy sentido atual da bola nos eixos x ou y
	*/
	public double acelerar(double cxy, double speed, long delta, double sentidoxy) { 
		
		return cxy += (speed*delta) * sentidoxy;
	}


	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.
		
		@param delta quantidade de milissegundos que se passaram entre o ciclo anterior de atualização do jogo e o atual.
	*/
	public void update(long delta){

		this.cy = acelerar(cy, this.speed, delta, sentidoY);
		this.cx = acelerar(cx, this.speed, delta, sentidoX);
	}


	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.
	
		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/	
	public void onPlayerCollision(String playerId){
		
		if(playerId == "Player 1") {
			this.sentidoX = 1.0;
		} else {
			this.sentidoX = -1.0;
		}
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/
	public void onWallCollision(String wallId){    

		switch(wallId) {
			case "Bottom":
				this.sentidoY = -1.0;
				break;
			case "Top":
				this.sentidoY = 1.0;
				break;
			case "Right":
				this.sentidoX = -1.0;	
				break;
			case "Left":
				this.sentidoX = 1.0;
				break;
			default:
		}
	}


	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/
	public boolean checkCollision(Wall wall){

		// variaveis que definem as bordas
		double bordaAltura = this.height/2;
		double bordaLargura = this.width/2;

		// variaveis que definem os limites da área útil do jogo
		double limiteSuperior = ( wall.getCy() + (wall.getHeight() / 2) );
		double limiteEsquerdo = ( wall.getCx() + (wall.getWidth() / 2) ); 
		double limiteDireito = ( wall.getCx() - (wall.getWidth() / 2) );
		double limiteInferior = ( wall.getCy() - (wall.getHeight() / 2) ); 

		// verificacao de colisoes
		if( (getCy() - bordaAltura < limiteSuperior) &&
			(getCy() + bordaLargura > limiteInferior) &&
			(getCx() - bordaLargura < limiteEsquerdo) &&
			(getCx() + bordaAltura > limiteDireito) ) { 
			
			onWallCollision(wall.getId()); // passe o wallId pro onWallCollision
			return true;
		}

		return false;
	}

	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/	
	public boolean checkCollision(Player player){

		// verificacao de colisoes com players
		if( (getCy() - getHeight()/2 < player.getCy() + player.getHeight()/2 ) && 
			(getCy() + getHeight()/2 > player.getCy() - player.getHeight()/2 ) && 
			(getCx() - getWidth()/2 < player.getCx() + player.getWidth()/2 ) && 
			(getCx() + getWidth()/2  > player.getCx() - player.getWidth()/2 ) ) {

			onPlayerCollision(player.getId()); // passe o wallId pro onWallCollision
			return true;
		}

		return false;
	}


	/**
		Método que devolve a altura da bola
		@return o valor double da altura.

	*/
	public double getHeight() {
		return this.height;
	}

	/**
		Método que devolve a largura da bola.
		@return o valor double da largura.

	*/
	public double getWidth() {
		return this.width;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/
	public double getCx(){
		return this.cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/
	public double getCy(){
		return this.cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/
	public double getSpeed(){
		return this.speed;
	}
}
