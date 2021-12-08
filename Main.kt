import pt.isel.canvas.*
import kotlin.math.*
import kotlin.random.Random

fun main() {
    var game = Game(Spaceship(325,0,-10),emptyList<Enemy>(),false, 0)
    val arena = Canvas(700,500, BLACK)
    onStart {
        arena.onMouseMove { mouse: MouseEvent ->
            if (mouse.x in 23..677) {
                if (!game.EndGame) {
                    game = Game(Spaceship(mouse.x - 23, game.nave.tirox, game.nave.tiroy), game.enemy, game.EndGame, game.points) //Altera o posicionamento segundo o eixo do x da nave
                    arena.drawAll(game)
                }
            }
        }

        arena.onMouseDown { mouse : MouseEvent ->
            if (game.nave.tiroy < 0 && !game.EndGame){ // Se o valor de tiroy == 0  significa que não existe tiro, logo o proximo a ser criado tem de ser com tiroy = 450 que é a linha inicial
                game = Game(Spaceship(game.nave.xCenter, game.nave.xCenter, 435),game.enemy, game.EndGame, game.points) } //Altera o posicionamento segundo o eixo do x da nave
        }
    }


    onFinish {

    }

    arena.onTimeProgress(500){
        game = Game(game.nave,arena.createEnemy(game), game.EndGame,game.points)
    }

    arena.onTimeProgress(25){
        if (game.nave.tiroy > 0)
            game = arena.drawAll(Game(Spaceship(game.nave.xCenter,game.nave.tirox, arena.positionShot(game.nave.tiroy)),game.enemy,game.EndGame,game.points))
        else{
            game = Game(Spaceship(game.nave.xCenter, game.nave.xCenter, -10),game.enemy,game.EndGame,game.points)
            game = arena.drawAll(game)
        }
        println(game.points)

    }

}

//function that draw a spaceship
fun Canvas.drawSapceship (xCenter : Int) {
    this.drawRect(xCenter,450,50,10, GREEN)
    this.drawRect(xCenter + 23,450-5,4,5, YELLOW)
}

//function that sum offsetVector and tiroy
fun Canvas.positionShot (tiroy: Int) : Int {return (tiroy - 4)}


//function that draw a spaceship
fun Canvas.drawShot (tirox: Int, tiroy: Int) {
    drawRect(tirox + 23,tiroy,4,7, WHITE)
}

//function that draw all the elements of game
fun Canvas.drawAll (game : Game) :  Game{
    val jogo = Game(game.nave,game.enemy.map{Enemy(it.x,MoveEnemy(it),it.delta) },game.EndGame,game.points)

    if (!interceptionShip(jogo).EndGame){
        erase()
        val jogoFinal =  drawEnemy(jogo)
        if (jogoFinal.nave.tirox > 0)
            drawShot(jogoFinal.nave.tirox,jogoFinal.nave.tiroy)

        drawSapceship(jogo.nave.xCenter)
        drawText(0,30, "Pontos ${jogoFinal.points}", YELLOW, 20)
        return jogoFinal
    }
    else {
        drawText(250,240, "GAME", YELLOW, 70)
        drawText(255,310,"OVER", YELLOW, 70)
        return Game(game.nave, game.enemy, true,game.points)
    }

}



fun Canvas.drawEnemy (jogo : Game) : Game{
    val game = interception(jogo)
    game.enemy.forEach{
        drawRect(it.x,it.y,4,7, RED)
    }
    return game
}

fun Canvas.interception (game : Game) : Game{
    //tiro
    val shotLength =  7
    val shotWith = 4
    val delta = 23
    val xi = game.nave.tirox + delta
    val xf = game.nave.tirox + shotWith + delta
    val yi = game.nave.tiroy
    val yf = game.nave.tiroy + shotLength
    val jogo = Game(game.nave, game.enemy.mapNotNull {if (it.x in xi-2..xf+2 && it.y in yi..yf) null else it},game.EndGame,game.points)
    return Game(jogo.nave, jogo.enemy,jogo.EndGame,jogo.points +  (game.enemy.size - jogo.enemy.size)*10)

}

fun Canvas.interceptionShip (game: Game) : Game{
    val nave = game.nave.xCenter
    game.enemy.forEach{
        if (it.x in nave..nave+50 && it.y in 450..460)
            return Game(game.nave,game.enemy,true,game.points)
    }

    return Game(game.nave,game.enemy,game.EndGame,game.points)
}


fun Canvas.createEnemy (game : Game) : List<Enemy> { return  game.enemy + Enemy(Random.nextInt(0,700),0, Random.nextInt(1,4)) }


fun Canvas.MoveEnemy (enemy : Enemy) : Int { return  enemy.y + enemy.delta }

//Dataclasses
data class Spaceship (val xCenter : Int, val tirox : Int, val tiroy : Int)
data class OffsetVector (val dx : Int, val dy : Int)
data class Enemy (val x: Int, val y :Int, val delta :Int)
data class Game (val nave :  Spaceship, val enemy : List<Enemy>, val EndGame : Boolean, val points : Int) {
}

data class BoundingBox (val connerx : Int, val connery : Int, val width : Int, val length : Int)