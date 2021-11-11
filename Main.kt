import pt.isel.canvas.*
import kotlin.math.*

fun main(){
    onStart {
        val arena = Canvas(900,500, WHITE)
        val raio = 60

        val xCenter = ((arena.width)/2).toInt(); val yCenter = ((arena.height)/2).toInt()
        arena.drawArc(xCenter , yCenter,raio,0,180, GREEN)//Arco da cabeça
        arena.drawLine(xCenter  ,yCenter ,(xCenter - (((raio+0.3*raio).toInt())/2)) ,(yCenter - (((sqrt(3.0))*(raio+0.3*raio))/2)).toInt(), GREEN,(0.08*raio).toInt()) // antena direita
        arena.drawLine(xCenter  ,yCenter ,(xCenter + (((raio+0.3*raio).toInt())/2)),(yCenter - (((sqrt(3.0))*(raio+0.3*raio))/2)).toInt(), GREEN,(0.08*raio).toInt()) // antena esquerda
        arena.drawCircle((xCenter - 0.44*raio).toInt() , (yCenter - 0.44*raio).toInt() ,(0.1*raio).toInt(),WHITE) //Olho esquerdo
        arena.drawCircle((xCenter + 0.44*raio).toInt() , (yCenter - 0.44*raio).toInt() ,(0.1*raio).toInt(),WHITE)//Olho direito
        arena.drawCircle((xCenter - (((60+0.3*raio).toInt())/2)),(yCenter - (((sqrt(3.0))*(60+0.3*raio))/2)).toInt(),(0.04*raio).toInt(), GREEN) // arredondar antena
        arena.drawCircle((xCenter + (((60+0.3*raio).toInt())/2)),(yCenter - (((sqrt(3.0))*(60+0.3*raio))/2)).toInt(),(0.04*raio).toInt(), GREEN)//arredondar antena
        var head = Head(xCenter, yCenter,60)
        val yto =((((sqrt(3.0))*(head.raio+0.3*head.raio))/2)).toInt()

        arena.onKeyPressed {
                K: KeyEvent ->
                when(K.code)
                {
                    LEFT_CODE -> {
                        head = arena.Desenhar(head.xCenter - 4,head.yCenter ,head.raio)
                    }
                    UP_CODE -> {
                        head = arena.Desenhar(head.xCenter,head.yCenter - 4,head.raio)
                    }
                    DOWN_CODE -> {
                        head = arena.Desenhar(head.xCenter,head.yCenter + 4,head.raio)
                    }
                    RIGHT_CODE ->{
                        head = arena.Desenhar(head.xCenter + 4,head.yCenter,head.raio)
                    }
                    521 ->{
                        head = arena.Desenhar(head.xCenter,head.yCenter,head.raio + 1 )
                    }
                    '-'.code ->{
                        head = arena.Desenhar(head.xCenter,head.yCenter,head.raio - 1)
                    }
                }
        }

        arena.onMouseDown {
            me :MouseEvent ->
                head = arena.Desenhar(me.x,me.y,head.raio)
        }

    }
    onFinish {

    }
}

fun Canvas.Desenhar (xCenter:Int,yCenter:Int,radius:Int) : Head{
    this.erase()
    val raio = radius
    this.drawArc(xCenter , yCenter,raio,0,180, GREEN)//Arco da cabeça

    this.drawLine(xCenter ,yCenter,(xCenter - (((raio+0.3*raio).toInt())/2)) ,(yCenter - (((sqrt(3.0))*(raio+0.3*raio))/2)).toInt(), GREEN,(0.08*raio).toInt()) // antena direita
    this.drawLine(xCenter ,yCenter,(xCenter + (((raio+0.3*raio).toInt())/2)) ,(yCenter - (((sqrt(3.0))*(raio+0.3*raio))/2)).toInt(), GREEN,(0.08*raio).toInt()) // antena esquerda

    this.drawCircle((xCenter - 0.44*raio).toInt() , (yCenter - 0.44*raio).toInt() ,(0.1*raio).toInt(),WHITE) //Olho esquerdo
    this.drawCircle((xCenter + 0.44*raio).toInt() , (yCenter - 0.44*raio).toInt() ,(0.1*raio).toInt(),WHITE)//Olho direito

    val head = Head(xCenter,yCenter,raio)
    return head
}

data class Head (val xCenter: Int, val yCenter: Int, val raio: Int)







































//Metodo onKeyPressed
//SE for para se mexer então
//Detetar se é um click continuo ou unico
//Função para apagar a cabeça -> fun Erase()
//Função desenho de cabeça mais à direita/esquerda
//SE for para aumentar
//Função para apagar a cabeça > fun Erase()
//Função para desenhar a cabeça maior

//Pegar nos dados de cada classe e enviar para uma função que altere esses dados criando outra classe igual