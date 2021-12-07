import pt.isel.canvas.*
import kotlin.math.*
import kotlin.random.Random

fun main() {
    var game = Game(Spaceship(325,0,-10),emptyList<Enemy>())
    val arena = Canvas(700,500, BLACK)
    val tiroyInicial = 450
    onStart {
        arena.onMouseMove { mouse : MouseEvent ->
            game = Game(Spaceship(mouse.x, game.nave.tirox, game.nave.tiroy),game.enemy) //Altera o posicionamento segundo o eixo do x da nave
            arena.drawAll(game)
        }
        arena.onMouseDown { mouse : MouseEvent ->
            if (game.nave.tiroy < 0){ // Se o valor de tiroy == 0  significa que não existe tiro, logo o proximo a ser criado tem de ser com tiroy = 450 que é a linha inicial
                game = Game(Spaceship(game.nave.xCenter, game.nave.xCenter, 435),game.enemy) } //Altera o posicionamento segundo o eixo do x da nave

        }
    }

    onFinish { }

    arena.onTimeProgress(500){
        game = Game(game.nave,arena.createEnemy(game))
    }

    arena.onTimeProgress(25){
        if (game.nave.tiroy > 0)
            game = arena.drawAll(Game(Spaceship(game.nave.xCenter,game.nave.tirox, arena.positionShot(game.nave.tiroy)),game.enemy))
        else{
            game = Game(Spaceship(game.nave.xCenter, game.nave.xCenter, -10),game.enemy)
            game = arena.drawAll(game)
        }

    }

}

//function that draw a spaceship
fun Canvas.drawSapceship (xCenter : Int) {
    this.drawRect(xCenter,450,50,10, GREEN)
    this.drawRect(xCenter+23,450-5,4,5, YELLOW)
}

//function that sum offsetVector and tiroy
fun Canvas.positionShot (tiroy: Int) : Int {return (tiroy - 4)}


//function that draw a spaceship
fun Canvas.drawShot (tirox: Int, tiroy: Int) {
    drawRect(tirox,tiroy,4,7, WHITE)
}

//function that draw all the elements of game
fun Canvas.drawAll (game : Game) :  Game{
    erase()
    drawSapceship(game.nave.xCenter)
    if (game.nave.tirox > 0)
        drawShot(game.nave.tirox,game.nave.tiroy)

    val jogo = Game(game.nave,game.enemy.map{ Enemy(it.x,MoveEnemy(it),it.delta) })

    drawEnemy(jogo.enemy)

    return Game(game.nave,game.enemy.map{ Enemy(it.x,MoveEnemy(it),it.delta) })
}

fun Canvas.drawEnemy (lista : List<Enemy>){
    lista.forEach{
        drawRect(it.x,it.y,4,7, RED)
    }
}

/*fun Canvas.interception (game : Game) : Game{
    game.enemy.mapNotNull {

        if (it)
    }
}*/

fun Canvas.createEnemy (game : Game) : List<Enemy> { return  game.enemy + Enemy(Random.nextInt(0,700),0, Random.nextInt(1,4)) }

fun Canvas.MoveEnemy (enemy : Enemy) : Int{ return  enemy.y + enemy.delta }

//Dataclasses
data class Spaceship (val xCenter : Int, val tirox : Int, val tiroy : Int)
data class OffsetVector (val dx : Int, val dy : Int)
data class Enemy (val x: Int, val y :Int, val delta :Int)
data class Game (val nave :  Spaceship, val enemy : List<Enemy>)


//rescerver
//Guardar
//Desenhar